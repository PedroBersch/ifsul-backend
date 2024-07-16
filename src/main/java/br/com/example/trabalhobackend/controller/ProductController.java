package br.com.example.trabalhobackend.controller;

import br.com.example.trabalhobackend.model.Product;
import br.com.example.trabalhobackend.model.ProductList;
import br.com.example.trabalhobackend.model.response.MessageResponse;
import br.com.example.trabalhobackend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "Produto", description = "CRUD de Produto")
public class ProductController {
    private final ProductService service;

    @Operation(
        summary = "Cadastra produto",
        description = "Cadastra produto e retorna cadastrado da base de dados."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Produto cadastrado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Product.class),
                examples = @ExampleObject(value = """
                    {
                        "id": 5,
                        "name": "Produto nome",
                        "price": 1.0
                    }
                    """)))
    })
    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product p = service.save(product);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Obtem todos produtos",
        description = "Retorna produtos cadastrados na base de dados."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produtos obtidos com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProductList.class),
                examples = @ExampleObject(value = """
                    {
                        "qttd": 2,
                        "products": [
                            {
                                "id": 1,
                                "name": "Novo produto 1",
                                "price": 1.1
                            },
                            {
                                "id": 2,
                                "name": "Novo produto 2",
                                "price": 1.2
                            }
                        ]
                    }
                    """)))
    })
    @GetMapping
    public ResponseEntity<ProductList> findAllProduct() {
        List<Product> pList = service.findAllProduct();
        Long qttdProduct = service.countProduct();
        ProductList productList = new ProductList(qttdProduct, pList);
        return ResponseEntity.ok(productList);
    }

    @Operation(
        summary = "Obtem produto por ID",
        description = "Retorna um produto específico baseado no ID fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produto obtido com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Product.class),
                examples = @ExampleObject(value = """
                    {
                        "id": 1,
                        "name": "Nome do Produto",
                        "price": 1.1
                    }
                    """)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) {
        Product p = service.findById(id);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @Operation(
        summary = "Deleta um produto por ID",
        description = "Remove um produto específico da base de dados baseado no ID fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produto deletado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(value = """
                    {
                        "message": "Produto deletado com Id: 1"
                    }
                    """)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(new MessageResponse("Produto deletado com Id: " + id), HttpStatus.OK);
    }

    @Operation(
        summary = "Atualiza um produto por ID",
        description = "Atualiza os detalhes de um produto específico baseado no ID fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produto atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(value = """
                    {
                        "message": "Produto atualizado com Id: 1"
                    }
                    """)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        service.update(id, product);
        return new ResponseEntity<>(new MessageResponse("Produto atualizado com Id: " + id ), HttpStatus.OK);
    }
}
