package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.Product;
import com.platzi.market.persistence.entity.Producto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/*
* https://mapstruct.org/
* Se necesita instalar la dependencia.
*
* El patron Data Mapper nos ayuda a convertir dos objetos que pueden hacer una misma labor
* en este caso una Entidad en una clase de dominio y viserversa.
*
* El proyecto tiene una estructura por capas enfocada al dominio.
*
* Esto toma sentido cuando se trabaja con APIs, ya que evita exponer la BD en la API, ademas
* permite desacoplar la API a una BD puntual. Tambien evita tener campos innecesarios en el API
* y evita mezclar idiomas en el dominio.
*
* Sí tienen los mismos nombres de atributos solo basta con crear el Mapper
* pero sin definirle ningún mapeo explicito porque se harán automáticamente.
*
* La idea es que el objeto de dominio solo lleve lo que sea estrictamente necesario,
* por lo cual debería tener menos campos que el entity y en ese sentido el Mapper
* tendrá uno que otro ignore.
*
* */

/*
* @Mapper Indica al proyecto que es un mapeador.
* uses, indica un mapeador existente que servidra en este mapeador para la conversion de un
* objeto, en este caso categoria => category
*
* Se debe indicar que el modelo del componente sera de Spring, esto para poder hacer la
* inyeccion de dependencias.
* */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    // Inidca la traduccion de los objetos
    // source(fuente) => target(destino)
    @Mappings({
            @Mapping(source = "idProducto", target = "productId"),
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "precioVenta", target = "price"),
            @Mapping(source = "cantidadStock", target = "stock"),
            @Mapping(source = "estado", target = "active"),
            @Mapping(source = "categoria", target = "category")
    })
    // Convierte una entidad Producto en un dominio Product
    Product toProduct(Producto producto);

    // Convierte una lista de entidades Producto en una lista de objetos Product
    // Utiliza los mappings anteriores
    List<Product> toProducts(List<Producto> productos);

    // Conversion inversa a la anterior
    @InheritInverseConfiguration
    // El atributo codigoBarras que espera la entidad, no va a ir por ello debe ignorarlo
    @Mapping(target = "codigoBarras", ignore = true)
    // Convierte un dominio Product a una entidad Producto
    Producto toProducto(Product product);

}
