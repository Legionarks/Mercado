package edu.pucmm.market.utils;

import io.javalin.Javalin;

public abstract class ServerHandler {

    protected Javalin app;

    public ServerHandler(Javalin app) {
	this.app = app;
    }

    abstract public void rutas();
}