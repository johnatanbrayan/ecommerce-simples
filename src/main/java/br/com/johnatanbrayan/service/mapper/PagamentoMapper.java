package br.com.johnatanbrayan.service.mapper;


import br.com.johnatanbrayan.domain.*;
import br.com.johnatanbrayan.service.dto.PagamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pagamento} and its DTO {@link PagamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PagamentoMapper extends EntityMapper<PagamentoDTO, Pagamento> {


    @Mapping(target = "pedido", ignore = true)
    Pagamento toEntity(PagamentoDTO pagamentoDTO);

    default Pagamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pagamento pagamento = new Pagamento();
        pagamento.setId(id);
        return pagamento;
    }
}
