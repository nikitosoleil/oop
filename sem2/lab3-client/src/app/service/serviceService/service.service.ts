import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Service} from '../../model/service.model';
import {environment} from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ServiceService {

    constructor(private http: HttpClient) {
    }

    getAllServices(): Observable<Service[]> {
        return this.http.get<Service[]>(environment.serviceService);
    }

    getServicesByUser(email: string): Observable<Service[]> {
        return this.http.get<Service[]>(environment.serviceService + '/' + email);
    }

    addService(service: Service): Observable<Service> {
        return this.http.post<Service>(environment.serviceService, service);
    }

    addServiceForUser(service: Service, email: string): Observable<Service> {
        return this.http.patch<Service>(environment.serviceService + '/' + service.id + '/' + email, service);
    }
}
