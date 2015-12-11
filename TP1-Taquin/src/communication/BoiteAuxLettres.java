package communication;

import agents.Agent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BoiteAuxLettres {
    private static final Map<Agent, LinkedList<Message>> boites = 
            new HashMap<Agent, LinkedList<Message>>();
    
    public static synchronized boolean send(Message m)
    {
        if (m == null) return false;
        if (m.getDestination() == null) return false;

        LinkedList<Message> list = boites.get(m.getDestination());
        if (list == null)
        {
            list = new LinkedList<>();
            boites.put(m.getDestination(), list);
        }

        return list.add(m);
    }
    
    public static synchronized Message pollFirst(Agent a)
    {
        if (a == null) return null;
        
        LinkedList<Message> list = boites.get(a);
        if (list == null) return null;
        
        return list.pollFirst();
    }

    public static synchronized Message getByPriority(Agent a)
    {
        if (a == null) return null;

        LinkedList<Message> list = boites.get(a);
        if (list == null || (list != null && list.isEmpty())) return null;

        int priority = list.getFirst().getEmetteur().getBlock().getPriority();
        int index = 0;
        for (int i = 0; i < list.size(); ++i)
        {
            int p = list.get(i).getEmetteur().getBlock().getPriority();
            if (p < priority)
            {
                priority = p;
                index = i;
            }
        }

        Message m = list.get(index);
        list.remove(index);

        return m;
    }
}
