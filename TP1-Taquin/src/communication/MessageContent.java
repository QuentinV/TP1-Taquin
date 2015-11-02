/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication;

import java.awt.Point;

/**
 *
 * @author p1308391
 */
public class MessageContent {
    private Transaction trans;
    private Action action;
    private Point nextPos;
    private Point finalPos;

    public MessageContent(Transaction trans, Action action, Point nextPos, Point finalPos) {
        this.trans = trans;
        this.action = action;
        this.nextPos = nextPos;
        this.finalPos = finalPos;
    }

    public Transaction getTrans() {
        return trans;
    }

    public void setTrans(Transaction trans) {
        this.trans = trans;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Point getNextPos() {
        return nextPos;
    }

    public void setNextPos(Point nextPos) {
        this.nextPos = nextPos;
    }

    public Point getFinalPos() {
        return finalPos;
    }

    public void setFinalPos(Point finalPos) {
        this.finalPos = finalPos;
    }
    
    
}
