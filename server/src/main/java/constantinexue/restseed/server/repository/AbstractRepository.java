package constantinexue.restseed.server.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import constantinexue.restseed.server.entity.PersistanceEntity;
import constantinexue.restseed.server.repository.query.QueryBuilderContext;
import constantinexue.restseed.server.util.DebugLogger;

public abstract class AbstractRepository<T extends PersistanceEntity> {
    
    @Inject
    private Provider<EntityManager> entityManagerProvider;
    
    private Class<T> entityClass;
    
    protected AbstractRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
        DebugLogger.logClassCreated(this.getClass());
    }
    
    protected EntityManager entityManager() {
        return entityManagerProvider.get();
    }
    
    @Transactional
    public void create(T entity) {
        entityManager().persist(entity);
    }
    
    @Transactional
    public T update(T entity) {
        return entityManager().merge(entity);
    }
    
    @Transactional
    public void delete(T entity) {
        entity = fetch(entity.getId());
        entityManager().remove(entity);
    }
    
    @Transactional
    public void delete(String id) {
        PersistanceEntity entity = fetch(id);
        entityManager().remove(entity);
    }
    
    @Transactional
    public T fetch(String id) {
        return entityManager().find(entityClass, id);
    }
    
    protected <K> K fetchSingleResult(CriteriaQuery<K> query) {
        K entity = entityManager().createQuery(query).getSingleResult();
        
        return entity;
    }
    
    protected List<T> fetchResultList(CriteriaQuery<T> query, int skip, int take) {
        List<T> entities = entityManager().createQuery(query)
                                          .setFirstResult(skip)
                                          .setMaxResults(take)
                                          .getResultList();
        
        return entities;
    }
    
    protected QueryBuilderContext<T> createQueryBuilderContext(Class<T> clazz) {
        return new QueryBuilderContext<T>(entityManager(), clazz);
    }
}
