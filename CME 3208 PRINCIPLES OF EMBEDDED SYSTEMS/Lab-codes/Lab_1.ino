int INCREMENT_VALUE = 5;
int WAIT_TIME=500;
int ctr = 0;


void setup() {

  // Outputs
  pinMode(12, OUTPUT);
  pinMode(14, OUTPUT);
  pinMode(27, OUTPUT);
  pinMode(26, OUTPUT);
  pinMode(25, OUTPUT);


  digitalWrite(12, LOW);
  digitalWrite(14, LOW);
  digitalWrite(27, LOW);
  digitalWrite(26, LOW);
  digitalWrite(25, LOW);
  delay(1000);

}

void loop() {
  

  if(ctr % 2 >=1){
    digitalWrite(12, HIGH);
  }else{
    digitalWrite(12, LOW);
  }

  
  if(ctr % 4 >=2){
    digitalWrite(14, HIGH);
  }else{
    digitalWrite(14, LOW);
  }

    
  if(ctr % 8 >=4){
    digitalWrite(27, HIGH);
  }else{
    digitalWrite(27, LOW);
  }

      
  if(ctr % 16 >= 8){
    digitalWrite(26, HIGH);
  }else{
    digitalWrite(26, LOW);
  }

        
  if(ctr % 32 >= 16){
    digitalWrite(25, HIGH);
  }else{
    digitalWrite(25, LOW);
  }


  ctr += INCREMENT_VALUE;
  ctr = ctr % 32;

  delay(WAIT_TIME);

}