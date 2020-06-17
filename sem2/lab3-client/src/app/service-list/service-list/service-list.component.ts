import {Component, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {ServiceService} from '../../service/serviceService/service.service';
import {Service} from '../../model/service.model';
import {UserService} from '../../service/userService/user.service';

@Component({
    selector: 'app-service-list',
    templateUrl: './service-list.component.html',
    styleUrls: ['./service-list.component.css']
})
export class ServiceListComponent implements OnInit {
    services: Observable<Service[]>;

    constructor(private serviceService: ServiceService,
                private userService: UserService) {
    }

    ngOnInit(): void {
        this.load();
    }

    load() {
        const user = this.userService.getCurrentUser();
        if (user) {
            this.services = this.serviceService.getServicesByUser(user.email);
        } else {
            this.services = this.serviceService.getAllServices();
        }
    }

}
