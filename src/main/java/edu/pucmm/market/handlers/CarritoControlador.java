package edu.pucmm.market.handlers;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.HashMap;
import java.util.Map;

import edu.pucmm.market.data.CarroCompra;
import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.utils.ServerHandler;
import io.javalin.Javalin;

public class CarritoControlador extends ServerHandler {

    public CarritoControlador(Mercado mercado, Javalin app) {
	super(mercado, app);
    }

    @Override
    public void rutas() {
	getApp().routes(() -> {
	    path("/carrito", () -> {
		get(ctx -> {
		    CarroCompra carrito = ctx.sessionAttribute("carrito");

		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("carrito", carrito);
		    modelo.put("total", carrito.calcularTotal());
		    modelo.put("carrito_cantidad",
			    (((CarroCompra) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/carrito.html", modelo);
		});

		post("/producto/agregar", ctx -> {
		    int producto = Integer.parseInt(ctx.formParam("id"));
		    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));

		    ((CarroCompra) ctx.sessionAttribute("carrito")).agregarProducto(getMercado().buscarProducto(producto),
			    cantidad);

		    ctx.redirect("/");
		});

		post("/producto/eliminar", ctx -> {
		    int producto = Integer.parseInt(ctx.formParam("id"));

		    ((CarroCompra) ctx.sessionAttribute("carrito")).eliminarProducto(getMercado().buscarProducto(producto));

		    ctx.redirect("/carrito");
		});

		get("/limpiar", ctx -> {
		    ((CarroCompra) ctx.sessionAttribute("carrito")).limpiarCarrito();

		    ctx.redirect("/carrito");
		});

		post("/comprar", ctx -> {
		    String cliente = ctx.formParam("cliente");

		    getMercado().procesarCompra(cliente, ctx.sessionAttribute("carrito"));

		    ctx.redirect("/carrito");
		});
	    });
	});
    }
}