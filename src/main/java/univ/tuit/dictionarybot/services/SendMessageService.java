package univ.tuit.dictionarybot.services;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import univ.tuit.dictionarybot.cache.Cache;
import univ.tuit.dictionarybot.domains.BotUser;
import univ.tuit.dictionarybot.domains.LessonNumber;
import univ.tuit.dictionarybot.domains.Word;
import univ.tuit.dictionarybot.messageSender.MessageSender;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class SendMessageService implements SendMessageImpl<Message> {

    private final MessageSender messageSender;
    private final Cache<BotUser> userCache;
    private final Cache<Word> wordCache;
    private final Cache<LessonNumber> lessonNumberCache;
    Random random = new Random();
    @Value("${telegram.bot.token}")
    private String token;
    private static final String filePath = "/Users/akbarhasanov/IdeaProjects/DictionaryBot/src/main/resources/test.txt";
    static BotUser user = new BotUser();
    static LessonNumber lessonNumber = new LessonNumber();
    static Word word = new Word();

    public SendMessageService(Cache<BotUser> cache, MessageSender messageSender, Cache<Word> wordCache, Cache<LessonNumber> lessonNumberCache) {
        this.userCache = cache;
        this.messageSender = messageSender;
        this.wordCache = wordCache;
        this.lessonNumberCache = lessonNumberCache;
    }

    ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
    ArrayList<KeyboardRow> keyboardRow = new ArrayList<>();

    public static void info(Message message, long user_id) {
        String username = message.getFrom().getUserName();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String format = dateFormat.format(date);

        user.setUserId(user_id);
        user.setUsername(username);
        user.setTime(format);
        user.setIsAnswer(0);
        user.setLessonPage("none");
        user.setQuestion("none");
        user.setAnswer("none");
    }

    @Override
    public SendMessage start(Message message) {

        long chat_id = message.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Hello " + message.getFrom().getFirstName() + "\nWelcome to synonyms dictionary bot");
        sendMessage.setChatId(String.valueOf(chat_id));
        keyboardRow.clear();
        info(message, chat_id);
        userCache.update(user);
        List<LessonNumber> all = lessonNumberCache.getAll();
        if (all.size() != 0) {
            for (LessonNumber lessonNumber : all) {
                sendMessage.setReplyMarkup(buttons(lessonNumber.getLessonNumber()));
            }
        }
        messageSender.sendMessage(sendMessage);
        return null;
    }

    @Override
    public SendMessage aboutUs(Message message) {
        long chat_id = message.getChatId();

        return SendMessage.builder()
                .text("About")
                .chatId(String.valueOf(chat_id))
                .build();
       /* SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText("O‘t to‘la nigohim boqqanlarimni, \n" +
                "O‘zimga aytolmas yoqqanlarimni, \n" +
                "Yodidan chiqarmas raqamlarimni, \n" +
                "Qalaysiz men sevsam sevmagan qizlar.");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(Collections.singletonList(
                InlineKeyboardButton.builder()
                        .text("Yangi She'r")
                        .callbackData("next_poem")
                        .build()));
        inlineKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        messageSender.sendMessage(sendMessage);*/
    }

    @Override
    public SendMessage addWord(Message message) {
        long chat_id = message.getChatId();

        return SendMessage.builder()
                .text("Send txt file for add new words")
                .chatId(String.valueOf(chat_id))
                .build();
    }

    @Override
    public SendMessage addNewFile(Message message) {
        long chat_id = message.getChatId();
        Document document = message.getDocument();

        String fileId = document.getFileId();
        String urlContents = getUrlContents("https://api.telegram.org/bot" + token + "/getFile?file_id=" + fileId);

        File localFile = new File(filePath);
        InputStream is = null;
        try {
            is = new URL("https://api.telegram.org/file/bot" + token + "/" + urlContents).openStream();
            FileUtils.copyInputStreamToFile(is, localFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader buffer = new BufferedReader(
                new FileReader(filePath))) {
            String str;
            while ((str = buffer.readLine()) != null) {
                word = new Word();
                word.setSynonym(str.toLowerCase().trim());
                word.setFromTo("none");
                wordCache.save(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SendMessage.builder()
                .text("Add lesson number")
                .chatId(String.valueOf(chat_id))
                .build();
    }

    @Override
    public SendMessage lesson(Message message) {
        user.setLessonPage(message.getText());
        userCache.update(user);

        SendMessage sm = new SendMessage();
        Long chatId = message.getChatId();
        sm.setChatId(String.valueOf(chatId));

        List<Word> words = wordCache.getAll();
        List<Word> all = new ArrayList<>();
        for (Word word : words) {
            if (word.getFromTo().equals(message.getText())) {
                all.add(word);
            }
        }
        generate(sm, all);
        return null;
    }

    @Override
    public SendMessage lessonContinue(Message message) {

        BotUser byId = userCache.findBy(user.getUserId(), user.getId());
        SendMessage sm = new SendMessage();
        Long chatId = message.getChatId();
        List<Word> all = new ArrayList<>();
        sm.setChatId(String.valueOf(chatId));

        List<Word> words = wordCache.getAll();
        String special = null;
        for (Word word : words) {
            if (word.getFromTo().equals(byId.getLessonPage()) && word.getSynonym().contains(byId.getQuestion())) {
                special = word.getSynonym();
            }
        }
        String[] split = special.split("=");
        List<String> lastWord = new ArrayList<>();
        for (String s : split) {
            if (!s.equals("=")) {
                lastWord.add(s.trim());
            }
        }
        int temp = 0;
        for (String s : lastWord) {
            if (s.equals(message.getText().toLowerCase().trim())) {
                sm.setText("Correct✅\n" + special + "\nNext");

                messageSender.sendMessage(sm);
                break;
            }
            temp++;
        }
        if (temp == lastWord.size()) {
            sm.setText("Wrong❌\n" + special + "\nNext");
            messageSender.sendMessage(sm);
        }
        for (Word word : words) {
            if (word.getFromTo().equals(byId.getLessonPage())) {
                all.add(word);
            }
        }
        generate(sm, all);
return null;
    }

    private void generate(SendMessage sm, List<Word> all) {
        int size = random.nextInt(all.size() - 1);

        String str = all.get(size).getSynonym();

        String[] split = str.split("=");
        List<String> word = new ArrayList<>();
        for (String s : split) {
            if (!s.equals("=")) {
                word.add(s.trim());
            }
        }
        String synonyms = word.get(random.nextInt(word.size()));
        sm.setText(synonyms + "\n" + "Your answer: ");
        user.setQuestion(synonyms);
        userCache.update(user);
        messageSender.sendMessage(sm);
    }

    @Override
    public SendMessage addTitle(Message message) {
        long chat_id = message.getChatId();
        List<Word> all = wordCache.getAll();
        try {
            for (Word value : all) {
                if (value.getFromTo().equals("none")) {
                    Word byId = wordCache.findById(value.getId());
                    byId.setFromTo(message.getText());
                    wordCache.update(byId);
                }
            }
            lessonNumber = new LessonNumber();
            lessonNumber.setLessonNumber(message.getText());
            lessonNumberCache.save(lessonNumber);

            messageSender.sendMessage(SendMessage.builder()
                    .text("Words successfully added")
                    .chatId(String.valueOf(chat_id))
                    .build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            messageSender.sendMessage(SendMessage.builder()
                    .text("Words not added")
                    .chatId(String.valueOf(chat_id))
                    .build());
        }
        return null;
    }

    @Override
    public SendMessage deleteWords(Message message) {
        return null;
    }
    private ReplyKeyboardMarkup buttons(String str) {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(str);
        keyboardRow.add(row1);

        markup.setKeyboard(keyboardRow);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        return markup;
    }

    private static String getUrlContents(String theUrl) {
        String path = null;
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String[] split = bufferedReader.readLine().split(":");
            String a = split[split.length - 1];
            path = a.substring(1, a.length() - 3);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }


}
