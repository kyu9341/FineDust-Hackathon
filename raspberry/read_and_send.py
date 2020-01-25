import RPi.GPIO as GPIO
import time
import requests

from mfrc522 import SimpleMFRC522

reader = SimpleMFRC522()

##print(response.text)

try:
    while 1:
        id,text= reader.read()
        print(id)
        print(text)
        data = {'Test':id}
        response = requests.post('http://kyu9341.cafe24.com/TestText.php',data=data)
        time.sleep(1)
        
finally:
    GPIO.cleanup()