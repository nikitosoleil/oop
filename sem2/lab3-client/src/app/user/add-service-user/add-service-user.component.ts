import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {UserProfileComponent} from '../user-profile/user-profile.component';
import {User} from '../../model/user.model';

@Component({
    selector: 'app-add-service-user',
    templateUrl: './add-service-user.component.html',
    styleUrls: ['./add-service-user.component.css']
})
export class AddServiceUserComponent implements OnInit {


    constructor(public dialogRef: MatDialogRef<UserProfileComponent>,
                @Inject(MAT_DIALOG_DATA) public data: User) {
    }

    ngOnInit(): void {
    }

}
