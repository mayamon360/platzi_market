package com.platzi.market.domain.service;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
* Para inidcar que la clase es un servcio de la logica de negocio
* Este servicio inyecta el ProductRepository.
*
* Esta clase service sirve como negciador entre el controldador y la
* capa del repositorio.
* */
@Service
public class ProductService {

    /*
    * Inyectamos ProductRepository e internamente Spring crea un
    * objeto de la clase ProductRepository, esta implementacion
    * la hace Spring automaticamente por lo que no es necesario
    * crear como tal una clase que implemente a la interface
    * ProductRepository.
    * */
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.getAll();
    }

    public Optional<Product> getById(int productId) {
        return productRepository.getById(productId);
    }

    public Optional<List<Product>> getByCategory(int categoryId) {
        return productRepository.getByCategory(categoryId);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public boolean delete(int productId) {
        /*
        * El map se ejecuta(lineas 51(despues de .map(..., 52, 53) cuando el producto existe
        * sino existe retorna un false.
        * */
        return getById(productId).map(product -> {
            productRepository.delete(productId);
            return true;
        }).orElse(false);

        /*
        Las lineas anteriores se pueden hacer de la siguiente manera:

        if( getById(productId).isPresent() ) {
            productRepository.delete(productId);
            return true;
        } else {
            return false;
        }

        Pero usando el metodo map de un Optional se puede simplificar evitando
        colocar if - else.
        */
    }


}
