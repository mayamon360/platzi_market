package com.platzi.market.web.controller;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/*
* @RestController   Indica a Spring que esta clase sera un controlador de una API Rest.
*                   Indica que nuestra API sera expuesta.
*
* @RequestMapping   Indica que peticion o que path van a aceptar las peticiones
*
* */
@RestController
@RequestMapping("/products")
public class ProductController {

    /*
    * Inyectamos el service ProductService
    * */
    @Autowired
    private ProductService productService;

    /*
    * @GetMapping, @PostMapping o @DeleteMapping: Indican que nuestros metodos seran expuestos
    * mediante alguno de estos metodo HTTP.
    *
    * ResponseEntity    sirve para controlar los llamados y respuestas
    *                   que recibe el controldaor. Recibe en el operador
    *                   diamente lo que en realidad esta retornabdo el cuerpo
    *                   de la peticion que es una lista de product.
    * @HttpStatus       indica el tipo de codigo de retorno segun sea el caso.
    *
    * Los metodo siguiente se restructuar para usar estos dos conceptos.
    *
    * public List<Product> getAll(){
    *    return productRepository.getAll();
    * }
    * */
    @GetMapping("/")

    @ApiOperation(value = "Get all supermarket products", authorizations = { @Authorization(value="JWT") })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })

    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    /*
    * @PathVariable indica el nombre de la variable que se pasa por el path, el nombre
    * puede ser diferente, por ejemplo, unicamente id.
    *
    * public Optional<Product> getById(@PathVariable("productId") int productId) {
    *    return productService.getById(productId);
    * }
    * */
    @GetMapping("/{productId}")

    @ApiOperation("Search a product with an ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Product not found")
    })

    public ResponseEntity<Product> getById(@ApiParam(value = "The id of the product", required = true, example = "7")
                                               @PathVariable("productId") int productId) {
        return productService.getById(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /*
    * public Optional<List<Product>> getByCategory(@PathVariable("categoryId") int categoryId) {
    *    return productService.getByCategory(categoryId);
    * }
    * */
    @GetMapping("/category/{categoryId}")

    @ApiOperation("Search products for the Category ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Category not found")
    })

    public ResponseEntity<List<Product>> getByCategory(@ApiParam(value = "The id of the catgeory", required = true, example = "1")
                                                           @PathVariable("categoryId") int categoryId) {
        /*
        Optional<List<Product>> products = productService.getByCategory(categoryId);
        if(products.isPresent() && !products.get().isEmpty()) {
            return new ResponseEntity<>(products.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        */
        return productService.getByCategory(categoryId).filter(Predicate.not(List::isEmpty))
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /*
    * public Product save(@RequestBody Product product) {
    *    return productService.save(product);
    * }
    * */
    @PostMapping("/")

    @ApiOperation("Save a new product")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product created")
    })

    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    /*
    * public boolean delete(@PathVariable("productId") int productId) {
    *    return productService.delete(productId);
    * }
    * */
    @DeleteMapping("/{productId}")

    @ApiOperation("Delete a product with an ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Product not found")
    })


    public ResponseEntity delete(@PathVariable("productId") int productId) {
        if(productService.delete(productId)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
