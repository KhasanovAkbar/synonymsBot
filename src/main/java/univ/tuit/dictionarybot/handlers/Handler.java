package univ.tuit.dictionarybot.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;

public interface Handler<T> {

    SendMessage choose(T t) throws IOException;
}
