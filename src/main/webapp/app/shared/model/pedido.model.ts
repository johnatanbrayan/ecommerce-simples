import { Moment } from 'moment';
import { IProduto } from 'app/shared/model/produto.model';

export interface IPedido {
  id?: number;
  instante?: Moment;
  pagamentoEstadoPagamento?: string;
  pagamentoId?: number;
  userFirstName?: string;
  userId?: number;
  produtos?: IProduto[];
}

export class Pedido implements IPedido {
  constructor(
    public id?: number,
    public instante?: Moment,
    public pagamentoEstadoPagamento?: string,
    public pagamentoId?: number,
    public userFirstName?: string,
    public userId?: number,
    public produtos?: IProduto[]
  ) {}
}
