package org.dhbw.mosbach.ai.pizzafactory.db;

import java.util.Collection;

/**
 * Basic DAO interface
 *
 * @param <E>
 *          entity class
 * @param <K>
 *          business key
 */
public interface BasicDataAccess<E, K>
{
	E get(K key);

	Collection<E> getAll();

	boolean add(E entity);

	boolean change(E entity);

	boolean delete(E entity);

	boolean deleteByKey(K key);
}
