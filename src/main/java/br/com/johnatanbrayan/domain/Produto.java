package br.com.johnatanbrayan.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * The Produto entity.\n@author A true hipster
 */
@Entity
@Table(name = "produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "preco", precision = 21, scale = 2, nullable = false)
    private BigDecimal preco;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "produto_categorias",
               joinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categorias_id", referencedColumnName = "id"))
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "produto_pedidos",
               joinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "pedidos_id", referencedColumnName = "id"))
    private Set<Pedido> pedidos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Produto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Produto preco(BigDecimal preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean isStatus() {
        return status;
    }

    public Produto status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public Produto categorias(Set<Categoria> categorias) {
        this.categorias = categorias;
        return this;
    }

    public Produto addCategorias(Categoria categoria) {
        this.categorias.add(categoria);
        categoria.getProdutos().add(this);
        return this;
    }

    public Produto removeCategorias(Categoria categoria) {
        this.categorias.remove(categoria);
        categoria.getProdutos().remove(this);
        return this;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public Produto pedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        return this;
    }

    public Produto addPedidos(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.getProdutos().add(this);
        return this;
    }

    public Produto removePedidos(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.getProdutos().remove(this);
        return this;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produto)) {
            return false;
        }
        return id != null && id.equals(((Produto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", preco=" + getPreco() +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
