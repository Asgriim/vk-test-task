# Тестовое REST API
- [Выполненные пункты задания](#task)
- [Описание](#desc)
- [Запуск](#run)
- [Интерфейсы](#interf)


## <a name="task"/>Выполненные пункты задания</a>

- Реализовать обработчики (GET, POST, PUT, DELETE), которые проксируют запросы к https://jsonplaceholder.typicode.com/
  - /api/posts/**
  - /api/users/**
  - /api/albums/**
- Реализовать базовую авторизацию, с несколькими
  аккаунтами, у которых будут разные роли.
- роработать ролевую модель доступа. Чтобы было минимум 4 роли - ROLE_ADMIN, ROLE_POSTS, ROLE_USERS, ROLE_ALBUMS
  - ROLE_ADMIN - имеет доступ ко всем обработчикам
  - ROLE_POSTS - имеет доступ к обработчикам /posts/**
  - ROLE_USERS - имеет доступ к обработчикам /users/**
  - ROLE_ALBUMS - имеет доступ к обработчикам /albums/**
- Реализовать ведение аудита действий. (дата-время, пользователь, имеет ли доступ, параметры запроса, …)
- Реализовать inmemory кэш, чтобы уменьшить число запросов к jsonplaceholder. To есть изменения данных сначала должны происходить в кэше, а потом отправляться запросы на jsonplaceholder.
- Использование базы данных:
  - для ведением аудита,
  - для хранения данных пользователей.
- Добавление rest api для создание пользователей.
- Расширенная ролевая модель. (например, ROLE_POSTS_VIEWER, ROLE_POSTS_EDITOR, …)
- Написать юнит тесты на написанный код.

## <a name="desc"/>Описание</a>
Для хранения пользователей и аудита используеться `H2-database` так что все данные пропадают после каждого запуска приложения.

Без авторизации достопны только ``/api/auth/*``.

Для авторизации используються JWT токены. После авторизации необходимо добавлять заголовок
```Authorization:Bearer <token>``` к каждому запросу.

Есть всего 4 презарегестрированных юзера. 
- ``{
"login": "admin",
"password": "pass"
}`` - Админ с доступом ко всем обработчикам

- ``{
  "login": "album",
  "password": "album"
  }
  `` - доступ к /api/albums/**
- ``{
  "login": "post",
  "password": "post"
  }
  `` - доступ к /api/posts/**
- ``{
  "login": "user",
  "password": "user"
  }
  `` - доступ к /api/user/**


## <a name="run"/>Запуск</a>
```
git clone
mvn package
java -jar target/vk-test-task-0.0.1-SNAPSHOT.jar
```
После запуска сервер будет крутиться на ``localhost:42069``.
Порт можно поменять в <a href=https://github.com/Asgriim/vk-test-task/blob/c6de41c18c847ec6e90994628b264122fe85ad11/src/main/resources/application.properties#L1/>application.properties</a>.

## <a name="interf"/>Интерфейсы</a>
### Регистрация

При регистрации необходимо указать логин, пароль и <a href=https://github.com/Asgriim/vk-test-task/blob/c6de41c18c847ec6e90994628b264122fe85ad11/src/main/java/org/omega/vktesttask/entity/Role.java#L11>роли</a>.

Для регистрации админа нужно использовать отдельный URL ``/api/auth/register/admin``. 
```http request
POST localhost:42069/api/auth/register
Content-type: application/json

{
  "login": "albumUser",
  "password": "pass",
  "roles": [
    "ALBUM_EDITOR",
    "ALBUM_DELETER"
  ]
}
```
Response
```
ok
```
Админа может зарегистрировать только авторизованный админ.
```http request
POST localhost:42069/api/auth/register/admin
Content-type: application/json
Authorization:Bearer <token>

{
  "login": "adminUser",
  "password": "pass",
  "roles": [
    "ADMIN"
  ]
}
```
Response
```
ok
```
### Авторизация
Для авторизации <a href=https://github.com/Asgriim/vk-test-task/blob/c6de41c18c847ec6e90994628b264122fe85ad11/src/main/java/org/omega/vktesttask/entity/Role.java#L11>роли</a> указывать не нужно.

После успешной авторицаии необходимо добавлять заголовок
```Authorization:Bearer <token>``` к каждому запросу. 
```http request
POST localhost:42069/api/auth/login
Content-type: application/json

{
  "login": "admin",
  "password": "pass"
}
```

Response
```js
{
  "token": <token>,
  "tokenType": "Bearer",
  "liveTime": 86400000
}
```


### Аудит
Получить аудит может только админ.

Весь аудит в базе:
```http request
GET localhost:42069/api/admin/audit
Authorization: Bearer <token>
```
Response
```js
[
  {
    "id": 1,
    "date": "2024-03-13T16:26:36.374855Z",
    "user": {
      "login": "admin",
      "password": "$2a$10$7rkwAI0Vw6.miiYtE8T9..Cj9XUfHm7xeXZJEj.zAqv9h/VGc8LtC",
      "roles": [
        "ADMIN"
      ]
    },
    "method": "GET",
    "endpoint": "/api/admin/audit",
    "requestArguments": "[]",
    "ipAddress": "127.0.0.1"
  },
  {
    "id": 2,
    "date": "2024-03-13T16:26:43.880462Z",
    "user": null,
    "method": "POST",
    "endpoint": "/api/auth/register",
    "requestArguments": "[UserDTO(login=album2, password=, roles=[ALBUM_EDITOR, ALBUM_DELETER, ALBUM_VIEWER])]",
    "ipAddress": "127.0.0.1"
  }
]
```

Получить аудит по конкретному юзеру: 
``localhost:42069/api/admin/audit/{username}``
```http request
GET localhost:42069/api/admin/audit/album
Authorization: Bearer <token>
```
Response
```js
[
  {
    "id": 6,
    "date": "2024-03-13T16:28:57.299611Z",
    "user": {
      "login": "album",
      "password": "$2a$10$/PLH0r0WiYNCX6wr44OdseJb6po2S5U3t.Uks3BhU4mV/1pZMz9/e",
      "roles": [
        "ALBUM_DELETER",
        "ALBUM_VIEWER",
        "ALBUM_CREATOR",
        "ALBUM_EDITOR"
      ]
    },
    "method": "GET",
    "endpoint": "/api/albums/1",
    "requestArguments": "[1]",
    "ipAddress": "127.0.0.1"
  },
  {
    "id": 7,
    "date": "2024-03-13T16:29:00.131074Z",
    "user": {
      "login": "album",
      "password": "$2a$10$/PLH0r0WiYNCX6wr44OdseJb6po2S5U3t.Uks3BhU4mV/1pZMz9/e",
      "roles": [
        "ALBUM_DELETER",
        "ALBUM_VIEWER",
        "ALBUM_CREATOR",
        "ALBUM_EDITOR"
      ]
    },
    "method": "GET",
    "endpoint": "/api/albums/",
    "requestArguments": "[]",
    "ipAddress": "127.0.0.1"
  }
]
```

Остальные интерфейсы идентичны ``https://jsonplaceholder.typicode.com/``
