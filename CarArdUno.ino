/*
* Pinout:
 * Key --> pin 9
 * VCC --> Vin
 * TXD --> pin 10
 * RXD --> pin 11
 * GND --> GND
*/
#include <SoftwareSerial.h>
SoftwareSerial BT(2,3);
void setup() {
  Serial.begin(9600);
  BT.begin(9600);
  Serial.println("Ready to connect\nDefualt password is 1234 or 000");
}

void loop() {
  if (BT.available() > 0) {
    Serial.println("Connected");
    char data = BT.read();
    switch (data) {
      case ('F'):
        Serial.print('F');
      case ('R'):
        Serial.print('R');
      default: break;
    }
  }


  delay(50);
}
