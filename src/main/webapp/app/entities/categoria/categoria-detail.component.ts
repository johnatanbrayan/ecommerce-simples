import { ICategoria } from './../../shared/model/categoria.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-categoria-detail',
  templateUrl: './categoria-detail.component.html',
})
export class CategoriaDetailComponent implements OnInit {
  categoria!: ICategoria;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoria }) => (this.categoria = categoria));
  }

  previousState(): void {
    window.history.back();
  }
}
