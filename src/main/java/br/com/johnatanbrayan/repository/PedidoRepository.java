package br.com.johnatanbrayan.repository;

import br.com.johnatanbrayan.domain.Pedido;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Pedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("select pedido from Pedido pedido where pedido.user.login = ?#{principal.username}")
    List<Pedido> findByUserIsCurrentUser();
}
