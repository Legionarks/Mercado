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

public class AdministrarControlador extends ServerHandler {

    public AdministrarControlador(Javalin app) {
	super(app);
    }

    @Override
    public void rutas() {
	app.routes(() -> {
	    path("/administrar", () -> {
		before(ctx -> {
		    if ((Usuario) ctx.sessionAttribute("usuario") == null) {
			ctx.redirect("/cuenta/logear");
		    }
		});

		get("/productos", ctx -> {
		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
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
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("producto", new Producto(-1, "", BigDecimal.valueOf(0.00)));
		    modelo.put("carrito_cantidad",
			    (((CarroCompra) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/crear-editar.html", modelo);
		});

		post("/producto/editar", ctx -> {
		    int id = Integer.parseInt(ctx.formParam("id"));

		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
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
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("ventas", Mercado.getVentasProductos());
		    modelo.put("carrito_cantidad",
			    (((CarroCompra) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/ventas.html", modelo);
		});
	    });
	});
    }
}