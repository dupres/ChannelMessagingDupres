package dupre.stephen.channelmessagingdupres;

import java.util.List;

/**
 * Created by dupres on 06/02/2017.
 */
public class MessageContainer {
    private List<Message> messages;

    public MessageContainer(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {

        return messages;
    }

    @Override
    public String toString() {
        return "MessageContainer{" +
                "messages=" + messages +
                '}';
    }


    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
