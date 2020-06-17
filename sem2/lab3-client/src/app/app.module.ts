import {AppAuthGuard} from './service/AppAuthGuard';
import {RouterModule} from '@angular/router';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule, APP_INITIALIZER} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {KeycloakAngularModule, KeycloakService, KeycloakBearerInterceptor} from 'keycloak-angular';
import {FlexModule, FlexLayoutModule} from '@angular/flex-layout';

import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatListModule} from '@angular/material/list';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NavigationComponent} from './navigation/navigation.component';
import {initializer} from '../util/app-init';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { AdminProfileComponent } from './admin/admin-profile/admin-profile.component';
import { AddServiceUserComponent } from './user/add-service-user/add-service-user.component';
import { AddServiceAdminComponent } from './admin/add-service-admin/add-service-admin.component';
import { ServiceCardComponent } from './service-list/service-card/service-card.component';
import { ServiceListComponent } from './service-list/service-list/service-list.component';
import { UserBillComponent } from './user-bill/user-bill/user-bill.component';
import { UserBillListComponent } from './user-bill/user-bill-list/user-bill-list.component';
import { ManageUsersComponent } from './admin/manage-users/manage-users.component';
import { ManageServicesComponent } from './admin/manage-services/manage-services.component';
import { UserListComponent } from './admin/user-list/user-list.component';
import { UserDialogComponent } from './admin/user-dialog/user-dialog.component';
import { UserCardComponent } from './admin/user-card/user-card.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    UserProfileComponent,
    AdminProfileComponent,
    AddServiceUserComponent,
    AddServiceAdminComponent,
    ServiceCardComponent,
    ServiceListComponent,
    UserBillComponent,
    UserBillListComponent,
    ManageUsersComponent,
    ManageServicesComponent,
    UserListComponent,
    UserDialogComponent,
    UserCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    KeycloakAngularModule,
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatPaginatorModule,
    MatListModule,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    FlexModule,
    FlexLayoutModule,
    RouterModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      deps: [KeycloakService],
      multi: true
    },
    {
      provide: AppAuthGuard
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: KeycloakBearerInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
