package univ.tuit.dictionarybot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import univ.tuit.dictionarybot.domains.BotUser;

import java.util.List;

public interface UserRepository extends JpaRepository<BotUser, Long> {

    BotUser findByUserIdAndId(Long id, Integer sequence);

    @Query(value = "select u from BotUser u order by u.id desc ")
    List<BotUser> getAll();

}
