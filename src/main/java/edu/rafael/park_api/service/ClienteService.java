package edu.rafael.park_api.service;

import edu.rafael.park_api.entity.Cliente;
import edu.rafael.park_api.exception.CpfUniqueViolationException;
import edu.rafael.park_api.exception.EntitiesNotFoundException;
import edu.rafael.park_api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(String.format("CPF %s já cadastrado", cliente.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new EntitiesNotFoundException(String.format("Cliente id=%s não encontrado", id))
        );
    }
}
