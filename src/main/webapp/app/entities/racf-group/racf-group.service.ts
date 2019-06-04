import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRacfGroup } from 'app/shared/model/racf-group.model';

type EntityResponseType = HttpResponse<IRacfGroup>;
type EntityArrayResponseType = HttpResponse<IRacfGroup[]>;

@Injectable({ providedIn: 'root' })
export class RacfGroupService {
  public resourceUrl = SERVER_API_URL + 'api/racf-groups';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/racf-groups';

  constructor(protected http: HttpClient) {}

  create(racfGroup: IRacfGroup): Observable<EntityResponseType> {
    return this.http.post<IRacfGroup>(this.resourceUrl, racfGroup, { observe: 'response' });
  }

  update(racfGroup: IRacfGroup): Observable<EntityResponseType> {
    return this.http.put<IRacfGroup>(this.resourceUrl, racfGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRacfGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRacfGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRacfGroup[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
