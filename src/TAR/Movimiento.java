package TAR;

import lejos.robotics.navigation.DifferentialPilot;

public class Movimiento implements Runnable
{
	DifferentialPilot pilot;
	Movimiento(DifferentialPilot p)
	{
		pilot = p;
	}
	
	public void run()
	{
        try 
        {
			Thread.sleep(200);
			pilot.rotate(180.0);
		} catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}