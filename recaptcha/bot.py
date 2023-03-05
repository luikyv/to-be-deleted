
from selenium import webdriver
from time import sleep

driver = webdriver.Chrome("chromedriver")

driver.get("http://localhost:5000/federate/login")
sleep(30)