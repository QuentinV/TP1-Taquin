package agents.algoPath;

import agents.Agent;
import environnement.Block;
import environnement.Grille;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Test {
    public Point getNextPos(Block b, Grille g, Map<Block, Agent> environnement) {
        int offsetx = b.getGoal().x - b.getActual().x;
        int offsety = b.getGoal().y - b.getActual().y;
        List<Point> points = new ArrayList<Point>();
        
        if (offsetx != 0)
            offsetx = offsetx / Math.abs(offsetx);
        else
        {
	        if(g.checkCaseAt(b.getActual().x, b.getActual().y+(offsety / Math.abs(offsety))))
	        {
	        	int alea=new Random().nextInt(4);
	        	switch(alea)
	        	{
	        	case 2:offsetx=1;
	        	break;
	        	case 3:offsetx=-1;
	        	break;
	        	}
	        }
        }
        if (offsety != 0)
            offsety = offsety / Math.abs(offsety);
        else
        {
        	if(g.checkCaseAt(b.getActual().x+(offsetx / Math.abs(offsetx)), b.getActual().y))
        	{
	        	int alea=new Random().nextInt(4);
	        	switch(alea)
	        	{
	        	case 2:offsety=1;
	        	break;
	        	case 3:offsety=-1;
	        	break;
	        	}
        	}
        }
        if (!g.checkCaseAt(b.getActual().x + offsetx, b.getActual().y))
        {
        	Point pointaTest=new Point(b.getActual().x+offsetx, b.getActual().y);
        	//System.out.println("Move case autour calcule");
        	points.add(pointaTest);
        }
        if (!g.checkCaseAt(b.getActual().x, b.getActual().y + offsety))
        {
        	Point pointaTest=new Point(b.getActual().x, b.getActual().y + offsety);
        	//System.out.println("Move case autour calcule");
        	points.add(pointaTest);
        }
        if(!points.isEmpty()){
        	if(points.size()==2)	
        	{
        		Random rand=new Random();
        		int index=rand.nextInt(2);
        		//System.out.println("Move case autour calcule");
        		return points.get(index); 
        	}
        	else
        	{
        		//System.out.println("Move case autour calcule");
        		return points.get(0);
        	}
        }else
        {
        	Random rand=new Random();
    		int index=rand.nextInt(2);
    		switch(index)
    		{
    		case 0:
				//System.out.println("Move case pleine");
            return new Point(b.getActual().x+offsetx, b.getActual().y);
            
    		case 1:
				//System.out.println("Move case pleine");
            return new Point(b.getActual().x, b.getActual().y + offsety);
    		}
        }

        return null;
    }
}
