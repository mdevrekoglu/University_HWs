int a = 2;
int b = 3;
int c = 4;
int d = 5;
int e = 6;
int f = 7;
int g = 8;
int dp = 9;

int letters[16][7] = {
  {0,0,0,0,0,0,1}, // 0
  {1,0,0,1,1,1,1}, // 1
  {0,0,1,0,0,1,0}, // 2
  {0,0,0,0,1,1,0}, // 3
  {1,0,0,1,1,0,0}, // 4
  {0,1,0,0,1,0,0}, // 5
  {0,1,0,0,0,0,0}, // 6
  {0,0,0,1,1,1,1}, // 7
  {0,0,0,0,0,0,0}, // 8
  {0,0,0,0,1,0,0}, // 9
  {0,0,0,1,0,0,0}, // A
  {1,1,0,0,0,0,0}, // B
  {0,1,1,0,0,0,1}, // C
  {1,0,0,0,0,1,0}, // D
  {0,1,1,0,0,0,0}, // E
  {0,1,1,1,0,0,0}  // F
};

int button_left = 12;
int button_right = 13;
int state = 0;
int MOTOR_STEPS = 10;
int max_speed = 250;

void setup(){
  
  pinMode(10,OUTPUT); //ENABLE
  pinMode(A5,OUTPUT);
  pinMode(A4,OUTPUT);

  pinMode(a,OUTPUT);
  pinMode(b,OUTPUT);
  pinMode(c,OUTPUT);
  pinMode(d,OUTPUT);
  pinMode(e,OUTPUT);
  pinMode(f,OUTPUT);
  pinMode(g,OUTPUT);
  pinMode(dp,OUTPUT);
  

  pinMode(button_left, INPUT);
  pinMode(button_right, INPUT);

  // Initialize the buzzer pin as output

  Serial.begin(9600);
}

void loop(){
    
  if (digitalRead(button_left) == HIGH) {
    Serial.println("left");

    if(state > MOTOR_STEPS * -1){
      state--;
    }

    digitalWrite(a,letters[abs(state)][0]); 
    digitalWrite(b,letters[abs(state)][1]); 
    digitalWrite(c,letters[abs(state)][2]); 
    digitalWrite(d,letters[abs(state)][3]); 
    digitalWrite(e,letters[abs(state)][4]); 
    digitalWrite(f,letters[abs(state)][5]); 
    digitalWrite(g,letters[abs(state)][6]); 

    if(state == 0){
      digitalWrite(A5,LOW);
      digitalWrite(A4,LOW);
    }
    else if(state > 0){
      digitalWrite(A5,HIGH);
      digitalWrite(A4,LOW);
    }else{
      digitalWrite(A4,HIGH);
      digitalWrite(A5,LOW);
    }

    analogWrite(10,abs(state) * 1.00 / MOTOR_STEPS * 1.00 * max_speed * 1.00);

    
    while(digitalRead(button_left) == HIGH){
      delay(10);
    }


  }

  if (digitalRead(button_right) == HIGH) {

    Serial.println("right");

    if(state < MOTOR_STEPS){
      state++;
    }

    digitalWrite(a,letters[abs(state)][0]); 
    digitalWrite(b,letters[abs(state)][1]); 
    digitalWrite(c,letters[abs(state)][2]); 
    digitalWrite(d,letters[abs(state)][3]); 
    digitalWrite(e,letters[abs(state)][4]); 
    digitalWrite(f,letters[abs(state)][5]); 
    digitalWrite(g,letters[abs(state)][6]);

    
    analogWrite(10,abs(state) * 1.00 / MOTOR_STEPS * 1.00 * max_speed * 1.00);

    if(state == 0){
      digitalWrite(A5,LOW);
      digitalWrite(A4,LOW);
    }
    else if(state > 0){
      digitalWrite(A5,HIGH);
      digitalWrite(A4,LOW);
    }else{
      digitalWrite(A4,HIGH);
      digitalWrite(A5,LOW);
    }

    while(digitalRead(button_right) == HIGH){
      delay(10);
    }

  }

  Serial.println(abs(state) * 1.00 / MOTOR_STEPS * 1.00 * max_speed * 1.00);

  

  delay(50);
}