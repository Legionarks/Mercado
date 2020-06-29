package edu.pucmm.market;

import java.sql.SQLException;

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

    private static Javalin app;
    private static DBServicio dbServicio;

    public static void main(String[] args) {

	try {
	    app = Javalin.create(config -> {
		config.addStaticFiles("/html");
		config.addStaticFiles("/css");
		config.addStaticFiles("/templates");
		config.addStaticFiles("/images");
	    }).start(7000);

	    registrarPlantilla();
	    
	    dbServicio = new DBServicio();
	    dbServicio.borrarTablas();
	    dbServicio.crearTablas();
	    new SimularDatos();

	    new Home(app).rutas();
	    new CuentaControlador(app).rutas();
	    new CarritoControlador(app).rutas();
	    new AdministrarControlador(app).rutas();
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