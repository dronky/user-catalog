import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IZosSystem } from 'app/shared/model/zos-system.model';

type EntityResponseType = HttpResponse<IZosSystem>;
type EntityArrayResponseType = HttpResponse<IZosSystem[]>;

@Injectable({ providedIn: 'root' })
export class ZosSystemService {
  public resourceUrl = SERVER_API_URL + 'api/zos-systems';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/zos-systems';

  constructor(protected http: HttpClient) {}

  create(zosSystem: IZosSystem): Observable<EntityResponseType> {
    return this.http.post<IZosSystem>(this.resourceUrl, zosSystem, { observe: 'response' });
  }

  update(zosSystem: IZosSystem): Observable<EntityResponseType> {
    return this.http.put<IZosSystem>(this.resourceUrl, zosSystem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IZosSystem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IZosSystem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IZosSystem[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
