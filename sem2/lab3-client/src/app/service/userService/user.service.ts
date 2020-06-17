import {Injectable} from '@angular/core';
import {AppAuthGuard} from '../AppAuthGuard';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../../model/user.model';
import {environment} from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private appAuthGuard: AppAuthGuard,
                private http: HttpClient) {
    }

    getCurrentUser(): User {
        return JSON.parse(localStorage.getItem('currentUser'));
    }

    setCurrentUsr(user: User) {
        localStorage.setItem('currentUser', JSON.stringify(user));
    }

    logout() {
        if (this.appAuthGuard.isStillLoggedIn()) {
            this.appAuthGuard.doLogout();
            localStorage.clear();
        }
    }

    getAllUsers(): Observable<User[]> {
        return this.http.get<User[]>(environment.userService);
    }

    updateUserBlocked(user: User): Observable<User> {
        return this.http.patch<User>(environment.userService, user);
    }
}
