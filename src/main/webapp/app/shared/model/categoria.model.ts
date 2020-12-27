import { IProduto } from 'app/shared/model/produto.model';

export interface ICategoria {
  id?: number;
  nome?: string;
  status?: boolean;
  produtos?: IProduto[];
}

export class Categoria implements ICategoria {
  constructor(public id?: number, public nome?: string, public status?: boolean, public produtos?: IProduto[]) {
    this.status = this.status || false;
  }
}
