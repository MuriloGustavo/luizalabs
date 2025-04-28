package com.murilogustavo.luizalabs.service;

import com.murilogustavo.luizalabs.dto.ClientDTO;
import com.murilogustavo.luizalabs.exception.EmailAlreadyRegisteredException;
import com.murilogustavo.luizalabs.exception.ResourceNotFoundException;
import com.murilogustavo.luizalabs.model.Client;
import com.murilogustavo.luizalabs.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClientRepository repository;

    public List<ClientDTO> findAll() {
        return repository.findAll().stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .toList();
    }

    public ClientDTO findById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        return modelMapper.map(client, ClientDTO.class);
    }

    @Transactional
    public ClientDTO create(ClientDTO clientDTO) {
        repository.findByEmail(clientDTO.getEmail()).ifPresent(c -> {
            throw new EmailAlreadyRegisteredException("Email already registered");
        });
        Client client = modelMapper.map(clientDTO, Client.class);
        return modelMapper.map(repository.save(client), ClientDTO.class);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDTO) {
        Client existingClient = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        if (!existingClient.getEmail().equals(clientDTO.getEmail())) {
            repository.findByEmail(clientDTO.getEmail()).ifPresent(c -> {
                throw new EmailAlreadyRegisteredException("Email already registered");
            });
        }

        existingClient.setName(clientDTO.getName());
        existingClient.setEmail(clientDTO.getEmail());

        return modelMapper.map(repository.save(existingClient), ClientDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
