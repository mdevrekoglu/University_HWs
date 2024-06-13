// Define the pin numbers
const int buttonPin = 2; // The push button pin
const int buzzerPin = 5; // The buzzer pin

// Define global variables
int SOUND_DURATION = 500;
int SILENCE_DURATION = 50;
#define NO_SOUND 0
#define DO_LOW 1
#define RE 2
#define MI 3
#define FA 4
#define SOL 5
#define LA 6
#define SI 7
#define DO_HIGH 8

// Define the melody using musical notes
int MELODY[] = {DO_LOW, DO_LOW, SOL, SOL, LA, LA, SOL, NO_SOUND, FA, FA, MI, MI, RE, RE, DO_LOW};
int MELODY2[] = {SOL, LA, SOL, MI, LA, RE, RE, FA, FA, RE, MI, SOL, RE, MI, DO_LOW};

int melodyLength = sizeof(MELODY) / sizeof(MELODY[0]); // Calculate the length of the melody array


void setup() {
  Serial.begin(9600);
  // Initialize the push button pin as input
  pinMode(buttonPin, INPUT);
  // Initialize the buzzer pin as output
  pinMode(buzzerPin, OUTPUT);
}

void loop() {
  // Check if the button is pressed
  if (digitalRead(buttonPin) == HIGH) {
    // If the button is pressed, play the melody
    playMelody();
    // Wait for the button to be released
    while (digitalRead(buttonPin) == HIGH) {
      delay(10); // Polling delay
    }
  }
  Serial.println(digitalRead(buttonPin));
}


// Function to play the melody
void playMelody() {
  for (int i = 0; i < melodyLength; i++) {
    int note = MELODY[i];
    if (note == NO_SOUND) {
      // No sound, just silence
      digitalWrite(buzzerPin, LOW);
      delay(SILENCE_DURATION);
    } else {
      // Play the note
      tone(buzzerPin, noteToFrequency(note), SOUND_DURATION); // Calculate the frequency based on the note
      delay(SOUND_DURATION);
    }
    // Make sure the buzzer is off after each note
    digitalWrite(buzzerPin, LOW);
    delay(SILENCE_DURATION);
  }
}

// Function to convert note to frequency
float noteToFrequency(int note) {
  switch (note) {
    case DO_LOW:
      return 261.63;
    case RE:
      return 293.66;
    case MI:
      return 329.63;
    case FA:
      return 349.23;
    case SOL:
      return 392.00;
    case LA:
      return 440.00;
    case SI:
      return 493.88;
    case DO_HIGH:
      return 523.25;
    default:
      return 0;
  }
}

