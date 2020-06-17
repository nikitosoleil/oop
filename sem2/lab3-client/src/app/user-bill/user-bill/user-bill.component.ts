import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Bill} from '../../model/bill.model';
import {BillService} from '../../service/billService/bill.service';
import {User} from '../../model/user.model';
import {UserService} from '../../service/userService/user.service';

@Component({
    selector: 'app-user-bill',
    templateUrl: './user-bill.component.html',
    styleUrls: ['./user-bill.component.css']
})
export class UserBillComponent implements OnInit {
    @Input() bill: Bill;
    @Output() paid = new EventEmitter<boolean>();
    user: User;

    constructor(private billService: BillService,
                private userService: UserService) {
    }

    ngOnInit(): void {
        this.user = this.userService.getCurrentUser();
    }

    pay() {
        this.billService.payBill(this.bill).subscribe(
            _ => this.paid.emit(true)
        );
    }

}
