import {Component, Input, OnInit} from '@angular/core';
import {BillService} from '../../service/billService/bill.service';
import {Observable} from 'rxjs';
import {Bill} from '../../model/bill.model';
import {UserService} from '../../service/userService/user.service';
import {User} from '../../model/user.model';

@Component({
    selector: 'app-user-bill-list',
    templateUrl: './user-bill-list.component.html',
    styleUrls: ['./user-bill-list.component.css']
})
export class UserBillListComponent implements OnInit {
    @Input() bills: Observable<Bill[]>;
    @Input() user: User;

    constructor(private billService: BillService) {
    }

    ngOnInit(): void {
        this.load();
    }

    load() {
        this.bills = this.billService.getBillsByUser(this.user.email);
    }
}
