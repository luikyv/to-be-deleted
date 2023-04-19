import requests
from time import sleep

while True:
    sleep(15)
    resp = requests.get("https://id.itau.com.br/.well-known/openid-configuration")
    print(resp.status_code)
    