package br.com.johnatanbrayan.web.rest;

import br.com.johnatanbrayan.service.CategoriaService;
import br.com.johnatanbrayan.web.rest.errors.BadRequestAlertException;
import br.com.johnatanbrayan.service.dto.CategoriaDTO;

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
 * REST controller for managing {@link br.com.johnatanbrayan.domain.Categoria}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaResource.class);

    private static final String ENTITY_NAME = "categoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaService categoriaService;

    public CategoriaResource(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * {@code POST  /categorias} : Create a new categoria.
     *
     * @param categoriaDTO the categoriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaDTO, or with status {@code 400 (Bad Request)} if the categoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorias")
    public ResponseEntity<CategoriaDTO> createCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) throws URISyntaxException {
        log.debug("REST request to save Categoria : {}", categoriaDTO);
        if (categoriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaDTO result = categoriaService.save(categoriaDTO);
        return ResponseEntity.created(new URI("/api/categorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorias} : Updates an existing categoria.
     *
     * @param categoriaDTO the categoriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaDTO,
     * or with status {@code 400 (Bad Request)} if the categoriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorias")
    public ResponseEntity<CategoriaDTO> updateCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) throws URISyntaxException {
        log.debug("REST request to update Categoria : {}", categoriaDTO);
        if (categoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriaDTO result = categoriaService.save(categoriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categorias} : get all the categorias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorias in body.
     */
    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias(Pageable pageable) {
        log.debug("REST request to get a page of Categorias");
        Page<CategoriaDTO> page = categoriaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categorias/:id} : get the "id" categoria.
     *
     * @param id the id of the categoriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDTO> getCategoria(@PathVariable Long id) {
        log.debug("REST request to get Categoria : {}", id);
        Optional<CategoriaDTO> categoriaDTO = categoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaDTO);
    }

    /**
     * {@code DELETE  /categorias/:id} : delete the "id" categoria.
     *
     * @param id the id of the categoriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        log.debug("REST request to delete Categoria : {}", id);
        categoriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
