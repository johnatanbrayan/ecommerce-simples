import { IUser } from './../../../core/user/user.model';
import { UserService } from '../../../core/user/user.service';
import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

@Component({
    templateUrl: './cliente-delete-dialog.component.html',
})
export class ClienteDeleteDialogComponent {
    cliente?: IUser;

    constructor(protected clienteService: UserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    cancel(): void {
        this.activeModal.dismiss();
    }

    confirmDelete(login: string): void {
        this.clienteService.delete(login).subscribe(() => {
            this.eventManager.broadcast('clienteListModification');
            this.activeModal.close();
        });
    }
}
