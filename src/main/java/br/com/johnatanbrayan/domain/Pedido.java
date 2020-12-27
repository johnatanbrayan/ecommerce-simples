package br.com.johnatanbrayan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * The Pedido entity.\n@author A true hipster
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "instante", nullable = false)
    private Instant instante;

    @OneToOne
    @JoinColumn(unique = true)
    private Pagamento pagamento;

    @ManyToOne
    @JsonIgnoreProperties(value = "pedidos", allowSetters = true)
    private User user;

    @ManyToMany(mappedBy = "pedidos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Produto> produtos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInstante() {
        return instante;
    }

    public Pedido instante(Instant instante) {
        this.instante = instante;
        return this;
    }

    public void setInstante(Instant instante) {
        this.instante = instante;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public Pedido pagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
        return this;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public User getUser() {
        return user;
    }

    public Pedido user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public Pedido produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public Pedido addProdutos(Produto produto) {
        this.produtos.add(produto);
        produto.getPedidos().add(this);
        return this;
    }

    public Pedido removeProdutos(Produto produto) {
        this.produtos.remove(produto);
        produto.getPedidos().remove(this);
        return this;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        return id != null && id.equals(((Pedido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", instante='" + getInstante() + "'" +
            "}";
    }
}
