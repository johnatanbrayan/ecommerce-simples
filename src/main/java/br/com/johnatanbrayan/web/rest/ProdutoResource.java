package br.com.johnatanbrayan.web.rest;

import br.com.johnatanbrayan.service.ProdutoService;
import br.com.johnatanbrayan.web.rest.errors.BadRequestAlertException;
import br.com.johnatanbrayan.service.dto.ProdutoDTO;

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

/**
 * REST controller for managing {@link br.com.johnatanbrayan.domain.Produto}.
 */
@RestController
@RequestMapping("/api")
public class ProdutoResource {

    private final Logger log = LoggerFactory.getLogger(ProdutoResource.class);

    private static final String ENTITY_NAME = "produto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProdutoService produtoService;

    public ProdutoResource(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    /**
     * {@code POST  /produtos} : Create a new produto.
     *
     * @param produtoDTO the produtoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produtoDTO, or with status {@code 400 (Bad Request)} if the produto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/produtos")
    public ResponseEntity<ProdutoDTO> createProduto(@Valid @RequestBody ProdutoDTO produtoDTO) throws URISyntaxException {
        log.debug("REST request to save Produto : {}", produtoDTO);
        if (produtoDTO.getId() != null) {
            throw new BadRequestAlertException("A new produto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProdutoDTO result = produtoService.save(produtoDTO);
        return ResponseEntity.created(new URI("/api/produtos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /produtos} : Updates an existing produto.
     *
     * @param produtoDTO the produtoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produtoDTO,
     * or with status {@code 400 (Bad Request)} if the produtoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produtoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/produtos")
    public ResponseEntity<ProdutoDTO> updateProduto(@Valid @RequestBody ProdutoDTO produtoDTO) throws URISyntaxException {
        log.debug("REST request to update Produto : {}", produtoDTO);
        if (produtoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProdutoDTO result = produtoService.save(produtoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produtoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /produtos} : get all the produtos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produtos in body.
     */
    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoDTO>> getAllProdutos(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Produtos");
        Page<ProdutoDTO> page;
        if (eagerload) {
            page = produtoService.findAllWithEagerRelationships(pageable);
        } else {
            page = produtoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /produtos/:id} : get the "id" produto.
     *
     * @param id the id of the produtoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produtoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produtos/{id}")
    public ResponseEntity<ProdutoDTO> getProduto(@PathVariable Long id) {
        log.debug("REST request to get Produto : {}", id);
        Optional<ProdutoDTO> produtoDTO = produtoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produtoDTO);
    }

    /**
     * {@code DELETE  /produtos/:id} : delete the "id" produto.
     *
     * @param id the id of the produtoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        log.debug("REST request to delete Produto : {}", id);
        produtoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
