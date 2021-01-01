import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.EcommercesimplesClienteModule)
      }
    ]),
  ],
})
export class EcommercesimplesEntityModule {}
