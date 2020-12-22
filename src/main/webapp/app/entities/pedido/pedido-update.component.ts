import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPedido, Pedido } from 'app/shared/model/pedido.model';
import { PedidoService } from './pedido.service';
import { IPagamento } from 'app/shared/model/pagamento.model';
import { PagamentoService } from 'app/entities/pagamento/pagamento.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IPagamento | IUser;

@Component({
  selector: 'jhi-pedido-update',
  templateUrl: './pedido-update.component.html',
})
export class PedidoUpdateComponent implements OnInit {
  isSaving = false;
  pagamentos: IPagamento[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    instante: [null, [Validators.required]],
    pagamentoId: [],
    userId: [],
  });

  constructor(
    protected pedidoService: PedidoService,
    protected pagamentoService: PagamentoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      if (!pedido.id) {
        const today = moment().startOf('day');
        pedido.instante = today;
      }

      this.updateForm(pedido);

      this.pagamentoService
        .query({ filter: 'pedido-is-null' })
        .pipe(
          map((res: HttpResponse<IPagamento[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPagamento[]) => {
          if (!pedido.pagamentoId) {
            this.pagamentos = resBody;
          } else {
            this.pagamentoService
              .find(pedido.pagamentoId)
              .pipe(
                map((subRes: HttpResponse<IPagamento>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPagamento[]) => (this.pagamentos = concatRes));
          }
        });

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(pedido: IPedido): void {
    this.editForm.patchValue({
      id: pedido.id,
      instante: pedido.instante ? pedido.instante.format(DATE_TIME_FORMAT) : null,
      pagamentoId: pedido.pagamentoId,
      userId: pedido.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pedido = this.createFromForm();
    if (pedido.id !== undefined) {
      this.subscribeToSaveResponse(this.pedidoService.update(pedido));
    } else {
      this.subscribeToSaveResponse(this.pedidoService.create(pedido));
    }
  }

  private createFromForm(): IPedido {
    return {
      ...new Pedido(),
      id: this.editForm.get(['id'])!.value,
      instante: this.editForm.get(['instante'])!.value ? moment(this.editForm.get(['instante'])!.value, DATE_TIME_FORMAT) : undefined,
      pagamentoId: this.editForm.get(['pagamentoId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPedido>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
