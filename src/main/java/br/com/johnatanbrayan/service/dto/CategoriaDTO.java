package br.com.johnatanbrayan.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.johnatanbrayan.domain.Categoria} entity.
 */
@ApiModel(description = "The Categoria entity.\n@author A true hipster")
public class CategoriaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Boolean status;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoriaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
