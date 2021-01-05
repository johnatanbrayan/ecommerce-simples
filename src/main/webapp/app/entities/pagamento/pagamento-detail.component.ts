import { IPagamento } from './../../shared/model/pagamento.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-pagamento-detail',
  templateUrl: './pagamento-detail.component.html',
})
export class PagamentoDetailComponent implements OnInit {
  pagamento!: IPagamento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pagamento }) => (this.pagamento = pagamento));
  }

  previousState(): void {
    window.history.back();
  }
}
