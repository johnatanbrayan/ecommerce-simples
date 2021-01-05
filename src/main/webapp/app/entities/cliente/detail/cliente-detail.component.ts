import { Validators, FormBuilder } from '@angular/forms';
import { UserService } from './../../../core/user/user.service';
import { IUser, User } from './../../../core/user/user.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-cliente-detail',
  templateUrl: './cliente-detail.component.html',
})
export class ClienteDetailComponent implements OnInit {
  /* Objeto */
  cliente!: IUser;

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

  constructor(protected clienteService: UserService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      // if( cliente ) {
      this.cliente = cliente;
      this.updateForm(cliente);
      this.disabledEditForm();
      // }
    });
  }

  /* ---------------------------------SALVAR-----------------------------------------*/
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
  private disabledEditForm(): void {
    this.editForm.get('id')!.disable();
    this.editForm.get('firstName')!.disable();
    this.editForm.get('email')!.disable();
    this.editForm.get('cep')!.disable();
    this.editForm.get('logradouro')!.disable();
    this.editForm.get('numero')!.disable();
    this.editForm.get('complemento')!.disable();
    this.editForm.get('bairro')!.disable();
    this.editForm.get('cidade')!.disable();
    this.editForm.get('uf')!.disable();
    this.editForm.get('fone')!.disable();
    this.editForm.get('celular')!.disable();
    this.editForm.get('cpf')!.disable();
    this.editForm.get('dataNascimento')!.disable();
  }
  /* -------------------------------------------------------------------------------*/

  /* -------CARREAMENTO----------*/
  previousState(): void {
    window.history.back();
  }
  /* ---------------------------*/
}
