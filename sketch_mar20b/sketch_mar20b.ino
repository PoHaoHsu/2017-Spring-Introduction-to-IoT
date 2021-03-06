#include <Wire.h>
#include <ADXL345.h>
#include <Grove_LED_Bar.h>

Grove_LED_Bar bar(9, 8, 0);
ADXL345 adxl;

int bits = 1;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  adxl.init();
  bar.begin();
  bar.setLevel(bits);
}

void loop() {
  // put your main code here, to run repeatedly:
  //Boring accelerometer stuff
    int x,y,z;
    adxl.readXYZ(&x, &y, &z); //read the accelerometer values and store them in variables  x,y,z
    // Output x,y,z values
    Serial.print("values of X , Y , Z: ");
    Serial.print(x);
    Serial.print(" , ");
    Serial.print(y);
    Serial.print(" , ");
    Serial.println(z);

    if(z > 300){
      bits++;
      if(bits == 11){
        bits = 0;
      }
      bar.setLevel(bits);
      delay(300);
    }
    Serial.println(bits);
    
    double xyz[3];
    double ax,ay,az;
    adxl.getAcceleration(xyz);
    ax = xyz[0];
    ay = xyz[1];
    az = xyz[2];
    Serial.print("X=");
    Serial.print(ax);
    Serial.println(" g");
    Serial.print("Y=");
    Serial.print(ay);
    Serial.println(" g");
    Serial.print("Z=");
    Serial.println(az);
    Serial.println(" g");
    Serial.println("**********************");
    delay(100);
}
