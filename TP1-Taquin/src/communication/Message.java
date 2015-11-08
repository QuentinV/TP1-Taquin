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

    private Transaction transaction;

    public Message(Agent emetteur, Agent destination, MessageContent content, Transaction transaction) {
        this.emetteur = emetteur;
        this.destination = destination;
        this.content = content;
        this.transaction = transaction;
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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
