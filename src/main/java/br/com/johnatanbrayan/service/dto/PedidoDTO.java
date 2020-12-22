package br.com.johnatanbrayan.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.johnatanbrayan.domain.Pedido} entity.
 */
@ApiModel(description = "The Pedido entity.\n@author A true hipster")
public class PedidoDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant instante;


    private Long pagamentoId;

    private String pagamentoEstadoPagamento;

    private Long userId;

    private String userFirstName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInstante() {
        return instante;
    }

    public void setInstante(Instant instante) {
        this.instante = instante;
    }

    public Long getPagamentoId() {
        return pagamentoId;
    }

    public void setPagamentoId(Long pagamentoId) {
        this.pagamentoId = pagamentoId;
    }

    public String getPagamentoEstadoPagamento() {
        return pagamentoEstadoPagamento;
    }

    public void setPagamentoEstadoPagamento(String pagamentoEstadoPagamento) {
        this.pagamentoEstadoPagamento = pagamentoEstadoPagamento;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PedidoDTO)) {
            return false;
        }

        return id != null && id.equals(((PedidoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PedidoDTO{" +
            "id=" + getId() +
            ", instante='" + getInstante() + "'" +
            ", pagamentoId=" + getPagamentoId() +
            ", pagamentoEstadoPagamento='" + getPagamentoEstadoPagamento() + "'" +
            ", userId=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            "}";
    }
}
