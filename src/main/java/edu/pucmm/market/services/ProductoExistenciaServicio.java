package edu.pucmm.market.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.pucmm.market.data.ProductoExistencia;

public class ProductoExistenciaServicio extends DBMServicio<ProductoExistencia> {
    
    public ProductoExistenciaServicio() {
 	super(ProductoExistencia.class);
     }
    
    public List<ProductoExistencia> obtenerPagina(int numero) {
	EntityManager entityManager = getEntityManager();
	Query query = entityManager.createNativeQuery("SELECT * FROM PRODUCTO_EXISTENCIA", ProductoExistencia.class);
	query.setFirstResult((numero - 1) * 10);
	query.setMaxResults(10);
	List<ProductoExistencia> existencias = query.getResultList();
	
	return existencias;
    }
}