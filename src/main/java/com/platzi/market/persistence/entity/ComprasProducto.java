package com.platzi.market.persistence.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "compras_productos")
public class ComprasProducto {

    /*
    * @EmbeddedId indica que la clave primaria es compuesta y esta dada por otra clase. (ComprasProductoPK)
    */
    @EmbeddedId
    private ComprasProductoPK id;

    private Integer cantidad;
    private Double total;
    private Boolean estado;

    /*
    * @ManyToOne            Muchas ComprasProductoPK (items) pertenecen a una Compra
    *
    * @MapsId("idCompra")   Cuando se guarde una compra a su vez se van ir guardando los productos con
    *                       el id de la compra correspondiente.
    * @JoinColumn           hace referencia a la clave foranea que hace relacion a la tabla compras.
    *                       No se puede insertar, actualizar o eliminar a traves de la relacion.
    *
    * El atributo compra tendra la Compra a la que pertence el item.
    * */
    @ManyToOne
    @MapsId("idCompra")
    @JoinColumn(name = "id_compra", insertable = false, updatable = false)
    private Compra compra;

    /*
    * @ManyToOne            Muchas ComprasProductoPK (items) pertenecen a un Producto.
    *
    * JoinColumn            hace referencia a la clave foranea que hace relacion a la tabla productos.
    *                       No se puede insertar, actualizar o eliminar a traves de la relacion.
    *
    * El atributo producto tendra el producto al quepertence el item.
    * */
    @ManyToOne
    @JoinColumn(name = "id_producto", insertable = false, updatable = false)
    private Producto producto;


    public ComprasProductoPK getId() {
        return id;
    }

    public void setId(ComprasProductoPK id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
