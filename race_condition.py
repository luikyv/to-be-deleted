import asyncio

class Incrementer:
    def __init__(self) -> None:
        self.x = 0
    
    async def inc(self):
        a = self.x
        await asyncio.sleep(0.5)
        self.x = a + 1
        print(self.x)

async def main():
    incrementer = Incrementer()

    await asyncio.gather(
        incrementer.inc(), incrementer.inc()
    )
    await incrementer.inc()

if __name__=="__main__":
    asyncio.run(main())