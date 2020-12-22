import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcommercesimplesSharedModule } from 'app/shared/shared.module';

import { PasswordStrengthBarComponent } from './password/password-strength-bar.component';
import { RegisterComponent } from './register/register.component';
import { ActivateComponent } from './activate/activate.component';
import { PasswordComponent } from './password/password.component';
import { PasswordResetInitComponent } from './password-reset/init/password-reset-init.component';
import { PasswordResetFinishComponent } from './password-reset/finish/password-reset-finish.component';
import { SettingsComponent } from './settings/settings.component';
import { accountState } from './account.route';

import { NgxViacepModule } from '@brunoc/ngx-viacep';
import { TextMaskModule } from 'angular2-text-mask';

@NgModule({
  imports: [EcommercesimplesSharedModule, RouterModule.forChild(accountState), TextMaskModule, NgxViacepModule, BsDatepickerModule.forRoot()],
  declarations: [
    ActivateComponent,
    RegisterComponent,
    PasswordComponent,
    PasswordStrengthBarComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SettingsComponent,
  ],
})
export class AccountModule {}
