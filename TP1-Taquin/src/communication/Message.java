package communication;

import agents.Agent;

/**
 *
 * @author p1308391
 */
public class Message {
    private Agent emetteur;
    private Agent destination;

    private MessageContent content;

    private Message attach;

    public Message(Agent emetteur, Agent destination, MessageContent content) {
        this.emetteur = emetteur;
        this.destination = destination;
        this.content = content;
    }

    public Message(Agent emetteur, Agent destination, MessageContent content, Message attach) {
        this.emetteur = emetteur;
        this.destination = destination;
        this.content = content;
        this.attach = attach;
    }

    public Message getAttach() {
        return attach;
    }

    public void setAttach(Message attach) {
        this.attach = attach;
    }

    public Agent getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(Agent emetteur) {
        this.emetteur = emetteur;
    }

    public Agent getDestination() {
        return destination;
    }

    public void setDestination(Agent destination) {
        this.destination = destination;
    }

    public MessageContent getContent() {
        return content;
    }

    public void setContent(MessageContent content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "["+emetteur+"], ["+destination+"] : "+content;
    }
}
