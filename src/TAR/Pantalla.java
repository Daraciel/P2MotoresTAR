package TAR;

import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.DifferentialPilot;

public class Pantalla extends Thread
{
	DifferentialPilot pilot;
	Pantalla(DifferentialPilot p)
	{
		pilot = p;
	}
	
	public void run()
	{
		float angle = 0;
		
		while(pilot.isMoving())
		{
			angle = pilot.getAngleIncrement();
			LCD.drawString("hola " + angle, 0, 0);	
		}
	}
}
