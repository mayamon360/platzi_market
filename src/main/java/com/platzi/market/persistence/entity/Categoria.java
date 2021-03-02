package com.platzi.market.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @Column(name = "id_categoria")
    private Integer idCategoria;

    private String descripcion;

    private Boolean estado;

    public Integer getIdCategoria() {
        return idCategoria;
    }

    /*
    * Una categoria pertenece a muchos productos
    *
    * mappedBy hace referencia a que la relacion ya fue construida por la
    * entidad Producto a traves del atributo categoria.
    *
    * */
    @OneToMany(mappedBy = "categoria")
    /*
    * Recuperar la lista de Productos que pertencen a la Categoria
    * */
    private List<Producto> productos;


    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
