package br.com.johnatanbrayan.web.rest;

import br.com.johnatanbrayan.EcommercesimplesApp;
import br.com.johnatanbrayan.domain.Pagamento;
import br.com.johnatanbrayan.repository.PagamentoRepository;
import br.com.johnatanbrayan.service.PagamentoService;
import br.com.johnatanbrayan.service.dto.PagamentoDTO;
import br.com.johnatanbrayan.service.mapper.PagamentoMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PagamentoResource} REST controller.
 */
@SpringBootTest(classes = EcommercesimplesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PagamentoResourceIT {

    private static final String DEFAULT_ESTADO_PAGAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_PAGAMENTO = "BBBBBBBBBB";

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PagamentoMapper pagamentoMapper;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPagamentoMockMvc;

    private Pagamento pagamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pagamento createEntity(EntityManager em) {
        Pagamento pagamento = new Pagamento()
            .estadoPagamento(DEFAULT_ESTADO_PAGAMENTO);
        return pagamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pagamento createUpdatedEntity(EntityManager em) {
        Pagamento pagamento = new Pagamento()
            .estadoPagamento(UPDATED_ESTADO_PAGAMENTO);
        return pagamento;
    }

    @BeforeEach
    public void initTest() {
        pagamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createPagamento() throws Exception {
        int databaseSizeBeforeCreate = pagamentoRepository.findAll().size();
        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);
        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Pagamento testPagamento = pagamentoList.get(pagamentoList.size() - 1);
        assertThat(testPagamento.getEstadoPagamento()).isEqualTo(DEFAULT_ESTADO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void createPagamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pagamentoRepository.findAll().size();

        // Create the Pagamento with an existing ID
        pagamento.setId(1L);
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEstadoPagamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagamentoRepository.findAll().size();
        // set the field null
        pagamento.setEstadoPagamento(null);

        // Create the Pagamento, which fails.
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);


        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPagamentos() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList
        restPagamentoMockMvc.perform(get("/api/pagamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].estadoPagamento").value(hasItem(DEFAULT_ESTADO_PAGAMENTO)));
    }
    
    @Test
    @Transactional
    public void getPagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get the pagamento
        restPagamentoMockMvc.perform(get("/api/pagamentos/{id}", pagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pagamento.getId().intValue()))
            .andExpect(jsonPath("$.estadoPagamento").value(DEFAULT_ESTADO_PAGAMENTO));
    }
    @Test
    @Transactional
    public void getNonExistingPagamento() throws Exception {
        // Get the pagamento
        restPagamentoMockMvc.perform(get("/api/pagamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();

        // Update the pagamento
        Pagamento updatedPagamento = pagamentoRepository.findById(pagamento.getId()).get();
        // Disconnect from session so that the updates on updatedPagamento are not directly saved in db
        em.detach(updatedPagamento);
        updatedPagamento
            .estadoPagamento(UPDATED_ESTADO_PAGAMENTO);
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(updatedPagamento);

        restPagamentoMockMvc.perform(put("/api/pagamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
        Pagamento testPagamento = pagamentoList.get(pagamentoList.size() - 1);
        assertThat(testPagamento.getEstadoPagamento()).isEqualTo(UPDATED_ESTADO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingPagamento() throws Exception {
        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();

        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagamentoMockMvc.perform(put("/api/pagamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        int databaseSizeBeforeDelete = pagamentoRepository.findAll().size();

        // Delete the pagamento
        restPagamentoMockMvc.perform(delete("/api/pagamentos/{id}", pagamento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
