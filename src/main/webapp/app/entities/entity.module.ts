import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.EcommercesimplesClienteModule),
      },
      {
        path: 'categoria',
        loadChildren: () => import('./categoria/categoria.module').then(m => m.EcommercesimplesCategoriaModule),
      },
    ]),
  ],
})
export class EcommercesimplesEntityModule {}
