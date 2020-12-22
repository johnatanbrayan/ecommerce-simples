package br.com.johnatanbrayan.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The Cliente entity.\n@author A true hipster
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @CPF
    @NotNull
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Size(max = 16)
    @Pattern(regexp = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})")
    @Column(name = "fone", length = 16)
    private String fone;

    @Size(max = 16)
    @Pattern(regexp = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})")
    @Column(name = "celular", length = 16)
    private String celular;

    @NotNull
    @Size(max = 50)
    @Column(name = "logradouro", length = 50, nullable = false)
    private String logradouro;

    @NotNull
    @Size(max = 6)
    @Column(name = "numero", length = 6, nullable = false)
    private String numero;

    @Size(max = 30)
    @Column(name = "complemento", length = 30)
    private String complemento;

    @Size(max = 50)
    @Column(name = "bairro", length = 50)
    private String bairro;

    @Size(max = 50)
    @Column(name = "cidade", length = 50)
    private String cidade;

    @NotNull
    @Size(max = 8)
    @Column(name = "cep", length = 8, nullable = false)
    private String cep;

    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Cliente dataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public Cliente cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFone() {
        return fone;
    }

    public Cliente fone(String fone) {
        this.fone = fone;
        return this;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getCelular() {
        return celular;
    }

    public Cliente celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public Cliente logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public Cliente numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public Cliente complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public Cliente bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public Cliente cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public Cliente cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public Cliente uf(String uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", fone='" + getFone() + "'" +
            ", celular='" + getCelular() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", cep='" + getCep() + "'" +
            ", uf='" + getUf() + "'" +
            "}";
    }
}
