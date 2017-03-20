#include <Grove_LED_Bar.h>
#include <math.h>

const int pinLight = A0;
Grove_LED_Bar bar(7, 6, 0);

void setup() {
  // put your setup code here, to run once:
  bar.begin();
}

void loop() {
  // put your main code here, to run repeatedly:
  int lightValue = analogRead(pinLight);
  
  if(lightValue > 0){
    int i = log(lightValue);
    bar.setLevel(i);
  }
  else{
    bar.setLevel(0);
  }

  delay(100);
}
