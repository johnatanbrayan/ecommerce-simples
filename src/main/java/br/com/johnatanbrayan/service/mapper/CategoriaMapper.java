package br.com.johnatanbrayan.service.mapper;


import br.com.johnatanbrayan.domain.*;
import br.com.johnatanbrayan.service.dto.CategoriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categoria} and its DTO {@link CategoriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriaMapper extends EntityMapper<CategoriaDTO, Categoria> {


    @Mapping(target = "produtos", ignore = true)
    @Mapping(target = "removeProdutos", ignore = true)
    Categoria toEntity(CategoriaDTO categoriaDTO);

    default Categoria fromId(Long id) {
        if (id == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(id);
        return categoria;
    }
}
