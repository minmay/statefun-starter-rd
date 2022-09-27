FROM python:3

ENV APP_DIR /opt/rd/statefun/pong/

WORKDIR $APP_DIR
COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt
COPY main.py ./
EXPOSE 8000
CMD [ "python", "-u", "./main.py" ]