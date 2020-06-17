import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {getService} from '../../model/service.model';
import {ServiceService} from '../../service/serviceService/service.service';
import {MatDialogRef} from '@angular/material/dialog';
import {ManageServicesComponent} from '../manage-services/manage-services.component';

@Component({
    selector: 'app-add-service-admin',
    templateUrl: './add-service-admin.component.html',
    styleUrls: ['./add-service-admin.component.css']
})
export class AddServiceAdminComponent implements OnInit {
    form: FormGroup;

    constructor(public dialogRef: MatDialogRef<ManageServicesComponent>,
                private serviceService: ServiceService) {
    }

    ngOnInit(): void {
        this.form = new FormGroup({
                name: new FormControl('', [Validators.required, Validators.maxLength(30)]),
                description: new FormControl('', [Validators.required, Validators.maxLength(50)]),
                price: new FormControl(0, [Validators.required, Validators.min(0)])
            }
        );
    }

    submit() {
        if (this.form.valid) {
            const service = getService(
                null,
                this.form.get('name').value,
                this.form.get('description').value,
                this.form.get('price').value
            );
            this.serviceService.addService(service).subscribe(
                _ => this.dialogRef.close(),
                err => {
                    console.log(err);
                    alert(err.message);
                }
            );
        }
    }
}
