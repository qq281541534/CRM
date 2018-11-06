import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICustomerMySuffix } from 'app/shared/model/customer-my-suffix.model';

type EntityResponseType = HttpResponse<ICustomerMySuffix>;
type EntityArrayResponseType = HttpResponse<ICustomerMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class CustomerMySuffixService {
    public resourceUrl = SERVER_API_URL + 'api/customers';

    constructor(private http: HttpClient) {}

    create(customer: ICustomerMySuffix): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(customer);
        return this.http
            .post<ICustomerMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(customer: ICustomerMySuffix): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(customer);
        return this.http
            .put<ICustomerMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICustomerMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICustomerMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(customer: ICustomerMySuffix): ICustomerMySuffix {
        const copy: ICustomerMySuffix = Object.assign({}, customer, {
            createTime: customer.createTime != null && customer.createTime.isValid() ? customer.createTime.toJSON() : null,
            updateTime: customer.updateTime != null && customer.updateTime.isValid() ? customer.updateTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        res.body.updateTime = res.body.updateTime != null ? moment(res.body.updateTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((customer: ICustomerMySuffix) => {
            customer.createTime = customer.createTime != null ? moment(customer.createTime) : null;
            customer.updateTime = customer.updateTime != null ? moment(customer.updateTime) : null;
        });
        return res;
    }
}
