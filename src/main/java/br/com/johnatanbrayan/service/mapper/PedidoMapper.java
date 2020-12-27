package br.com.johnatanbrayan.service.mapper;


import br.com.johnatanbrayan.domain.*;
import br.com.johnatanbrayan.service.dto.PedidoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pedido} and its DTO {@link PedidoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PagamentoMapper.class, UserMapper.class})
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {

    @Mapping(source = "pagamento.id", target = "pagamentoId")
    @Mapping(source = "pagamento.estadoPagamento", target = "pagamentoEstadoPagamento")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    PedidoDTO toDto(Pedido pedido);

    @Mapping(source = "pagamentoId", target = "pagamento")
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "produtos", ignore = true)
    @Mapping(target = "removeProdutos", ignore = true)
    Pedido toEntity(PedidoDTO pedidoDTO);

    default Pedido fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pedido pedido = new Pedido();
        pedido.setId(id);
        return pedido;
    }
}
