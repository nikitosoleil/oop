import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {KeycloakService} from 'keycloak-angular';
import {UserService} from './service/userService/user.service';
import {User} from './model/user.model';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    currentUser: User;

    constructor(private router: Router,
                private userService: UserService,
                private keycloakAngular: KeycloakService) {
    }

    ngOnInit(): void {
        const roles = this.keycloakAngular.getUserRoles();
        if (roles.includes('admin')) {
            this.router.navigateByUrl('/admin_profile');
        } else if (roles.includes('client')) {
            this.router.navigateByUrl('/user_profile');
        }
    }
}

