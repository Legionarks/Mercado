package edu.pucmm.market.services;

import edu.pucmm.market.data.Producto;

public class ProductoServicio extends DBMServicio<Producto> {

    public ProductoServicio() {
	super(Producto.class);
    }
}