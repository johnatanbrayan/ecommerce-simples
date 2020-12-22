package br.com.johnatanbrayan.service;

import br.com.johnatanbrayan.domain.Pagamento;
import br.com.johnatanbrayan.repository.PagamentoRepository;
import br.com.johnatanbrayan.service.dto.PagamentoDTO;
import br.com.johnatanbrayan.service.mapper.PagamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Pagamento}.
 */
@Service
@Transactional
public class PagamentoService {

    private final Logger log = LoggerFactory.getLogger(PagamentoService.class);

    private final PagamentoRepository pagamentoRepository;

    private final PagamentoMapper pagamentoMapper;

    public PagamentoService(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoMapper = pagamentoMapper;
    }

    /**
     * Save a pagamento.
     *
     * @param pagamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public PagamentoDTO save(PagamentoDTO pagamentoDTO) {
        log.debug("Request to save Pagamento : {}", pagamentoDTO);
        Pagamento pagamento = pagamentoMapper.toEntity(pagamentoDTO);
        pagamento = pagamentoRepository.save(pagamento);
        return pagamentoMapper.toDto(pagamento);
    }

    /**
     * Get all the pagamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PagamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pagamentos");
        return pagamentoRepository.findAll(pageable)
            .map(pagamentoMapper::toDto);
    }



    /**
     *  Get all the pagamentos where Pedido is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<PagamentoDTO> findAllWherePedidoIsNull() {
        log.debug("Request to get all pagamentos where Pedido is null");
        return StreamSupport
            .stream(pagamentoRepository.findAll().spliterator(), false)
            .filter(pagamento -> pagamento.getPedido() == null)
            .map(pagamentoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pagamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PagamentoDTO> findOne(Long id) {
        log.debug("Request to get Pagamento : {}", id);
        return pagamentoRepository.findById(id)
            .map(pagamentoMapper::toDto);
    }

    /**
     * Delete the pagamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pagamento : {}", id);
        pagamentoRepository.deleteById(id);
    }
}
