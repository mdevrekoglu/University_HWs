#include <Adafruit_LiquidCrystal.h>

Adafruit_LiquidCrystal lcd_1(0);

boolean SCROLL_FLAG = true;
int SCROLL_WAIT = 1000;
char SCROLL_DIRECTION = 'R';

char LCD_ROW_1[] = "This text is for row 0 and longer than 16 characters.";
char LCD_ROW_2[] = "ABCD EFGH";

int pointer_1 = 0;
int pointer_2 = 0;
int len_1 = 0;
int len_2 = 0;

byte customChar0[] = { B01110, B00000, B01111, B10001, B10001, B01111, B00001, B01110 };  //ğ
byte customChar1[] = { B10001, B00000, B10001, B10001, B10001, B10011, B01101, B00000 };  //ü
byte customChar2[] = { B00000, B00000, B01110, B10000, B01110, B00001, B11110, B00100 };  //ş
byte customChar3[] = { B00000, B00000, B01100, B00100, B00100, B00100, B01110, B00000 };  //ı
byte customChar4[] = { B00000, B00000, B01110, B10000, B10000, B10001, B01110, B00100 };  //ç
byte customChar5[] = { B01010, B00000, B01110, B10001, B10001, B10001, B01110, B00000 };  //ö



void setup() {

  len_1 = strlen(LCD_ROW_1);
  len_2 = strlen(LCD_ROW_2);

  lcd_1.begin(16, 2);
  lcd_1.setBacklight(1);
  lcd_1.clear();
  lcd_1.setCursor(0, 0);
  lcd_1.write(customChar0);

  if (len_1 < 16) {

    for (int i = len_1; i < 16; i++) {

      LCD_ROW_1[i] = ' ';
    }

    LCD_ROW_1[16] = '\0';
    len_1 = 16;
    Serial.println(LCD_ROW_1);
  }

  if (len_2 < 16) {

    for (int i = len_2; i < 16; i++) {

      LCD_ROW_2[i] = ' ';
    }

    LCD_ROW_2[16] = '\0';
    len_2 = 16;
    Serial.println(LCD_ROW_1);
  }

  delay(2 * SCROLL_WAIT);
}

void loop() {

  if (SCROLL_FLAG && SCROLL_DIRECTION == 'R') {
	  
    lcd_1.clear();
    lcd_1.setCursor(0, 0);
    char msg[] = "                ";

    for (int i = 0; i < 16; i++) {
      msg[i] = LCD_ROW_1[pointer_1];
      pointer_1++;
      pointer_1 %= len_1;
    }

    if (pointer_1 < 17) {
      pointer_1 += len_1;
    }
    pointer_1 -= 17;
    lcd_1.print(msg);

    lcd_1.setCursor(0, 1);

    for (int i = 0; i < 16; i++) {
      msg[i] = LCD_ROW_2[pointer_2];
      pointer_2++;
      pointer_2 %= len_2;
    }

    if (pointer_2 < 17) {
      pointer_2 += len_2;
    }
    pointer_2 -= 17;
    lcd_1.print(msg);


  } else if (SCROLL_FLAG && SCROLL_DIRECTION == 'L') {

    lcd_1.clear();
    lcd_1.setCursor(0, 0);
    char msg[] = "                ";

    for (int i = 0; i < 16; i++) {
      msg[i] = LCD_ROW_1[pointer_1];
      pointer_1++;
      pointer_1 %= len_1;
    }

    if (pointer_1 < 16) {
      pointer_1 += len_1;
    }
    pointer_1 -= 15;
    lcd_1.print(msg);

    lcd_1.setCursor(0, 1);

    for (int i = 0; i < 16; i++) {
      msg[i] = LCD_ROW_2[pointer_2];
      pointer_2++;
      pointer_2 %= len_2;
    }

    if (pointer_2 < 16) {
      pointer_2 += len_2;
    }
    pointer_2 -= 15;
    lcd_1.print(msg);

  } else {
	  
  }


  delay(SCROLL_WAIT);
}