import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRacfUser } from 'app/shared/model/racf-user.model';

type EntityResponseType = HttpResponse<IRacfUser>;
type EntityArrayResponseType = HttpResponse<IRacfUser[]>;

@Injectable({ providedIn: 'root' })
export class RacfUserService {
  public resourceUrl = SERVER_API_URL + 'api/racf-users';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/racf-users';

  constructor(protected http: HttpClient) {}

  create(racfUser: IRacfUser): Observable<EntityResponseType> {
    return this.http.post<IRacfUser>(this.resourceUrl, racfUser, { observe: 'response' });
  }

  update(racfUser: IRacfUser): Observable<EntityResponseType> {
    return this.http.put<IRacfUser>(this.resourceUrl, racfUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRacfUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRacfUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRacfUser[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
