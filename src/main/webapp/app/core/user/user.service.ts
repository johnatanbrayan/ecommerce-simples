import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Pagination } from 'app/shared/util/request-util';
import { IUser } from './user.model';
import { ICliente } from './user.model';
import { DATE_FORMAT } from '../../shared/constants/input.constants';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<IUser>;
type EntityArrayResponseType = HttpResponse<IUser[]>;

@Injectable({ providedIn: 'root' })
export class UserService {
  public resourceUrl = SERVER_API_URL + 'api/users';

  constructor(private http: HttpClient) {}

  create(user: IUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(user);
    return this.http
      .post<IUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(user: IUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(user);
    return this.http
      .put<IUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  updateAssociadoByTelaAssociado(user: IUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(user);
    return this.http
      .put<IUser>(`${this.resourceUrl}/update-associado`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(login: string): Observable<EntityResponseType> {
    return this.http
      .get<IUser>(`${this.resourceUrl}/${login}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findByRole(role: string, req?: Pagination, firstName?: string): Observable<EntityArrayResponseType> {
    let options;
    if (firstName) {
      options = createRequestOption({ ...req, firstName });
    } else {
      options = createRequestOption(req);
    }

    return this.http
      .get<IUser[]>(`${this.resourceUrl}/findByRole/${role}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  findCriteria(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUser[]>(this.resourceUrl + '/filtro', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(login: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${login}`, { observe: 'response' });
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities');
  }

  /* -------------------- Formatacao-Data -------------------- */
  protected convertDateFromClient(user: IUser): IUser {
    const cliente = user.cliente;
    if (cliente && cliente.dataNascimento) {
      const copy: ICliente = Object.assign({}, cliente, {
        dataNascimento:
          cliente.dataNascimento != null && cliente.dataNascimento.isValid() ? cliente.dataNascimento.format(DATE_FORMAT) : null,
      });
      user.cliente = copy;
      return user;
    }
    return user;
  }
  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body && res.body.cliente) {
      res.body.cliente.dataNascimento = res.body.cliente.dataNascimento != null ? moment(res.body.cliente.dataNascimento) : undefined;
    }
    return res;
  }
  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body && res.body) {
      res.body.forEach((user: IUser) => {
        if (user.cliente) {
          user.cliente.dataNascimento = user.cliente.dataNascimento != null ? moment(user.cliente.dataNascimento) : undefined;
        }
      });
    }
    return res;
  }
}
