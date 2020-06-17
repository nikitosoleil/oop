import {KeycloakAuthGuard, KeycloakService} from 'keycloak-angular';
import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';

@Injectable()
export class AppAuthGuard extends KeycloakAuthGuard {
    constructor(protected router: Router, protected keycloakAngular: KeycloakService) {
        super(router, keycloakAngular);
    }

    public doLogout(): void {
        this.keycloakAngular.logout('http://localhost:4200/').then();
    }

    public isStillLoggedIn(): boolean {
        console.log(this.authenticated);
        if (this.authenticated === undefined) {
            return false;
        }
        return this.authenticated;
    }

    isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
        return new Promise(async (resolve) => {
            if (!this.authenticated) {
                this.keycloakAngular.login();
                return;
            }
            console.log('role restriction at app-routing.module for this route', route.data.roles);
            console.log('User roles coming after login from keycloak :', this.roles);
            const requiredRoles = route.data.roles;
            let granted = false;
            if (!requiredRoles || requiredRoles.length === 0) {
                granted = true;
            } else {
                for (const requiredRole of requiredRoles) {
                    if (this.roles.indexOf(requiredRole) > -1) {
                        granted = true;
                        break;
                    }
                }
            }

            if (granted === false) {
                this.router.navigateByUrl('/');
            }

            resolve(granted);
        });
    }
}
