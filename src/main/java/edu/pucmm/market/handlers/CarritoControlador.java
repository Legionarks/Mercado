package edu.pucmm.market.handlers;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.HashMap;
import java.util.Map;

import edu.pucmm.market.data.Carro;
import edu.pucmm.market.data.Mercado;
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
		    Carro carrito = ctx.sessionAttribute("carrito");

		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("carrito", carrito);
		    modelo.put("carrito_cantidad", carrito.cantidadProductos());

		    ctx.render("/html/carrito.html", modelo);
		});

		post("/producto/agregar", ctx -> {
		    int id = Integer.parseInt(ctx.formParam("id"));
		    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));

		    ((Carro) ctx.sessionAttribute("carrito")).agregarProducto(getMercado().buscarExistencia(id), cantidad);

		    ctx.redirect("/");
		});

		post("/producto/eliminar", ctx -> {
		    int id = Integer.parseInt(ctx.formParam("id"));

		    ((Carro) ctx.sessionAttribute("carrito")).eliminarProducto(getMercado().buscarExistencia(id));

		    ctx.redirect("/carrito");
		});

		get("/limpiar", ctx -> {
		    ((Carro) ctx.sessionAttribute("carrito")).limpiarCarrito();

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