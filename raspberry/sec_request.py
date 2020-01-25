import RPi.GPIO as GPIO
import requests
import time
import threading

from operator import eq

#import myRC522
#import mfrc522
from mfrc522 import SimpleMFRC522
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
control_pins = [7,11,13,15] ## 1 2
                            ## 3 4

for pin in control_pins:
  GPIO.setup(pin, GPIO.OUT)
  GPIO.output(pin, 0)
halfstep_seq = [
  [1,0,0,0],
  [1,1,0,0],
  [0,1,0,0],
  [0,1,1,0],
  [0,0,1,0],
  [0,0,1,1],
  [0,0,0,1],
  [1,0,0,1]
]

reader = SimpleMFRC522()
#reader = myRC522.myRC522()

def run_wheel():
    GPIO.setmode(GPIO.BOARD)
    for i in range(1,514):
      for halfstep in range(8):
        for pin in range(4):
          GPIO.output(control_pins[pin], halfstep_seq[halfstep][pin])
        time.sleep(0.001)
    
        
def open_close():## onopen requests
    basic_time = time.time()## 기준 시간
    nowtime = round(time.time()-basic_time)
    
    while 1:
        if(nowtime > 9):## 10 초에 한번씩 요청
            response2 = requests.post('http://kyu9341.cafe24.com/ValidateTag.php')
            onoff = response2.json()
            print(response2.text)
            basic_time = time.time()
        
        if(nowtime != round(time.time()-basic_time)):
            print(round(time.time()-basic_time))
            nowtime = round(time.time()-basic_time)
            
        time.sleep(0.001)
            
def scan_nfc():
    while 1:
        global onoff
        id,text= reader.read()
        print(id)
        print(text)
        data = {'ID':id}
        response2 = requests.post('http://kyu9341.cafe24.com/ValidateTag.php',data=data)
        
        onoff = response2.json()['OnOff']#box open or close?
        validate = response2.json()['Validate']# used box 
        
        if onoff is True:
            print('open')
            if validate is True:
                run_wheel()
                print('take mask')
            else:
                print("but you can't")
            
        else:
            print('close')
        time.sleep(1)

t1 = threading.Thread(target = open_close)
t2 = threading.Thread(target = scan_nfc)
t1.start()
t2.start()



