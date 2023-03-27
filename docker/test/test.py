import testcontainers.compose
import requests
import time
import psycopg2

COMPOSE_PATH = '../'
DATABASE_URL = 'postgresql+psycopg2://postgres:postgres@db:5432/postgres'

def get_db():
    conn = psycopg2.connect(
        database='postgres',
        user='postgres',
        password='postgres',
        host='127.0.0.1',
        port=5432
    )
    return conn

def setup():
    compose = testcontainers.compose.DockerCompose(COMPOSE_PATH)
    #compose.start()

    #time.sleep(10)

    return compose, get_db()
#cursor.execute("""SELECT table_name FROM information_schema.tables
#       WHERE table_schema = 'public'""")
#for table in cursor.fetchall():
#    print(