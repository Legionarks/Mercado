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
		    modelo.put("carrito_cantidad",
			    (((CarroCompra) ctx.sessionAttribute("carrito")).cantidadProductos()));

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
		    modelo.put("carrito_cantidad",
			    (((CarroCompra) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/crud.html", modelo);
		});

		post("/producto/eliminar", ctx -> {
		    int id = Integer.parseInt(ctx.formParam("id"));

		    Mercado.eliminarProducto(id);

		    ctx.redirect("/administrar/productos");
		});

		get("/producto/crear", ctx -> {
		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("producto", new Producto(-1, "", BigDecimal.valueOf(0.00)));
		    modelo.put("carrito_cantidad",
			    (((CarroCompra) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/crear-editar.html", modelo);
		});

		post("/producto/editar", ctx -> {
		    int id = Integer.parseInt(ctx.formParam("id"));

		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("producto", Mercado.buscarProducto(id));
		    modelo.put("carrito_cantidad",
			    (((CarroCompra) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/crear-editar.html", modelo);
		});

		post("/producto/crear-editar", ctx -> {
		    int id = Integer.valueOf(ctx.formParam("id"));
		    String nombre = ctx.formParam("nombre");
		    BigDecimal precio = BigDecimal.valueOf(Double.valueOf(ctx.formParam("precio")));

		    Mercado.crearEditarProducto(id, nombre, precio);

		    ctx.redirect("/administrar/productos");
		});

		get("/info/ventas", ctx -> {
		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("ventas", Mercado.getVentasProductos());
		    modelo.put("carrito_cantidad",
			    (((CarroCompra) ctx.sessionAttribute("carrito")).cantidadProductos()));

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

			if (usuario != null) {
			    ctx.sessionAttribute("usuario", usuario);
			    ctx.redirect("/");
			} else {
			    ctx.redirect("/autenticar");
			}
		    }
		});
	    });

	    path("/carrito", () -> {
		get(ctx -> {
		    CarroCompra carrito = ctx.sessionAttribute("carrito");

		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("carrito", carrito);
		    modelo.put("total", carrito.calcularTotal());
		    modelo.put("carrito_cantidad",
			    (((CarroCompra) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/carrito.html", modelo);
		});

		post("/producto/agregar", ctx -> {
		    int producto = Integer.parseInt(ctx.formParam("id"));
		    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));

		    ((CarroCompra) ctx.sessionAttribute("carrito")).agregarProducto(Mercado.buscarProducto(producto),
			    cantidad);

		    ctx.redirect("/");
		});

		post("/producto/eliminar", ctx -> {
		    int producto = Integer.parseInt(ctx.formParam("id"));

		    ((CarroCompra) ctx.sessionAttribute("carrito")).eliminarProducto(Mercado.buscarProducto(producto));

		    ctx.redirect("/carrito");
		});

		get("/limpiar", ctx -> {
		    ((CarroCompra) ctx.sessionAttribute("carrito")).limpiarCarrito();

		    ctx.redirect("/carrito");
		});

		post("/comprar", ctx -> {
		    String cliente = ctx.formParam("cliente");

		    Mercado.procesarCompra(cliente, ctx.sessionAttribute("carrito"));

		    ctx.redirect("/carrito");
		});
	    });
	});

	app.before(ctx -> {
	    CarroCompra carrito = ctx.sessionAttribute("carrito");

	    if (carrito == null) {
		carrito = new CarroCompra(1);
		ctx.sessionAttribute("carrito", carrito);
	    }
	});
    }
}