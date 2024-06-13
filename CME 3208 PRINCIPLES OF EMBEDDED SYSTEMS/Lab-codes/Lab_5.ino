int TIMER_START = 30;
int TIMER_SPEED = 250;
int ANIMATION_SPEED = 200;

int a = 2;
int b = 3;
int c = 4;
int d = 5;
int e = 6;
int f = 7;
int g = 8;
int dp = 9;

int led = 12;
int sensor = 13;

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


void setup() {
  pinMode(a,OUTPUT);
  pinMode(b,OUTPUT);
  pinMode(c,OUTPUT);
  pinMode(d,OUTPUT);
  pinMode(e,OUTPUT);
  pinMode(f,OUTPUT);
  pinMode(g,OUTPUT);
  pinMode(dp,OUTPUT);
  pinMode(led,OUTPUT);
  pinMode(sensor, INPUT);
}

void loop() {

  int flag = digitalRead(sensor);

  if(flag == 1){
    showNumbers();
  }

}

void showNumbers(){

  digitalWrite(led,HIGH);
  digitalWrite(g,HIGH);

  int ctr = 0;
  int startTime = millis();

  while(millis() - startTime <= 1500){

    // a-c-e   f-d-b
    if(ctr % 2 == 0){
      digitalWrite(a,LOW);
      digitalWrite(b,HIGH);
      digitalWrite(c,LOW);
      digitalWrite(d,HIGH);
      digitalWrite(e,LOW);
      digitalWrite(f,HIGH);
    }else{
      digitalWrite(a,HIGH);
      digitalWrite(b,LOW);
      digitalWrite(c,HIGH);
      digitalWrite(d,LOW);
      digitalWrite(e,HIGH);
      digitalWrite(f,LOW); 
    }
    ctr++;
    delay(ANIMATION_SPEED);
  }


  for(int i = 15; i >= 0; i--){

    digitalWrite(a,letters[i][0]);  //a
    digitalWrite(b,letters[i][1]);  //b
    digitalWrite(c,letters[i][2]);  //c
    digitalWrite(d,letters[i][3]);  //d
    digitalWrite(e,letters[i][4]);  //e
    digitalWrite(f,letters[i][5]);  //f
    digitalWrite(g,letters[i][6]);  //g

    delay(TIMER_SPEED);
  }

  digitalWrite(led,LOW);
  delay(1000);
}
