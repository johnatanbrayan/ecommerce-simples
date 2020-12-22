export interface IPagamento {
  id?: number;
  estadoPagamento?: string;
  pedidoId?: number;
}

export class Pagamento implements IPagamento {
  constructor(public id?: number, public estadoPagamento?: string, public pedidoId?: number) {}
}
