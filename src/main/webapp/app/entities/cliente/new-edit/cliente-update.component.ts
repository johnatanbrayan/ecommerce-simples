import { maskFone, maskCelular, maskCpf } from './../../../shared/constants/mask.constants';
import { JhiLanguageService } from 'ng-jhipster';
import { User } from 'app/core/user/user.model';
import { Validators, FormBuilder } from '@angular/forms';
import { UserService } from './../../../core/user/user.service';
import { IUser, Cliente } from './../../../core/user/user.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Endereco, NgxViacepService } from '@brunoc/ngx-viacep';
import { maskCep } from 'app/shared/constants/mask.constants';
import { UFS } from 'app/shared/constants/input.constants';
import * as moment from 'moment';
import { BsLocaleService } from 'ngx-bootstrap/datepicker';
import { ptBrLocale } from 'ngx-bootstrap/locale';
import { defineLocale } from 'ngx-bootstrap/chronos';

defineLocale('pt-br', ptBrLocale);

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
    public maskFone = maskFone;
    public maskCelular = maskCelular;
    public maskCpf = maskCpf;

    /* Cep */
    public ufs = UFS;
    validador = false;

    editForm = this.fb.group({
        id: [],
        firstName: ['', [Validators.maxLength(50)]],
        email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
        cep: [null, [Validators.required, Validators.minLength(9)]],
        logradouro: [null, [Validators.required, Validators.maxLength(50)]],
        numero: [null, [Validators.required, Validators.maxLength(6)]],
        complemento: [null, [Validators.maxLength(30)]],
        bairro: [null, [Validators.required, Validators.maxLength(50)]],
        cidade: [null, [Validators.required, Validators.maxLength(50)]],
        uf: [null, [Validators.required, Validators.minLength(2)]],
        fone: [null, [Validators.minLength(14)]],
        celular: [null, [Validators.required, Validators.minLength(15)]],
        cpf: [null, [Validators.required, Validators.minLength(14)]],
        dataNascimento: [null, [Validators.required]],
    });

    constructor(
        protected clienteService: UserService,
        protected activatedRoute: ActivatedRoute,
        private viacep: NgxViacepService,
        private fb: FormBuilder,
        private languageService: JhiLanguageService,
        private localeService: BsLocaleService,
    ) {
        this.localeService.use('pt-br');
    }

    ngOnInit(): void {
        this.isSaving = false;
        this.validador = false;
        this.activatedRoute.data.subscribe(({ cliente }) => {
            this.cliente = cliente;
            this.updateForm(cliente);
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
        this.updateCliente(this.cliente);
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
    private updateForm(cliente: User): void {
        this.editForm.patchValue({
          id: cliente.id,
          authorities: cliente.authorities,
          login: cliente.login,
          firstName: cliente.firstName,
          lastName: cliente.lastName,
          cpf: cliente.cliente?.cpf,
          dataNascimento: cliente.cliente?.dataNascimento?.toDate(),
          email: cliente.email,
          langKey: cliente.langKey,
          cliente: cliente.cliente,
          logradouro: cliente.cliente?.logradouro,
          numero: cliente.cliente?.numero,
          complemento: cliente.cliente?.complemento,
          bairro: cliente.cliente?.bairro,
          cep: cliente.cliente?.cep,
          cidade: cliente.cliente?.cidade,
          uf: cliente.cliente?.uf,
          fone: cliente.cliente?.fone,
          celular: cliente.cliente?.celular,
        });
    }
    private updateCliente(cliente: User): void {
        const cep = this.editForm.get(['cep'])!.value;
        const logradouro = this.editForm.get(['logradouro'])!.value;
        const numero = this.editForm.get(['numero'])!.value;
        const complemento = this.editForm.get(['complemento'])!.value;
        const bairro = this.editForm.get(['bairro'])!.value;
        const cidade = this.editForm.get(['cidade'])!.value;
        const uf = this.editForm.get(['uf'])!.value;
        const fone = this.editForm.get(['fone'])!.value;
        const celular = this.editForm.get(['celular'])!.value;
        const dataNascimento: Date = this.editForm.get(['dataNascimento'])!.value;
        const cpf = this.editForm.get(['cpf'])!.value;
        cliente.firstName = this.editForm.get(['firstName'])!.value;
        cliente.email = this.editForm.get(['email'])!.value;
        cliente.langKey = this.languageService.getCurrentLanguage();
        cliente.authorities = ['ROLE_USER'];
        cliente.login = cliente.email;
    
        cliente.cliente = new Cliente(
            moment(dataNascimento), cpf, fone, celular, logradouro, numero, complemento, bairro,
            cidade, cep, uf,
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

    /* --------- Função para abrir calendario em modo cascata. Primeiro ano, mes e dia --------- */
  onOpenCalendar(container: any): any {
    setTimeout(() => {
      container.setViewMode('year');
    });

    // eslint-disable-next-line
    container.dayHoverHandler = (event: any): void => {};
    container.daySelectHandler = (event: any): void => {
      container._store.dispatch(container._actions.select(event.date));
    };
  }
}
