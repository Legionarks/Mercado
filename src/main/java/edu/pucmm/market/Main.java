package edu.pucmm.market;

import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.handlers.Home;
import edu.pucmm.market.utils.SimularDatos;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

public class Main {

    private static Javalin app;

    @SuppressWarnings("unused")
    private static final Mercado mercado = new Mercado();

    public static void main(String[] args) {

	app = Javalin.create(config -> {
	    config.addStaticFiles("/html");
	    config.addStaticFiles("/css");
	    config.addStaticFiles("/templates");
	    config.addStaticFiles("/images");
	}).start(7000);

	registrarPlantilla();

	new SimularDatos();
	new Home(app).rutas();
    }

    private static void registrarPlantilla() {
	JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }
}