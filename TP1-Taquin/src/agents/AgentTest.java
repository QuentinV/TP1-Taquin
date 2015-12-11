package agents;

import agents.algoPath.Astar;
import agents.algoPath.elements.Edge;
import communication.*;
import environnement.Block;
import environnement.Grille;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AgentTest extends Agent {
    protected static int MAX_WAITING_TIME = REFRESH_THREAD * 3;

    private boolean waitingAnswer;

    public AgentTest(Block b, Grille grille) {
        super(b, grille);

        this.waitingAnswer = false;
    }

    @Override
    public void run() {
        long waitingTime = 0;
        for (;;) {
            long timeStart = System.currentTimeMillis();

            if (isEnvSatisfied()) break;

            //System.out.println(this);

            Message m = BoiteAuxLettres.getByPriority(this);
            if (m != null) {
                //Récup la premiere requete
                int priority = 0;
                Message mP = m;
                while (mP.getAttach() != null) {
                    mP = mP.getAttach();
                    // priority += 1;
                }
                priority += mP.getEmetteur().getBlock().getPriority();// - 1;

                if (priority <= this.getBlock().getPriority())
                { //Ne pas prendre les messages avec une priorité inférieure
                    MessageContent mc = m.getContent();
                    //System.out.println("Message content");
                    if (mc != null) {
                        if (Transaction.RESPONSE == mc.getTrans()) {
                            waitingAnswer = false; //bonne reponse acquiese
                            System.out.println(this + " get a RESPONSE from " + m.getEmetteur());
                            if (mc.getCurrentPos().equals(b.getActual())) {
                                //Si le message correspond toujours à la position actuelle
                                if (!grille.checkCaseAt(mc.getNextPos())) { //bouger
                                    b.move(mc.getNextPos());
                                    //System.out.println("MOVE");
                                }
                            }

                            //propager la reponse
                            if (m.getAttach() != null && m.getAttach().getAttach() != null) {
                                m = m.getAttach().getAttach(); //Requete de la requete de la response

                                //envoi reponse
                                Message mess = new Message(
                                        this,
                                        m.getEmetteur(),
                                        new MessageContent(
                                                Transaction.RESPONSE,
                                                m.getContent().getAction(),
                                                m.getContent().getCurrentPos(),
                                                m.getContent().getNextPos(),
                                                m.getContent().getFinalPos()
                                        ),
                                        m);

                                BoiteAuxLettres.send(mess);
                                //System.out.println("message response send");
                            }

                        } else if (Transaction.REQUEST == mc.getTrans()) {
                            //System.out.println("REQUEST");
                            if (mc.getNextPos().equals(b.getActual())) { //si position voulu = position actuel de l'agent
                                //System.out.println("Someone need my position");

                                List<Edge> edgeAvoids = new ArrayList<>();
                                edgeAvoids.add(new Edge(b.getActual(), mc.getCurrentPos()));

                                Point goal = (!b.getActual().equals(b.getGoal()))
                                                ? b.getGoal()
                                                : new Point((grille.getSizeX() - 1 - mc.getFinalPos().x) / 2, (grille.getSizeY() - 1 - mc.getFinalPos().y) / 2);

                                Point p = new Astar().nextPos(b.getActual(), goal, grille, edgeAvoids);

                                if (p != null) {
                                    if (!grille.checkCaseAt(p)) { //bouger
                                        b.move(p);
                                        //System.out.println("MOVE");

                                        //envoi reponse
                                        Message mess = new Message(
                                                this,
                                                m.getEmetteur(),
                                                new MessageContent(
                                                        Transaction.RESPONSE,
                                                        mc.getAction(),
                                                        mc.getCurrentPos(),
                                                        mc.getNextPos(),
                                                        mc.getFinalPos()
                                                ),
                                                m);

                                        BoiteAuxLettres.send(mess);
                                        //System.out.println("message response send");
                                    } else { //Send a request to move
                                        Agent dest = environnement.get(grille.getCaseAt(p.x, p.y));
                                        //System.out.println("CONFLICT with " + dest);
                                        Message mess = new Message(this,
                                                dest,
                                                new MessageContent(Transaction.REQUEST, Action.MOVE, b.getActual(), p, b.getGoal()),
                                                m
                                        );
                                        BoiteAuxLettres.send(mess);
                                        System.out.println(this+" message request send to "+mess.getDestination());
                                    }
                                } else {
                                    System.out.println(this + " next pos null from request");
                                }
                            } else {
                                //envoi reponse
                                Message mess = new Message(
                                        this,
                                        m.getEmetteur(),
                                        new MessageContent(
                                                Transaction.RESPONSE,
                                                mc.getAction(),
                                                mc.getCurrentPos(),
                                                mc.getNextPos(),
                                                mc.getFinalPos()
                                        ),
                                        m);

                                BoiteAuxLettres.send(mess);
                                //System.out.println("message response send");
                            }
                        } else {
                            System.out.println("I do not know what is this mess");
                        }
                    }
                } else {
                    System.out.println(this+" message from "+m.getEmetteur()+" with less priority");

                    //envoi reponse
                    Message mess = new Message(
                            this,
                            m.getEmetteur(),
                            new MessageContent(
                                    Transaction.RESPONSE,
                                    m.getContent().getAction(),
                                    m.getContent().getCurrentPos(),
                                    m.getContent().getNextPos(),
                                    m.getContent().getFinalPos()
                            ),
                            m);

                    BoiteAuxLettres.send(mess);
                }
            } else
            { //Aucun message
                if (!b.isSatisfy() && !waitingAnswer)
                {
                    Point p = new Astar().nextPos(b, grille);
                    if (p != null) {
                        if (!grille.checkCaseAt(p)) { //bouger
                            b.move(p);
                        } else { //Send a request to move
                            Agent dest = environnement.get(grille.getCaseAt(p.x, p.y));
                            //System.out.println("CONFLICT with " + dest);
                            Message mess = new Message(this,
                                    dest,
                                    new MessageContent(Transaction.REQUEST, Action.MOVE, b.getActual(), p, b.getGoal())
                            );
                            BoiteAuxLettres.send(mess);
                            System.out.println(this+" message request (test) send to "+mess.getDestination());

                            waitingAnswer = true;
                        }
                    } else {
                        System.out.println(this+" next pos null");
                    }
                } else if (waitingAnswer) {
                    System.out.println(this+"is waiting for an answer");

                    waitingTime += (System.currentTimeMillis() - timeStart);
                    if (waitingTime >= MAX_WAITING_TIME)
                        waitingAnswer = false;

                } else {
                    //System.out.println(this+"is satistfied");
                }
            }

            try {
                long timeExec = System.currentTimeMillis() - timeStart;
                //System.out.println(this+" goes to sleep for "+String.valueOf(REFRESH_THREAD)+" ; exec : "+timeExec);
                Thread.sleep(REFRESH_THREAD);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("End of "+this);
    }
}
