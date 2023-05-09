from websocket import create_connection

if __name__=="__main__":

    ws = create_connection("ws://localhost:8000/chat/ws?client_name=Receiver")

    try:
        while True:
            message: str = ws.recv()
            print(message)
    except KeyboardInterrupt:
        ws.close()
