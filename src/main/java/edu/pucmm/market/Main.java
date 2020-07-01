package edu.pucmm.market;

import java.sql.SQLException;

import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.handlers.AdministrarControlador;
import edu.pucmm.market.handlers.CarritoControlador;
import edu.pucmm.market.handlers.CuentaControlador;
import edu.pucmm.market.handlers.Home;
import edu.pucmm.market.services.DBServicio;
import edu.pucmm.market.utils.SimularDatos;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

public class Main {

    private static DBServicio dbServicio;
    private static Mercado mercado;
    private static Javalin app;

    public static void main(String[] args) {

	try {
	    dbServicio = new DBServicio();
	    mercado = new Mercado();
	    
	    app = Javalin.create(config -> {
		config.addStaticFiles("/html");
		config.addStaticFiles("/css");
		config.addStaticFiles("/templates");
		config.addStaticFiles("/images");
	    }).start(7000);

	    registrarPlantilla();

	    new SimularDatos(mercado);

	    new Home(mercado, app).rutas();
	    new CuentaControlador(mercado, app).rutas();
	    new CarritoControlador(mercado, app).rutas();
	    new AdministrarControlador(mercado, app).rutas();
	} catch (SQLException e) {
	    e.printStackTrace();
	    try {
		dbServicio.pararDB();
	    } catch (SQLException ex) {
		ex.printStackTrace();
	    }
	}
    }

    private static void registrarPlantilla() {
	JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }
}