package univ.tuit.dictionarybot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import univ.tuit.dictionarybot.cache.Cache;
import univ.tuit.dictionarybot.domains.BotUser;
import univ.tuit.dictionarybot.domains.LessonNumber;
import univ.tuit.dictionarybot.services.SendMessageService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class MessageHandler implements Handler<Message> {

    @Autowired
    private SendMessageService sendMessageService;

    @Autowired
    private final Cache<BotUser> cache;

    @Autowired
    private final Cache<LessonNumber> lessonNumberCache;

    public MessageHandler(Cache<BotUser> cache, Cache<LessonNumber> lessonNumberCache) {
        this.cache = cache;
        this.lessonNumberCache = lessonNumberCache;
    }


    @Override
    public SendMessage choose(Message message) {
        SendMessage sm = null;
        String user_first_name = message.getChat().getFirstName();
        String user_last_name = message.getChat().getLastName();
        long user_id = message.getChat().getId();
        String message_text = message.getText();
        String answer = message_text;

        if (message.hasText()) {
            log(user_first_name, user_last_name, Long.toString(user_id), message_text, answer);

            switch (message.getText()) {
                case "/start":
                    sm = sendMessageService.start(message);
                    //    cache.add(user);
                    break;

                case "/about":
                    sm = sendMessageService.aboutUs(message);
                    break;

                case "/add7660":
                    sm = sendMessageService.addWord(message);
                    break;
                case "/delete7660":
                    sm = sendMessageService.deleteWords(message);
                    break;

                case "/allUser7660":
                    sm = sendMessageService.allUsers(message);
                    break;

                default:
                    List<BotUser> all = cache.getAll();
                    BotUser lastUser = new BotUser();
                    for (BotUser botUser : all) {
                        if (botUser.getUserId().equals(user_id)) {
                            lastUser = botUser;
                            break;
                        }
                    }
                    List<LessonNumber> lessonAll = lessonNumberCache.getAll();
                    String lastNumber = "none";
                    for (LessonNumber lesson : lessonAll) {
                        if (lesson.getLessonNumber().equals(message_text)) {
                            lastNumber = message_text;
                            break;
                        }
                    }

                    if (message.getFrom().getId().equals(cache.findBy(user_id, lastUser.getId()).getUserId()) && !lastUser.getLessonPage().equals("none"))
                        sm = sendMessageService.lessonContinue(message);
                    else if (message_text.contains("from") && message_text.contains("to") && lastNumber.equals("none"))
                        sm = sendMessageService.addTitle(message);
                    else sm = sendMessageService.lesson(message);
            }
        } else if (message.hasDocument()) {
            sm = sendMessageService.addNewFile(message);
        }
        return sm;
    }

    public static void log(String first_name, String last_name, String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
    }

}
