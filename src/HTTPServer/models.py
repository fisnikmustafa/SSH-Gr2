import enum
import json
from pydantic import BaseModel

class Success:
    def __init__(self, shqip, anglisht, matematike, fizike, kimi, biologji, filozofi):
        self.shqip = shqip
        self.anglisht = anglisht
        self.matematike = matematike
        self.fizike = fizike
        self.kimi = kimi
        self.biologji = biologji
        self.filozofi = filozofi

class Gender(str, enum.Enum):
    F = "F"
    M = "M"

class Status(enum.Enum):
    ONLINE = "ONLINE"
    OFFLINE = "OFFLINE"

class User(BaseModel):
    firstName: str
    lastName: str
    gender: Gender
    phoneNumber: int
    email: str
    password: str
    status: Status = Status.OFFLINE;

class Student(User):
    id: int
    parentName: str
    school: str
    success: Success

