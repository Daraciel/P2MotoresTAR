
/*mision3_1*/
public void mision3_1(){
	EV3TouchSensor touch = new EV3TouchSensor((ADSensorPort) SensorPort.S1);

	while(!touch2.isPressed())
		//muevete hacia adelante

}
/*end 3_1*/

/*mision3_2*/
public void mision3_2(){
	EV3TouchSensor touch = new EV3TouchSensor((ADSensorPort) SensorPort.S1);

	int cont = 0;
	int delay = 3000;
	while(!touch.isPressed()){}
	cont++;
	while(delay <= 0){
		if(touch.isPressed())
			cont++;
	}

	while(!touch.isPressed()){
		mueveteYGira(touch, 360/cont, distancia);
	}
}
private void mueveteYGira(EV3TouchSensor touch, double giro, double distancia){
	//mueve distancia

	if(touch.isPressed())
		//exit

	//gira giro

}
/*end 3_2 */

/*mision 4_1*/
public void mision4_1(){
	EV3ColorSensor colSensor = new EV3ColorSensor(SensorPort.S2);
	//To get the Color
	System.out.println(colSensor.getColorID());

	//To get the reflection/ambient:
	float[] buf = new float[colSensor.getMode(/* the mode number/string here */).sampleSize()];
	System.out.println(colSensor.getMode(/* the mode number/string here */).fetchSample(buf, 0));
}

/*mision 4_2*/
public void mision4_2(){ //si detecta un choque debe salir

	EV3TouchSensor touch = new EV3TouchSensor((ADSensorPort) SensorPort.S1);
	EV3ColorSensor colSensor = new EV3ColorSensor(SensorPort.S2);
	while(!touch.isPressed()){
		if(colSensor.getColorID() == NEGRO)
			mueveteYGira(touch, 180, 0); //se moverá una pequeña distancia para ir comprobando la luz
		else
			mueveteYGira(touch, 0, 10); //se moverá una pequeña distancia para ir comprobando la luz
	}

}

public void mision5_1(){
	EV3TouchSensor touch = new EV3TouchSensor((ADSensorPort) SensorPort.S1);
	EV3UltrasonicSensor sonar = new EV3UltrasonicSensor(SensorPort.S3.open(UARTPort.class));
	double distanciaAnt = sonar.getDistanceMode();
	while(!touch.isPressed()){
		sonar.enable();
		double distanceAct = sonar.getDistanceMode();
		mueveteYGira(touch,0,distanciaAnt - distanceAct);
		distanciaAnt = distanceAct;
		sonar.disable();
	}
}

public void mision5_2(){

	EV3TouchSensor touch = new EV3TouchSensor((ADSensorPort) SensorPort.S1);
	EV3UltrasonicSensor sonar = new EV3UltrasonicSensor(SensorPort.S3.open(UARTPort.class));

	while(!touch.isPressed()){
		sonar.enable();
		if(sonar.getDistanceMode()<20)
		{
			mueveteYGira(touch, 90, 0);
		}
		else{
			mueveteYGira(touch, 0, 20);
		}

		sonar.disable();
	}
}

public void mision6(){
	NXTSoundSensor sound = new NXTSoundSensor(Port.S4);
}



