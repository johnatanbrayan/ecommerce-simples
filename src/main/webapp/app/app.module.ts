import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import './vendor';
import { EcommercesimplesSharedModule } from 'app/shared/shared.module';
import { EcommercesimplesCoreModule } from 'app/core/core.module';
import { EcommercesimplesAppRoutingModule } from './app-routing.module';
import { EcommercesimplesHomeModule } from './home/home.module';
import { EcommercesimplesEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { SidebarComponent } from './layouts/sidebar/sidebar.component';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import * as moment from 'moment';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    EcommercesimplesSharedModule,
    EcommercesimplesCoreModule,
    EcommercesimplesHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    EcommercesimplesEntityModule,
    EcommercesimplesAppRoutingModule,
    CurrencyMaskModule,
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    SidebarComponent,
  ],
  bootstrap: [MainComponent],
})
export class EcommercesimplesAppModule {
  constructor(private dpConfig: NgbDatepickerConfig) {
    this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
  }
}
