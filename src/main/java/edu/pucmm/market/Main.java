package edu.pucmm.market;

import java.sql.SQLException;

import org.jasypt.util.text.AES256TextEncryptor;

import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.handlers.AdministrarControlador;
import edu.pucmm.market.handlers.CarritoControlador;
import edu.pucmm.market.handlers.CuentaControlador;
import edu.pucmm.market.handlers.Home;
import edu.pucmm.market.handlers.ProductoControlador;
import edu.pucmm.market.services.DBServicio;
import edu.pucmm.market.utils.SimularDatos;
import io.javalin.Javalin;

public class Main {

    private static AES256TextEncryptor encriptador;
    private static final String contraseña = "admin";

    private static DBServicio dbServicio;
    private static Mercado mercado;
    private static Javalin app;

    public static void main(String[] args) {

	try {
	    encriptador = new AES256TextEncryptor();
	    encriptador.setPassword(contraseña);

	    dbServicio = new DBServicio();
	    mercado = new Mercado(encriptador);

	    app = Javalin.create(config -> {
		config.addStaticFiles("/html");
		config.addStaticFiles("/css");
		config.addStaticFiles("/templates");
		config.addStaticFiles("/images");
		config.enableCorsForAllOrigins();
	    }).start(puertoHerokuAsignado());

	    new SimularDatos(mercado, encriptador);

	    new Home(mercado, app).rutas();
	    new CuentaControlador(mercado, encriptador, app).rutas();
	    new ProductoControlador(mercado, app).rutas();
	    new CarritoControlador(mercado, app).rutas();
	    new AdministrarControlador(mercado, app).rutas();
	} catch (SQLException e) {
	    e.printStackTrace();
	    try {
		dbServicio.pararDB();
		app.stop();
	    } catch (SQLException ex) {
		ex.printStackTrace();
	    }
	}
    }

    private static int puertoHerokuAsignado() {
	int port = 7000;
	ProcessBuilder processBuilder = new ProcessBuilder();

	if (processBuilder.environment().get("PORT") != null) {
	    port = Integer.parseInt(processBuilder.environment().get("PORT"));
	}

	return port;
    }
}