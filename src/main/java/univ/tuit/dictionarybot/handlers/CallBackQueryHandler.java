package univ.tuit.dictionarybot.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallBackQueryHandler implements Handler<CallbackQuery> {
    @Override
    public SendMessage choose(CallbackQuery callbackQuery) {
        SendMessage sm = new SendMessage();
        sm.setChatId(String.valueOf(callbackQuery.getFrom().getId()));
        sm.setText(callbackQuery.getFrom().getFirstName());
        return sm;
    }
}
