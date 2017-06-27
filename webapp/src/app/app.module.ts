import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';

import { AppRoutingModule }     from './app-routing.module';

import { AppComponent }  from './app.component';
import { DashboardComponent } from './dashboard.component';
import { HeroesComponent }     from './heroes.component';
import { HeroDetailComponent } from './hero-detail.component';
import { HeroService }         from './hero.service';
import {SystemConfigComponent} from "./system-config/system-config.component";
import {SystemConfigService} from "./api/system-config.service";
import { ToasterModule } from 'angular2-toaster';

@NgModule({
  imports:      [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    ToasterModule,
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    HeroDetailComponent,
    HeroesComponent,
    SystemConfigComponent
  ],
  providers: [
    HeroService,
    SystemConfigService
  ],
  bootstrap:    [ AppComponent ]
})

export class AppModule { }
