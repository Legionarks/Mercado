package edu.pucmm.market.handlers;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.HashMap;
import java.util.Map;

import edu.pucmm.market.data.CarroCompra;
import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.data.Usuario;
import io.javalin.Javalin;

public class Home extends ServerHandler {

    public Home(Mercado mercado, Javalin app) {
	super(mercado, app);
    }

    @Override
    public void rutas() {

	getApp().routes(() -> {

	    path("/", () -> {
		get(ctx -> {
		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("productos", getMercado().getProductos());
		    modelo.put("carrito_cantidad",
			    (((CarroCompra) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/index.html", modelo);
		});
	    });
	});

	getApp().before(ctx -> {
	    Usuario usuario = ctx.sessionAttribute("usuario");
	    String idUsuario;
	    String contraseñaEncriptada;
	    CarroCompra carrito = ctx.sessionAttribute("carrito");

	    if (usuario == null) {
		idUsuario = ctx.cookie("usuario");
		contraseñaEncriptada = ctx.cookie("contrasena");

		if (idUsuario != null && contraseñaEncriptada != null) {
		    if (!((idUsuario + contraseñaEncriptada).isBlank() || (idUsuario + contraseñaEncriptada).isEmpty())) {
			usuario = getMercado().autenticarUsuario(idUsuario, contraseñaEncriptada, true);

			if (usuario != null) {
			    ctx.sessionAttribute("usuario", usuario);
			}
		    }
		}
	    }

	    if (carrito == null) {
		carrito = new CarroCompra();
		ctx.sessionAttribute("carrito", carrito);
	    }
	});
    }
}