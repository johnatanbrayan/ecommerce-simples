import { ICategoria, Categoria } from './../../shared/model/categoria.model';
import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { CategoriaService } from './categoria.service';

@Component({
  selector: 'jhi-categoria-update',
  templateUrl: './categoria-update.component.html',
})
export class CategoriaUpdateComponent implements OnInit {
  isSaving = false;
  categoria!: ICategoria;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    status: [null, [Validators.required]],
  });

  constructor(protected categoriaService: CategoriaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoria }) => {
      this.categoria = categoria;
      this.updateForm(categoria);
    });
  }

  updateForm(categoria: ICategoria): void {
    this.editForm.patchValue({
      id: categoria.id,
      nome: categoria.nome,
      status: categoria.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.categoria = this.createFromForm();
    if (this.categoria.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriaService.update(this.categoria));
    } else {
      this.subscribeToSaveResponse(this.categoriaService.create(this.categoria));
    }
  }

  private createFromForm(): ICategoria {
    return {
      ...new Categoria(),
      // id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      status: true,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoria>>): void {
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
