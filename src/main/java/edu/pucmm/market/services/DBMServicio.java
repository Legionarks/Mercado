package edu.pucmm.market.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;

import edu.pucmm.market.Main;

public class DBMServicio<T> {

	private static EntityManagerFactory entityManagerFactory;
	private Class<T> claseEntidad;

	public DBMServicio(Class<T> clase) {
		if (entityManagerFactory == null) {
			if (Main.getConexion().equalsIgnoreCase("Heroku")) {
				entityManagerFactory = entityManagerFactoryHeroku();
			} else {
				entityManagerFactory = Persistence.createEntityManagerFactory("Mercado");
			}
		}

		this.claseEntidad = clase;
	}

	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	private EntityManagerFactory entityManagerFactoryHeroku() {
		String databaseUrl = System.getenv("DATABASE_URL");
		StringTokenizer tokenizer = new StringTokenizer(databaseUrl, ":@/");

		// String dbVendor = tokenizer.nextToken();
		String username = tokenizer.nextToken();
		String password = tokenizer.nextToken();
		String host = tokenizer.nextToken();
		String port = tokenizer.nextToken();
		String databaseName = tokenizer.nextToken();

		String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, databaseName);

		Map<String, String> properties = new HashMap<>();
		properties.put("javax.persistence.jdbc.url", jdbcUrl);
		properties.put("javax.persistence.jdbc.user", username);
		properties.put("javax.persistence.jdbc.password", password);

		return Persistence.createEntityManagerFactory("PostgreSQL", properties);
	}

	public Object getId(T entidad) {
		Object id = null;

		if (entidad != null) {
			for (Field campo : entidad.getClass().getDeclaredFields()) {
				if (campo.isAnnotationPresent(Id.class)) {
					try {
						campo.setAccessible(true);
						id = campo.get(entidad);

						System.out.println("Nombre del campo: " + campo.getName());
						System.out.println("Tipo del campo: " + campo.getType().getName());
						System.out.println("Valor del campo: " + id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return id;
	}

	public boolean crear(T entidad) throws IllegalArgumentException, EntityExistsException, PersistenceException {
		boolean ok = false;
		EntityManager entityManager = getEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.persist(entidad);
			entityManager.getTransaction().commit();
			ok = true;
		} finally {
			entityManager.close();
		}

		return ok;
	}

	public boolean editar(T entidad) throws PersistenceException {
		boolean ok = false;
		EntityManager entityManager = getEntityManager();

		entityManager.getTransaction().begin();
		try {
			entityManager.merge(entidad);
			entityManager.getTransaction().commit();
			ok = true;
		} finally {
			entityManager.close();
		}

		return ok;
	}

	public boolean eliminar(Object entidadId) throws PersistenceException {
		boolean ok = false;
		EntityManager entityManager = getEntityManager();
		T entidad;

		entityManager.getTransaction().begin();
		try {
			entidad = entityManager.find(this.claseEntidad, entidadId);
			entityManager.remove(entidad);
			entityManager.getTransaction().commit();
			ok = true;
		} finally {
			entityManager.close();
		}

		return ok;
	}

	public T buscar(Object id) throws PersistenceException {
		EntityManager entityManager = getEntityManager();
		T entidad = null;

		try {
			entidad = entityManager.find(this.claseEntidad, id);
		} finally {
			entityManager.close();
		}

		return entidad;
	}

	public List<T> buscarTodos() throws PersistenceException {
		EntityManager entityManager = getEntityManager();
		CriteriaQuery<T> criteriaQuery;
		List<T> entidades = null;

		try {
			criteriaQuery = entityManager.getCriteriaBuilder().createQuery(this.claseEntidad);
			criteriaQuery.select(criteriaQuery.from(this.claseEntidad));
			entidades = entityManager.createQuery(criteriaQuery).getResultList();
		} finally {
			entityManager.close();
		}

		return entidades;
	}

	public List<T> castLista(Collection<?> coleccion) {
		List<T> lista = new ArrayList<T>(coleccion.size());

		for (Object entidad : coleccion) {
			lista.add(this.claseEntidad.cast(entidad));
		}

		return lista;
	}
}