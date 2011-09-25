package br.zero.customdao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public abstract class CustomDAO<T> {

	private static EntitySetup setup;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	public CustomDAO() {
		if (setup == null) {
			setup = getEntityClass().getAnnotation(EntitySetup.class);
		}
	}

	/**
	 * Deve devolver o nome da unidade de persistência para o projeto.
	 * 
	 * @return
	 */
	protected String getPersistenceUnitName() {
		DAOInfo puinfo = getClass().getAnnotation(DAOInfo.class);
		
		if (puinfo != null) {
			return puinfo.persistenceUnitName();
		}
		
		return null;
	}

	private EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory(getPersistenceUnitName());
		}

		return entityManagerFactory;
	}

	public void close() {
		getEntityManager().close();
		entityManager = null;
		getEntityManagerFactory().close();
	}

	protected EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = getEntityManagerFactory().createEntityManager();
		}

		return entityManager;
	}

	protected void commitTransaction() {
		entityTransaction.commit();
	}

	protected void beginTransaction() {
		entityTransaction = getEntityManager().getTransaction();

		entityTransaction.begin();
	}

	/**
	 * Quero que a anotação com a configuração para o DAO fique na entidade, não
	 * aqui. Por isso preciso pagar o preço de ter esse método aqui.
	 * 
	 * @return
	 */
	private Class<?> getEntityClass() {
		DAOInfo daoInfo = getClass().getAnnotation(DAOInfo.class);
		
		if (daoInfo == null) {
			throw new RuntimeException("DAO's herdados de CustomDAO<T> devem ser anotados com @DAOInfo.");
		}
		
		return daoInfo.entityClass();
	}

	@SuppressWarnings("unchecked")
	public List<T> listarTodos() {
		Query q = getEntityManager().createNamedQuery(setup.findAllQueryName());

		List<T> results = q.getResultList();

		return results;
	}

	public void inserir(T o) {
		beginTransaction();

		getEntityManager().persist(o);

		commitTransaction();
	}

	@SuppressWarnings("unchecked")
	public T getById(int id) {
		Query q = getEntityManager().createNamedQuery(setup.findByIdQueryName());
		q.setParameter(setup.idFieldName(), id);

		List<T> results = q.getResultList();

		return results.get(0);
	}

	public void excluir(T o) {
		beginTransaction();

		getEntityManager().remove(o);

		commitTransaction();
	}

	public void alterar(T o) {
		beginTransaction();

		getEntityManager().persist(o);

		commitTransaction();
	}
}
