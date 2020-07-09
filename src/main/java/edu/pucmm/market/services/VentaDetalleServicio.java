package edu.pucmm.market.services;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.pucmm.market.data.VentaDetalle;

public class VentaDetalleServicio extends DBMServicio<VentaDetalle> {

	public VentaDetalleServicio() {
		super(VentaDetalle.class);
	}

	public boolean eliminarTodoProducto(int id) {
		EntityManager entityManager = getEntityManager();
		Query query = entityManager.createQuery("DELETE VENTA_DETALLE WHERE PRODUCTO_ID_PRODUCTO = :ID");
		query.setParameter("ID", "%" + id + "%");
		entityManager.getTransaction().begin();
		boolean ok = query.executeUpdate() > 0;
		entityManager.getTransaction().commit();

		return ok;
	}
}