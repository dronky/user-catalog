import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IArmIp } from 'app/shared/model/arm-ip.model';

type EntityResponseType = HttpResponse<IArmIp>;
type EntityArrayResponseType = HttpResponse<IArmIp[]>;

@Injectable({ providedIn: 'root' })
export class ArmIpService {
  public resourceUrl = SERVER_API_URL + 'api/arm-ips';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/arm-ips';

  constructor(protected http: HttpClient) {}

  create(armIp: IArmIp): Observable<EntityResponseType> {
    return this.http.post<IArmIp>(this.resourceUrl, armIp, { observe: 'response' });
  }

  update(armIp: IArmIp): Observable<EntityResponseType> {
    return this.http.put<IArmIp>(this.resourceUrl, armIp, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArmIp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArmIp[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArmIp[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
