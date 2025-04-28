package com.murilogustavo.luizalabs.service;

import com.murilogustavo.luizalabs.dto.ProductDTO;
import com.murilogustavo.luizalabs.dto.ProductResponseDTO;
import com.murilogustavo.luizalabs.exception.ProductAlreadyFavorited;
import com.murilogustavo.luizalabs.exception.ResourceNotFoundException;
import com.murilogustavo.luizalabs.model.Client;
import com.murilogustavo.luizalabs.model.Product;
import com.murilogustavo.luizalabs.repository.ClientRepository;
import com.murilogustavo.luizalabs.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService {

    private final String PRODUCT_API_URL = "https://fakestoreapi.com/products/";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<ProductDTO> listFavorites(Long clientId) {
        Client client = getClient(clientId);
        return productRepository.findByClient(client).stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    @Transactional
    public ProductDTO addFavorite(Long clientId, Long productId) {
        Client client = getClient(clientId);

        if (productRepository.existsByClientAndProductId(client, productId.toString())) {
            throw new ProductAlreadyFavorited("Product already favorited by this client");
        }

        ProductResponseDTO response = restTemplate.getForObject(PRODUCT_API_URL + productId, ProductResponseDTO.class);
        if (response == null || response.getId() == null) {
            throw new ResourceNotFoundException("Product not found in external API");
        }

        Product product = Product.builder()
                .productId(String.valueOf(response.getId()))
                .title(response.getTitle())
                .image(response.getImage())
                .price(response.getPrice())
                .review(response.getRating() != null ? response.getRating().toString() : null)
                .client(client)
                .build();

        return modelMapper.map(productRepository.save(product), ProductDTO.class);
    }

    @Transactional
    public void removeFavorite(Long clientId, String productId) {
        Client client = getClient(clientId);
        productRepository.deleteByClientAndProductId(client, productId);
    }

    private Client getClient(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }
}
