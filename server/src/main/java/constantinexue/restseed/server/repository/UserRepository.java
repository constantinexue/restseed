package constantinexue.restseed.server.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import constantinexue.restseed.server.entity.UserEntity;
import constantinexue.restseed.server.util.IDUtils;

@Singleton
public class UserRepository extends AbstractRepository<UserEntity> {
    
    public UserRepository() {
        super(UserEntity.class);
    }
    
    @Transactional
    public UserEntity create(String username, String password) {
        UserEntity user = new UserEntity();
        user.setId(IDUtils.generate())
            .setUsername(username)
            .setPassword(password)
            .setCreatedAt(new Date());
        create(user);
        user = fetch(user.getId());
        
        return user;
    }
    
    @Transactional()
    public List<UserEntity> findAll() {
        CriteriaBuilder builder = entityManager().getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        
        query.select(root);
        
        return fetchResultList(query, 0, 100);
    }
}
