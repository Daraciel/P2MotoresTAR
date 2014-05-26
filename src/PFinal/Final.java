package PFinal;
import java.awt.Color;

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
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.TextMenu;

public class Final 
{

	private static boolean debug=false;
	private final static float radiotierra=11.0f;
	private static float viaje=11.0f;
	private static float umbral_sensor_min = 50f;
	private static float umbral_sensor_max = 55f;
	private static float umbral_sensor = umbral_sensor_max - umbral_sensor_min;
	private static Robot rober;
	/*	C O L O R E S	*/
	public static final int GREEN = 1;
	public static final int BLUE = 2;
	public static final int MAGENTA = 4;
	public static final int ORANGE = 5;
	public static final int PINK = 8;
	public static final int GRAY = 9;
	public static final int LIGHT_GRAY = 10;
	public static final int DARK_GRAY = 11;
	public static final int CYAN = 12;
	public static final int NONE = -1;
	

	public static final int BLACK = 7;
	public static final int RED = 0;
	public static final int BROWN = 13;
	public static final int WHITE = 6;
	public static final int YELLOW = 3;

	public static final int SUELO = 7;
	public static final int IZQ = 0;
	public static final int FINAL = 13;
	public static final int INTERIOR = 6;
	public static final int DCHA = 3;
	/*
	100 80
	50 27
	55 33*/

	public static void main(String[] args) throws InterruptedException
    {
		rober = new Robot();
		float distancia;
		float OldDistancia;
		float[] sample;
		float radiogiro;
		final SensorMode mode = rober.color.getColorIDMode();
		int colorActual;
		int colorAnterior;
		int vecesdentro=0;
		boolean aparcao = false;
		System.out.println("GO!");

		SampleProvider distance;
		if(debug)
		{
			
		}
		else
		{
			rober.pilot.forward();
			distance = rober.sonar.getDistanceMode();
			sample = new float[distance.sampleSize()];
			distance.fetchSample(sample, 0);
			distancia = sample[0];
			while(!((distancia<umbral_sensor_max) && (distancia>umbral_sensor_min)))
			{
				OldDistancia = distancia;
				distance.fetchSample(sample, 0);
				distancia = sample[0];
			}
			rober.pilot.stop();
			rober.pilot.rotate(90);
			/*
			OldDistancia = distancia;
			distance.fetchSample(sample, 0);
			distancia = sample[0];*/
			System.out.println("distancia: " + distancia);
			radiogiro = (radiotierra+30)*10;
			System.out.println("radiogiro: " + radiogiro);
			viaje = (float) (2.0 * Math.PI * radiogiro);
			System.out.println("viaje: " + viaje);
			rober.pilot.travelArc(-radiogiro, viaje);
			rober.pilot.rotate(90);
			rober.pilot.forward();

			colorActual = rober.color.getColorID();
			do
			{
				colorAnterior = colorActual;
				colorActual = rober.color.getColorID();
			}while(colorActual==SUELO);
			
			rober.pilot.setTravelSpeed(120);
			
			do
			{
				switch(colorAnterior)
				{
					case SUELO:
						switch(colorActual)
						{
						case SUELO:
							//GG
							break;
						case IZQ:
							//sigues
							break;
						case DCHA:
							//sigues
							break;
						case FINAL:
							//sigues
							break;
						case INTERIOR:
							//sigues
							break;
						default:
							break;
						}
					break;
					case IZQ:
						switch(colorActual)
						{
						case SUELO:
							rober.pilot.stop();
							rober.pilot.rotate(120);
							rober.pilot.forward();
							break;
						case IZQ:
							//sigues
							break;
						case DCHA:
							//GG
							break;
						case FINAL:
							rober.pilot.stop();
							rober.pilot.rotate(-120);
							rober.pilot.forward();
							break;
						case INTERIOR:
							//sigues
							break;
						default:
							break;
						}
						break;
					case DCHA:
						switch(colorActual)
						{
						case SUELO:
							rober.pilot.stop();
							rober.pilot.rotate(-120);
							rober.pilot.forward();
							break;
						case IZQ:
							//GG
							break;
						case DCHA:
							//sigues
							break;
						case FINAL:
							rober.pilot.stop();
							rober.pilot.rotate(120);
							rober.pilot.forward();
							break;
						case INTERIOR:
							//sigues
							break;
						default:
							break;
						}
						break;
					case FINAL:
						switch(colorActual)
						{
						case SUELO:
							rober.pilot.stop();
							rober.pilot.rotate(-170);
							rober.pilot.forward();
							break;
						case IZQ:
							rober.pilot.stop();
							rober.pilot.rotate(45);
							rober.pilot.forward();
							break;
						case DCHA:
							rober.pilot.stop();
							rober.pilot.rotate(-45);
							rober.pilot.forward();
							break;
						case FINAL:
							//sigues
							break;
						case INTERIOR:
							//sigues
							break;
						default:
							break;
						}
						break;
					case INTERIOR:
						switch(colorActual)
						{
						case SUELO:
							rober.pilot.stop();
							rober.pilot.rotate(140);
							rober.pilot.forward();
							break;
						case IZQ:
							rober.pilot.stop();
							rober.pilot.rotate(-45);
							rober.pilot.forward();
							break;
						case DCHA:
							rober.pilot.stop();
							rober.pilot.rotate(45);
							rober.pilot.forward();
							break;
						case FINAL:
							aparcao = true;
							break;
						case INTERIOR:
							vecesdentro++;
							break;
						default:
							break;
						}
						break;
					default:
						break;
				}
				
				
				colorAnterior = colorActual;
				colorActual = rober.color.getColorID();
			}while(!aparcao);
			

			rober.pilot.stop();
			if(vecesdentro<2)
				rober.pilot.rotate(45);
			else
				rober.pilot.rotate(170);
			rober.pilot.travel(150);
			
			
			
			
		}
    }

}
