[![Codacy Badge](https://app.codacy.com/project/badge/Grade/35f78eaef7d84690ad81819853527f8c)](https://www.codacy.com/gh/Zhenyria/monro-consulting-bot/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Zhenyria/monro-consulting-bot&amp;utm_campaign=Badge_Grade)
[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](https://github.com/Zhenyria/monro-consulting-bot/blob/master/LICENSE)

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

## Launching the application :arrow_forward:

### Local run

- Turn on `local` Maven profile
- Rename `config/telegram-example.conf` to `config/telegram.conf`
- Write name and token of your Telegram bot in `config/telegram.conf`

### Docker image creating :whale:

- Replace **zhenyria** in `Makefile` by name of your Dockerhub account (it is necessary for pushing created image to
  your Dockerhub repository)
- Run `make build tag=<WRITE TAG HERE>`
