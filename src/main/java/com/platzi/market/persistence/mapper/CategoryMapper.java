package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.Category;
import com.platzi.market.persistence.entity.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

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
 * */

/*
 * @Mapper Indica al proyecto que es un mapeador.
 * */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // Inidca la traduccion de los objetos
    // source(fuente) => target(destino)
    @Mappings({
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "descripcion", target = "category"),
            @Mapping(source = "estado", target = "active"),
    })
    // Convierte una entidad Categoria a un dominio Category
    Category toCategory(Categoria categoria);

    // Conversion inversa a la anterior
    @InheritInverseConfiguration
    // El atributo productos que espera la entidad, no va a ir por ello debe ignorarlo
    @Mapping(target = "productos", ignore = true)
    // Convierte un dominio Category a una Entidad Categoria
    Categoria toCategoria(Category category);
}
