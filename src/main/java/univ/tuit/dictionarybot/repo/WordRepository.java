package univ.tuit.dictionarybot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import univ.tuit.dictionarybot.domains.Word;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {

    Word findBySynonym(String str);

    Word findById(int id);

    @Query(value = "select w from Word w order by w.id desc ")
    List<Word> getAll();


}
