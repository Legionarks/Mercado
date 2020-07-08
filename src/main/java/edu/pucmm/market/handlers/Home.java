package edu.pucmm.market.handlers;

import static io.javalin.apibuilder.ApiBuilder.*;

import edu.pucmm.market.data.Carro;
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
		    ctx.redirect("/productos");
		});
	    });
	});

	getApp().before(ctx -> {
	    Usuario usuario = ctx.sessionAttribute("usuario");
	    String idUsuario;
	    String contraseñaEncriptada;
	    Carro carrito = ctx.sessionAttribute("carrito");

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
		carrito = new Carro();
		ctx.sessionAttribute("carrito", carrito);
	    }
	});
    }
}