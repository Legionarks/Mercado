package edu.pucmm.market.data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jasypt.util.text.AES256TextEncryptor;

import edu.pucmm.market.services.ProductoExistenciaServicio;
import edu.pucmm.market.services.ProductoServicio;
import edu.pucmm.market.services.UsuarioServicio;
import edu.pucmm.market.services.VentaServicio;

public class Mercado {

    private final AES256TextEncryptor encriptador;
    private final UsuarioServicio usuarioServicio;
    private final ProductoServicio productoServicio;
    private final ProductoExistenciaServicio productoExistenciaServicio;
    private final VentaServicio ventaServicio;

    public Mercado(AES256TextEncryptor encriptador) {
	this.encriptador = encriptador;
	this.usuarioServicio = new UsuarioServicio();
	this.productoServicio = new ProductoServicio();
	this.productoExistenciaServicio = new ProductoExistenciaServicio();
	this.ventaServicio = new VentaServicio();
    }

    public List<Venta> getVentas() {
	return this.ventaServicio.buscarTodos();
    }

    public List<Producto> getProductos() {
	return this.productoServicio.buscarTodos();
    }

    public List<ProductoExistencia> getExistencias() {
	return this.productoExistenciaServicio.buscarTodos();
    }

    public Producto buscarProducto(int id) {
	return this.productoServicio.buscar(id);
    }

    public ProductoExistencia buscarExistencia(Producto producto) {
	return this.productoExistenciaServicio.buscar(producto.getId());
    }
    
    public ProductoExistencia buscarExistencia(int id) {
	return this.productoExistenciaServicio.buscar(id);
    }
    
    public boolean agregar(Producto producto, BigDecimal precio) {
	boolean ok = false;
	ok = this.productoServicio.crear(producto);
	ok = this.productoExistenciaServicio.crear(new ProductoExistencia(producto, precio));
	return ok;
    }

    public boolean quitar(Producto producto) {
	return this.productoExistenciaServicio.eliminar(this.buscarExistencia(producto));
    }

    public boolean crearEditarProducto(int id, String nombre, BigDecimal precio) {
	boolean ok = false;
	Producto producto = this.buscarProducto(id);
	ProductoExistencia existencia;

	if (producto == null) {
	    producto = new Producto(nombre);
	    ok = this.agregar(producto, precio);
	} else {
	    producto.setNombre(nombre);
	    ok = this.productoServicio.editar(producto);

	    existencia = this.buscarExistencia(producto);
	    
	    if (existencia == null) {
		ok = this.productoExistenciaServicio.crear(new ProductoExistencia(producto, precio));
	    } else {
		existencia.setPrecio(precio);
		ok = this.productoExistenciaServicio.editar(existencia);
	    }
	}

	return ok;
    }

    public boolean procesarCompra(String cliente, Carro carrito) {
	boolean ok = false;
	Set<VentaDetalle> detalles;

	if (!(carrito.getProductos().isEmpty())) {
	    detalles = new HashSet<VentaDetalle>();

	    for (ProductoCarro carro : carrito.getProductos()) {
		detalles.add(new VentaDetalle(carro.getExistencia().getProducto(), carro.getExistencia().getPrecio(),
			carro.getCantidad()));
	    }

	    ok = this.ventaServicio.crear(new Venta(cliente, detalles));

	    if (ok) {
		carrito.limpiarCarrito();
	    }
	}

	return ok;
    }

    public boolean crearUsuario(String usuario, String nombre, String password) {
	return this.usuarioServicio.crear(new Usuario(usuario, nombre, password));
    }

    public Usuario autenticarUsuario(String idUsuario, String contraseña, boolean encriptado) {
	Usuario usuario = this.usuarioServicio.buscar(idUsuario);

	if (usuario != null) {
	    if (encriptado) {
		if (!(encriptador.decrypt(contraseña).equals(encriptador.decrypt(usuario.getContraseña())))) {
		    usuario = null;
		}
	    } else {
		if (!(contraseña.equals(encriptador.decrypt(usuario.getContraseña())))) {
		    usuario = null;
		}
	    }
	}

	return usuario;
    }
}