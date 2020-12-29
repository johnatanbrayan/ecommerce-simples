import { User } from './../../../core/user/user.model';
import { Cliente } from '../../../core/user/user.model';
import { BsLocaleService } from 'ngx-bootstrap/datepicker';
import { maskCelular, maskCep, maskCpf, maskFone } from '../../../shared/constants/mask.constants';
import { JhiLanguageService } from 'ng-jhipster';
import { Endereco, ErroCep, NgxViacepService } from '@brunoc/ngx-viacep';
import { LANGUAGES } from '../../../core/language/language.constants';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ptBrLocale } from 'ngx-bootstrap/locale';
import { defineLocale } from 'ngx-bootstrap/chronos';
import * as moment from 'moment';
import { UserService } from '../../../core/user/user.service';

defineLocale('pt-br', ptBrLocale);

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html',
})
export class ClienteUpdateComponent implements OnInit {
  currentAccount: Account | null = null;
  cliente!: User;
  languages = LANGUAGES;
  isSaving = false;

  /* ViaCep */
  validador?: boolean;

  /* Maskaras */
  public maskCep = maskCep;
  public maskFone = maskFone;
  public maskCelular = maskCelular;
  public maskCpf = maskCpf;

  editForm = this.fb.group({
    id: [],
    firstName: ['', [Validators.maxLength(50)]],
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    /* UserExtra */
    cep: [null, [Validators.required, Validators.minLength(9)]],
    logradouro: [null, [Validators.required, Validators.maxLength(50)]],
    numero: [null, [Validators.required, Validators.maxLength(6)]],
    complemento: [null, [Validators.maxLength(30)]],
    bairro: [null, [Validators.required, Validators.maxLength(50)]],
    cidade: [null, [Validators.required, Validators.maxLength(50)]],
    uf: [null, [Validators.required, Validators.minLength(2)]],
    fone: [null, [Validators.minLength(14)]],
    celular: [null, [Validators.required, Validators.minLength(15)]],
    /* Cliente */
    cpf: [null, [Validators.required, Validators.minLength(14)]],
    dataNascimento: [null, [Validators.required]],
  });

  constructor(
    private clienteService: UserService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private viacep: NgxViacepService,
    private languageService: JhiLanguageService,
    private localeService: BsLocaleService,
  ) {
    this.localeService.use('pt-br');
  }

  ngOnInit(): void {
    this.route.data.subscribe(({ cliente }) => {
      if (cliente) {
        this.cliente = cliente;
        if (this.cliente.id === undefined) {
          this.cliente.activated = true;
        }
        this.updateForm(cliente);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.updateAssociado(this.cliente);
    if (this.cliente.id) {
      this.clienteService.update(this.cliente).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    } else {
      this.clienteService.create(this.cliente).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    }
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
      /* cliente */
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

  private updateAssociado(cliente: User): void {
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
    cliente.login = cpf.replace(/\D/g, '');

    cliente.cliente = new Cliente(moment(dataNascimento), cpf, fone, celular, logradouro, numero,
      complemento, bairro, cidade, cep, uf);
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

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
  /* ----------------------------------------------------------------------------------------- */

  /* -------------------------------------CEP------------------------------------ */
  buscarCep(): void {
    let cep: string = this.editForm.get(['cep'])!.value;

    if (cep) {
      cep = cep.replace(/\D/g, '');
    }
    if (this.isCEPvalido(cep)) {
      this.viacep
        .buscarPorCep(cep)
        .then((endereco: Endereco) => {
          this.trataResultadoEndereco(endereco);
          this.validador = false;
        }) // eslint-disable-next-line
        .catch((error: ErroCep) => {
          this.validador = true;
          this.trataTrocaCep();
        });
    } else {
      alert('cep invalido');
      this.validador = true;
      this.trataTrocaCep();
    }
  }

  /* Função para validação de CEP */
  isCEPvalido(strCEP: string): any {
    /* Caso o CEP não esteja nesse formato ele é inválido! */
    const objER = /^[0-9]{8}$/;

    strCEP = this.trim(strCEP);
    if (strCEP.length > 0) {
      /* console.log('Entron validacao de lenght' + objER.test(strCEP)); */
      if (objER.test(strCEP)) {
        return true;
      }
    }
    return false;
  }
  /* Passa os atributos da Classe Endereco da biblioteca para a sua classe */
  trataResultadoEndereco(endereco: Endereco): void {
    this.editForm.get(['cidade'])!.setValue(endereco.localidade);
    this.editForm.get(['uf'])!.setValue(endereco.uf);
    this.editForm.get(['logradouro'])!.setValue(endereco.logradouro);
    this.editForm.get(['bairro'])!.setValue(endereco.bairro);
  }
  /* Substitúi os espaços vazios no inicio e no fim da string por vazio */
  trim(strTexto: string): any {
    return strTexto.replace(/^s+|s+$/g, '');
  }
  trataTrocaCep(): void {
    this.editForm.get(['cidade'])!.setValue(null);
    this.editForm.get(['uf'])!.setValue(null);
    this.editForm.get(['logradouro'])!.setValue(null);
    this.editForm.get(['bairro'])!.setValue(null);
  }
  /* --------------------------------------------------------------------------- */
}
