import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {User} from '../../model/user.model';
import {Observable} from 'rxjs';
import {Bill} from '../../model/bill.model';
import {BillService} from '../../service/billService/bill.service';
import {UserService} from '../../service/userService/user.service';
import {UserListComponent} from '../user-list/user-list.component';

@Component({
    selector: 'app-user-dialog',
    templateUrl: './user-dialog.component.html',
    styleUrls: ['./user-dialog.component.css']
})
export class UserDialogComponent implements OnInit {
    bills: Observable<Bill[]>;

    constructor(
        public dialogRef: MatDialogRef<UserListComponent>,
        @Inject(MAT_DIALOG_DATA) public data: User,
        private billService: BillService,
        private userService: UserService) {
    }

    ngOnInit(): void {
        this.bills = this.billService.getBillsByUser(this.data.email);
    }

    addBill() {
        this.billService.addBillForUser(this.data.email).subscribe(
            _ => this.dialogRef.close(),
            err => {
                alert(err.message);
                console.log(err);
            }
        );
    }

    blockUser() {
        this.data.blocked = !this.data.blocked;
        this.userService.updateUserBlocked(this.data).subscribe(
            _ => this.dialogRef.close(),
            err => {
                alert(err.message);
                console.log(err);
            }
        );
    }
}
