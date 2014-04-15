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
import lejos.robotics.SampleProvider;


public class Sensores 
{
	public static void main(String[] args)
    {
		LCD.drawString("Pograma!", 0, 0);
		Button.waitForAnyPress(4000);
		LCD.clear();
		mision4_1();
    
    }
	
	
	
	
	
/*mision3_1*/
	
	
	
	public void mision3_1()
	{
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
	
		while(!isPressed(touch))
		{
			//muevete hacia adelante
		}
	
	}
/*end 3_1*/

	/*mision3_2*/
	public void mision3_2()
	{
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
	
		int cont = 0;
		int delay = 3000;
		double distancia =0;
		while(!isPressed(touch)){}
		cont++;
		while(delay <= 0){
			if(isPressed(touch))
				cont++;
		}
	
		while(!isPressed(touch)){
			mueveteYGira(touch, 360/cont, distancia);
		}
	}
	
	private void mueveteYGira(EV3TouchSensor touch, double giro, double distancia)
	{
		//mueve distancia
	
		if(isPressed(touch))
		{
		
		}
			//exit
	
		//gira giro
	
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
	public void mision4_2()
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

	public void mision5_1()
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

	public void mision5_2()
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

	public void mision6()
	{
		NXTSoundSensor sound = new NXTSoundSensor(SensorPort.S4);
	}
	
	  public boolean isPressed(EV3TouchSensor sensor) 
	  {
		  
		  float[] sample = new float[sensor.sampleSize()];
		  sensor.fetchSample(sample, 0);
		  if (sample[0] == 0)
			  return false;
		  return true;
	  }

}


