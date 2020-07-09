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
		Query query = entityManager.createQuery("SELECT c FROM Comentario AS c WHERE c.producto.id = :ID");
		query.setParameter("ID", producto);
		List<Comentario> comentarios = this.castLista(query.getResultList());

		return comentarios;
	}
}