package constantinexue.guicelike.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public abstract class AbstractRepository<T> {
    
    @Inject
    private EntityManager entityManager;
    
    protected EntityManager entityManager() {
        return entityManager;
    }
    
    @Transactional
    public T persist(T entity) {
        entityManager.persist(entity);
        entity = entityManager.merge(entity);
        
        return entity;
    }
    
    protected List<T> getResultList(CriteriaQuery<T> query) {
        List<T> entities = entityManager.createQuery(query)
                                        .getResultList();
        
        return entities;
    }
}
