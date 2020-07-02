package edu.pucmm.market.services;

import edu.pucmm.market.data.Usuario;

public class UsuarioServicio extends DBMServicio<Usuario> {

    public UsuarioServicio() {
	super(Usuario.class);
    }

    public Usuario buscarUsuario(String idUsuario) {
	return buscarUsuario(idUsuario);
    }

    public boolean crearUsuario(Usuario usuario) {
	return crear(usuario);
    }
}