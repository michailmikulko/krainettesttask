This project is a microservice-based system consisting of two services:

## How to Run

1.Clone repository: git clone https://github.com/michailmikulko/krainettesttask.git
2.Add `.env` file according to .env.example in root directory
3.Run system: docker-compose up -d --build

1. Auth Service
Responsible for:
- User authentication and authorization (JWT)
- CRUD operations for user accounts
- Role-based access control (USER / ADMIN)
- Sending events to RabbitMQ on user actions (create, update, delete)

2. Notification Service (Receiver)
Responsible for:
- Receiving messages from RabbitMQ
- Processing user events
- Sending email notifications (ADMIN users)

## Roles

### USER
- Can register
- Can view/update/delete only own account

### ADMIN
- Can view/update/delete any user
- Receives notifications about USER actions

##  Database Initialization

After startup, database contains:
- Test users
- Admin account (Email:pmikulko2@gmail.com,password:1234)

## API Endpoints

### Auth

| Method | URL | Description |
|------|-----|-------------|
| POST | /auth/sign-in | Login user |
| POST | /auth/refresh | Refresh JWT token |
| POST | /user/registration | User registration |

### Users (Auth Service)

| Method | URL | Role | Description |
|------|-----|------|-------------|
| GET | /user/{id} | ADMIN | Get user by id |
| GET | /user | ADMIN | Get all users |
| POST | /user | ADMIN | Create user |
| PATCH | /user/{id} | ADMIN | Update user |
| DELETE | /user/{id} | ADMIN | Delete user |
| PATCH | /user/me | USER/ADMIN | Update own profile |
| GET | /user/me | USER/ADMIN | Get own profile |
| DELETE | /user/me | USER/ADMIN | Delete own profile |

## Example Requests

### Sign In

POST /auth/sign-in

{
    "email":"pmikulko2@gmail.com",
    "password":"1234"
}

### Registration

POST /user/registration
{
    "username":"A22",
    "password":"1",
    "email":"pmikulko222@gmail.com",
    "firstName":"Mihail",
    "lastName":"Mikulka"
}

### Edit me

PATCH /user/me
{
    "username":"Abem",
    "password":"1",
    "email":"p@gmail.com",
    "firstName":"Mihail",
    "lastName":"Mikulka"
}

### Delete user

Delete /user/{id}
{}

### Get all users

GET /user
{}

### Get user by ID

PATCH /user/{id}
{
}

### Create user

POST /user
{
    "username":"Abem",
    "password":"1232223",
    "email":"pmikulk@gmail.com",
    "firstName":"Mihail",
    "lastName":"Mikulka",
    "role":"USER"
}