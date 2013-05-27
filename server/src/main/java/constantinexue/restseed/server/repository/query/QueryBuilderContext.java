package constantinexue.restseed.server.repository.query;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class QueryBuilderContext<T> {
    
    private ArrayList<Predicate> predicates = new ArrayList<Predicate>();
    private Order order;
    private CriteriaBuilder builder;
    private Root<T> root;
    private CriteriaQuery<T> query;
    private Class<T> clazz;
    
    public QueryBuilderContext(EntityManager entityManager, Class<T> clazz) {
        builder = entityManager.getCriteriaBuilder();
        query = builder.createQuery(clazz);
        root = query.from(clazz);
        this.clazz = clazz;
    }
    
    public CriteriaQuery<T> getQuery() {
        query.select(root)
             .where(predicates.toArray(new Predicate[0]));
        if (order == null) {
            // Default order by database
        }
        else {
            query.orderBy(order);
        }
        return query;
    }
    
    public CriteriaQuery<Long> getCountQuery() {
        CriteriaQuery<Long> cq = builder.createQuery(Long.class);
        cq.select(builder.count(cq.from(clazz))).where(predicates.toArray(new Predicate[0]));
        return cq;
    }
    
    public QueryBuilderContext<T> equal(Object object, String fieldName) {
        if (object != null) {
            Path<?> path = root.get(fieldName);
            Predicate predicate = builder.equal(path, object);
            predicates.add(predicate);
        }
        return this;
    }
    
    public <Y extends Comparable<? super Y>> QueryBuilderContext<T> greaterThan(Y object, String fieldName) {
        if (object != null) {
            Path<Y> path = root.get(fieldName);
            Predicate predicate = builder.greaterThan(path, object);
            predicates.add(predicate);
        }
        return this;
    }
    
    public <Y extends Comparable<? super Y>> QueryBuilderContext<T> lessThan(Y object, String fieldName) {
        if (object != null) {
            Path<Y> path = root.get(fieldName);
            Predicate predicate = builder.lessThan(path, object);
            predicates.add(predicate);
        }
        return this;
    }
    
    public QueryBuilderContext<T> like(String keyword, String fieldName) {
        if (keyword != null) {
            Path<String> path = root.<String>get(fieldName);
            Predicate predicate = builder.like(path, "%" + keyword + "%");
            predicates.add(predicate);
        }
        return this;
    }
    
    public QueryBuilderContext<T> orderByAsc(String fieldName) {
        order = builder.asc(root.get(fieldName));
        return this;
    }
    
    public QueryBuilderContext<T> orderByDesc(String fieldName) {
        order = builder.desc(root.get(fieldName));
        return this;
    }
}
