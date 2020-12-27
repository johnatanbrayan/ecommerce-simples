package br.com.johnatanbrayan.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.johnatanbrayan.domain.Pagamento} entity.
 */
@ApiModel(description = "The Pagamento entity.\n@author A true hipster")
public class PagamentoDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String estadoPagamento;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstadoPagamento() {
        return estadoPagamento;
    }

    public void setEstadoPagamento(String estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PagamentoDTO)) {
            return false;
        }

        return id != null && id.equals(((PagamentoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PagamentoDTO{" +
            "id=" + getId() +
            ", estadoPagamento='" + getEstadoPagamento() + "'" +
            "}";
    }
}
