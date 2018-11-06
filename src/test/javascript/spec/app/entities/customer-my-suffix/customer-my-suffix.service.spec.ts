/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CustomerMySuffixService } from 'app/entities/customer-my-suffix/customer-my-suffix.service';
import { ICustomerMySuffix, CustomerMySuffix, Level, IntentionProduct, Source } from 'app/shared/model/customer-my-suffix.model';

describe('Service Tests', () => {
    describe('CustomerMySuffix Service', () => {
        let injector: TestBed;
        let service: CustomerMySuffixService;
        let httpMock: HttpTestingController;
        let elemDefault: ICustomerMySuffix;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CustomerMySuffixService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CustomerMySuffix(
                0,
                'AAAAAAA',
                Level.A,
                'AAAAAAA',
                IntentionProduct.HOUSING,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                Source.CALL,
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                false
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createTime: currentDate.format(DATE_TIME_FORMAT),
                        updateTime: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a CustomerMySuffix', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createTime: currentDate.format(DATE_TIME_FORMAT),
                        updateTime: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createTime: currentDate,
                        updateTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CustomerMySuffix(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CustomerMySuffix', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        level: 'BBBBBB',
                        phone: 'BBBBBB',
                        intentionProduct: 'BBBBBB',
                        resistance: 'BBBBBB',
                        intentionPrice: 'BBBBBB',
                        intentionSpace: 'BBBBBB',
                        source: 'BBBBBB',
                        demandArea: 'BBBBBB',
                        visitNumber: 1,
                        homeAddress: 'BBBBBB',
                        profession: 'BBBBBB',
                        remark: 'BBBBBB',
                        createTime: currentDate.format(DATE_TIME_FORMAT),
                        updateTime: currentDate.format(DATE_TIME_FORMAT),
                        delFlag: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createTime: currentDate,
                        updateTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of CustomerMySuffix', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        level: 'BBBBBB',
                        phone: 'BBBBBB',
                        intentionProduct: 'BBBBBB',
                        resistance: 'BBBBBB',
                        intentionPrice: 'BBBBBB',
                        intentionSpace: 'BBBBBB',
                        source: 'BBBBBB',
                        demandArea: 'BBBBBB',
                        visitNumber: 1,
                        homeAddress: 'BBBBBB',
                        profession: 'BBBBBB',
                        remark: 'BBBBBB',
                        createTime: currentDate.format(DATE_TIME_FORMAT),
                        updateTime: currentDate.format(DATE_TIME_FORMAT),
                        delFlag: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createTime: currentDate,
                        updateTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a CustomerMySuffix', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
