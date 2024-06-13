#define led_pin 3

int LED_ACTIVATION_VALUE = 50; // Value from sensor
int SERIAL_OUTPUT_FREQUENCY = 500; // 500 ms = 0.5 seconds
long int last_millis = 0;
void setup() {

 last_millis = millis();

  pinMode(led_pin,OUTPUT);
  Serial.begin(9600);
}

void loop() {

  int val = analogRead(A0);
  long int current = millis();
  if(current - last_millis > SERIAL_OUTPUT_FREQUENCY){
    Serial.print("Led val: ");
    Serial.println(val);
    last_millis = current;

    if(val < LED_ACTIVATION_VALUE){
      Serial.println("Led status: on\n");
    }else{
      Serial.println("Led status: off\n");
      
    }
  }

  if(val < LED_ACTIVATION_VALUE){
    digitalWrite(led_pin,HIGH);
  }
  else{
    digitalWrite(led_pin,LOW);
  }


  

  delay(20);
}