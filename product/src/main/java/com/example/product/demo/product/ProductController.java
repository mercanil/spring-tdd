package com.example.product.demo.product;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Integer id) {
        return productService.findById(id)
                .map(product -> {
                    try {
                        return ResponseEntity.ok().eTag(Integer.toString(product.getVersion()))
                                .location(new URI("/product/" + product.getId()))
                                .body(product);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProduct() {
        return productService.findAll();
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product saved = productService.save(product);

        try {
            return ResponseEntity.created(new URI("product" + saved.getId()))
                    .eTag(saved.getId())
                    .body(saved);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product ,
                                                 @RequestHeader("If-Match") Integer ifMatch ,
                                                 @PathVariable("id") Integer id){
        Optional<Product> existingProduct = productService.findById(id);
        existingProduct.map(p->{
            if(!p.getVersion().equals(ifMatch)){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            p.setVersion(product.getVersion());
            p.setQuantity(product.getQuantity());
            p.setVersion(p.getVersion()+1);

            productService.updateProduct(p);
            return ResponseEntity.ok().body(p);
        });
        return ResponseEntity.notFound().build();
    }

}
