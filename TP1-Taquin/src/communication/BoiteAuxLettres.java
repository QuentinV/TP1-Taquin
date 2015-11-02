package communication;

import agents.Agent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author p1308391
 */
public class BoiteAuxLettres {
    private static final Map<Agent, LinkedList<Message>> boites = 
            new HashMap<Agent, LinkedList<Message>>();
    
    public static synchronized boolean send(Agent a, Message m)
    {
        if (m == null) return false;
        if (a == null) return false;
        
        LinkedList<Message> list = boites.get(a);
        if (list == null)
            list = new LinkedList<>();
               
        return list.add(m);
    }
    
    public static synchronized Message pollFirst(Agent a)
    {
        if (a == null) return null;
        
        LinkedList<Message> list = boites.get(a);
        if (list == null) return null;
        
        return list.pollFirst();
    }
}
