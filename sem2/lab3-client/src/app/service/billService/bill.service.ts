import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Bill} from '../../model/bill.model';

@Injectable({
    providedIn: 'root'
})
export class BillService {

    constructor(private http: HttpClient) {
    }

    getBillsByUser(email: string): Observable<Bill[]> {
        return this.http.get<Bill[]>(environment.billService + '/' + email);
    }

    addBillForUser(email: string): Observable<Bill> {
        return this.http.post<Bill>(environment.billService + '/' + email, null);
    }

    payBill(bill: Bill): Observable<Bill> {
        return this.http.patch<Bill>(environment.billService + '/' + bill.id + '/pay', bill);
    }
}