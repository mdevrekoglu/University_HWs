int motor_pin_1 = 10;
int motor_pin_2 = 11;
int led_pin = 7;
int limit = 120;
int delay_time = 100;

int LED_WAIT_DURATION_MIN = 2000;
int LED_WAIT_DURATION_MAX = 6000;

int LED_ON_DURATION_MIN = 2000;
int LED_ON_DURATION_MAX = 6000;

int LIGHT_SENSOR_OUTPUT_INTERVAL = 1000;

long last_millis = 0;

int led_wait_time = 0;
int led_on_time = 0;

long current_time = 0;


void setup(){

  pinMode(motor_pin_1,OUTPUT);
  pinMode(motor_pin_2,OUTPUT);
  pinMode(led_pin,OUTPUT);

  last_millis = millis();
  current_time = millis();

  led_wait_time = random(LED_WAIT_DURATION_MIN, LED_WAIT_DURATION_MAX);
  digitalWrite(led_pin, LOW);


  Serial.begin(9600);
}

void loop(){

  // LED ON
  if(led_wait_time != 0 && led_on_time == 0 && millis() - current_time > led_wait_time){
    current_time = millis();
    led_wait_time = 0;
    led_on_time = random(LED_ON_DURATION_MIN, LED_ON_DURATION_MAX);


    Serial.print("\nLED is turned ON for ");
    Serial.print(led_on_time);
    Serial.println(" milliseconds.");

    digitalWrite(led_pin, HIGH);
  }
  // LED OFF
  else if(led_on_time != 0 && led_wait_time == 0 && millis() - current_time > led_on_time){
    current_time = millis();
    led_on_time = 0;
    led_wait_time = random(LED_WAIT_DURATION_MIN, LED_WAIT_DURATION_MAX);
    Serial.print("\nLED is turned OFF for ");
    Serial.print(led_wait_time);
    Serial.println(" milliseconds.");
    digitalWrite(led_pin, LOW);
    //Serial.println("3");
  }


  if(analogRead(A5) < limit){
   // Serial.println("Motor is working");
    digitalWrite(motor_pin_1, LOW);
    digitalWrite(motor_pin_2, HIGH);
    analogWrite(motor_pin_2, 255);
  }else{
   // Serial.println("Motor is not working");
    digitalWrite(motor_pin_1, LOW);
    digitalWrite(motor_pin_2, LOW);
    //analogWrite(motor_pin_2, 255);
  }
  //Serial.println(analogRead(A5));

  if(millis() - last_millis > LIGHT_SENSOR_OUTPUT_INTERVAL){
    Serial.print("Light sensor output is ");
    Serial.println(analogRead(A5));
    //Serial.println(random(LED_ON_DURATION_MIN, LED_ON_DURATION_MAX));
    last_millis = millis();
  }

  delay(delay_time);
}