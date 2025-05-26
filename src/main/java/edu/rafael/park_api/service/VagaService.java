package edu.rafael.park_api.service;

import edu.rafael.park_api.entity.Vaga;
import edu.rafael.park_api.exception.CodigoUniqueViolationException;
import edu.rafael.park_api.exception.EntitiesNotFoundException;
import edu.rafael.park_api.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class VagaService {

    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga salvar(Vaga vaga) {
        try {
            return vagaRepository.save(vaga);
        } catch (DataIntegrityViolationException ex) {
            throw new CodigoUniqueViolationException(String.format("Vaga com código %s já cadastrado", vaga.getCodigo()));
        }
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorCodigo(String codigo) {
        return vagaRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntitiesNotFoundException(String.format("Vaga com código %s não encontrada", codigo))
        );
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorVagaLivre() {
        return vagaRepository.findFirstByStatus(Vaga.StatusVaga.LIVRE).orElseThrow(
                () -> new EntitiesNotFoundException("Nenhuma vaga livre encontrada")
        );
    }
}
