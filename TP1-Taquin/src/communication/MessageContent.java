package communication;

import java.awt.*;

public class MessageContent {
    private Transaction trans;
    private Action action;
    private Point nextPos;
    private Point finalPos;
    private Point currentPos;

    public MessageContent(Transaction trans, Action action, Point currentPos, Point nextPos, Point finalPos) {
        this.trans = trans;
        this.action = action;
        this.nextPos = nextPos;
        this.finalPos = finalPos;
        this.currentPos = currentPos;
    }

    public void setTrans(Transaction trans) {
        this.trans = trans;
    }

    public Point getCurrentPos() {
        return currentPos;
    }

    public Transaction getTrans() {
        return trans;
    }

    public Action getAction() {
        return action;
    }

    public Point getNextPos() {
        return nextPos;
    }

    public Point getFinalPos() {
        return finalPos;
    }

    @Override
    public String toString() {
        return getTrans()+" : "+getAction()+" - "+getNextPos()+" / "+getFinalPos();
    }
}
