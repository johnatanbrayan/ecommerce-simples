package br.com.johnatanbrayan.service.mapper;


import br.com.johnatanbrayan.domain.*;
import br.com.johnatanbrayan.service.dto.ProdutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produto} and its DTO {@link ProdutoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoriaMapper.class, PedidoMapper.class})
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto> {


    @Mapping(target = "removeCategorias", ignore = true)
    @Mapping(target = "removePedidos", ignore = true)

    default Produto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produto produto = new Produto();
        produto.setId(id);
        return produto;
    }
}
