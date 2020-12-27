import { ICategoria } from 'app/shared/model/categoria.model';
import { IPedido } from 'app/shared/model/pedido.model';

export interface IProduto {
  id?: number;
  nome?: string;
  preco?: number;
  status?: boolean;
  categorias?: ICategoria[];
  pedidos?: IPedido[];
}

export class Produto implements IProduto {
  constructor(
    public id?: number,
    public nome?: string,
    public preco?: number,
    public status?: boolean,
    public categorias?: ICategoria[],
    public pedidos?: IPedido[]
  ) {
    this.status = this.status || false;
  }
}
