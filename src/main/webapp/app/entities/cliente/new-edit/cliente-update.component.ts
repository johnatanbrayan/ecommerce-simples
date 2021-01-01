import { UserService } from './../../../core/user/user.service';
import { IUser } from './../../../core/user/user.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Endereco, NgxViacepService } from '@brunoc/ngx-viacep';
import { maskCep } from 'app/shared/constants/mask.constants';
import { UFS } from 'app/shared/constants/input.constants';

@Component({
    selector: 'jhi-cliente-update',
    templateUrl: './cliente-update.component.html',
})
export class ClienteUpdateComponent implements OnInit {
    /* Objeto */
    cliente!: IUser;

    /* Configuracoes */
    isSaving = true;

    /* Mascara */
    public maskCEP = maskCep;

    /* Cep */
    public ufs = UFS;
    validador = false;

    constructor(
        protected clienteService: UserService,
        protected activatedRoute: ActivatedRoute,
        private viacep: NgxViacepService,
    ) {}

    ngOnInit(): void {
        this.isSaving = false;
        this.validador = false;
        this.activatedRoute.data.subscribe(({ cliente }) => {
            this.cliente = cliente;
        });
    }

    /* -----------CONFIGURACOES----------------*/
    private onError(): void {
        this.previousState();
    }
    /* --------------------------------------*/

    /* ---------------------------------SALVAR-----------------------------------------*/
    save(): void {
        this.isSaving = true;
        
        if (this.cliente.id !== undefined) {
            this.subscribeToSaveResponse(this.clienteService.update(this.cliente));
        } else {
            this.subscribeToSaveResponse(this.clienteService.create(this.cliente));
        }
    }
    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUser>>): void {
        result.subscribe(
            () => this.onSaveSuccess(),
            () => this.onSaveError()
        );
    }
    protected onSaveSuccess(): void {
        this.isSaving = false;
        this.previousState();
    }
    protected onSaveError(): void {
        this.isSaving = false;
    }
    /* -------------------------------------------------------------------------------*/

    /* ---------------------CEP--------------------*/
    buscarCep(): void {
        let cep = this.cliente.cliente!.cep!;

        if (cep) {
            cep = cep.replace(/\D/g, '');
        }
        if (this.isCEPvalido(cep)) {
            this.viacep
                .buscarPorCep(cep)
                .then((endereco: Endereco) => {
                    this.trataResultadoEndereco(endereco);
                    this.validador = false;
                })
                .catch(() => {
                    this.validador = true;
                    this.trataTrocaCep();
                });
        } else {
            this.validador = true;
            this.trataTrocaCep();
        }
    }
    isCEPvalido(strCEP: string): boolean {
        // Caso o CEP não esteja nesse formato ele é inválido!
        const objER = /^[0-9]{8}$/;

        strCEP = strCEP.replace(/\s/g, '');
        if (strCEP.length > 0) {
            if (objER.test(strCEP)) {
                return true;
            }
        }
        return false;
    }

    trataTrocaCep(): void {
        this.cliente.cliente!.cidade = undefined;
        this.cliente.cliente!.uf = undefined;
        this.cliente.cliente!.logradouro = undefined;
        this.cliente.cliente!.bairro = undefined;
    }
    trataResultadoEndereco(endereco: Endereco): void {
        this.cliente.cliente!.cidade = endereco.localidade;
        this.cliente.cliente!.uf = endereco.uf;
        this.cliente.cliente!.logradouro = endereco.logradouro;
        this.cliente.cliente!.bairro = endereco.bairro;
    }
    /* --------------------------------------------*/

    /* -------CARREAMENTO----------*/
    previousState(): void {
        window.history.back();
    }
    /* ---------------------------*/
}
