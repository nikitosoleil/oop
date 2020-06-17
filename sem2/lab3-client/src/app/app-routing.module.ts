import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AppAuthGuard} from './service/AppAuthGuard';
import {UserProfileComponent} from './user/user-profile/user-profile.component';
import {AdminProfileComponent} from './admin/admin-profile/admin-profile.component';
import {AppComponent} from './app.component';
import {ManageUsersComponent} from './admin/manage-users/manage-users.component';
import {ManageServicesComponent} from './admin/manage-services/manage-services.component';


const routes: Routes = [
    {
        path: 'user_profile',
        canActivate: [AppAuthGuard],
        component: UserProfileComponent,
        data: {roles: ['client']}
    },
    {
        path: 'admin_profile',
        canActivate: [AppAuthGuard],
        component: AdminProfileComponent,
        data: {roles: ['admin']}
    },
    {
        path: 'manage/users',
        canActivate: [AppAuthGuard],
        component: ManageUsersComponent,
        data: {roles: ['admin']}
    },
    {
        path: 'manage/services',
        canActivate: [AppAuthGuard],
        component: ManageServicesComponent,
        data: {roles: ['admin']}
    },
    {
        path: '',
        canActivate: [AppAuthGuard],
        component: AppComponent
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
