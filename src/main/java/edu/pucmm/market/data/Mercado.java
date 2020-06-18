package edu.pucmm.market.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Mercado {

	private static final List<Usuario> USUARIOS = new ArrayList<Usuario>();
	private static final List<VentasProductos> VENTAS_PRODUCTOS = new ArrayList<VentasProductos>();
	private static final List<Producto> PRODUCTOS = new ArrayList<Producto>();
	private static final List<CarroCompra> CARRO_COMPRAS = new ArrayList<CarroCompra>();

	public Mercado() {
		this.inicializar();
	}

	private void inicializar() {
		this.usuarioDefecto();
		this.productosDefecto();
		this.ventasDefecto();
	}

	public static List<Usuario> getUsuarios() {
		return USUARIOS;
	}

	public static List<VentasProductos> getVentasProductos() {
		return VENTAS_PRODUCTOS;
	}

	public static List<Producto> getProductos() {
		return PRODUCTOS;
	}

	private void usuarioDefecto() {
		USUARIOS.add(new Usuario("admin", "admin", "admin"));
	}

	private void productosDefecto() {
		PRODUCTOS.add(new Producto(1, "Apio", BigDecimal.valueOf(55.25)));
		PRODUCTOS.add(new Producto(2, "Recaito", BigDecimal.valueOf(20.00)));
		PRODUCTOS.add(new Producto(3, "Pollo", BigDecimal.valueOf(385.95)));
		PRODUCTOS.add(new Producto(4, "Paquete de pan", BigDecimal.valueOf(80.20)));
		PRODUCTOS.add(new Producto(5, "Queso", BigDecimal.valueOf(90.15)));
		PRODUCTOS.add(new Producto(6, "Manzana", BigDecimal.valueOf(15.15)));
		PRODUCTOS.add(new Producto(7, "Piña", BigDecimal.valueOf(45.00)));
	}
	
	private void ventasDefecto() {
		List<ProductoComprar> carro = new ArrayList<ProductoComprar>();
		carro.add(new ProductoComprar(getProductos().get(1), 5));
		carro.add(new ProductoComprar(getProductos().get(3), 11));
		carro.add(new ProductoComprar(getProductos().get(5), 3));
		VENTAS_PRODUCTOS.add(new VentasProductos(1, "Jorge", carro));
		
		List<ProductoComprar> carroBibi = new ArrayList<ProductoComprar>();
		carroBibi.add(new ProductoComprar(getProductos().get(1), 4));
		VENTAS_PRODUCTOS.add(new VentasProductos(2, "Bibi", carroBibi));
		
		List<ProductoComprar> carroJohn = new ArrayList<ProductoComprar>();
		carroJohn.add(new ProductoComprar(getProductos().get(1), 5));
		carroJohn.add(new ProductoComprar(getProductos().get(2), 9));
		carroJohn.add(new ProductoComprar(getProductos().get(3), 3));
		carroJohn.add(new ProductoComprar(getProductos().get(4), 1));
		carroJohn.add(new ProductoComprar(getProductos().get(5), 8));
		VENTAS_PRODUCTOS.add(new VentasProductos(3, "John", carroJohn));
	}

	public static void eliminarProducto(int id) {
		Producto auxProducto = buscarProducto(id);
		
		if (auxProducto != null) {
			PRODUCTOS.remove(auxProducto);
		}
	}

	public static Producto buscarProducto(int id) {
		for (Producto auxProducto : PRODUCTOS) {
			if (auxProducto.getId() == id) {
				return auxProducto;
			}
		}

		return null;
	}

	public static Producto nuevoProducto() {
		return new Producto(-1, "", BigDecimal.valueOf(0.00));
	}

	private static int generarIdProducto() {
		int id;
		
		do {
			id = (int) (Math.random() * Integer.MAX_VALUE + 1);
		} while (buscarProducto(id) != null);
		
		return id;
	}
	
	public static void crearEditarProducto(Producto producto) {
		Producto auxProducto;
		
		if (producto.getId() == -1 || buscarProducto(producto.getId()) == null) {
			producto.setId(generarIdProducto());
			PRODUCTOS.add(producto);
		} else {			
			auxProducto = Mercado.buscarProducto(producto.getId());
			auxProducto.setNombre(producto.getNombre());
			auxProducto.setPrecio(producto.getPrecio());
		}
	}
	
	public static Usuario autenticarUsuario(String nombreUsuario, String password) {
		Usuario usuario = buscarUsuario(nombreUsuario);
		
		if (usuario != null) {
			if (usuario.getPassword().equals(password)) {
				return usuario;
			}
		}
		
		return null;
	}

	private static Usuario buscarUsuario(String nombreUsuario) {
		for (Usuario auxUsuario : USUARIOS) {
			if (auxUsuario.getUsuario().equals(nombreUsuario)) {
				return auxUsuario;
			}
		}

		return null;
	}
	
	public static CarroCompra crearCarrito() {
		CarroCompra carrito = new CarroCompra(generarIdCarrito());
		agregarCarrito(carrito);
		return carrito;
	}
	
	private static void agregarCarrito(CarroCompra carrito) {
		CARRO_COMPRAS.add(carrito);
	}
	
	public static long generarIdCarrito() {
		int id = 0;
		
		do {
			id = (int) (Math.random() * Integer.MAX_VALUE + 1);
		} while (buscarCarrito(id) != null);
		
		return id;
	}
	
	public static CarroCompra buscarCarrito(long id) {
		for (CarroCompra auxCarrito : Mercado.CARRO_COMPRAS) {
			if (auxCarrito.getId() == id) {
				return auxCarrito;
			}
		}

		return null;
	}

	public static void procesarCompra(String cliente, long carrito) {
		CarroCompra carro = buscarCarrito(carrito);
		
		if (!(carro.getListaProductos().isEmpty())) {
			VENTAS_PRODUCTOS.add(new VentasProductos(generarIdVenta(), cliente, carro.getListaProductos()));
			carro.limpiarCarrito();
		}
	}

	private static long generarIdVenta() {
		int id = 0;
		
		do {
			id = (int) (Math.random() * Integer.MAX_VALUE + 1);
		} while (buscarCarrito(id) != null);
		
		return id;
	}
}