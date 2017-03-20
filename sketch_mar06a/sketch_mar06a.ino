#include <Grove_LED_Bar.h>
Grove_LED_Bar bar(9, 8, 0);
int inPin = 2;
int val = 0;
int bits = 0x1;

void setup() {
  // put your setup code here, to run once:
  bar.begin();
  pinMode(inPin, INPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  
  while(val==0){
    bar.setBits(0x3ff);
    val = digitalRead(inPin);
  }
  for(int i=1; i<=10; i++){
    bits = pow(2,i) - 1;
    bar.setBits(bits);
    delay(100);
    val = digitalRead(inPin);
    if(val==0)
      break;
  }
  bits = 0x1;
}
