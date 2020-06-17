import {Component, OnInit, ViewChild} from '@angular/core';
import {ServiceListComponent} from '../../service-list/service-list/service-list.component';
import {AddServiceAdminComponent} from '../add-service-admin/add-service-admin.component';
import {MatDialog} from '@angular/material/dialog';

@Component({
    selector: 'app-manage-services',
    templateUrl: './manage-services.component.html',
    styleUrls: ['./manage-services.component.css']
})
export class ManageServicesComponent implements OnInit {
    @ViewChild(ServiceListComponent) serviceList!: ServiceListComponent;

    constructor(public dialog: MatDialog) {
    }

    ngOnInit(): void {
    }

    addService() {
        const dialogRef = this.dialog.open(
            AddServiceAdminComponent,
            {
                width: '30em'
            }
        );

        dialogRef.afterClosed().subscribe(
            _ => this.serviceList.load()
        );
    }

}
