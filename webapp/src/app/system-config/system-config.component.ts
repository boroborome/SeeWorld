import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';
import { SystemConfig } from '../model/system-config';
import { SystemConfigService } from '../api/system-config.service';
import { arrayToString, stringToArray } from '../utils';
import 'rxjs/add/operator/switchMap';
import { ToasterService } from 'angular2-toaster';

class SystemConfigWrapper {
  config: SystemConfig;
  domainScopeUI: string;

  constructor(config: SystemConfig) {
    this.config = config;
    console.log('config:' + JSON.stringify(config));
    this.dataToUI();
  }

  uiToData() : SystemConfig {
    this.config.domainScope = stringToArray(this.domainScopeUI);
    return this.config;
  }
  dataToUI() {
    this.domainScopeUI = arrayToString(this.config.domainScope);
  }
}

@Component({
  selector: 'system-config',
  templateUrl: 'system-config.component.html',
  styleUrls: [ 'system-config.component.css' ],
})
export class SystemConfigComponent implements OnInit {
  wrapper: SystemConfigWrapper;

  constructor(
    private configService: SystemConfigService,
    private route: ActivatedRoute,
    private _toasterService: ToasterService,
    private location: Location
  ) {}
  ngOnInit(): void {
    this.route.params
      .switchMap((params: Params) => this.configService.getConfig())
      .subscribe(config => {
        this.wrapper = new SystemConfigWrapper(config);
      });
  }

  goBack(): void {
    this.location.back();
  }
  save(): void {
    console.log('configs:' + JSON.stringify(this.wrapper));
    this.configService.updateConfig(this.wrapper.uiToData())
      .then(() => {
        this._toasterService.pop({
          type: 'success',
          title: 'Success',
          body: 'Save Config success',
        });
        // console.log('Save Success');
        // this.goBack()
      });
  }
}
