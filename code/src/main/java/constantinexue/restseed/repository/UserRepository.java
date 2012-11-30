package constantinexue.restseed.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import constantinexue.restseed.entity.UserEntity;

@Singleton
public class UserRepository extends AbstractRepository<UserEntity> {
    
    @Transactional()
    public List<UserEntity> findAll() {
        CriteriaBuilder builder = entityManager().getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        
        query.select(root);
        
        return getResultList(query);
    }
}
