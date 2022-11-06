package univ.tuit.dictionarybot.cache;

import java.util.List;

public interface Cache<T> {

    void save (T t);
    void update(T t);

    T findBy(Long id, Integer sequence);

    T findByWord(String str);

    T findById(int id);

    List<T> getAll();

    void delete(T t);

}
