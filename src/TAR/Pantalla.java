package TAR;

import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.DifferentialPilot;

public class Pantalla implements Runnable
{
	DifferentialPilot pilot;
	Pantalla(DifferentialPilot p)
	{
		pilot = p;
	}
	
	public void run()
	{
		float angle = 0;
        try 
        {
			Thread.sleep(200);
			
			LCD.clear();
			while(pilot.isMoving())
			{
				Thread.sleep(100);	
				angle = pilot.getAngleIncrement();
				LCD.drawString("hola " + angle, 0, 0);	
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
