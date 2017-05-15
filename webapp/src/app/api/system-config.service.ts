import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { SystemConfig } from '../model/system-config';
import { HTTP_HEADERS } from '../const-values'
@Injectable()
export class SystemConfigService {
  configUrl = '/api/system-config';

  constructor(private http: Http) { }

  getConfig(): Promise<SystemConfig> {
  return this.http.get(this.configUrl)
             .toPromise()
             .then(response => {
                return response.json() as SystemConfig})
             .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  updateConfig(config: SystemConfig): Promise<SystemConfig> {
    return this.http
      .put(this.configUrl, JSON.stringify(config), {headers: HTTP_HEADERS})
      .toPromise()
      .then(() => config)
      .catch(this.handleError);
  }
}
