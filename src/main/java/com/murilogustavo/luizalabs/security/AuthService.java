package com.murilogustavo.luizalabs.security;

import com.murilogustavo.luizalabs.exception.ResourceNotFoundException;
import com.murilogustavo.luizalabs.model.Client;
import com.murilogustavo.luizalabs.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("authService")
public class AuthService {

    @Autowired
    private ClientRepository clientRepository;

    public boolean canAccessClient(Long clientId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) return true;

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        return client.getId().equals(clientId);
    }
}
