package edu.pucmm.market.handlers;

import static io.javalin.apibuilder.ApiBuilder.*;

import org.jasypt.util.text.AES256TextEncryptor;

import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.data.Usuario;
import io.javalin.Javalin;

public class CuentaControlador extends ServerHandler {

	private final AES256TextEncryptor encriptador;

	public CuentaControlador(Mercado mercado, AES256TextEncryptor encriptador, Javalin app) {
		super(mercado, app);
		this.encriptador = encriptador;
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
					String contraseña = ctx.formParam("contrasena");
					boolean recordarme = ctx.formParam("recordarme") != null;

					Usuario usuario = getMercado().autenticarUsuario(idUsuario, contraseña, false);

					if (usuario != null) {
						ctx.sessionAttribute("usuario", usuario);

						if (recordarme) {
							ctx.cookie("usuario", usuario.getUsuario(), 604800);
							ctx.cookie("contrasena", this.encriptador.encrypt(contraseña), 604800);
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