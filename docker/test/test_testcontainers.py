import requests
import time

import psycopg2
import testcontainers.compose

COMPOSE_PATH = '../'
CLIENT_URL = 'http://127.0.0.1:8001'
EXCHANGE_URL = 'http://127.0.0.1:8000'


def get_db():
    conn = psycopg2.connect(
        database='db',
        user='db',
        password='db',
        host='127.0.0.1',
        port=5432
    )
    return conn


def setup():
    compose = testcontainers.compose.DockerCompose(COMPOSE_PATH)
    compose.stop()
    time.sleep(10)
    compose.start()
    time.sleep(10)

    return compose, get_db().cursor()


def test_1():
    comp, cur = setup()

    assert requests.post(
        f'{EXCHANGE_URL}/companies/',
        json={'name': 'Google'}
    ).ok
    assert requests.post(
        f'{EXCHANGE_URL}/companies/',
        json={'name': 'Apple'}
    ).ok

    cur.execute('SELECT * FROM company')
    rows = cur.fetchall()
    assert len(rows) == 2
    assert rows[0] == ('Google', 1)
    assert rows[1] == ('Apple', 2)


def test_2():
    comp, cur = setup()

    requests.post(f'{EXCHANGE_URL}/companies/', json={'name': 'Google'})
    requests.post(f'{EXCHANGE_URL}/companies/', json={'name': 'Apple'})

    assert requests.post(
        f'{EXCHANGE_URL}/stocks/',
        json={'ticker': 'GOOG', 'company_id': 1, 'price': 105.1, 'quantity': 2000}
    ).ok
    assert requests.post(
        f'{EXCHANGE_URL}/stocks/',
        json={'ticker': 'AAPL', 'company_id': 2, 'price': 180.2, 'quantity': 10000}
    ).ok

    cur.execute('SELECT * FROM stock')
    rows = cur.fetchall()
    assert len(rows) == 2
    assert rows[0] == ('GOOG', 1, 105.1, 2000)
    assert rows[1] == ('AAPL', 2, 180.2, 10000)


def test_3():
    comp, cur = setup()

    assert requests.post(f'{CLIENT_URL}/users/', json={'cash': 1000}).ok
    assert requests.post(f'{CLIENT_URL}/users/', json={'cash': 2000}).ok

    cur.execute('SELECT * FROM exchangeuser')
    rows = cur.fetchall()
    assert len(rows) == 2
    assert rows[0] == (1000.0, 1)
    assert rows[1] == (2000.0, 2)


def test_4():
    comp, cur = setup()

    assert requests.post(
        f'{EXCHANGE_URL}/companies/',
        json={'name': 'Google'}
    ).ok
    assert requests.post(
        f'{EXCHANGE_URL}/stocks/',
        json={'ticker': 'GOOG', 'company_id': 1, 'price': 105.1, 'quantity': 2000}
    ).ok
    assert requests.post(f'{CLIENT_URL}/users/', json={'cash': 1000}).ok
    assert requests.get(f'{CLIENT_URL}/stocks/buy/GOOG', params={'quantity': 2, 'user_id': 1}).ok    

    cur.execute('SELECT * FROM asset')
    rows = cur.fetchall()
    assert len(rows) == 1
    assert rows[0] == ('GOOG', 2, 1, 1)
