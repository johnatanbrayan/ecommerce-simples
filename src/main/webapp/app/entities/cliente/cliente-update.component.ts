import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html',
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;
  dataNascimentoDp: any;

  editForm = this.fb.group({
    id: [],
    dataNascimento: [null, [Validators.required]],
    cpf: [null, [Validators.required]],
    fone: [null, [Validators.maxLength(16), Validators.pattern('(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})')]],
    celular: [null, [Validators.maxLength(16), Validators.pattern('(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})')]],
    logradouro: [null, [Validators.required, Validators.maxLength(50)]],
    numero: [null, [Validators.required, Validators.maxLength(6)]],
    complemento: [null, [Validators.maxLength(30)]],
    bairro: [null, [Validators.maxLength(50)]],
    cidade: [null, [Validators.maxLength(50)]],
    cep: [null, [Validators.required, Validators.maxLength(8)]],
    uf: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(2)]],
  });

  constructor(protected clienteService: ClienteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);
    });
  }

  updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      dataNascimento: cliente.dataNascimento,
      cpf: cliente.cpf,
      fone: cliente.fone,
      celular: cliente.celular,
      logradouro: cliente.logradouro,
      numero: cliente.numero,
      complemento: cliente.complemento,
      bairro: cliente.bairro,
      cidade: cliente.cidade,
      cep: cliente.cep,
      uf: cliente.uf,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id'])!.value,
      dataNascimento: this.editForm.get(['dataNascimento'])!.value,
      cpf: this.editForm.get(['cpf'])!.value,
      fone: this.editForm.get(['fone'])!.value,
      celular: this.editForm.get(['celular'])!.value,
      logradouro: this.editForm.get(['logradouro'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      complemento: this.editForm.get(['complemento'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      cidade: this.editForm.get(['cidade'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      uf: this.editForm.get(['uf'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
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
}
