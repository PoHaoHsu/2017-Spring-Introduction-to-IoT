#include "LDHT.h"
#include "Grove_LED_Bar.h"

LDHT dht(2, DHT22);
Grove_LED_Bar bar(9, 8, 0);

float tempC = 0.0, Humi = 0.0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  dht.begin();
  bar.begin();

  Serial.print(DHT22);
  Serial.println(" test!");
}

void loop() {
  // put your main code here, to run repeatedly:
  if(dht.read())
    {
        tempC = dht.readTemperature();
        Humi = dht.readHumidity();


        Serial.println("------------------------------");
        Serial.print("Temperature Celcius = ");
        Serial.print(dht.readTemperature());
        Serial.println("C");

        Serial.print("Temperature Fahrenheit = ");
        Serial.print(dht.readTemperature(false));
        Serial.println("F");

        int bits = dht.readTemperature(false) - 74;
        Serial.println(bits);
        bar.setLevel(bits);
        
        Serial.print("Humidity = ");
        Serial.print(dht.readHumidity());
        Serial.println("%");

        Serial.print("HeatIndex = ");
        Serial.print(dht.readHeatIndex(tempC,Humi));
        Serial.println("C");

        Serial.print("DewPoint = ");
        Serial.print(dht.readDewPoint(tempC,Humi));
        Serial.println("C");
    }

    delay(100);
}
