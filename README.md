[![Build Status](https://travis-ci.com/Zhenyria/telegram-consulting-plugin.svg?branch=master)](https://travis-ci.org/Zhenyria/telegram-consulting-plugin)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/35f78eaef7d84690ad81819853527f8c)](https://www.codacy.com/gh/Zhenyria/telegram-consulting-plugin/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Zhenyria/telegram-consulting-plugin&amp;utm_campaign=Badge_Grade)

# Monro consulting bot

The bot was created within educational work for common principles of **Telegram Bot API** using demonstrating.

The bot is able to greet new or returned users and keeps records of all users which have non blocked chats with the bot.

## Commands :speech_balloon:

| command | description |
|--|--|
| `/members_count` | returns total amount of all users which use the bot nowadays |

## Technology stack

- Java 15
  - Maven
  - Spring Boot
  - Hibernate
  - HOCON
- Hyper SQL Database
- Docker

## Docker image creating :whale:

### Requirements

- Docker
- Makefile
- Java 15
- Maven (with toolchain plugin)

### Steps

- Create Hyper SQL Database instance in `db` directory with username **sa** and password **password**
- Run `src/main/resources/schema.sql` and `src/main/resources/init.sql` for created database
- Rename `config/telegram-example.conf` to `config/telegram.conf`
- Write name and token of your bot in `config/telegram.conf`
- Replace **zhenyria** in `Makefile` by name of your Docker hub account (it is necessary for pushing created image to
  your
  Docker hub repository)
- Run `make create_image tag=<WRITE TAG HERE>`

The image will is created. It could be run by `make run tag=<WRITE TAG HERE>`
