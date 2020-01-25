import requests

data = {'Test':'hello2'}

response = requests.post('http://kyu9341.cafe24.com/TestText.php',data=data)
print(response)

