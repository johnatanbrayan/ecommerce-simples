package br.com.johnatanbrayan.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PagamentoMapperTest {

    private PagamentoMapper pagamentoMapper;

    @BeforeEach
    public void setUp() {
        pagamentoMapper = new PagamentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pagamentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pagamentoMapper.fromId(null)).isNull();
    }
}
