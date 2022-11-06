package univ.tuit.dictionarybot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.dictionarybot.domains.LessonNumber;
import univ.tuit.dictionarybot.repo.LessonNumberRepository;

import java.util.List;

@Component
@Repository
public class LessonNumberCache implements Cache<LessonNumber> {

    @Autowired
    LessonNumberRepository lessonNumberRepository;
    @Override
    public void save(LessonNumber lessonNumber) {
        lessonNumberRepository.save(lessonNumber);
    }

    @Override
    public void update(LessonNumber lessonNumber) {
    }

    @Override
    public LessonNumber findBy(Long id, Integer sequence) {
        return null;
    }

    @Override
    public LessonNumber findByWord(String str) {
        return null;
    }

    @Override
    public LessonNumber findById(int id) {
        return null;
    }

    @Override
    public List<LessonNumber> getAll() {
        return lessonNumberRepository.findAll();
    }

    @Override
    public void delete(LessonNumber lessonNumber) {

    }
}
