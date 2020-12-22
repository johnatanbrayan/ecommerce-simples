package br.com.johnatanbrayan.web.rest;

import br.com.johnatanbrayan.service.PagamentoService;
import br.com.johnatanbrayan.web.rest.errors.BadRequestAlertException;
import br.com.johnatanbrayan.service.dto.PagamentoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link br.com.johnatanbrayan.domain.Pagamento}.
 */
@RestController
@RequestMapping("/api")
public class PagamentoResource {

    private final Logger log = LoggerFactory.getLogger(PagamentoResource.class);

    private static final String ENTITY_NAME = "pagamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PagamentoService pagamentoService;

    public PagamentoResource(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    /**
     * {@code POST  /pagamentos} : Create a new pagamento.
     *
     * @param pagamentoDTO the pagamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pagamentoDTO, or with status {@code 400 (Bad Request)} if the pagamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pagamentos")
    public ResponseEntity<PagamentoDTO> createPagamento(@Valid @RequestBody PagamentoDTO pagamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Pagamento : {}", pagamentoDTO);
        if (pagamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new pagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PagamentoDTO result = pagamentoService.save(pagamentoDTO);
        return ResponseEntity.created(new URI("/api/pagamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pagamentos} : Updates an existing pagamento.
     *
     * @param pagamentoDTO the pagamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pagamentoDTO,
     * or with status {@code 400 (Bad Request)} if the pagamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pagamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pagamentos")
    public ResponseEntity<PagamentoDTO> updatePagamento(@Valid @RequestBody PagamentoDTO pagamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Pagamento : {}", pagamentoDTO);
        if (pagamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PagamentoDTO result = pagamentoService.save(pagamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pagamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pagamentos} : get all the pagamentos.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pagamentos in body.
     */
    @GetMapping("/pagamentos")
    public ResponseEntity<List<PagamentoDTO>> getAllPagamentos(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("pedido-is-null".equals(filter)) {
            log.debug("REST request to get all Pagamentos where pedido is null");
            return new ResponseEntity<>(pagamentoService.findAllWherePedidoIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Pagamentos");
        Page<PagamentoDTO> page = pagamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pagamentos/:id} : get the "id" pagamento.
     *
     * @param id the id of the pagamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pagamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pagamentos/{id}")
    public ResponseEntity<PagamentoDTO> getPagamento(@PathVariable Long id) {
        log.debug("REST request to get Pagamento : {}", id);
        Optional<PagamentoDTO> pagamentoDTO = pagamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pagamentoDTO);
    }

    /**
     * {@code DELETE  /pagamentos/:id} : delete the "id" pagamento.
     *
     * @param id the id of the pagamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pagamentos/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        log.debug("REST request to delete Pagamento : {}", id);
        pagamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
