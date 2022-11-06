package univ.tuit.dictionarybot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.dictionarybot.domains.BotUser;
import univ.tuit.dictionarybot.repo.UserRepository;

import java.util.List;

@Component
@Repository
public class BotUserCache implements Cache<BotUser> {
    @Autowired
    UserRepository userRepository;

    @Override
    public void save(BotUser botUser) {

    }

    @Override
    public void update(BotUser botUser) {
        BotUser save;
        if (botUser.getUserId() != null) {
            save = userRepository.save(botUser);
        } else
            throw new NullPointerException("No id");
    }

    @Override
    public BotUser findBy(Long id, Integer sequence) {
        return userRepository.findByUserIdAndId(id, sequence);
    }

    @Override
    public BotUser findByWord(String str) {
        return null;
    }

    @Override
    public BotUser findById(int id) {
        return null;
    }

    @Override
    public List<BotUser> getAll() {
        return userRepository.getAll();
    }

    @Override
    public void delete(BotUser botUser) {
        userRepository.delete(botUser);
    }

}
