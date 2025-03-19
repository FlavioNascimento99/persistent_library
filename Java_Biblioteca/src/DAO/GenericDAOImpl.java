package DAO;

import Interfaces.GenericDAO;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class GenericDAOImpl<T, Integer> implements GenericDAO<T> {
    protected static final Logger logger = Logger.getLogger(GenericDAOImpl.class);
    protected final EntityManager manager;
    protected final Class<T> entityClass;


    public GenericDAOImpl(EntityManager manager, Class<T> entityClass) {
        this.manager = manager;
        this.entityClass = entityClass;
    }


    public void save(T entity) {
        try {
            System.out.println("Saving entity: " + entity);
            manager.persist(entity);
        } catch (Exception e) {
            logger.error("Error saving entity: " + entity, e);
            throw new RuntimeException("Error saving entity", e);
        }
    }
    public void update(T entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        manager.merge(entity);

    }
    public void delete(T  entity){
        try {
            if (entity != null) {
                // Verifique se a entidade está gerenciada
                if (!manager.contains(entity)) {
                    entity = manager.merge(entity); // Garantir que está sendo gerenciado
                }
                manager.remove(entity); // Realiza a exclusão
            }
        } catch (Exception e) {
            logger.error("Erro ao excluir a entidade", e);
            throw new RuntimeException("Erro ao excluir a entidade", e);
        }
    }
    public List<T> list() {

        TypedQuery<T> query = manager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
        return query.getResultList();

    }
    public boolean isFieldExists(String fieldName, String currentValue) {

        TypedQuery<T> query = manager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE  e." + fieldName + " = :value", entityClass);
        query.setParameter("value", currentValue);
        return !query.getResultList().isEmpty();

    }
    protected TypedQuery<T> createQuery(String jpql) {

        return manager.createQuery(jpql, entityClass);

    }

}
