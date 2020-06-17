import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Service} from '../../model/service.model';
import {UserService} from '../../service/userService/user.service';
import {User} from '../../model/user.model';
import {ServiceService} from '../../service/serviceService/service.service';

@Component({
    selector: 'app-service-card',
    templateUrl: './service-card.component.html',
    styleUrls: ['./service-card.component.css']
})
export class ServiceCardComponent implements OnInit {
    @Input() service: Service;
    @Output() selected = new EventEmitter<boolean>();
    user: User;

    constructor(private userService: UserService,
                private serviceService: ServiceService) {
    }

    ngOnInit(): void {
        this.user = this.userService.getCurrentUser();
    }

    addService(): void {
        this.serviceService.addServiceForUser(this.service, this.user.email).subscribe(
            _ => this.selected.emit(true)
        );
    }
}
