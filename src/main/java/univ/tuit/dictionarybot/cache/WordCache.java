package univ.tuit.dictionarybot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.dictionarybot.domains.Word;
import univ.tuit.dictionarybot.repo.WordRepository;

import java.util.List;

@Component
@Repository
public class WordCache implements Cache<Word> {

    @Autowired
    WordRepository wordRepository;

    @Override
    public void save(Word word) {
        wordRepository.save(word);
    }

    @Override
    public void update(Word word) {
        wordRepository.save(word);
    }

    @Override
    public Word findBy(Long id, Integer sequence) {
        return null;
    }

    @Override
    public Word findByWord(String str) {
        return wordRepository.findBySynonym(str);
    }

    @Override
    public Word findById(int id) {
        return wordRepository.findById(id);
    }

    @Override
    public List<Word> getAll() {
        return wordRepository.getAll();
    }

    @Override
    public void delete(Word word) {

    }
}
