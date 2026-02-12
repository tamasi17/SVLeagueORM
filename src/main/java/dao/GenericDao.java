package dao;

import java.util.List;

/**
 * Interfaz generica de DAO que obliga a utilizar los metodos CRUD basicos
 * @author mati
 */
public interface GenericDao<T> {

    void save(T entity);
    void delete(T entity);
    void update(T entity);
    T findById(Long id);
    List<T> findAll();

}
