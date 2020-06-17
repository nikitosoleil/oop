import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {getUser, User} from '../../model/user.model';
import {KeycloakService} from 'keycloak-angular';
import {UserService} from '../../service/userService/user.service';
import {RegistrationService} from '../../service/registrationService/registration.service';
import {map} from 'rxjs/operators';
import {Bill} from '../../model/bill.model';
import {MatDialog} from '@angular/material/dialog';
import {AddServiceUserComponent} from '../add-service-user/add-service-user.component';
import {BillService} from '../../service/billService/bill.service';

@Component({
    selector: 'app-user-profile',
    templateUrl: './user-profile.component.html',
    styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
    userData: User;
    bills: Observable<Bill[]>;

    constructor(private keycloakAngular: KeycloakService,
                private userService: UserService,
                private registrationService: RegistrationService,
                private billService: BillService,
                public dialog: MatDialog) {
    }

    ngOnInit(): void {
        try {
            this.keycloakAngular.loadUserProfile(true).then(
                data => {
                    const user = getUser(Number(data.id), data.email, data.firstName, data.lastName, null);
                    this.userData = user;
                    this.registrationService.registerUser(user).subscribe(
                        _ => {
                            this.userService.setCurrentUsr(user);
                            this.loadBills();
                            return user;
                        },
                        err => {
                            console.log(err);
                            alert(err.message);
                        }
                    );
                },
                reason => {
                    console.log(reason);
                }
            );
        } catch (e) {
            console.log('Failed to load user details');
        }
    }

    loadBills() {
        this.bills = this.billService.getBillsByUser(this.userData.email);
    }

    addService(): void {
        const dialogRef = this.dialog.open(
            AddServiceUserComponent,
            {
                width: '30em'
            }
        );

        dialogRef.afterClosed().subscribe(
            _ => this.loadBills()
        );
    }
}
