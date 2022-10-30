package univ.tuit.dictionarybot.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.net.MalformedURLException;

public interface SendMessageImpl<T> {

    SendMessage start(T t);

    SendMessage aboutUs(T t);

    SendMessage addWord(T t);

    SendMessage addNewFile(T t) throws IOException;

    SendMessage lesson(T t);
    SendMessage lessonContinue(T t);

    SendMessage addTitle(T t);

    SendMessage deleteWords(T t);
}
