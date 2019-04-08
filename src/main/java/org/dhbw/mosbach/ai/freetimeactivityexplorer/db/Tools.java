package org.dhbw.mosbach.ai.freetimeactivityexplorer.db;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;

@Dependent
public final class Tools {
	private static final Logger logger = LoggerFactory.getLogger(Tools.class);

	@PersistenceContext
	private EntityManager entityManager;

	private static final LoadingCache<Class<?>, Method> idGetters = CacheBuilder.newBuilder().maximumSize(20)
			.build(new CacheLoader<Class<?>, Method>() {
				@Override
				public Method load(Class<?> key) throws Exception {
					return getEntityKeyGetterByReflection(key);
				}
			});

	@SuppressWarnings("unchecked")
	public static <I> I getEntityKey(Object entity) {
		if (entity == null) {
			return null;
		}

		final Method idGetterMethod = getEntityKeyGetter(entity.getClass());

		try {
			return (idGetterMethod != null) ? (I) idGetterMethod.invoke(entity) : null;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
			throw new IllegalArgumentException("Could not invoke id getter", exception);
		}
	}

	/**
	 * Returns the getter method for the given entity's primary key by using
	 * reflection.
	 *
	 * @param entityClass entity class
	 * @return getter method or null if none found
	 */
	private static Method getEntityKeyGetterByReflection(Class<?> entityClass) {
		for (final Method method : entityClass.getMethods()) {
			if (method.isAnnotationPresent(Id.class)) {
				return method;
			}
		}

		// Or search for field
		for (final Field field : getAllFields(entityClass)) {
			if (field.isAnnotationPresent(Id.class)) {
				final String fieldName = field.getName();
				final String getterMethodName = String.format("get%c%s",
						Character.valueOf(Character.toUpperCase(fieldName.charAt(0))), fieldName.substring(1));

				try {
					return entityClass.getMethod(getterMethodName);
				} catch (NoSuchMethodException | SecurityException e) {
					throw new IllegalArgumentException(String.format("Entity class %s does not have getter %s",
							entityClass.getName(), getterMethodName));
				}
			}
		}

		return null;
	}

	/**
	 * Recursively traverses all super classes to derive a list of all fields,
	 * including private ones.
	 *
	 * @param clazz Class
	 * @return list of fields
	 */
	private static Collection<Field> getAllFields(Class<?> clazz) {
		final Set<Field> fields = Sets.newHashSet();

		for (Class<?> cls = clazz; cls != null; cls = cls.getSuperclass()) {
			fields.addAll(Arrays.asList(cls.getDeclaredFields()));
		}

		return fields;
	}

	/**
	 * Returns the getter method for the given entity's primary key by using a cache
	 * lookup. If no entry exists in the cache, it is searched by reflection.
	 *
	 * @param entityClass entity class
	 * @return getter method or null if none found
	 */
	public static Method getEntityKeyGetter(Class<?> entityClass) {
		return idGetters.getUnchecked(entityClass);
	}

	/**
	 * @return an entity manager with dependent scope
	 */
	public static EntityManager getEntityManager() {
		return getInstance().entityManager;
	}

	/**
	 * Gets the singleton instance of this class
	 *
	 * @return {@link Tools} singleton
	 */
	private static Tools getInstance() {
		return CDI.current().select(Tools.class).get();
	}

	/**
	 * Tries to invoke {@link #loadFully(Object)} for each object in the list.
	 *
	 * @param list list
	 */
	public static void loadFully(List<?> list) {
		list.stream().forEach(Tools::loadFully);
	}

	/**
	 * Tries to load lazy values of the given object by invoking all of its getter
	 * methods. The operation is not transitive, i.e., no getters of referenced
	 * objects will be called. This method must be called within an active
	 * transaction!
	 *
	 * @param obj object to be loaded fully
	 */
	public static void loadFully(Object obj) {
		Preconditions.checkNotNull(obj);

		for (final Method method : obj.getClass().getMethods()) {
			// check whether method starts with get and has no parameters
			if (method.getName().startsWith("get") && (method.getParameterTypes().length == 0)) {
				try {
					final Object result = method.invoke(obj);

					if (result instanceof Collection) {
						((Collection<?>) result).size();
					}

					System.out.println(result);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// silently ignore errors here
					logger.info(
							String.format("Exception while invoking %s.%s", obj.getClass().getName(), method.getName()),
							e);
				}
			}
		}
	}
}
