import { Moment } from 'moment';

export const enum Level {
    A = 'A',
    B = 'B',
    C = 'C'
}

export const enum IntentionProduct {
    HOUSING = 'HOUSING',
    APARTMENT = 'APARTMENT',
    COMMERCE = 'COMMERCE'
}

export const enum Source {
    CALL = 'CALL',
    INITIATIVE = 'INITIATIVE',
    LEAFLET = 'LEAFLET'
}

export interface ICustomerMySuffix {
    id?: number;
    name?: string;
    level?: Level;
    phone?: string;
    intentionProduct?: IntentionProduct;
    resistance?: string;
    intentionPrice?: string;
    intentionSpace?: string;
    source?: Source;
    demandArea?: string;
    visitNumber?: number;
    homeAddress?: string;
    profession?: string;
    remark?: string;
    createTime?: Moment;
    updateTime?: Moment;
    delFlag?: boolean;
    userId?: number;
}

export class CustomerMySuffix implements ICustomerMySuffix {
    constructor(
        public id?: number,
        public name?: string,
        public level?: Level,
        public phone?: string,
        public intentionProduct?: IntentionProduct,
        public resistance?: string,
        public intentionPrice?: string,
        public intentionSpace?: string,
        public source?: Source,
        public demandArea?: string,
        public visitNumber?: number,
        public homeAddress?: string,
        public profession?: string,
        public remark?: string,
        public createTime?: Moment,
        public updateTime?: Moment,
        public delFlag?: boolean,
        public userId?: number
    ) {
        this.delFlag = this.delFlag || false;
    }
}
