package com.platzi.market.domain.repository;

import com.platzi.market.domain.Product;
import java.util.List;
import java.util.Optional;

/*
* Este repositorio indica el nombre de los metodos que cualquier repositorio que
* quiera trabajar con la clase de dominio Product, tenga que implementar.
*
* Es similar al que se tiene en la capa de persistencia, que de echo sera la clase
* que implemente esta interface. Viendose obligada a implentar estos metodos pero
* en terminos de dominio, para lo cual debe hacer la conversion de Entidad a clase de
* dominio.
*
* Este tipo de clases definien las reglas que tendra el dominio al momento que
* cualquier repositorio quiera utilizar o acceder a productos dentro de una BD,
* esto garantiza no acomplar a una BD especifica, sino que siempre se hable en terminos
* de dominio en este caso Product.
* */
public interface ProductRepository {
    List<Product> getAll();
    Optional<Product> getById(int productId);
    Optional<List<Product>> getByCategory(int categoryId);
    Optional<List<Product>> getScarseProducts(int quantity);
    Product save(Product product);
    void delete(int productId);
}
