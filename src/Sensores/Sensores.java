package Sensores;
import javax.sound.sampled.Port;

import Rober.Robot;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.TachoMotorPort;
import lejos.hardware.port.UARTPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.SensorConstants;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.TextMenu;


public class Sensores 
{

	private static boolean debug=false;
	private static String[] menu ={"Mision 3_1","Mision 3_2","Mision 4_1","Mision 4_2","Mision 5_1","Mision 5_2","Mision 6","Debug Mission","Salir"};
	private static float umbral_sensor = 60f;
	private static float umbral_sensor_min = 50f;
	private static float umbral_sensor_max = 60f;
	private Robot rober;

	/*		M O T O R E S		*//*
	private DifferentialPilot pilot;
	private EV3MediumRegulatedMotor headmotor;*/
	
	/*		S E N S O R E S		*//*
	private EV3IRSensor sonar;
	private EV3TouchSensor touch;
	private EV3ColorSensor color;
	private NXTSoundSensor microfono;*/
	
	/*		C O S A S			*/
	//distancia entre ruedas
	static double axisDistance=159.0;
	 //diametro de la rueda
	static double wheelRadius=42.0;
	
	
	public static void main(String[] args) throws InterruptedException
    {
		int mode = 0;
		int resul = 0;

		int salir = menu.length-1;
		if(debug)
		{
			System.out.println("Cosas");
			mision3_2();
		}
		else
		{
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
		        	case 0: LCD.drawString("Ejecutando 3_1...", 0, 0); mision3_1(); break;
		        	case 1: LCD.drawString("Ejecutando 3_2...", 0, 0); mision3_2(); break;
		        	case 2: LCD.drawString("Ejecutando 4_1...", 0, 0); mision4_1(); break;
		        	case 3: LCD.drawString("Ejecutando 4_2...", 0, 0); mision4_2(); break;
		        	case 4: LCD.drawString("Ejecutando 5_1...", 0, 0); mision5_1(); break;
		        	case 5: LCD.drawString("Ejecutando 5_2...", 0, 0); mision5_2(); break;
		        	case 6: LCD.drawString("Ejecutando 6...", 0, 0); mision6(); break;
		        	case 7: LCD.drawString("Ejecutando Debug...", 0, 0); debugmission(); break;
		        }
		        if(mode!=salir)
		        {
			        LCD.drawString("Presiona un boton para continuar",0,7);
			        Button.waitForAnyPress(4000);
		        }
			}
			while(mode != salir);
		}
		
    
    }
	
	
	




	
/*mision3_1*/
	
	
	
	public static void mision3_1()
	{
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);
		DifferentialPilot pilot = new DifferentialPilot(wheelRadius,axisDistance,Motor.C,Motor.B);
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(180.0);

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
	public static void mision3_2() throws InterruptedException
	{
		/*Para detectar el click en el sensor, haz que solo sume cuando el sensor se
		 * vuelva a poner a 0. Es decir, haga la secuencia 1 y luego 0*/
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);
	
		int cont = 2;
		int delay = 3000;
		double distancia = 500;
		double max_dist=1500;
		double min_dist=200;
		//while(!isPressed(touch)){}
		LCD.clear();
		LCD.drawString("Giros: "+cont, 0, 0);
		LCD.drawString("Distancia: "+distancia, 0, 1);
		while(Button.readButtons()!=Button.ID_ENTER)
		{
			if(distancia<min_dist)
				distancia = min_dist;
			else if(distancia>max_dist)
				distancia = max_dist;
			if(isPressed(touch))
			{
				while(isPressed(touch)){}
				if(cont==6)
					cont = 2;
				else
					cont++;
			}
			else if(Button.readButtons()==Button.ID_RIGHT)
			{
				if(distancia<max_dist)
					distancia+=5;
			}
			else if(Button.readButtons()==Button.ID_LEFT)
			{
				if(distancia>min_dist)
					distancia-=5;
			}
			System.out.println("Giros: " + cont);
			System.out.println("Distancia: " + distancia);
			LCD.drawString(""+cont, 7, 0);
			LCD.drawString(""+distancia, 11, 1);
		}
	
		mueveteYGira(touch, 360/cont, distancia);
	}
	
	private static boolean GoUntilCrash(DifferentialPilot p, double d, EV3TouchSensor touch)
	{
		boolean ok = false;
		p.travel(d, true);
		while(p.isMoving())
		{
			if(isPressed(touch))
			{
				ok=true;
				p.stop();
				return ok;
			}
		}
		
		return ok;
	}
	
	private static boolean mueveteYGira(EV3TouchSensor touch, double giro, double distancia)
	{
		DifferentialPilot pilot = new DifferentialPilot(wheelRadius,axisDistance,Motor.C,Motor.B);
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(720.0);
		boolean choque = false;
		//mueve distancia
		while(!choque)
		{
			choque = GoUntilCrash(pilot, distancia, touch);
			if(!choque)
			{
				pilot.rotate(giro);
			}
			/*if(isPressed(touch))
			{
				return false;
			}*/
		}
		return choque;
	}
	/*end 3_2 */

	/*mision 4_1*/
	public static void mision4_1()
	{
		DifferentialPilot pilot = new DifferentialPilot(wheelRadius,axisDistance,Motor.C,Motor.B);
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(10.0);
		
		LCD.drawString("mision 4_1", 0, 0);
		Button.waitForAnyPress(4000);
		LCD.clear();
		int but=0;
		EV3ColorSensor colSensor = new EV3ColorSensor(SensorPort.S1);
		//To get the Color
		//float[] sample = new float[colSensor.sampleSize()];
		float[] sample = new float[100];
		pilot.forward();
		while(but == 0)
		{
			colSensor.getRGBMode().fetchSample(sample, 0);
			
			LCD.drawString("R: "+sample[0], 0, 0);
			System.out.println("R: "+sample[0]);
			LCD.drawString("G: "+sample[1], 0, 1);
			System.out.println("G: "+sample[1]);
			LCD.drawString("B: "+sample[2], 0, 2);
			System.out.println("B: "+sample[2]);
			LCD.drawString("Color: "+colSensor.getColorID(), 0, 3);
			System.out.println("Color: "+colSensor.getColorID());
			LCD.drawString("Luz: "+colSensor.getFloodlight(), 0, 4);
			System.out.println("Luz: "+colSensor.getFloodlight());
			but = Button.readButtons();
		}
		colSensor.close();
	
		//To get the reflection/ambient:
	}

/*mision 4_2*/
	public static void mision4_2()
	{ //si detecta un choque debe salir
	
		DifferentialPilot pilot = new DifferentialPilot(wheelRadius,axisDistance,Motor.C,Motor.B);
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(10.0);
		float[] sample = new float[100];
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);
		EV3ColorSensor colSensor = new EV3ColorSensor(SensorPort.S1);
		while(!isPressed(touch))
		{
			colSensor.getRGBMode().fetchSample(sample, 0);
			if(sample[0]>0.1 && sample[1]>0.1 && sample[2]>0.1)
			{
				pilot.rotate(180);
				System.out.println("Detectado blanco. Girando 180�...");
			}
			else
			{
				pilot.forward(); //se moverá una pequeña distancia para ir comprobando la luz
			}
		}
		
		pilot.stop();
		colSensor.close();
	
	}

	public static void mision5_1()
	{
		DifferentialPilot pilot = new DifferentialPilot(wheelRadius,axisDistance,Motor.C,Motor.B);
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(100.0);
		int but=0;
		//EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
		EV3IRSensor sonar = new EV3IRSensor(SensorPort.S3.open(UARTPort.class));
		//sonar.enable();
		SampleProvider distance = sonar.getDistanceMode();

		/*float[] sample = new float[distance.sampleSize()];
		//SampleProvider distanciaAnt = distance.fetchSample(sample, 0);
		float distanciaAnt = sample[0];*/
		float[] sample = new float[distance.sampleSize()];
		float distanceAct;
		while(but == 0){
			distance.fetchSample(sample, 0);
			distanceAct = sample[0];
			
			LCD.drawString("Distancia: " + distanceAct, 0, 0);
			
			if(distanceAct > umbral_sensor)
			{
				pilot.forward();
			}
			else if(distanceAct < umbral_sensor)
			{
				pilot.backward();
			}
			else
			{
				pilot.stop();
			}
			
			//pilot.travel(distanciaAnt - distanceAct);
			but = Button.readButtons();
		}
		pilot.stop();
		//sonar.disable();
	}

	public static void mision5_2()
	{
		

		
		DifferentialPilot pilot = new DifferentialPilot(wheelRadius,axisDistance,Motor.C,Motor.B);
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(180.0);

		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);
		
		//pilot.travel(10);
		/*
		EV3MediumRegulatedMotor serv = new EV3MediumRegulatedMotor(MotorPort.D);
		

		serv.rotateTo(90);

		serv.rotateTo(0);
		serv.rotateTo(-90);*/
		/*

		
		*/

		EV3IRSensor sonar = new EV3IRSensor(SensorPort.S3.open(UARTPort.class));

		SampleProvider distance_sample = sonar.getDistanceMode();
		float[] sample = new float[distance_sample.sampleSize()];
		

		int but=0;
		
		
		pilot.forward();
		float distance=0;
		float lastdistance=0;
		
		while(but == 0)
		{

			lastdistance = distance;
			distance_sample.fetchSample(sample, 0);
			distance = sample[0];
			
			if(distance < umbral_sensor_min)
			{
				pilot.stop();
				pilot.rotate(15);
				pilot.travel(100);
				pilot.forward();
				do
				{

					lastdistance = distance;
					distance_sample.fetchSample(sample, 0);
					distance = sample[0];
					if(isPressed(touch))
					{
						pilot.stop();
						pilot.travel(-50);
						pilot.rotate(90);
						pilot.forward();
					}
					else if (lastdistance > distance)
					{

						pilot.stop();
						pilot.rotate(15);
						pilot.travel(100);
						pilot.forward();
					}
					
				}while(distance<umbral_sensor_min+3);
				pilot.stop();
				pilot.rotate(-15);
				pilot.forward();
			}
			else
			{
				if(isPressed(touch))
				{
					pilot.stop();
					pilot.travel(-50);
					pilot.rotate(90);
					pilot.forward();
				}
			}
			but = Button.readButtons();
		}
		
		//pilot.stop();
		//sonar.close();
		//serv.close();
	}

	public static void mision6() throws InterruptedException
	{
		NXTSoundSensor sound = new NXTSoundSensor(SensorPort.S4);
		DifferentialPilot pilot = new DifferentialPilot(wheelRadius,axisDistance,Motor.C,Motor.B);
		pilot.setAcceleration(400);
		pilot.setRotateSpeed(100.0);
		pilot.setTravelSpeed(100.0);
		
		
	    LCD.clear();
	    int cont =0;
	    while (!Button.ESCAPE.isDown()) {
	        LCD.drawString("SS: " + sonidos(sound), 0, 0);
	        
	        if(sonidos(sound)>0.85){
	        	cont++;
	        	System.out.println("SS: " + sonidos(sound));
	        	Thread.sleep(200);
	        }
	        else{ //acciones
	        	System.out.println("cont: " + cont);
	        	switch(cont){

	        		case 1: LCD.drawString("Moviendome indefinidamente", 0, 50); 
	        			System.out.println("muevo ind ");	
	        				pilot.forward();
	        			break;
	        		case 2: pilot.rotateRight(); 	
	        				//pilot.forward();
	        				LCD.drawString("Hacia la derecha", 0, 50);
	        			System.out.println("hacia la derecha ");	
	        			break;
	        		case 3: pilot.rotate(360); LCD.drawString("Rotamos y paro", 0, 50);
	        			System.out.println("roto y paro ");	
	        				pilot.stop();
	        			break;
	        	}
	        	cont = 0;
	        }
	        
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
	

	
	private static void debugmission() 
	{
		EV3IRSensor sonar = new EV3IRSensor(SensorPort.S3.open(UARTPort.class));
		EV3MediumRegulatedMotor serv = new EV3MediumRegulatedMotor(MotorPort.D);
		LCD.clear();
		LCD.drawString("headposition: "+serv.getPosition(), 0, 0);
		LCD.drawString("headposition: "+serv.getTachoCount(), 0, 1);
		
	}
	  
	  
}


