package com.argusoft.kite.database.common.impl;

import com.argusoft.kite.database.common.GenericDao;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;

/**
 * Basic CRUD operations are implemented in the GenericDao interface.
 *
 * @author Kelvin
 * @param <EntityType>
 * @param <IDType>
 * @since 15-JAN-2014
 */
public abstract class BaseAbstractGenericDao<EntityType, IDType extends Serializable>
        implements GenericDao<EntityType, IDType> {

    @Autowired
    protected SessionFactory sessionFactory;

    private final Class<EntityType> persistentClass = (Class<EntityType>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

    public Class<EntityType> getPersistentClass() {
        return persistentClass;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public Criteria getCriteria() {
        return sessionFactory.getCurrentSession().createCriteria(persistentClass);
    }

    public void flush() {
        getCurrentSession().flush();
    }

    /**
     * Return the persistent instance of the given entity class with the given
     * identifier, or null if there is no such persistent instance.
     *
     * @param id an identifier
     * @return a persistent instance or null
     */
    @Override
    @SuppressWarnings("unchecked")
    public EntityType retrieveById(IDType id) {
//        if (HkEntity.class.isAssignableFrom(persistentClass)) {
//            Object entity = getCurrentSession().get(persistentClass, id);
//            if (!((HkEntity) entity).isArchive) {
//                return (EntityType) entity;
//            } else {
//                return null;
//            }
//        } else {
        return (EntityType) getCurrentSession().get(persistentClass, id);
//        }

    }

    /**
     * Return the persistent instances of the given entity class with the given
     * list of identifier, or null if there is no such persistent instances.
     *
     * @param idFieldName
     * @param ids
     * @return
     */
    /* Added on 12/03/2014 by satyajit */
    @Override
    public List<EntityType> retriveByIds(String idFieldName, List<IDType> ids) {
        List<EntityType> entities = null;
        DetachedCriteria detachedCriteria = DetachedCriteria
                .forClass(getPersistentClass());
        if (ids != null && !ids.isEmpty()) {
            detachedCriteria.add(Restrictions.in(idFieldName, ids));
            entities = findByCriteria(detachedCriteria);
            DistinctRootEntityResultTransformer transformer = DistinctRootEntityResultTransformer.INSTANCE;
            entities = transformer.transformList(entities);
        }
        return entities;
    }

    /**
     * Persist the given transient instance, first assigning a generated
     * identifier.
     *
     * @param entity object a transient instance of a persistent class
     * @return the generated identifier
     */
    @Override
    public IDType create(EntityType entity) {
        Serializable id = getCurrentSession().save(entity);
        return (IDType) id;
    }

    /**
     * Either save or update the given instance, depending upon resolution of
     * the unsaved-value checks.
     *
     * @param entity object a transient or detached instance containing new or
     * updated state
     */
    @Override
    public void createOrUpdate(EntityType entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    /**
     * Update the persistent instance with the identifier of the given detached
     * instance.
     *
     * @param entity object a detached instance containing updated state
     */
    @Override
    public void update(EntityType entity) {
        getCurrentSession().update(entity);
    }

    /**
     * Update the persistent instance with the identifier of the given detached
     * instance.
     *
     * @param entity object a detached instance containing updated state
     */
    @Override
    public void updateWithFlush(EntityType entity) {
        getCurrentSession().update(entity);
        getCurrentSession().flush();
    }

    /**
     * Remove a persistent instance from the data store. The argument may be an
     * instance associated with the receiving Session or a transient instance
     * with an identifier associated with existing persistent state. This
     * operation cascades to associated instances if the association is mapped
     * with cascade="delete".
     *
     * @param entity object the instance to be removed
     */
    @Override
    public void delete(EntityType entity) {
        getCurrentSession().delete(entity);
    }

    /**
     * Remove instance from the data store with provided identifier.
     *
     * @param id
     */
    @Override
    public void deleteById(IDType id) {
        EntityType entity = this.retrieveById(id);
        if (entity != null) {
            getCurrentSession().delete(entity);
        }
    }

    /**
     * Remove all the instance from the data store with provided identifier.
     *
     * @param entityList
     */
    @Override
    public void deleteAll(List<EntityType> entityList) {
        if (!CollectionUtils.isEmpty(entityList)) {
            for (EntityType entity : entityList) {
                getCurrentSession().delete(entity);
            }
        }
    }

    /**
     *
     * @return list of records or null
     */
    @Override
    public List<EntityType> retrieveAll() {
        return getCurrentSession().createCriteria(persistentClass)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    /**
     *
     * @param orderBy
     * @return list of sorted records of null
     */
    @Override
    public List<EntityType> findAll(String orderBy) {
        return getCurrentSession().createCriteria(persistentClass)
                .addOrder(Order.asc(orderBy))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<EntityType> findByCriteria(Criterion... criterion) {
        Criteria criteria = getCurrentSession().createCriteria(persistentClass);
        for (Criterion c : criterion) {
            criteria.add(c);
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public List<EntityType> findByCriteriaList(List<Criterion> criterions) {
        Criteria criteria = getCurrentSession().createCriteria(persistentClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public EntityType findEntityByCriteriaList(List<Criterion> criterions) {
        Criteria criteria = getCurrentSession().createCriteria(persistentClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (EntityType) criteria.uniqueResult();
    }

    @Override
    public int findTotalCountforCriteria(Criteria criteria) {
        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.list().get(0)).intValue();
    }

    /**
     * Updates all entities provided in entities
     *
     * @param entities
     */
    @Override
    public void updateAll(List<EntityType> entities) {
        Session session = getCurrentSession();
        for (EntityType entity : entities) {
            session.update(entity);
        }
    }

    @Override
    public void createOrUpdateAll(List<EntityType> entitytypes) {
        Session session = getCurrentSession();
        for (EntityType entity : entitytypes) {
            session.saveOrUpdate(entity);
        }
    }

    /*
     * 
     * MERGE WITH HIBERNATE TEMPLATE METHODS
     */
    private boolean allowCreate = true;
    private boolean alwaysUseNewSession = false;
    private boolean exposeNativeSession = false;
    private boolean checkWriteOperations = true;
    private boolean cacheQueries = false;
    private String queryCacheRegion;
    private int fetchSize = 0;
    private int maxResults = 0;

    /**
     * Return if a new Session should be created if no thread-bound found.
     *
     * @return
     */
    public boolean isAllowCreate() {
        return this.allowCreate;
    }

    /**
     * Set whether to always use a new Hibernate Session for this template.
     * Default is "false"; if activated, all operations on this template will
     * work on a new Hibernate Session even in case of a pre-bound Session (for
     * example, within a transaction or OpenSessionInViewFilter).
     * <p>
     * Within a transaction, a new Hibernate Session used by this template will
     * participate in the transaction through using the same JDBC Connection. In
     * such a scenario, multiple Sessions will participate in the same database
     * transaction.
     * <p>
     * Turn this on for operations that are supposed to always execute
     * independently, without side effects caused by a shared Hibernate Session.
     *
     * @param alwaysUseNewSession
     */
    public void setAlwaysUseNewSession(boolean alwaysUseNewSession) {
        this.alwaysUseNewSession = alwaysUseNewSession;
    }

    /**
     * Return whether to always use a new Hibernate Session for this template.
     *
     * @return
     */
    public boolean isAlwaysUseNewSession() {
        return this.alwaysUseNewSession;
    }

    /**
     * Return whether to expose the native Hibernate Session to
     * HibernateCallback code, or rather a Session proxy.
     *
     * @return
     */
    public boolean isExposeNativeSession() {
        return this.exposeNativeSession;
    }

    /**
     * Return whether to check that the Hibernate Session is not in read-only
     * mode in case of write operations (save/update/delete).
     *
     * @return
     */
    public boolean isCheckWriteOperations() {
        return this.checkWriteOperations;
    }

    /**
     * Return whether to cache all queries executed by this template.
     *
     * @return
     */
    public boolean isCacheQueries() {
        return this.cacheQueries;
    }

    /**
     * Return the name of the cache region for queries executed by this
     * template.
     *
     * @return
     */
    public String getQueryCacheRegion() {
        return this.queryCacheRegion;
    }

    /**
     * Return the fetch size specified for this HibernateTemplate.
     *
     * @return
     */
    public int getFetchSize() {
        return this.fetchSize;
    }

    /**
     * Return the maximum number of rows specified for this HibernateTemplate.
     *
     * @return
     */
    public int getMaxResults() {
        return this.maxResults;
    }

    @Override
    public Serializable save(final Object entity) throws DataAccessException {
        return getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(final Object entity) throws DataAccessException {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(final Collection entities)
            throws DataAccessException {
        for (Iterator it = entities.iterator(); it.hasNext();) {
            getCurrentSession().saveOrUpdate(it.next());
        }
    }

    @Override
    public Object merge(final Object entity) throws DataAccessException {
        return getCurrentSession().merge(entity);
    }

    // -------------------------------------------------------------------------
    // Convenience finder methods for HQL strings
    // -------------------------------------------------------------------------
    @Override
    public List find(String queryString) throws DataAccessException {
        return find(queryString, (Object[]) null);
    }

    @Override
    public List find(String queryString, Object value)
            throws DataAccessException {
        return find(queryString, new Object[]{value});
    }

    /**
     *
     * @param queryString
     * @param values
     * @return
     * @throws DataAccessException
     */
    @Override
    public List find(final String queryString, final Object[] values)
            throws DataAccessException {
        Query queryObject = getCurrentSession().createQuery(queryString);
        prepareQuery(queryObject);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        return queryObject.list();
    }

    // Convenience finder methods for detached criteria
    // -------------------------------------------------------------------------
    @Override
    public List findByCriteria(DetachedCriteria criteria)
            throws DataAccessException {
        return findByCriteria(criteria, -1, -1);
    }

    @Override
    public List findByCriteria(final DetachedCriteria criteria,
            final int firstResult, final int maxResults)
            throws DataAccessException {

        Criteria executableCriteria = criteria
                .getExecutableCriteria(getCurrentSession());
        prepareCriteria(executableCriteria);
        if (firstResult >= 0) {
            executableCriteria.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            executableCriteria.setMaxResults(maxResults);
        }
        return executableCriteria.list();

    }

    /**
     * Prepare the given Query object, applying cache settings and/or a
     * transaction timeout.
     *
     * @param queryObject the Query object to prepare
     * @see #setCacheQueries
     * @see #setQueryCacheRegion
     */
    protected void prepareQuery(Query queryObject) {
        if (isCacheQueries()) {
            queryObject.setCacheable(true);
            if (getQueryCacheRegion() != null) {
                queryObject.setCacheRegion(getQueryCacheRegion());
            }
        }
        if (getFetchSize() > 0) {
            queryObject.setFetchSize(getFetchSize());
        }
        if (getMaxResults() > 0) {
            queryObject.setMaxResults(getMaxResults());
        }
    }

    /**
     * Prepare the given Criteria object, applying cache settings and/or a
     * transaction timeout.
     *
     * @param criteria the Criteria object to prepare
     * @see #setCacheQueries
     * @see #setQueryCacheRegion
     */
    protected void prepareCriteria(Criteria criteria) {
        if (isCacheQueries()) {
            criteria.setCacheable(true);
            if (getQueryCacheRegion() != null) {
                criteria.setCacheRegion(getQueryCacheRegion());
            }
        }
        if (getFetchSize() > 0) {
            criteria.setFetchSize(getFetchSize());
        }
        if (getMaxResults() > 0) {
            criteria.setMaxResults(getMaxResults());
        }
    }

}
