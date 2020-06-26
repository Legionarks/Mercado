package edu.pucmm.market.handlers;

import static io.javalin.apibuilder.ApiBuilder.*;

import org.jasypt.util.password.StrongPasswordEncryptor;

import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.data.Usuario;
import edu.pucmm.market.utils.ServerHandler;
import io.javalin.Javalin;

public class CuentaControlador extends ServerHandler {

    public CuentaControlador(Javalin app) {
	super(app);
    }

    @Override
    public void rutas() {
	app.routes(() -> {
	    path("/cuenta", () -> {
		get("/logear", ctx -> {
		    if ((Usuario) ctx.sessionAttribute("usuario") != null) {
			ctx.redirect("/administrar/productos");
		    } else {
			ctx.render("/html/login.html");
		    }
		});

		post("/logear", ctx -> {
		    String idUsuario = ctx.formParam("usuario");
		    String password = ctx.formParam("password");
		    boolean recordarme = ctx.formParam("recordarme") != null;

		    Usuario usuario;
		    String encryptedPassword = new StrongPasswordEncryptor().encryptPassword(password);

		    usuario = Mercado.autenticarUsuario(idUsuario, password, false);

		    if (usuario != null) {
			ctx.sessionAttribute("usuario", usuario);

			if (recordarme) {
			    ctx.cookie("usuario", usuario.getUsuario(), 604800);
			    ctx.cookie("password", encryptedPassword, 604800);
			}

			ctx.redirect("/");
		    } else {
			ctx.redirect("/cuenta/logear");
		    }
		});

		get("/desloguear", ctx -> {
		    ctx.sessionAttribute("usuario", null);
		    ctx.cookie("usuario", "", 0);
		    ctx.cookie("password", "", 0);
		    ctx.redirect("/");
		});
		
		get("/registrar", ctx -> {
		    ctx.result("Todavia no ha sido asignado");
		});
	    });
	});
    }
}