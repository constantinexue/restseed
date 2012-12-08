package constantinexue.restseed.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import constantinexue.restseed.entity.PersistanceEntity;

public abstract class AbstractRepository<T extends PersistanceEntity> {
    
    @Inject
    private Provider<EntityManager> entityManagerProvider;
    
    private Class<T> entityClass;
    
    protected AbstractRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
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
    public T fetch(String id) {
        return entityManager().find(entityClass, id);
    }
    
    protected List<T> fetchResultList(CriteriaQuery<T> query) {
        List<T> entities = entityManager().createQuery(query)
                                          .getResultList();
        
        return entities;
    }
}
