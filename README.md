[![Codacy Badge](https://app.codacy.com/project/badge/Grade/35f78eaef7d84690ad81819853527f8c)](https://www.codacy.com/gh/Zhenyria/monro-consulting-bot/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Zhenyria/monro-consulting-bot&amp;utm_campaign=Badge_Grade)

# Monro consulting bot

The bot was created within educational work for common principles of **Telegram Bot API** using demonstrating.

The bot is able to greet new or returned users and keeps records of all users which have non blocked chats with the bot.

## Commands :speech_balloon:

All commands are described in [the enum](src/main/java/ru/zhenyria/monro_consulting_bot/util/StartCommand.java).

| Command              | Description                                                  | Localized description                   |
|:---------------------|:-------------------------------------------------------------|:----------------------------------------|
| `/members_count`     | returns total amount of all users which use the bot nowadays | Получить общее количество пользователей |
| `/recommended_shoes` | returns recommended shoes                                    | Получить рекомендуемую обувь            |
| `/size`              | sets customer's scale                                        | Установить свой размер                  |
| `/wish_list`         | returns current wish list                                    | Получить список желаемого               |

## Environment variables :wrench:

| Name               | Description                                         | Default value        |
|:-------------------|:----------------------------------------------------|:---------------------|
| ADMIN_NAME         | The name of user for access to management panel     | root                 | 
| ADMIN_PASSWORD     | The password of user for access to management panel | root                 |
| DB_ADDRESS         | The address of used database                        | localhost:5432/monro |
| DB_USER            | The name of user for access to database             | user                 |
| DB_PASSWORD        | The password of user for access to database         | password             |
| TELEGRAM_BOT_NAME  | The name of Telegram bot                            | -                    |
| TELEGRAM_BOT_TOKEN | The token of Telegram token                         | -                    |

## Technology stack

- Java 17
  - Hibernate
  - Maven _(with `toolchains` plugin)_
  - Spring Boot
  - HOCON
- PostgresSQL
- Docker

## Docker image creating :whale:

### Requirements

- Docker
- Makefile
- Java 17
- Maven (with toolchain plugin)

### Steps

To create `Docker` image follow these steps:

- Rename `config/telegram-example.conf` to `config/telegram.conf`
- Write name and token of your bot in `config/telegram.conf`
- Replace **zhenyria** in `Makefile` by name of your Docker hub account (it is necessary for pushing created image to
  your
  Docker hub repository)
- Run `make create_image tag=<WRITE TAG HERE>`

The image will is created. It could be run by `make run tag=<WRITE TAG HERE>`
