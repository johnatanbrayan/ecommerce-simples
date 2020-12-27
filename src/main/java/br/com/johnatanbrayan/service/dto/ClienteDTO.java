package br.com.johnatanbrayan.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.johnatanbrayan.domain.Cliente} entity.
 */
@ApiModel(description = "The Cliente entity.\n@author A true hipster")
public class ClienteDTO implements Serializable {
    
    private Long id;

    @NotNull
    private LocalDate dataNascimento;

    @NotNull
    private String cpf;

    @Size(max = 16)
    @Pattern(regexp = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})")
    private String fone;

    @Size(max = 16)
    @Pattern(regexp = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})")
    private String celular;

    @NotNull
    @Size(max = 50)
    private String logradouro;

    @NotNull
    @Size(max = 6)
    private String numero;

    @Size(max = 30)
    private String complemento;

    @Size(max = 50)
    private String bairro;

    @Size(max = 50)
    private String cidade;

    @NotNull
    @Size(max = 8)
    private String cep;

    @NotNull
    @Size(min = 2, max = 2)
    private String uf;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClienteDTO)) {
            return false;
        }

        return id != null && id.equals(((ClienteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteDTO{" +
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
