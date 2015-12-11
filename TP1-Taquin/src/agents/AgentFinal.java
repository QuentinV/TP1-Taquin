package agents;

import agents.algoPath.Astar;
import agents.algoPath.Away;
import communication.*;
import environnement.Block;
import environnement.Grille;

import java.awt.*;

public class AgentFinal extends Agent {
    public AgentFinal(Block b, Grille grille)
    {
        super(b, grille);
    }

    @Override
    public void run()
    {
        int refresh;
        for (;;) {
            if (isEnvSatisfied()) break;

            refresh = REFRESH_THREAD;

            Message m = BoiteAuxLettres.pollFirst(this);
            if (m != null)
            {
                MessageContent mc = m.getContent();
                if (mc.getTrans() == Transaction.REQUEST)
                {
                    if (mc.getNextPos() != null && mc.getNextPos().equals(this.getBlock().getActual()))
                    {
                        Point pNext = new Away().nextPos(b.getActual(), grille);
                        if (pNext != null) {
                            if (!grille.checkCaseAt(pNext))  //bouger
                            {
                                b.move(pNext);
                                //System.out.println(this+" is moving to "+pNext+" due to a request");
                            } else {
                                Agent dest = environnement.get(grille.getCaseAt(pNext.x, pNext.y));
                                //System.out.println("CONFLICT with " + dest);
                                Message mess = new Message(this,
                                        dest,
                                        new MessageContent(Transaction.REQUEST, Action.MOVE, b.getActual(), pNext, b.getGoal())
                                );
                                BoiteAuxLettres.send(mess);
                                //System.out.println(this+" message request 2 send to "+mess.getDestination());
                            }
                            refresh *= 2;
                        }
                    }
                }
            } else {
                if (!b.isSatisfy())
                {
                    Point p = new Astar().nextPos(b, grille);
                    if (p != null)
                    {
                        if (!grille.checkCaseAt(p))  //bouger
                        {
                            b.move(p);
                            //System.out.println(this+" is moving to "+p);
                        } else {
                            Agent dest = environnement.get(grille.getCaseAt(p.x, p.y));
                            //System.out.println("CONFLICT with " + dest);
                            Message mess = new Message(this,
                                    dest,
                                    new MessageContent(Transaction.REQUEST, Action.MOVE, b.getActual(), p, b.getGoal())
                            );
                            BoiteAuxLettres.send(mess);
                            //System.out.println(this+" message request 1 send to "+mess.getDestination());
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

        //System.out.println("End of "+this);
    }
}
