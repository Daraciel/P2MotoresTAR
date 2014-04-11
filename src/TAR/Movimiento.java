package TAR;

import lejos.robotics.navigation.DifferentialPilot;

public class Movimiento extends Thread
{
	DifferentialPilot pilot;
	Movimiento(DifferentialPilot p)
	{
		pilot = p;
	}
	
	public void run()
	{
		pilot.rotate(180.0);
	}
}