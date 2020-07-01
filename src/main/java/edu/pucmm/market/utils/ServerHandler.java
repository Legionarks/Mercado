package edu.pucmm.market.utils;

import edu.pucmm.market.data.Mercado;
import io.javalin.Javalin;

public abstract class ServerHandler {

    private static Mercado mercado;
    private static Javalin app;

    public ServerHandler(Mercado mercado, Javalin app) {
	ServerHandler.mercado = mercado;
	ServerHandler.app = app;
    }
    
    public static Mercado getMercado() {
	return mercado;
    }

    public static void setMercado(Mercado mercado) {
	ServerHandler.mercado = mercado;
    }

    public static Javalin getApp() {
	return app;
    }

    public static void setApp(Javalin app) {
	ServerHandler.app = app;
    }
    
    abstract public void rutas();
}