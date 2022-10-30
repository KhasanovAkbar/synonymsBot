package univ.tuit.dictionarybot.processor;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import univ.tuit.dictionarybot.handlers.CallBackQueryHandler;
import univ.tuit.dictionarybot.handlers.MessageHandler;

import java.io.IOException;

@Component
public class DefaultProcessor implements Processor {

    private final CallBackQueryHandler callBackQueryHandler;
    private final MessageHandler messageHandler;

    public DefaultProcessor(CallBackQueryHandler callBackQueryHandler, MessageHandler messageHandler) {
        this.callBackQueryHandler = callBackQueryHandler;
        this.messageHandler = messageHandler;
    }

    @Override
    public SendMessage executeQuery(Message message) {
      return  messageHandler.choose(message);

    }

    @Override
    public SendMessage executeCallBackQuery(CallbackQuery callbackQuery) {
       return callBackQueryHandler.choose(callbackQuery);
    }
}
