#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>
#include <Adafruit_MLX90614.h>
#include <Adafruit_GFX.h>       // Include core graphics library for the display
#include <Adafruit_SSD1306.h>   // Include Adafruit_SSD1306 library to drive the display
#include <Fonts/FreeMonoBold18pt7b.h>  // Add a custom font
#include <SPI.h>
#include <Wire.h>

/*Firebase account Rahulkesharwani main*/

#define FIREBASE_HOST "co-rakshak-dwaar-default-rtdb.firebaseio.com" // Firebase host
#define FIREBASE_AUTH "tYZUQG66EzSDO9GWb9Lz6rYcaSnFRvRaPJb5R1Jn" //Firebase Auth code
#define WIFI_SSID "Hogwarts" //Enter your wifi Name
#define WIFI_PASSWORD "Rahul@10900" // Enter your password

#define OLED_RESET     -1
#define SCREEN_WIDTH 128 // OLED display width, in pixels
#define SCREEN_HEIGHT 64 

int fireStatus = 1;
String uid = "",pnr= "",date = "", path = "";
int steps = 1;
Adafruit_MLX90614 mlx = Adafruit_MLX90614();
FirebaseData fbWrite;
FirebaseData fbRead;
FirebaseData fbdo;

Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);         //Create display
int temp_d;  // Create a variable to have something dynamic to show on the display

void setup() {
 
  /*Locla Setup*/
  Serial.begin(9600);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connect");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(1000);
  }
  Serial.println();
  Serial.println("Connected.");
  Serial.println(WiFi.localIP());

  /*Pin Define*/
  pinMode(D7, OUTPUT);

  /*Firebase Setups*/
 Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);

  /*MLX90614 setup*/
  mlx.begin();

  /*Display Setup*/
  delay(100);  // This delay is needed to let the display to initialize
  display.begin(SSD1306_SWITCHCAPVCC, 0x3C);  // Initialize display with the I2C address of 0x3C
  display.clearDisplay();  // Clear the buffer
  display.setTextColor(WHITE);  // Set color of the text
}

void loop() {
  projectName();
  fireStatus = 0;
  int count =0;
  float avg = 0;
  float c_temp= 0;

     /*get module current status*/
    Firebase.getInt(fbRead,"/controller/module1/status");
    fireStatus = fbRead.intData();

      /*ON condition*/
    if(fireStatus==1)
    {
      
      /*Get Details*/
      Firebase.getString(fbRead,"/controller/module1/currentURI");
      uid = fbRead.stringData();
      Firebase.getString(fbRead,"/controller/module1/currentDate");
      date = fbRead.stringData();
      Firebase.getString(fbRead,"/controller/module1/currentPNR");
      pnr = fbRead.stringData();

      path = "record/"+date+"/"+pnr+"/temp";
      Serial.println("Path : "+path);
      digitalWrite(D7, HIGH);
      /*Greeting*/
      greeting1();
      delay(3000);
      Serial.println("Please Hold Your Hand For 10 sec to Measure Temprature.... ");

      digitalWrite(D7, HIGH);
      /*After Getting Details Measure Temprature*/
      for(int i = 0 ; i<10;)
      {
        c_temp =0;
        delay(1000);
        c_temp = mlx.readObjectTempF();
        c_temp = c_temp + 6;
        Serial.println("F Temp : ");Serial.print(c_temp);
        if(c_temp > 95 && c_temp<100){
          disp_temp(c_temp);
          avg = c_temp + avg;
          i++;
        }
        else if(c_temp>=100 && c_temp<110){
          disp_temp(c_temp);
          avg = c_temp + avg;
          i++;
          
      //  buz();
        }
        
      }
      avg = avg/10;
      Serial.println("Avg Temp : ");Serial.print(avg);
      Firebase.setFloat(fbWrite,path,avg);
      Firebase.setInt(fbWrite,"/controller/module1/status",0);
      disp_thank();
      delay(2000);
      display.clearDisplay();
    }

    /*OFF condition*/
    else if (fireStatus == 0) 
    {
      Serial.println("OFF");
      digitalWrite(D7, LOW);
    }

    else
    {
      Serial.println("Command Error! Please send 0/1");
    }
    
  delay(500);
}



    /*Display Temprartute in oled*/
void disp_temp(float temp){
   display.clearDisplay();  // Clear the display so we can refresh

  // Print text:
  display.setFont();
  display.setCursor(45,10);  // (x,y)
  display.println("TEMPERATURE");  // Text or value to print

  // Print temperature
  char string[10];  // Create a character array of 10 characters
  // Convert float to a string:
  dtostrf(temp, 3, 0, string);  // (<variable>,<amount of digits we are going to use>,<amount of decimal digits>,<string name>)
  
  display.setFont(&FreeMonoBold18pt7b);  // Set a custom font
  display.setCursor(20,50);  // (x,y)
  display.println(string);  // Text or value to print
  display.setCursor(90,50);  // (x,y)
  display.println("F");  // Text or value to print
  display.setCursor(77,32);  // (x,y)
  display.println(".");  // Text or value to print
  
  // Draw a filled circle:
  display.fillCircle(18, 55, 7, WHITE);  // Draw filled circle (x,y,radius,color). X and Y are the coordinates for the center point

  // Draw rounded rectangle:
  display.drawRoundRect(16, 3, 5, 49, 2, WHITE);  // Draw rounded rectangle (x,y,width,height,radius,color)
                                                  // It draws from the location to down-right
    // Draw ruler step
  for (int i = 6; i<=45; i=i+3){
    display.drawLine(21, i, 22, i, WHITE);  // Draw line (x0,y0,x1,y1,color)
  }
  
  //Draw temperature
  temp_d = temp*0.43; //ratio for show
  display.drawLine(18, 46, 18, 46-temp_d, WHITE);  // Draw line (x0,y0,x1,y1,color)

  display.display();  // Print everything we set previously
    
}

void projectName(void) {
  display.clearDisplay();

  display.setTextSize(2); // Draw 2X-scale text
  display.setTextColor(SSD1306_WHITE);
  display.setCursor(10, 15);
  display.println(("CoRakshak"));;
  display.setCursor(35, 35);
   display.println(("Dwaar"));
  display.display();      // Show initial text
  delay(100);
}

void disp_thank(void) {
  display.clearDisplay();
  display.setTextSize(1); 
  display.setTextColor(SSD1306_WHITE);
  display.setFont();
  display.setCursor(15, 30);
  display.println(("Thankyou\nHave Safe Journey"));;
  display.display();      // Show initial text
  delay(100);
}

void greeting1(void) {
  display.clearDisplay();
  display.setTextSize(2); // Draw 2X-scale text
  display.setTextColor(SSD1306_WHITE);
  display.setFont();
  display.setCursor(20, 30);
  display.println(("WELCOME"));;
  display.display();      // Show initial text
  delay(2000);


  /*instructions*/
  display.clearDisplay();
  display.setTextSize(1); // Draw 2X-scale text
  display.setTextColor(SSD1306_WHITE);
  display.setFont();
  display.setCursor(0,5);
  display.println(("Please Hold Your Hand to Measure Temprature.... "));;
  display.display();      // Show initial text
}
