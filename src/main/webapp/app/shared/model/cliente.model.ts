import { Moment } from 'moment';

export interface ICliente {
  id?: number;
  dataNascimento?: Moment;
  cpf?: string;
  fone?: string;
  celular?: string;
  logradouro?: string;
  numero?: string;
  complemento?: string;
  bairro?: string;
  cidade?: string;
  cep?: string;
  uf?: string;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public dataNascimento?: Moment,
    public cpf?: string,
    public fone?: string,
    public celular?: string,
    public logradouro?: string,
    public numero?: string,
    public complemento?: string,
    public bairro?: string,
    public cidade?: string,
    public cep?: string,
    public uf?: string
  ) {}
}
