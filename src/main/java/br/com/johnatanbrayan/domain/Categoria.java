package br.com.johnatanbrayan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Categoria entity.\n@author A true hipster
 */
@Entity
@Table(name = "categoria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToMany(mappedBy = "categorias")
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

    public String getNome() {
        return nome;
    }

    public Categoria nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isStatus() {
        return status;
    }

    public Categoria status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public Categoria produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public Categoria addProdutos(Produto produto) {
        this.produtos.add(produto);
        produto.getCategorias().add(this);
        return this;
    }

    public Categoria removeProdutos(Produto produto) {
        this.produtos.remove(produto);
        produto.getCategorias().remove(this);
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
        if (!(o instanceof Categoria)) {
            return false;
        }
        return id != null && id.equals(((Categoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categoria{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
