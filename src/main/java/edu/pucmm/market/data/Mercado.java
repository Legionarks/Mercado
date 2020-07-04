package edu.pucmm.market.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jasypt.util.password.StrongPasswordEncryptor;

import edu.pucmm.market.services.ProductoExistenciaServicio;
import edu.pucmm.market.services.ProductoServicio;
import edu.pucmm.market.services.UsuarioServicio;
import edu.pucmm.market.services.VentaDetalleServicio;
import edu.pucmm.market.services.VentaServicio;

public class Mercado {

    private final UsuarioServicio usuarioServicio;
    private final ProductoServicio productoServicio;
    private final ProductoExistenciaServicio productoExistenciaServicio;
    private final VentaServicio ventaServicio;
    private final VentaDetalleServicio ventaDetalleServicio;

    public Mercado() {
	this.usuarioServicio = new UsuarioServicio();
	this.productoServicio = new ProductoServicio();
	this.productoExistenciaServicio = new ProductoExistenciaServicio();
	this.ventaServicio = new VentaServicio();
	this.ventaDetalleServicio = new VentaDetalleServicio();
    }

    public boolean agregar(Producto producto, BigDecimal precio) {
	return this.productoExistenciaServicio.crear(new ProductoExistencia(producto, precio));
    }

    public boolean quitar(Producto producto) {
	return this.productoExistenciaServicio.eliminar(this.buscarExistencia(producto));
    }

    @Deprecated
    public void eliminar(Producto producto) throws Exception {
	boolean ok = false;
	ok = this.quitar(producto);
	ok = this.ventaDetalleServicio.eliminarTodoProducto(producto.getId());
	ok = this.productoServicio.eliminar(producto);

	throw new Exception("Producto eliminado de todas las referencias: " + ok);
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

    public boolean crearEditarProducto(int id, String nombre, BigDecimal precio) {
	boolean ok = false;
	Producto producto = this.buscarProducto(id);
	ProductoExistencia existencia;

	if (producto == null) {
	    producto = new Producto(nombre);
	    existencia = new ProductoExistencia(producto, precio);
	    ok = this.productoServicio.crear(producto);
	    ok = this.productoExistenciaServicio.crear(existencia);
	} else {
	    producto.setNombre(nombre);
	    ok = this.productoServicio.editar(producto);

	    existencia = this.buscarExistencia(producto);
	    
	    if (existencia != null) {
		existencia.setPrecio(precio);
		ok = this.productoExistenciaServicio.editar(existencia);
	    }
	}

	return ok;
    }

    public boolean procesarCompra(String cliente, CarroCompra carrito) {
	boolean ok = false;
	Venta venta;
	Set<VentaDetalle> detalles;

	if (!(carrito.getCarro().isEmpty())) {
	    detalles = new HashSet<VentaDetalle>();

	    for (ProductoCarro carro : carrito.getCarro()) {
		detalles.add(new VentaDetalle(carro.getExistencia().getProducto(), carro.getExistencia().getPrecio(),
			carro.getCantidad()));
	    }

	    venta = new Venta(cliente, detalles);
	    ok = this.ventaServicio.crear(venta);

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
	StrongPasswordEncryptor encriptador;
	String contraseñaEncriptada;

	if (usuario != null) {
	    encriptador = new StrongPasswordEncryptor();
	    contraseñaEncriptada = encriptador.encryptPassword(usuario.getContraseña());

	    if (!(encriptado) ? encriptador.checkPassword(usuario.getContraseña(), contraseña)
		    : encriptador.checkPassword(contraseña, contraseñaEncriptada)) {
		usuario = null;
	    }
	}

	return usuario;
    }
}