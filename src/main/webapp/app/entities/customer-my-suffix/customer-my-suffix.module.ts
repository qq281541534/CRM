import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CrmSharedModule } from 'app/shared';
import { CrmAdminModule } from 'app/admin/admin.module';
import {
    CustomerMySuffixComponent,
    CustomerMySuffixDetailComponent,
    CustomerMySuffixUpdateComponent,
    CustomerMySuffixDeletePopupComponent,
    CustomerMySuffixDeleteDialogComponent,
    customerRoute,
    customerPopupRoute
} from './';

const ENTITY_STATES = [...customerRoute, ...customerPopupRoute];

@NgModule({
    imports: [CrmSharedModule, CrmAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CustomerMySuffixComponent,
        CustomerMySuffixDetailComponent,
        CustomerMySuffixUpdateComponent,
        CustomerMySuffixDeleteDialogComponent,
        CustomerMySuffixDeletePopupComponent
    ],
    entryComponents: [
        CustomerMySuffixComponent,
        CustomerMySuffixUpdateComponent,
        CustomerMySuffixDeleteDialogComponent,
        CustomerMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CrmCustomerMySuffixModule {}
