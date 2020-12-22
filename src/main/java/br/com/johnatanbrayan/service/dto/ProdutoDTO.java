package br.com.johnatanbrayan.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link br.com.johnatanbrayan.domain.Produto} entity.
 */
@ApiModel(description = "The Produto entity.\n@author A true hipster")
public class ProdutoDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private BigDecimal preco;

    @NotNull
    private Boolean status;

    private Set<CategoriaDTO> categorias = new HashSet<>();
    private Set<PedidoDTO> pedidos = new HashSet<>();
    
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

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    public Set<PedidoDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<PedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdutoDTO)) {
            return false;
        }

        return id != null && id.equals(((ProdutoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdutoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", preco=" + getPreco() +
            ", status='" + isStatus() + "'" +
            ", categorias='" + getCategorias() + "'" +
            ", pedidos='" + getPedidos() + "'" +
            "}";
    }
}
