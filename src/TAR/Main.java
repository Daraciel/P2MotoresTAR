package TAR;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.TextMenu;

public class Main {
	
	private static String[] menu ={"Mision 1_1","Mision 1_2","Mision 1_3","Mision 1_4","Mision 2","Prueba","Salir"};
	
	//distancia entre ruedas
	private static double axisDistance=159.0;
	//diametro de la rueda
	private static double wheelRadius=42.0;
	public static void main(String[] args)
    {
		int mode = 0;
		int resul = 0;
		do
		{
			//borramos la pantalla
			LCD.clear();
			//creamos el nuevo menu con el array de stirings
	        TextMenu modeMenu = new TextMenu(menu, 1, "Misiones");
	        //ponemos el modo a selección, para que el usuario pueda seleccionar
	        mode = modeMenu.select();
	        //borramos la pantalla, ya que ha seleccionado algo el usuario
	        LCD.clear();
	        
	        //Dependiendo de lo seleccionado, ejecutamos uno u otro
	        switch(mode)
	        {
	        	case 0: LCD.drawString("Ejecutando mision\n 1_1...", 0, 0); Mision1_1(); break;
	        	case 1: LCD.drawString("Ejecutando mision\n 1_2...", 0, 0); Mision1_2(); break;
	        	case 2: LCD.drawString("Ejecutando mision\n 1_3...", 0, 0); Mision1_3(); break;
	        	case 3: LCD.drawString("Ejecutando mision\n 1_4...", 0, 0); Mision1_4(); break;
	        	case 4: LCD.drawString("Ejecutando mision\n 2...", 0, 0); Mision2(); break;
	        	case 5: Prueba(); break;
	        }
	        if(mode!=6)
	        {
		        LCD.drawString("Presiona un boton para continuar",0,7);
		        Button.waitForAnyPress(4000);
	        }
		}
		while(mode != 6);

		

    }
	//Prueba para comprobar el movimiento
	private static void Prueba()
	{
		final DifferentialPilot pilot = new DifferentialPilot(43.2,155.0,Motor.C,Motor.B);
		
		LCD.clear();
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
		//Creamos un differentialPilot con los parametros dados
		final DifferentialPilot pilot = new DifferentialPilot(43.2,155.0,Motor.C,Motor.B);
		//le damos las ordenes basicas para que se mueva
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		//y lo hacemos mover 600 milimetros
		pilot.travel(600.0);
	}
	
	private static void Mision1_2()
	{
		//Creamos un differentialPilot con los parametros dados
		final DifferentialPilot pilot = new DifferentialPilot(43.2,155.0,Motor.C,Motor.B);
		//le damos las ordenes basicas para que se mueva
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		//Movemos hacia adelante 60 cm
		pilot.travel(600.0);
		//y hacia atras 60 cm
		pilot.travel(-600.0);
	}
	
	private static void Mision1_3()
	{
		//Creamos un differentialPilot con los parametros dados
		final DifferentialPilot pilot = new DifferentialPilot(43.2,155.0,Motor.C,Motor.B);
		//le damos las ordenes basicas para que se mueva
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		//rota 180 grados
		pilot.rotate(180.0);
	}
	
	private static void Mision1_4()
	{
		//Creamos un differentialPilot con los parametros dados
		final DifferentialPilot pilot = new DifferentialPilot(43.2,155.0,Motor.C,Motor.B);
		
		//le damos las ordenes basicas para que se mueva
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		//Preparamos dos hilos distintos: movimiento y pantalla
		//separados cada uno en un clase.
		Movimiento m = new Movimiento(pilot);
		Pantalla p = new Pantalla(pilot);
		
		
		try 
		{
			//Y ejecutamos cada hilo a la vez.
			Thread t1 = new Thread(m);
			t1.start();

			Thread t2 = new Thread(p);
			t2.start();
			
			t1.join();
			t2.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static void Mision2()
	{	
		//Creamos un differentialPilot con los parametros dados
		final DifferentialPilot pilot = new DifferentialPilot(43.2,155.0,Motor.C,Motor.B);
		//le damos las ordenes basicas para que se mueva
		float angle =0.0f;
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		
		//Realizamos los movimientos correspondientes
		//a la correcta realizacion del 8
		//nos movemos 60 cm
		pilot.travel(600.0);
		//para ejecutar los 60 grados mencionados en el doc
		pilot.rotate(-120.0);
		//mostramos el angulo girado
		angle = pilot.getAngleIncrement();
		LCD.clear();
		LCD.drawString("Angulo: "+angle, 0, 0);
		Button.waitForAnyPress(2000);
		//nos movemos 120 cm
		pilot.travel(1200.0);
		//equivalente a -60 grados en el dibujo
		pilot.rotate(120.0);
		//mostramos el angulo girado
		angle = pilot.getAngleIncrement();
		LCD.clear();
		LCD.drawString("Angulo: "+angle, 0, 0);
		Button.waitForAnyPress(2000);
		//nos movemos 60 cm
		pilot.travel(600.0);
		//equivalente a 60 grados en el dibujo
		pilot.rotate(120.0);
		//mostramos el angulo girado
		angle = pilot.getAngleIncrement();
		LCD.clear();
		LCD.drawString("Angulo: "+angle, 0, 0);
		Button.waitForAnyPress(2000);
		//nos movemos 120 cm
		pilot.travel(1200.0);
		//equivalente a 60 grados en el dibujo
		pilot.rotate(-120.0);
		//mostramos el angulo girado
		angle = pilot.getAngleIncrement();
		LCD.clear();
		LCD.drawString("Angulo: "+angle, 0, 0);
		Button.waitForAnyPress(2000);
	}

}
