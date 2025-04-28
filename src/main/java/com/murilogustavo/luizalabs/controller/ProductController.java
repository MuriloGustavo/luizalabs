package com.murilogustavo.luizalabs.controller;

import com.murilogustavo.luizalabs.dto.ProductDTO;
import com.murilogustavo.luizalabs.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients/{clientId}/products")
@Tag(name = "Products", description = "Operations related to products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "List favorites products")
    @PreAuthorize("@authService.canAccessClient(#clientId)")
    public List<ProductDTO> listFavorites(@PathVariable Long clientId) {
        return productService.listFavorites(clientId);
    }

    @PostMapping("/{productId}")
    @Operation(summary = "Add favorite product")
    @PreAuthorize("@authService.canAccessClient(#clientId)")
    public ProductDTO addFavorite(@PathVariable Long clientId, @PathVariable Long productId) {
        return productService.addFavorite(clientId, productId);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove favorite product")
    @PreAuthorize("@authService.canAccessClient(#clientId)")
    public void removeFavorite(@PathVariable Long clientId, @PathVariable String productId) {
        productService.removeFavorite(clientId, productId);
    }
}
