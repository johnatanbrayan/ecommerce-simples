import { EcommercesimplesSharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PagamentoComponent } from './pagamento.component';
import { PagamentoDetailComponent } from './pagamento-detail.component';
import { PagamentoUpdateComponent } from './pagamento-update.component';
import { PagamentoDeleteDialogComponent } from './pagamento-delete-dialog.component';
import { pagamentoRoute } from './pagamento.route';

@NgModule({
  imports: [EcommercesimplesSharedModule, RouterModule.forChild(pagamentoRoute)],
  declarations: [PagamentoComponent, PagamentoDetailComponent, PagamentoUpdateComponent, PagamentoDeleteDialogComponent],
  entryComponents: [PagamentoDeleteDialogComponent],
})
export class EcommercesimplesPagamentoModule {}
