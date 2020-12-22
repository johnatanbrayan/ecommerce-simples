package br.com.johnatanbrayan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * The Pagamento entity.\n@author A true hipster
 */
@Entity
@Table(name = "pagamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "estado_pagamento", nullable = false)
    private String estadoPagamento;

    @OneToOne(mappedBy = "pagamento")
    @JsonIgnore
    private Pedido pedido;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstadoPagamento() {
        return estadoPagamento;
    }

    public Pagamento estadoPagamento(String estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
        return this;
    }

    public void setEstadoPagamento(String estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Pagamento pedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pagamento)) {
            return false;
        }
        return id != null && id.equals(((Pagamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pagamento{" +
            "id=" + getId() +
            ", estadoPagamento='" + getEstadoPagamento() + "'" +
            "}";
    }
}
