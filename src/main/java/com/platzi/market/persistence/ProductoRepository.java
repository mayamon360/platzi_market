package com.platzi.market.persistence;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import com.platzi.market.persistence.crud.ProductoCrudRepository;
import com.platzi.market.persistence.entity.Producto;
import com.platzi.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
* @Repository: indica que la clase sera la encarga de interactuar con la base de datos.
*
* Implementa la interface ProductRepository encargada de establecer los metodos que se tienen
* que implementar en terminos de dominio.
* */
@Repository
public class ProductoRepository implements ProductRepository {

    /*
    * @Autowired indica la inyeccion de dependencias, en este caso el atributo productoCrudRepository
    * y mapper deben tener esta anotacion ya que sin ella, unicamente se estan instanciando y tienen
    * un valor nulo. Al hacer la inyeccion lo que sucede es que Spring se encarga de instanciar estos
    * objetos en el momento que se mandan a llamar en los metodos.
    * Pero al utilizar la notacion se debe asegurar que el objeto que se va a inyectar en los atributos
    * debe ser un componente de Spring.
    * */
    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    /*
    * Atributo mapper ayudara a transformar los objetos de entidad en objetos de dominio
    * ya que los tipos de retorno que se esperan son Product y no Produto
    * */
    @Autowired
    private ProductMapper mapper;

    /*
    * Sobreescribe el metodo de ProductRepository.
    *
    * Obtener todos los productos.
    * */
    @Override
    public List<Product> getAll() {
        /*
         * Primero recuperamos de la BD para despues convertir los objetos.
         * Usar el metodo finAll() de CrudRepository mediante ProductoCrudRepository.
         * */
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        /*
         * Para retornar una lista de objetos de dominio Product se debe usar mapper para
         * convertir la lista de entidades tipo Producto a la de objetos de la dominio Product.
         *
         * Estos metodos estan definidos en el mapper. Lo que hacen es convertir la lista de
         * Producto y convertirla en la lista de Product.
         * */
        return mapper.toProducts(productos);
    }

    /*
    * Obtener un Producto por su id.
    * */
    @Override
    public Optional<Product> getById(int productId) {
        /*
         * Usar el metodo finById() de CrudRepository mediante ProductoCrudRepository.
         * */
        return productoCrudRepository.findById(productId).map(producto -> mapper.toProduct(producto));
    }

    /*
    * Obtener productos por una categoria
    * */
    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        /*
         * Para retornar una lista de objetos de tipo dominio Product, se debe usar mapper para
         * convertir la lista de entidad tipo Producto a una lista de tipo dominio Product,
         * adicionalmente usar el metodo estatico of de la clase Optional para convertir la lista
         * normal en una lista tipo Optional.
         *
         * El metodo of permite convertir cualquier objeto en un Optional.
         * */
        return Optional.of(mapper.toProducts(productos));
    }

    /*
    * Obtener los productos escasos, (determinados por una cantidad dada).
    * */
    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        /*
        * Como no se tiene un mapeador para convertir la lista Optional, se usa map().
        * La funcion map retorna un Optional y requiere como parametro una Funcion, en este caso se usa
        * una funcion lambda.
        * */
        return productos.map(prods -> mapper.toProducts(prods));
    }

    /*
    * Guadar un Producto y lo retorna.
    * */
    @Override
    public Product save(Product product) {
        // Se hace la converion de un Product a un Producto, ya que es lo que espera el metodo save()
        Producto producto = mapper.toProducto(product);
        return mapper.toProduct(productoCrudRepository.save(producto));
    }

    /*
    * Eliminar un producto por su id.
    * Se puede usar el metodo delete(Producto producto) pero se envia el objeto completo.
    * */
    @Override
    public void delete(int idProduct) {
        productoCrudRepository.deleteById(idProduct);
    }

}
