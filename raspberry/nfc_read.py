#!/usr/bin/env python

import RPi.GPIO as GPIO
import time
from mfrc522 import SimpleMFRC522

reader = SimpleMFRC522()
i=1

try:
    while 1:
        id,text= reader.read()
        print(id)
        print(text)
        print(i)
        time.sleep(1)
        i = i+1        
finally:
    GPIO.cleanup()