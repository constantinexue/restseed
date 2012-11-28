package constantinexue.guicelike.repository;

import javax.persistence.EntityManager;

import com.google.inject.Inject;

public abstract class AbstractRepository<T> {
    
    @Inject
    private EntityManager entityManager;
    
    protected EntityManager entityManager() {
        return entityManager;
    }
    
    public T persist(T entity) {
        entityManager.persist(entity);
        entity = entityManager.merge(entity);
        
        return entity;
    }
}
