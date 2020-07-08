package edu.pucmm.market.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.pucmm.market.data.Comentario;

public class ComentarioServicio extends DBMServicio<Comentario> {

    public ComentarioServicio() {
	super(Comentario.class);
    }
    
    public List<Comentario> obtenerComentarios(int producto) {
	EntityManager entityManager = getEntityManager();
	Query query = entityManager.createNativeQuery("SELECT * FROM COMENTARIO WHERE PRODUCTO_ID_PRODUCTO = :ID", Comentario.class);
	query.setParameter("ID", producto);
	List<Comentario> comentarios = query.getResultList();
	
	return comentarios;
    }
}