package univ.tuit.dictionarybot.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import univ.tuit.dictionarybot.domains.LessonNumber;

import java.util.List;

public interface LessonNumberRepository extends JpaRepository<LessonNumber, Long>    {

}
