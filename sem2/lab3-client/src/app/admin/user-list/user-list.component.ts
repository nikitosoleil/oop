import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {UserDialogComponent} from '../user-dialog/user-dialog.component';
import {User} from '../../model/user.model';
import {Observable} from 'rxjs';
import {UserService} from '../../service/userService/user.service';

@Component({
    selector: 'app-user-list',
    templateUrl: './user-list.component.html',
    styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
    users: Observable<User[]>;

    constructor(public dialog: MatDialog,
                private userService: UserService) {
    }

    ngOnInit(): void {
        this.users = this.userService.getAllUsers();
    }

    openUserDialog(user: User) {
        this.dialog.open(
            UserDialogComponent,
            {
                width: '30em',
                data: user
            }
        );
    }

}
