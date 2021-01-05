import { EcommercesimplesSharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClienteComponent } from './cliente.component';
// import { ClienteDetailComponent } from './detail/cliente-detail.component';
import { ClienteUpdateComponent } from './new-edit/cliente-update.component';
import { ClienteDeleteDialogComponent } from './delete/cliente-delete-dialog.component';
import { clienteRoute } from './cliente.route';

// CUSTOM LIBRARIES
import { NgSelectModule } from '@ng-select/ng-select';
import { TextMaskModule } from 'angular2-text-mask';
import { NgxViacepModule } from '@brunoc/ngx-viacep';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';

const ENTITY_STATES = [...clienteRoute];
@NgModule({
  imports: [
    EcommercesimplesSharedModule,
    RouterModule.forChild(ENTITY_STATES),
    TextMaskModule,
    NgxViacepModule,
    BsDatepickerModule.forRoot(),
    NgSelectModule,
  ],
  declarations: [
    ClienteComponent,
    // ClienteDetailComponent,
    ClienteUpdateComponent,
    ClienteDeleteDialogComponent,
  ],
  entryComponents: [ClienteDeleteDialogComponent],
})
export class EcommercesimplesClienteModule {}
