from statefun import *
from aiohttp import web

print("Starting python statefun ")
functions = StatefulFunctions()

# documentation on stateful functions can be found here
# https://nightlies.apache.org/flink/flink-statefun-docs-release-3.2/docs/sdk/python/
# if you want to use flask instead, there is an example here:
# https://github.com/apache/flink-statefun/tree/master/statefun-sdk-python

@functions.bind(
    typename='python.mvillalobos.rd.statefun/pong',
    specs=[ValueSpec(name='seen', type=IntType)])
async def pong(ctx: Context, message: Message):

    ping_message = message.as_string()

    storage = ctx.storage
    seen = storage.seen or 0
    seen = seen + 1
    storage.seen = seen

    pong_message = f"Pong for the {seen}th time! with ping message: {ping_message}"
    print(pong_message)

    # context.send_egress(kafka_egress_message(
    #     typename='io.statefun.types/string',
    #     topic='pong',
    #     key=ctx.address.id,
    #     value=pong_message))


handler = RequestReplyHandler(functions)

async def handle(request):
    req = await request.read()
    res = await handler.handle_async(req)
    return web.Response(body=res, content_type="application/octet-stream")


app = web.Application()
app.add_routes([web.post('/statefun', handle)])

if __name__ == '__main__':
    web.run_app(app, port=8000)