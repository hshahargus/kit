package com.argusoft.kite.database.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

/**
 * Basic CRUD operations are defined in GenericDao interface.
 *
 * @author kelvin
 * @param <EntityType> Represents ORM entity
 * @param <IDType> Identity property type
 * @since 15-JAN-2014
 */
public interface GenericDao<EntityType, IDType extends Serializable> {

    public EntityType retrieveById(IDType id);

    public IDType create(EntityType entity);

    public List<EntityType> retriveByIds(String idFieldName, List<IDType> ids);

    public void createOrUpdate(EntityType entity);

    public void update(EntityType entity);

    public void updateWithFlush(EntityType entity);

    public void delete(EntityType entity);

    public void deleteById(IDType id);

    public void deleteAll(List<EntityType> entityList);

    public List<EntityType> retrieveAll();

    public List<EntityType> findAll(String orderBy);

    public List<EntityType> findByCriteria(Criterion... criterion);

    public List<EntityType> findByCriteriaList(List<Criterion> criterions);

    public EntityType findEntityByCriteriaList(List<Criterion> criterions);

    public int findTotalCountforCriteria(Criteria criteria);

    public void updateAll(List<EntityType> e);

    public void createOrUpdateAll(List<EntityType> entitytypes);

    //-------------------------------------------------------------------------
    // Convenience finder methods for HQL strings
    //-------------------------------------------------------------------------
    public List find(String queryString) throws DataAccessException;

    public List find(String queryString, Object value) throws DataAccessException;

    /**
     *
     * @param queryString
     * @param values
     * @return
     * @throws DataAccessException
     */
    public List find(final String queryString, final Object[] values) throws DataAccessException;

    //-------------------------------------------------------------------------
    // Convenience finder methods for detached criteria
    //-------------------------------------------------------------------------
    public List findByCriteria(DetachedCriteria criteria) throws DataAccessException;

    public List findByCriteria(final DetachedCriteria criteria, final int firstResult, final int maxResults) throws DataAccessException;

    public Serializable save(final Object entity) throws DataAccessException;

    public void saveOrUpdate(final Object entity) throws DataAccessException;

    public void saveOrUpdateAll(final Collection entities);

    public Object merge(final Object entity) throws DataAccessException;

    public void flush();

}
