package edu.pucmm.market.handlers;

import static io.javalin.apibuilder.ApiBuilder.*;

import org.jasypt.util.password.StrongPasswordEncryptor;

import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.data.Usuario;
import io.javalin.Javalin;

public class CuentaControlador extends ServerHandler {

    public CuentaControlador(Mercado mercado, Javalin app) {
	super(mercado, app);
    }

    @Override
    public void rutas() {
	getApp().routes(() -> {
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
		    String contrase�a = ctx.formParam("contrase�a");
		    boolean recordarme = ctx.formParam("recordarme") != null;
		    
		    System.out.println(contrase�a);

		    Usuario usuario;
		    String contrase�aEncriptada = new StrongPasswordEncryptor().encryptPassword(contrase�a);

		    usuario = getMercado().autenticarUsuario(idUsuario, contrase�a, false);

		    if (usuario != null) {
			ctx.sessionAttribute("usuario", usuario);

			if (recordarme) {
			    ctx.cookie("usuario", usuario.getUsuario(), 604800);
			    ctx.cookie("contrasena", contrase�aEncriptada, 604800);
			}

			ctx.redirect("/");
		    } else {
			ctx.redirect("/cuenta/logear");
		    }
		});

		get("/desloguear", ctx -> {
		    ctx.sessionAttribute("usuario", null);
		    ctx.cookie("usuario", "", 0);
		    ctx.cookie("contrasena", "", 0);
		    ctx.redirect("/");
		});
		
		get("/registrar", ctx -> {
		    ctx.result("Todavia no ha sido asignado");
		});
	    });
	});
    }
}