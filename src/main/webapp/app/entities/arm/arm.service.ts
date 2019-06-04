import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IArm } from 'app/shared/model/arm.model';

type EntityResponseType = HttpResponse<IArm>;
type EntityArrayResponseType = HttpResponse<IArm[]>;

@Injectable({ providedIn: 'root' })
export class ArmService {
  public resourceUrl = SERVER_API_URL + 'api/arms';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/arms';

  constructor(protected http: HttpClient) {}

  create(arm: IArm): Observable<EntityResponseType> {
    return this.http.post<IArm>(this.resourceUrl, arm, { observe: 'response' });
  }

  update(arm: IArm): Observable<EntityResponseType> {
    return this.http.put<IArm>(this.resourceUrl, arm, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArm[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArm[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
