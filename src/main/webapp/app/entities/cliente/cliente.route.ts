import { HttpResponse } from '@angular/common/http';
import { flatMap } from 'rxjs/operators';
import { IUser, User } from 'app/core/user/user.model';
import { UserService } from './../../core/user/user.service';
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ClienteComponent } from './cliente.component';
import { ClienteDetailComponent } from './detail/cliente-detail.component';
import { ClienteUpdateComponent } from './new-edit/cliente-update.component';

@Injectable({ providedIn: 'root' })
export class ClienteResolve implements Resolve<IUser> {
  constructor(private service: UserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUser> {
    const id = route.params['login'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cliente: HttpResponse<User>) => {
          if (cliente.body) {
            return of(cliente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new User());
  }
}

export const clienteRoute: Routes = [
  {
    path: '',
    component: ClienteComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'ecommercesimplesApp.cliente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':login/view',
    component: ClienteDetailComponent,
    resolve: {
      cliente: ClienteResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'ecommercesimplesApp.cliente.home.title',
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
      authorities: [Authority.ADMIN],
      pageTitle: 'ecommercesimplesApp.cliente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':login/edit',
    component: ClienteUpdateComponent,
    resolve: {
      cliente: ClienteResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'ecommercesimplesApp.cliente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
