package TAR;

import lejos.robotics.navigation.DifferentialPilot;

public class Movimiento implements Runnable
{
	DifferentialPilot pilot;
	private int end=0;
	Movimiento(DifferentialPilot p)
	{
		pilot = p;
	}
	
	//En esta clase, unicamente ejecutamos el movimiento
	//que queramos, en este caso, 180 grados
	public void run()
	{
        try 
        {
			Thread.sleep(200);
			pilot.rotate(180.0);
		} 
        catch (InterruptedException e) 
		{
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