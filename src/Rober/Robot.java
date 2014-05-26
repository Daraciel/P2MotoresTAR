package Rober;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.UARTPort;
import lejos.hardware.sensor.*;
import lejos.robotics.navigation.DifferentialPilot;

public class Robot {
	
	/*		M O T O R E S		*/
	public DifferentialPilot pilot;
	public EV3MediumRegulatedMotor headmotor;
	
	/*		S E N S O R E S		*/
	public EV3IRSensor sonar;
	public EV3TouchSensor ostiometro;
	public EV3ColorSensor color;
	public NXTSoundSensor microfono;
	
	/*		C O S A S			*/
	//distancia entre ruedas
	public static double axisDistance=150.0;
	//diametro de la rueda
	public static double wheelRadius=43.2;
	
	public Robot()
	{
		pilot = new DifferentialPilot(wheelRadius,axisDistance,Motor.C,Motor.B);
		
		//headmotor = new EV3MediumRegulatedMotor(MotorPort.D);

		sonar = new EV3IRSensor(SensorPort.S3.open(UARTPort.class));
		ostiometro = new EV3TouchSensor(SensorPort.S2);
		color = new EV3ColorSensor(SensorPort.S1);
		//microfono = new NXTSoundSensor(SensorPort.S4);
	}
	

}
