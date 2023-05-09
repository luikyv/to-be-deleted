from fastapi import FastAPI, WebSocket, WebSocketDisconnect
from typing import Dict

class ConnectionManager:
    def __init__(self):
        self.active_connections: Dict[str, WebSocket] = {}

    async def connect(self, client_name: str, websocket: WebSocket) -> None:
        await websocket.accept()
        for websocket_ in self.active_connections.values():
                await websocket_.send_text(f"{client_name} joined the chat")
        self.active_connections[client_name] = websocket

    async def disconnect(self, client_name: str) -> None:
        self.active_connections.pop(client_name)
        for websocket_ in self.active_connections.values():
                await websocket_.send_text(f"{client_name} left the chat")

    async def send_message(self, message: str, client_name: str) -> None:
        for client_name_, websocket_ in self.active_connections.items():
            if client_name_ !=  client_name:
                await websocket_.send_text(f"{client_name} said: {message}")

manager = ConnectionManager()
app = FastAPI()

@app.websocket("/chat/ws")
async def get(websocket: WebSocket, client_name: str):
    await manager.connect(client_name, websocket)

    try:
        while True:
            data = await websocket.receive_text()
            await manager.send_message(data, client_name)
    except WebSocketDisconnect:
        await manager.disconnect(client_name)

@app.get("/test")
async def test():
     return "This is a test"

@app.get("/")
async def test2():
     return "This is a test"