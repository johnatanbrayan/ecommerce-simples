package br.com.johnatanbrayan.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.johnatanbrayan.web.rest.TestUtil;

public class PagamentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pagamento.class);
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setId(1L);
        Pagamento pagamento2 = new Pagamento();
        pagamento2.setId(pagamento1.getId());
        assertThat(pagamento1).isEqualTo(pagamento2);
        pagamento2.setId(2L);
        assertThat(pagamento1).isNotEqualTo(pagamento2);
        pagamento1.setId(null);
        assertThat(pagamento1).isNotEqualTo(pagamento2);
    }
}
