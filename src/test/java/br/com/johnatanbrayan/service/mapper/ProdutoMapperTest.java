package br.com.johnatanbrayan.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProdutoMapperTest {

    private ProdutoMapper produtoMapper;

    @BeforeEach
    public void setUp() {
        produtoMapper = new ProdutoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(produtoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(produtoMapper.fromId(null)).isNull();
    }
}
