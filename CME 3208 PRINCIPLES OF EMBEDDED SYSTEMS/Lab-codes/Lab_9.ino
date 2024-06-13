// C++ code
//
//#include <Adafruit_LiquidCrystal.h>
//Adafruit_LiquidCrystal lcd(0);
#include <LiquidCrystal_I2C.h>
LiquidCrystal_I2C lcd(0x27,20,2);
// Pins
int buzzer_pin = 4;
int led_pin = 5;
int trig_pin = 6;
int echo_pin = 7;

// Variables
int BUZZER_LED_DURATION_MAX = 1000;
int BUZZER_LED_DURATION_MIN = 10;
int DISTANCE_MAX = 40;
int DISTANCE_MIN = 20;
int CURRENT_DISTANCE = 0;
int CURRENT_DURATION = 0;
int UPDATE_DURATION_BUZZER = 200;
int UPDATE_DURATION_LCD = 200;
int SCREEN_UPDATE = 500;
long SCREEN_LAST = millis();


void setup()
{
  Serial.begin(9600);

  lcd.init();
  lcd.backlight();
  
  
  pinMode(buzzer_pin, OUTPUT);
  pinMode(led_pin, OUTPUT);
  pinMode(trig_pin, OUTPUT);
  pinMode(echo_pin, INPUT);
  
  digitalWrite(buzzer_pin, LOW);
  digitalWrite(led_pin, LOW);
  digitalWrite(trig_pin, LOW);
  
}

void loop()
{
  find_distance();
  Serial.println(CURRENT_DISTANCE);
  //Serial.println(CURRENT_DURATION);

  // First state no need for alert
  if(CURRENT_DISTANCE > DISTANCE_MAX){
    digitalWrite(led_pin, LOW);
    noTone(buzzer_pin);
    delay(BUZZER_LED_DURATION_MAX);
  }
  // It is in interval
  else if(CURRENT_DISTANCE >= DISTANCE_MIN && CURRENT_DISTANCE <= DISTANCE_MAX){
	  int time = int(alert_formula());
	  buzzer_sound(time / 2);
    led_blink(time / 2);
    CURRENT_DURATION = time / 2;

  }
  // Critical area-crash
  else if(CURRENT_DISTANCE < DISTANCE_MIN){

    tone(buzzer_pin, 1000);
    digitalWrite(led_pin, HIGH);
    delay(BUZZER_LED_DURATION_MAX);
    CURRENT_DURATION = 0;
    //lcd_print();
  }
	  
  if(millis() - SCREEN_LAST > SCREEN_UPDATE){
    lcd_print();
    SCREEN_LAST = millis();
  }
  
  
  delay(10);
}

void led_blink(int time){	
	digitalWrite(led_pin, HIGH);
	delay(time);
	digitalWrite(led_pin, LOW);
}

void buzzer_sound(int time){
	
	tone(buzzer_pin, 1000);
	delay(time);
	noTone(buzzer_pin);
}

void lcd_print(){
  lcd.clear();
  
  lcd.setCursor(0,0);
  lcd.print("Distance: ");
  //cd.print(CURRENT_DISTANCE);
  
  if(CURRENT_DISTANCE >= 100){
    // Do nothing
  }
  else if(CURRENT_DISTANCE < 100 && CURRENT_DISTANCE >= 10){
    lcd.print(" ");
  }
  else{
    lcd.print("  ");
  }
  
  lcd.print(CURRENT_DISTANCE);
  lcd.print("CM");
  
  lcd.setCursor(0,1);
  lcd.print("Duration:  ");
  //lcd.print(CURRENT_DURATION);
  
  if(CURRENT_DURATION >= 100){
  }
  else if(CURRENT_DURATION < 100 && CURRENT_DURATION >= 10){
    lcd.print(" ");
  }
  else{
    lcd.print("  ");
  }
  
  lcd.print(CURRENT_DURATION);
  lcd.print("MS");
  
}

void find_distance(){
  
  float distance = -1;       
  digitalWrite(trig_pin,HIGH);  
  delayMicroseconds(10);      
  digitalWrite(trig_pin,LOW); 
  distance=pulseIn(echo_pin,HIGH);      
  distance = distance/58;             
  
  CURRENT_DISTANCE = distance;
}

float alert_formula(){
  float time = (((CURRENT_DISTANCE * 1.00 - DISTANCE_MIN * 1.00)/(DISTANCE_MAX * 1.00 - DISTANCE_MIN * 1.00)) * (BUZZER_LED_DURATION_MAX * 1.00 - BUZZER_LED_DURATION_MIN * 1.00)) + BUZZER_LED_DURATION_MIN * 1.00;
  CURRENT_DURATION = int(time);
  return time;
}