import { IPagamento, Pagamento } from './../../shared/model/pagamento.model';
import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { PagamentoService } from './pagamento.service';

@Component({
  selector: 'jhi-pagamento-update',
  templateUrl: './pagamento-update.component.html',
})
export class PagamentoUpdateComponent implements OnInit {
  pagamento!: IPagamento;
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    estadoPagamento: [null, [Validators.required]],
  });

  constructor(protected pagamentoService: PagamentoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pagamento }) => {
      this.pagamento = pagamento;
      this.updateForm(pagamento);
    });
  }

  updateForm(pagamento: IPagamento): void {
    this.editForm.patchValue({
      id: pagamento.id,
      estadoPagamento: pagamento.estadoPagamento,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.pagamento = this.createFromForm();
    if (this.pagamento.id !== undefined) {
      this.subscribeToSaveResponse(this.pagamentoService.update(this.pagamento));
    } else {
      this.subscribeToSaveResponse(this.pagamentoService.create(this.pagamento));
    }
  }

  private createFromForm(): IPagamento {
    return {
      ...new Pagamento(),
      estadoPagamento: this.editForm.get(['estadoPagamento'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPagamento>>): void {
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
