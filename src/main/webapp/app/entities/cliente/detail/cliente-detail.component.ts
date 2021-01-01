import { IUser } from './../../../core/user/user.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

@Component({
    selector: 'jhi-cliente-detail',
    templateUrl: './cliente-detail.component.html',
})
export class ClienteDetailComponent implements OnInit {
    /* Objeto */
    cliente!: IUser;

    constructor(protected activatedRoute: ActivatedRoute, protected dataUtils: JhiDataUtils) {}

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({ cliente }) => {
            this.cliente = cliente;
        });
    }

    previousState(): void {
        window.history.back();
    }
}
