package org.dhbw.mosbach.ai.freetimeactivityexplorer.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.db.BaseDao;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.SearchLabel;

import com.google.common.collect.ImmutableList;

@Dependent
public class SearchLabelDao extends BaseDao<SearchLabel, Long, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2851660350097526269L;

	private final Logger logger = Logger.getLogger("root");

	public SearchLabelDao() {
		super();
	}

	@Override
	public List<SearchLabel> getAll() {
		logger.log(Level.INFO, "Call to getAllSearchLabels");

		return ImmutableList.copyOf(super.getAll());
	}

	@Override
	public SearchLabel get(String searchLabel) {
		logger.log(Level.INFO, "Call to getSearchLabel({0})", searchLabel);

		if (searchLabel == null) {
			return null;
		}

		final List<SearchLabel> resultList = em
				.createQuery("from SearchLabel s where s.searchLabel = :searchLabel", SearchLabel.class)
				.setParameter("searchLabel", searchLabel).getResultList();

		return resultList.isEmpty() ? null : resultList.iterator().next();
	}

	@Override
	@Transactional
	public boolean add(SearchLabel searchLabel) {
		logger.log(Level.INFO, "Call to addSearchLabel({0})", searchLabel);

		try {
			persist(searchLabel);
		} catch (final Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean change(SearchLabel searchLabel) {
		logger.log(Level.INFO, "Call to changeSearchLabel({0})", searchLabel);

		if ((searchLabel != null) && (searchLabel.getSearchLabel() != null)) {
			delete(searchLabel);
			add(searchLabel);
		}

		return true;
	}

	@Override
	@Transactional
	public boolean delete(final SearchLabel searchLabel) {
		logger.log(Level.INFO, "Call to deleteSearchLabel({0})", searchLabel);

		return (searchLabel != null) ? deleteByKey(searchLabel.getSearchLabel()) : false;
	}

	@Override
	@Transactional
	public boolean deleteByKey(String key) {
		logger.log(Level.INFO, "Call to deleteByKey({0})", key);

		if (key != null) {

			final int updateNum = em.createQuery("DELETE FROM SearchLabel s WHERE s.searchLabel = :searchLabel")
					.setParameter("searchLabel", key).executeUpdate();

			return updateNum > 0;
		}

		return false;
	}
}
