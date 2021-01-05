import { PedidoService } from './../pedido/pedido.service';
import { CategoriaService } from './../categoria/categoria.service';
import { IProduto, Produto } from './../../shared/model/produto.model';
import { ICategoria } from './../../shared/model/categoria.model';
import { IPedido } from './../../shared/model/pedido.model';
import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ProdutoService } from './produto.service';

type SelectableEntity = ICategoria | IPedido;

@Component({
  selector: 'jhi-produto-update',
  templateUrl: './produto-update.component.html',
})
export class ProdutoUpdateComponent implements OnInit {
  produto!: IProduto;
  isSaving = false;
  categorias: ICategoria[] = [];
  pedidos: IPedido[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    preco: [null, [Validators.required]],
    status: [null, [Validators.required]],
    categorias: [],
    pedidos: [],
  });

  constructor(
    protected produtoService: ProdutoService,
    protected categoriaService: CategoriaService,
    protected pedidoService: PedidoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produto }) => {
      this.produto = produto;
      this.updateForm(produto);

      this.categoriaService.query().subscribe((res: HttpResponse<ICategoria[]>) => (this.categorias = res.body || []));
      this.pedidoService.query().subscribe((res: HttpResponse<IPedido[]>) => (this.pedidos = res.body || []));
    });
  }

  updateForm(produto: IProduto): void {
    this.editForm.patchValue({
      id: produto.id,
      nome: produto.nome,
      preco: produto.preco,
      status: produto.status,
      categorias: produto.categorias,
      pedidos: produto.pedidos,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.produto = this.createFromForm();
    if (this.produto.id !== undefined) {
      this.subscribeToSaveResponse(this.produtoService.update(this.produto));
    } else {
      this.subscribeToSaveResponse(this.produtoService.create(this.produto));
    }
  }

  private createFromForm(): IProduto {
    return {
      ...new Produto(),
      nome: this.editForm.get(['nome'])!.value,
      preco: this.editForm.get(['preco'])!.value,
      status: true,
      categorias: this.editForm.get(['categorias'])!.value,
      pedidos: this.editForm.get(['pedidos'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduto>>): void {
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

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
