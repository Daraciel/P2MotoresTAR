package Sensores;
import javax.sound.sampled.Port;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.UARTPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.SensorConstants;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.SampleProvider;
import lejos.utility.TextMenu;


public class Sensores 
{

	private static String[] menu ={"Mision 3_1","Mision 3_2","Mision 4_1","Mision 4_2","Mision 5_1","Mision 5_2","Mision 6","Salir"};
	
	public static void main(String[] args) throws InterruptedException
    {
		int mode = 0;
		int resul = 0;
		int salir = menu.length;
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
	        	case 0: LCD.drawString("Ejecutando mision\n 3_1...", 0, 0); mision3_1(); break;
	        	case 1: LCD.drawString("Ejecutando mision\n 3_2...", 0, 0); mision3_2(); break;
	        	case 2: LCD.drawString("Ejecutando mision\n 4_1...", 0, 0); mision4_1(); break;
	        	case 3: LCD.drawString("Ejecutando mision\n 4_2...", 0, 0); mision4_2(); break;
	        	case 4: LCD.drawString("Ejecutando mision\n 5_1...", 0, 0); mision5_1(); break;
	        	case 5: LCD.drawString("Ejecutando mision\n 5_2...", 0, 0); mision5_2(); break;
	        	case 6: LCD.drawString("Ejecutando mision\n 6...", 0, 0); mision6(); break;
	        }
	        if(mode!=salir)
	        {
		        LCD.drawString("Presiona un boton para continuar",0,7);
		        Button.waitForAnyPress(4000);
	        }
		}
		while(mode != salir);
    
    }
	
	
	
	
	//distancia entre ruedas
	private static double axisDistance=159.0;
	//diametro de la rueda
	private static double wheelRadius=42.0;
	
/*mision3_1*/
	
	
	
	public static void mision3_1()
	{
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);
		DifferentialPilot pilot = new DifferentialPilot(wheelRadius,axisDistance,Motor.C,Motor.B);
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);

		pilot.forward();
		while(!isPressed(touch))
		{
			//muevete hacia adelante
			//pilot.travel(10.0);
		}
		pilot.stop();
	
	}
/*end 3_1*/

	/*mision3_2*/
	public static void mision3_2()
	{
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);
	
		int cont = 0;
		int delay = 3000;
		double distancia =0;
		while(!isPressed(touch)){}
		cont++;
		while(delay <= 0){
			if(isPressed(touch))
				cont++;
		}
	
		while(mueveteYGira(touch, 360/cont, distancia));
	}
	
	private static boolean mueveteYGira(EV3TouchSensor touch, double giro, double distancia)
	{
		DifferentialPilot pilot = new DifferentialPilot(wheelRadius,axisDistance,Motor.C,Motor.B);
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		//mueve distancia
		pilot.travel(distancia);
		if(isPressed(touch))
		{
			return false;
		}
		pilot.rotate(giro);
		if(isPressed(touch))
		{
			return false;
		}
		return true;
	}
	/*end 3_2 */

	/*mision 4_1*/
	public static void mision4_1()
	{
		LCD.drawString("mision 4_1", 0, 0);
		Button.waitForAnyPress(4000);
		LCD.clear();
		int but=0;
		EV3ColorSensor colSensor = new EV3ColorSensor(SensorPort.S1);
		//To get the Color
		//float[] sample = new float[colSensor.sampleSize()];
		float[] sample = new float[100];
		while(but == 0)
		{
			colSensor.getRedMode().fetchSample(sample, 0);
			LCD.drawString("R: "+sample[0], 0, 0);
			LCD.drawString("G: "+sample[1], 0, 1);
			LCD.drawString("B: "+sample[2], 0, 2);
			LCD.drawString("Color: "+colSensor.getColorID(), 0, 3);
			LCD.drawString("Luz: "+colSensor.getFloodlight(), 0, 4);
			but = Button.readButtons();
		}
		colSensor.close();
	
		//To get the reflection/ambient:
	}

/*mision 4_2*/
	public static void mision4_2()
	{ //si detecta un choque debe salir
	
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
		EV3ColorSensor colSensor = new EV3ColorSensor(SensorPort.S2);
		while(isPressed(touch))
		{
			if(colSensor.getColorID() == SensorConstants.BLACK)
				mueveteYGira(touch, 180, 0); //se moverá una pequeña distancia para ir comprobando la luz
			else
				mueveteYGira(touch, 0, 10); //se moverá una pequeña distancia para ir comprobando la luz
		}
	
	}

	public static void mision5_1()
	{
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
		EV3UltrasonicSensor sonar = new EV3UltrasonicSensor(SensorPort.S3.open(UARTPort.class));
		sonar.enable();
		SampleProvider distanciaAnt = sonar.getDistanceMode();
		while(isPressed(touch)){
			sonar.enable();
			SampleProvider distanceAct = sonar.getDistanceMode();
			//mueveteYGira(touch,0,distanciaAnt - distanceAct);
			distanciaAnt = distanceAct;
			sonar.disable();
		}
	}

	public static void mision5_2()
	{
	
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
		EV3UltrasonicSensor sonar = new EV3UltrasonicSensor(SensorPort.S3.open(UARTPort.class));
		sonar.enable();
		while(!isPressed(touch)){
			sonar.enable();/*
			if(sonar.getDistanceMode()<20)
			{
				mueveteYGira(touch, 90, 0);
			}
			else{
				mueveteYGira(touch, 0, 20);
			}*/
	
			sonar.disable();
		}
	}

	public static void mision6() throws InterruptedException
	{
		NXTSoundSensor sound = new NXTSoundSensor(SensorPort.S4);
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);

	    LCD.clear();
	    int cont =0;
	    while (!Button.ESCAPE.isDown()) {
	        LCD.drawString("SS: " + sonidos(sound), 0, 0);
	        System.out.println("SS: " + sonidos(sound));
	        if(sonidos(sound)>0.8){
	        	cont++;
	        }
	        else{ //acciones
	        	switch(cont){
	        		case 1: mueveteYGira(touch, 0, 2000);
	        			break;
	        		case 2: mueveteYGira(touch, 180, 0); //giro a la derecha
	        				mueveteYGira(touch, 0, 2000);
	        			break;
	        		case 3: mueveteYGira(touch, -360, 0);
	        			break;
	        	}
	        	cont = 0;
	        }
	        Thread.sleep(200);
	    }

	    System.out.println("EXIT");
	    System.exit(0);
	}
	
	public static float sonidos(NXTSoundSensor port){
		float[] sample = new float[port.sampleSize()];
		port.fetchSample(sample,0);
		return sample[0];
   	}
	
	  public static boolean isPressed(EV3TouchSensor sensor) 
	  {
		  
		  float[] sample = new float[sensor.sampleSize()];
		  sensor.fetchSample(sample, 0);
		  if (sample[0] == 0)
			  return false;
		  return true;
	  }

}


