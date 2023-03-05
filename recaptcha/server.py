from flask import Flask, render_template, request, Response
import requests

app = Flask(__name__)

@app.route("/")
def hello_world():
    return render_template('test.html', name=None)

@app.route("/federate/login")
def federate():
    # headers = {k:v for k,v in request.headers if k.lower() == 'host'}
    headers = {}
    headers["my_test"] = "my_test"
    res = requests.request(
        method = request.method,
        url = "http://localhost:5000/header",
        headers = headers,
        data = request.get_data(),
        cookies = request.cookies,
        allow_redirects = False,
    )

    # excluded_headers = ['content-encoding', 'content-length', 'transfer-encoding', 'connection']  #NOTE we here exclude all "hop-by-hop headers" defined by RFC 2616 section 13.5.1 ref. https://www.rfc-editor.org/rfc/rfc2616#section-13.5.1
    # excluded_headers = []
    # headers          = [
    #     (k,v) for k,v in res.raw.headers.items()
    #     if k.lower() not in excluded_headers
    # ]

    response = Response(res.content, res.status_code, dict(res.raw.headers.items()))
    return response

@app.route("/header")
def header():
    print(request.headers)
    print(request.headers.get("my_test"))
    return "<p>Hello, World!</p>"