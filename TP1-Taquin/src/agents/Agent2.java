package agents;

import agents.algoPath.Astar;
import agents.algoPath.Edge;
import communication.*;
import environnement.Block;
import environnement.Grille;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Agent2 extends Agent {
    protected static int REFRESH_THREAD = 50;

    public Agent2(Block b, Grille grille) {
        super(b, grille);
    }

    @Override
    public void run() {
        int refresh;

       /* int cptReqFromSameAg = 1;
        Agent ag = null;
        Edge eAvoid = null;*/

        int cptReqToSameAg = 1;
        Agent ag2 = null;
        Edge eAvoid2 = null;
        int limitToSameAg = 5;

        for (;;) {
            refresh = REFRESH_THREAD;

            if (isEnvSatisfied()) break;

            Message m = BoiteAuxLettres.pollFirst(this);
            if (m != null)
            {
                MessageContent mc = m.getContent();
                if (mc.getTrans() == Transaction.REQUEST)
                {
                    if (mc.getNextPos() != null && mc.getNextPos().equals(this.getBlock().getActual()))
                    { //request from someone to move
                        /*if (m.getEmetteur().equals(ag)) {
                            cptReqFromSameAg++;
                        } else {
                            ag = m.getEmetteur();
                            cptReqFromSameAg = 1;
                        }*/

                        Point pNext = null;
                        if (!b.isSatisfy())
                        { //n'est pas déjà sur son but
                            Edge e = new Edge(mc.getNextPos(), mc.getCurrentPos());
                            List<Edge> le = new ArrayList<>();
                            le.add(e);
                            /*if (eAvoid != null) {
                                le.add(eAvoid);
                                eAvoid = null;
                                cptReqFromSameAg = 1;
                            }*/
                            if (eAvoid2 != null)
                            {
                                le.add(eAvoid2);
                                eAvoid2 = null;
                                cptReqToSameAg = 1;
                            }
                            pNext = new Astar().nextPos(b, grille, le);
                            System.out.println(this+" algo astar request");
                        } else
                        { //s'eloigner dans la direction opposé à l'autre
                            pNext = new Astar().nextPosAway(b.getActual(), mc.getCurrentPos(), grille);
                            System.out.println(this+" algo test request");
                        }

                        if (pNext != null) {
                            if (!grille.checkCaseAt(pNext))  //bouger
                            {
                                b.move(pNext);
                                /*if (cptReqFromSameAg >= 3) {
                                    eAvoid = new Edge(pNext, b.getActual());
                                    System.out.println(this+" nb request > from "+m.getEmetteur());
                                }*/
                                System.out.println(this+" is moving to "+pNext+" due to a request");
                            } else {
                                Agent dest = environnement.get(grille.getCaseAt(pNext.x, pNext.y));
                                System.out.println("CONFLICT with " + dest);
                                Message mess = new Message(this,
                                        dest,
                                        new MessageContent(Transaction.REQUEST, Action.MOVE, b.getActual(), pNext, b.getGoal())
                                );
                                BoiteAuxLettres.send(mess);
                                System.out.println(this+" message request 2 send to "+mess.getDestination());

                                if (dest.equals(ag2))
                                {
                                    cptReqToSameAg++;
                                } else {
                                    ag2 = dest;
                                    cptReqToSameAg = 1;
                                }

                                if (cptReqToSameAg >= limitToSameAg) {
                                    eAvoid2 = new Edge(b.getActual(), pNext);
                                    System.out.println(this+" nb request > to "+dest);
                                }
                            }
                            refresh = REFRESH_THREAD * 2;//cptReqFromSameAg; //attendre plus longtemps
                            System.out.println(this+" sleeps for "+refresh);
                        }
                    }
                }
            } else {
                if (!b.isSatisfy())
                {
                    List<Edge> le = new ArrayList<>();
                   /* if (eAvoid != null) {
                        le.add(eAvoid);
                        eAvoid = null;
                        cptReqFromSameAg = 0;
                    }*/
                    if (eAvoid2 != null)
                    {
                        le.add(eAvoid2);
                        eAvoid2 = null;
                        cptReqToSameAg = 0;
                    }
                    Point p = new Astar().nextPos(b, grille, le);
                    if (p != null)
                    {
                        if (!grille.checkCaseAt(p))  //bouger
                        {
                            b.move(p);
                            //System.out.println(this+" is moving to "+p);
                        } else {
                            Agent dest = environnement.get(grille.getCaseAt(p.x, p.y));
                            System.out.println("CONFLICT with " + dest);
                            Message mess = new Message(this,
                                    dest,
                                    new MessageContent(Transaction.REQUEST, Action.MOVE, b.getActual(), p, b.getGoal())
                            );
                            BoiteAuxLettres.send(mess);
                            System.out.println(this+" message request 1 send to "+mess.getDestination());

                            if (dest.equals(ag2))
                            {
                                cptReqToSameAg++;
                            } else {
                                ag2 = dest;
                                cptReqToSameAg = 1;
                            }

                            if (cptReqToSameAg >= limitToSameAg) {
                                eAvoid2 = new Edge(b.getActual(), p);
                                System.out.println(this+" nb request > to "+dest);
                            }

                            refresh = REFRESH_THREAD * cptReqToSameAg; //attendre plus longtemps
                        }
                    }
                }
            }

            try {
                Thread.sleep(refresh);
                //System.out.println(this+" waiting for "+refresh);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("End of "+this);
    }
}
