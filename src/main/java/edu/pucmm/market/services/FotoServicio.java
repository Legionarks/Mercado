package edu.pucmm.market.services;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.pucmm.market.data.Foto;

public class FotoServicio extends DBMServicio<Foto> {

    public FotoServicio() {
	super(Foto.class);
    }

    public boolean eliminar(String id, String nombre) {
	EntityManager entityManager = getEntityManager();
	Query query;
	boolean ok = false;
	
	entityManager.getTransaction().begin();
	try {
	    query = entityManager.createQuery("DELETE FOTO_PRODUCTO WHERE PRODUCTO_ID_PRODUCTO = :ID AND NOMBRE = :NOMBRE");
	    query.setParameter("ID", id);
	    query.setParameter("NOMBRE", nombre);
	    query.executeUpdate();
	    entityManager.getTransaction().commit();
	    ok = true;
	} finally {
	    entityManager.close();
	}
	
        return ok;
    }
}