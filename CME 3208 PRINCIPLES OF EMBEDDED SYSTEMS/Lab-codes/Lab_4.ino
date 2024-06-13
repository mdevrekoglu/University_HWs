#include <DHT11.h>
#include <LiquidCrystal_I2C.h>

#define dhtPIN 2
#define butPIN 8

LiquidCrystal_I2C lcd(0x27,16,2);
bool flag = false;

DHT11 dht11(dhtPIN);
char LCD_LANGUAGE = 'E';
void setup() {

    Serial.begin(9600);
    pinMode(butPIN, INPUT);
    lcd.init();
    lcd.backlight();
    
}

void loop() {
    int temperature = 0;
    int humidity = 0;


    int result = dht11.readTemperatureHumidity(temperature, humidity);


    if (result == 0 && LCD_LANGUAGE == 'T') {
        lcd.setCursor(0,0); 
        lcd.print("SIC(C): ");
        lcd.print(temperature);
        lcd.print("    TR");
        lcd.setCursor(0,1);
        lcd.print("NEM(%): ");
        lcd.print(humidity);
        lcd.print("%");

    } 
    else if(result == 0 && LCD_LANGUAGE == 'E'){
        lcd.setCursor(0,0);      
        lcd.print("TEM(F): ");
        lcd.print((temperature*180/100)+32);
        lcd.print("    EN");
        lcd.setCursor(0,1);
        lcd.print("HUM(%): ");
        lcd.print(humidity);
        lcd.print("%");
    } else {
        // Print error message based on the error code.
        Serial.println(DHT11::getErrorString(result));
    }

    if(digitalRead(butPIN) == HIGH){
      if(LCD_LANGUAGE == 'E'){
        while(digitalRead(butPIN) == HIGH){
          
        }
        LCD_LANGUAGE = 'T';
      }
      else{
        while(digitalRead(butPIN) == HIGH){
          
        }
        LCD_LANGUAGE = 'E';
      }
    }
}