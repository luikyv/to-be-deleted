from websocket import create_connection

if __name__=="__main__":

    ws = create_connection("ws://localhost:8000/chat/ws?client_name=Sender")

    try:
        while True:
            message: str = input()
            ws.send(message)
    except KeyboardInterrupt:
        ws.close()