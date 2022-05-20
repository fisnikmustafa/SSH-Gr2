import mysql.connector
from mysql.connector import Error

from fastapi import FastAPI

app = FastAPI()

@app.get("/")
def root():
    return {"Hello": "World"}

@app.get("/api/users")
async def fetch_users():
    return