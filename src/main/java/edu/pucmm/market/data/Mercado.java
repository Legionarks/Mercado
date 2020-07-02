package edu.pucmm.market.data;

import java.math.BigDecimal;
import java.util.List;

import org.jasypt.util.password.StrongPasswordEncryptor;

import edu.pucmm.market.services.ProductoServicio;
import edu.pucmm.market.services.UsuarioServicio;
import edu.pucmm.market.services.VentaServicio;

public class Mercado {
    
    private final UsuarioServicio usuarioServicio;
    private final ProductoServicio productoServicio;
    private final VentaServicio ventaServicio;

    public Mercado() {
	this.usuarioServicio = new UsuarioServicio();
	this.productoServicio = new ProductoServicio();
	this.ventaServicio = new VentaServicio();
    }

    public List<VentasProductos> getVentasProductos() {
	return this.ventaServicio.getVentas();
    }

    public List<Producto> getProductos() {
	return productoServicio.getProductos(false);
    }

    public boolean eliminarProducto(int id) {
	boolean ok = false;
	Producto auxProducto = buscarProducto(id);

	if (auxProducto != null) {
	    ok = this.productoServicio.eliminarProducto(auxProducto);
	}

	return ok;
    }

    public Producto buscarProducto(int id) {
	return this.productoServicio.buscarProducto(id, false);
    }

    public boolean crearEditarProducto(int id, String nombre, BigDecimal precio) {
	boolean ok = false;
	Producto auxProducto = buscarProducto(id);

	if (id == -1 || auxProducto == null) {
	    auxProducto = new Producto(1, nombre, precio);
	    ok = this.productoServicio.crearProducto(auxProducto, false);
	} else {
	    auxProducto.setNombre(nombre);
	    auxProducto.setPrecio(precio);
	    ok = this.productoServicio.editarProducto(auxProducto);
	}

	return ok;
    }

    public Usuario autenticarUsuario(String idUsuario, String contraseña, boolean encriptado) {
	Usuario usuario = this.usuarioServicio.buscarUsuario(idUsuario);
	StrongPasswordEncryptor encriptador;
	String contraseñaEncriptada;

	if (usuario != null) {
	    encriptador = new StrongPasswordEncryptor();
	    contraseñaEncriptada = encriptador.encryptPassword(usuario.getContraseña());
	    
	    if (encriptado ? encriptador.checkPassword(usuario.getContraseña(), contraseña)
		    : encriptador.checkPassword(contraseña, contraseñaEncriptada)) {
		return usuario;
	    } else {
		usuario = null;
	    }
	}

	return usuario;
    }

    public boolean procesarCompra(String cliente, CarroCompra carrito) {
	boolean ok = false;
	VentasProductos ventasProductos;

	if (!(carrito.getListaProductos().isEmpty())) {
	    ventasProductos = new VentasProductos(1, cliente, carrito.getListaProductos());
	    ok = this.ventaServicio.crearVenta(ventasProductos);
	    carrito.limpiarCarrito();
	}

	return ok;
    }

    public VentasProductos buscarVenta(long id) {
	return this.ventaServicio.buscarVenta(id);
    }

    public boolean crearUsuario(String usuario, String nombre, String password) {
	boolean ok = false;

	ok = this.usuarioServicio.crearUsuario(new Usuario(usuario, nombre, password));

	return ok;
    }
}