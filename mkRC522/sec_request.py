import RPi.GPIO as GPIO
import requests
import time
import threading

from operator import eq


import uidReadmd
import MFRC522


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


reader= uidReadmd.uidReader()


def run_wheel():
    GPIO.setmode(GPIO.BOARD)
    for i in range(1,514):
      for halfstep in range(8):
        for pin in range(4):
          GPIO.output(control_pins[pin], halfstep_seq[halfstep][pin])
        time.sleep(0.001)
    
        
def open_close():## onopen requests
    basic_time = time.time()## basic time
    nowtime = round(time.time()-basic_time)
    
    while 1:
        if(nowtime > 9):## request once in 10 secs
            response2 = requests.post('http://kyu9341.cafe24.com/ValidateTag.php')
            onoff = response2.json()["OnOff"]
            if onoff is True:
                print("box open")
            else:
                print("box close")
            
            basic_time = time.time()
        
        if(nowtime != round(time.time()-basic_time)):
            print(round(time.time()-basic_time))
            nowtime = round(time.time()-basic_time)
            
        time.sleep(0.001)
            
def scan_nfc():
    while 1:
        global onoff

        id = reader.uidToString(reader.init())
        
        print(id)

        data = {'CardNum':id}
        response2 = requests.post('http://kyu9341.cafe24.com/ValidateTag.php',data=data)
        print(response2.json())
        onoff = response2.json()['OnOff']#box open or close?
        validate = response2.json()['Validate']# used box 
        existUser = response2.json()['existUser']
        if onoff is True:
            print('open')
            if existUser is True:
                print("you exist user")
                if validate is True:
                    run_wheel()
                    print('take mask')
            
                else:
                   print("but you already use")
            else:
                print("you not exist")
            
        else:
            print('close')
        time.sleep(1)

t1 = threading.Thread(target = open_close)
t2 = threading.Thread(target = scan_nfc)
t1.start()
t2.start()



