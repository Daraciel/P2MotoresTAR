package TAR;

import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.DifferentialPilot;

public class Pantalla implements Runnable
{
	DifferentialPilot pilot;
	private int end=0;
	Pantalla(DifferentialPilot p)
	{
		pilot = p;
	}
	
	//Mostramos por pantalla lo que nos dice los encodes de los angulos
	public void run()
	{
		float angle = 0;
        try 
        {
			Thread.sleep(200);
			
			
			LCD.clear();

			LCD.drawString("Prueba de angulos", 0, 0);
			//mientras se este moviendo
			while(pilot.isMoving())
			{
				Thread.sleep(100);
				//mostramos el angulo
				angle = pilot.getAngleIncrement();
				LCD.drawString("Angulo: " + angle, 0, 1);	
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally
        {
        	end = 1;
        }
	}
	public int getState()
	{
		return end;
	}
}
