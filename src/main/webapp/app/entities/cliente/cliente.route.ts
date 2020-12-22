import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Authority } from './../../shared/constants/authority.constants';
import { IUser } from './../../core/user/user.model';
import { User } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { ClienteComponent } from './cliente.component';
import { ClienteDetailComponent } from './detail/cliente-detail.component';
import { ClienteUpdateComponent } from './new-edit/cliente-update.component';

@Injectable({ providedIn: 'root' })
export class ClienteResolve implements Resolve<IUser> {
    constructor(private service: UserService) {}
  
    resolve(route: ActivatedRouteSnapshot): Observable<IUser> {
      const id = route.params['login'];
      if (id) {
        return this.service.find(id);
      }
      return of(new User());
    }
  }

export const clienteRoute: Routes = [
    {
        path: '',
        component: ClienteComponent,
        data: {
            authorities: [Authority.USER],
            defaultSort: 'id,asc',
            pageTitle: 'arqueoprojectApp.cliente.home.title',
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: ':id/view',
        component: ClienteDetailComponent,
        resolve: {
            cliente: ClienteResolve,
        },
        data: {
            authorities: [Authority.USER],
            pageTitle: 'arqueoprojectApp.cliente.home.title',
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'new',
        component: ClienteUpdateComponent,
        resolve: {
            cliente: ClienteResolve,
        },
        data: {
            authorities: [Authority.USER],
            pageTitle: 'arqueoprojectApp.cliente.home.title',
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: ':id/edit',
        component: ClienteUpdateComponent,
        resolve: {
            cliente: ClienteResolve,
        },
        data: {
            authorities: [Authority.USER],
            pageTitle: 'arqueoprojectApp.cliente.home.title',
        },
        canActivate: [UserRouteAccessService],
    },
];
