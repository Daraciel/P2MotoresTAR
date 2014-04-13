package TAR;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.TextMenu;

public class Main {
	
	private static String[] menu ={"Mision 1_1","Mision 1_2","Mision 1_3","Mision 1_4","Mision 2","Prueba","Salir"};
	
	public static void main(String[] args)
    {
		int mode = 0;
		do
		{
			LCD.clear();
	        TextMenu modeMenu = new TextMenu(menu, 1, "Misiones");
	        mode = modeMenu.select();
	        LCD.clear();
	        
	        switch(mode)
	        {
	        	case 0: LCD.drawString("Ejecutando mision\n 1_1...", 0, 0); Mision1_1(); break;
	        	case 1: LCD.drawString("Ejecutando mision\n 1_2...", 0, 0); Mision1_2(); break;
	        	case 2: LCD.drawString("Ejecutando mision\n 1_3...", 0, 0); Mision1_3(); break;
	        	case 3: LCD.drawString("Ejecutando mision\n 1_4...", 0, 0); Mision1_4(); break;
	        	case 4: LCD.drawString("Ejecutando mision\n 2...", 0, 0); Mision2(); break;
	        	case 5: Prueba(); break;
	        }			
		}
		while(mode != 6);

		

    }
	
	private static void Prueba()
	{
		LCD.clear();
		
		
		final DifferentialPilot pilot = new DifferentialPilot(43.2,155.0,Motor.C,Motor.B);
		
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		
		float movement = 0;
		int count = 0;
		for(;;)
		{
			movement = pilot.getMovementIncrement();
			pilot.forward();
		
			if(count == 10)
			{
				LCD.drawString("Movimiento: " + movement, 0, 0);
				count = 0;
			}
			count++;
		}
	}
	
	private static void Mision1_1()
	{
		final DifferentialPilot pilot = new DifferentialPilot(43.2,155.0,Motor.C,Motor.B);
		
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		
		pilot.travel(600.0);
	}
	
	private static void Mision1_2()
	{
		final DifferentialPilot pilot = new DifferentialPilot(43.2,155.0,Motor.C,Motor.B);
		
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		
		pilot.travel(-600.0);
	}
	
	private static void Mision1_3()
	{
		final DifferentialPilot pilot = new DifferentialPilot(43.2,155.0,Motor.C,Motor.B);
		
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		
		pilot.rotate(180.0);
	}
	
	private static void Mision1_4()
	{
		final DifferentialPilot pilot = new DifferentialPilot(43.2,155.0,Motor.C,Motor.B);
		
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		
		Movimiento m = new Movimiento(pilot);
		new Thread(m).start();
		
		Pantalla p = new Pantalla(pilot);
		new Thread(p).start();

	}
	
	private static void Mision2()
	{
		final DifferentialPilot pilot = new DifferentialPilot(43.2,155.0,Motor.C,Motor.B);
				
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		
		pilot.travel(600.0);
		pilot.rotate(-120.0);
		pilot.travel(1200.0);
		pilot.rotate(120.0);
		pilot.travel(600.0);
		pilot.rotate(120.0);
		pilot.travel(1200.0);
		pilot.rotate(-120.0);
	}

}
