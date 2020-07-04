package edu.pucmm.market.handlers;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import edu.pucmm.market.data.Carro;
import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.data.Producto;
import edu.pucmm.market.data.ProductoExistencia;
import edu.pucmm.market.data.Usuario;
import io.javalin.Javalin;

public class AdministrarControlador extends ServerHandler {

    public AdministrarControlador(Mercado mercado, Javalin app) {
	super(mercado, app);
    }

    @Override
    public void rutas() {
	getApp().routes(() -> {
	    path("/administrar", () -> {
		before(ctx -> {
		    if ((Usuario) ctx.sessionAttribute("usuario") == null) {
			ctx.redirect("/cuenta/logear");
		    }
		});

		get("/productos", ctx -> {
		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("existencias", getMercado().getExistencias());
		    modelo.put("carrito_cantidad",
			    (((Carro) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/crud.html", modelo);
		});

		post("/producto/eliminar", ctx -> {
		    int id = Integer.parseInt(ctx.formParam("id"));

		    getMercado().quitar(getMercado().buscarProducto(id));

		    ctx.redirect("/administrar/productos");
		});

		get("/producto/crear", ctx -> {
		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("existencia", new ProductoExistencia(new Producto(), new BigDecimal(0)));
		    modelo.put("carrito_cantidad",
			    (((Carro) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/crear-editar.html", modelo);
		});

		post("/producto/editar", ctx -> {
		    int id = Integer.parseInt(ctx.formParam("id"));

		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("existencia", getMercado().buscarExistencia(id));
		    modelo.put("carrito_cantidad",
			    (((Carro) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/crear-editar.html", modelo);
		});

		post("/producto/crear-editar", ctx -> {
		    int id = Integer.valueOf(ctx.formParam("id"));
		    String nombre = ctx.formParam("nombre");
		    BigDecimal precio = BigDecimal.valueOf(Double.valueOf(ctx.formParam("precio")));

		    getMercado().crearEditarProducto(id, nombre, precio);

		    ctx.redirect("/administrar/productos");
		});

		get("/info/ventas", ctx -> {
		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("ventas", getMercado().getVentas());
		    modelo.put("carrito_cantidad",
			    (((Carro) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/ventas.html", modelo);
		});
	    });
	});
    }
}