package com.platzi.market.persistence.crud;

import com.platzi.market.persistence.entity.Producto;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

/*
* CrudRepository<T, ID>, Recibe la clase entidad y el tipo de la clave primaria
* */
public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {

    /*
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
     *
     * CrudRepository provee de muchas operaciones basicas para trabajar con una base de datos.
     * Como guardar, listar, eliminar.
     *
     * En ocaciones necesitamos consultas que el Repository de Spring Data no nos puede ofrecer.
     *
     * Query Methods: Proveen la posibilidad de generar consultas mediante el nombre de los
     *                metodos.
     *                Tienen la posibilidad de retornar Optional<T>, que permite operar con
     *                datos del cual no tenemos certeza cual es su valor o si estan o no presentes
     *                y asi poder operar cuando el dato esta presente sin lidiar con multiples
     *                validaciones para saber si el valor es vacio o nulo.
     *
     * El siguiente query method hace una busqueda por idCategoria ordenando por Nombre de
     * forma ascendente y retorna una lista de los resultados.
     * El segundo query method hace una busqueda por CantidadStock menor que y el estado, donde
     * los valores de cada uno de estos se pasan como parametros.
     *
     * Al usar query methods se debe respetar el CamelCase en la firma.
     *
     * Esto evita tener que escribir SQL nativo.
     *
     * */
    List<Producto> findByIdCategoriaOrderByNombreAsc(int idCategoria);

    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidad, boolean estado);

    /*
    * Es posible hacer lo anterior con un query nativo, para ello se debe usar la anotacion @query
    * y despues especificar el SQL y adiconalmente indicar el nativeQuery como true. Con esto el
    * nombre del metodo puede ser cuaqluiera, ya que se tomara el @query y no el nombre del metodo
    * para generar y ejecutar la consulta, por ejemplo findByCategoria() o buscarPorCategoria().
    *
    * @Query(value = "SELECT * FROM productos WHERE id_categoria = ? ORDER BY nombre ASC", nativeQuery = true)
    * List<Producto> findByCategoria(int idCategoria);
    *
    * Tambien se puede usar JPQL en lugar de SQL nativo:
    *
    * @Query("SELECT p FROM Producto WHERE u.idCategoria = ?")
    * ...
    *
    * */

}
