package edu.pucmm.market.handlers;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import edu.pucmm.market.data.CarroCompra;
import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.data.Producto;
import edu.pucmm.market.data.Usuario;
import edu.pucmm.market.utils.ServerHandler;
import io.javalin.Javalin;

public class Home extends ServerHandler {

	public Home(Javalin app) {
		super(app);
	}

	@Override
	public void rutas() {

		app.routes(() -> {

			path("/", () -> {
				get(ctx -> {
					Map<String, Object> modelo = new HashMap<String, Object>();
					modelo.put("productos", Mercado.getProductos());

					ctx.render("/html/index.html", modelo);
				});
			});

			path("/administrar", () -> {
				before(ctx -> {
					if ((Usuario) ctx.sessionAttribute("usuario") == null) {
						ctx.redirect("/autenticar");
					}
				});

				get("/productos", ctx -> {
					Map<String, Object> modelo = new HashMap<String, Object>();
					modelo.put("productos", Mercado.getProductos());

					ctx.render("/html/crud.html", modelo);
				});

				post("/producto/eliminar", ctx -> {
					int id = Integer.parseInt(ctx.formParam("id"));

					Mercado.eliminarProducto(id);

					ctx.redirect("/administrar/productos");
				});

				get("/producto/crear", ctx -> {
					Map<String, Object> modelo = new HashMap<String, Object>();
					modelo.put("producto", Mercado.nuevoProducto());

					ctx.render("/html/crear-editar.html", modelo);
				});

				post("/producto/editar", ctx -> {
					int id = Integer.parseInt(ctx.formParam("id"));

					Map<String, Object> modelo = new HashMap<String, Object>();
					modelo.put("producto", Mercado.buscarProducto(id));

					ctx.render("/html/crear-editar.html", modelo);
				});

				post("/producto/crear-editar", ctx -> {
					int id = Integer.valueOf(ctx.formParam("id"));
					String nombre = ctx.formParam("nombre");
					BigDecimal precio = BigDecimal.valueOf(Double.valueOf(ctx.formParam("precio")));

					Mercado.crearEditarProducto(new Producto(id, nombre, precio));

					ctx.redirect("/administrar/productos");
				});

				get("/info/ventas", ctx -> {
					Map<String, Object> modelo = new HashMap<String, Object>();
					modelo.put("ventas", Mercado.getVentasProductos());

					ctx.render("/html/ventas.html", modelo);
				});
			});

			path("/autenticar", () -> {
				get(ctx -> {
					ctx.render("/html/login.html");
				});

				post(ctx -> {
					String nombreUsuario = ctx.formParam("usuario");
					String password = ctx.formParam("password");
					Usuario usuario;

					if ((Usuario) ctx.sessionAttribute("usuario") != null) {
						ctx.redirect("/administrar/productos");
					} else {
						usuario = Mercado.autenticarUsuario(nombreUsuario, password);
						ctx.sessionAttribute("usuario", usuario);
						ctx.redirect("/");
					}
				});
			});

			path("/carrito", () -> {				
				get(ctx -> {
					long id = Long.valueOf(ctx.cookie("carrito"));
					CarroCompra carrito = Mercado.buscarCarrito(id);

					Map<String, Object> modelo = new HashMap<String, Object>();
					modelo.put("carrito", carrito);
					modelo.put("total", carrito.calcularTotal());

					ctx.render("/html/carrito.html", modelo);
				});

				post("/producto/agregar", ctx -> {
					long id = Long.valueOf(ctx.cookie("carrito"));
					int producto = Integer.parseInt(ctx.formParam("id"));
					int cantidad = Integer.parseInt(ctx.formParam("cantidad"));

					Mercado.buscarCarrito(id).agregarProducto(Mercado.buscarProducto(producto), cantidad);

					ctx.redirect("/");
				});
				
				post("/producto/eliminar", ctx -> {
					long id = Long.valueOf(ctx.cookie("carrito"));
					int producto = Integer.parseInt(ctx.formParam("id"));

					Mercado.buscarCarrito(id).eliminarProducto(Mercado.buscarProducto(producto));

					ctx.redirect("/carrito");
				});

				get("/limpiar", ctx -> {
					long id = Long.valueOf(ctx.cookie("carrito"));

					Mercado.buscarCarrito(id).limpiarCarrito();

					ctx.redirect("/carrito");
				});

				post("/comprar", ctx -> {
					String cliente = ctx.formParam("cliente");
					long id = Long.valueOf(ctx.cookie("carrito"));

					Mercado.procesarCompra(cliente, id);

					ctx.redirect("/carrito");
				});
			});
		});

		app.before(ctx -> {
			CarroCompra carrito;
			String id = ctx.cookie("carrito");

			if (ctx.cookie("carrito") == null) {
				carrito = Mercado.crearCarrito();
				ctx.cookie("carrito", String.valueOf(carrito.getId()));
			} else if (Mercado.buscarCarrito(Long.valueOf(id)) == null) {
				carrito = Mercado.crearCarrito();
				ctx.cookie("carrito", String.valueOf(carrito.getId()));
			}
		});
	}
}