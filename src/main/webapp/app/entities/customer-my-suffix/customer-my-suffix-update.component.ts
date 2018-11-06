import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICustomerMySuffix } from 'app/shared/model/customer-my-suffix.model';
import { CustomerMySuffixService } from './customer-my-suffix.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-customer-my-suffix-update',
    templateUrl: './customer-my-suffix-update.component.html'
})
export class CustomerMySuffixUpdateComponent implements OnInit {
    customer: ICustomerMySuffix;
    isSaving: boolean;

    users: IUser[];
    createTime: string;
    updateTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private customerService: CustomerMySuffixService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ customer }) => {
            this.customer = customer;
            this.createTime = this.customer.createTime != null ? this.customer.createTime.format(DATE_TIME_FORMAT) : null;
            this.updateTime = this.customer.updateTime != null ? this.customer.updateTime.format(DATE_TIME_FORMAT) : null;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.customer.createTime = this.createTime != null ? moment(this.createTime, DATE_TIME_FORMAT) : null;
        this.customer.updateTime = this.updateTime != null ? moment(this.updateTime, DATE_TIME_FORMAT) : null;
        if (this.customer.id !== undefined) {
            this.subscribeToSaveResponse(this.customerService.update(this.customer));
        } else {
            this.subscribeToSaveResponse(this.customerService.create(this.customer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerMySuffix>>) {
        result.subscribe((res: HttpResponse<ICustomerMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
