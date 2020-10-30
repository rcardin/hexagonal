![](https://github.com/rcardin/hexagonal/workflows/Hexagonal/badge.svg)
[![Code Style](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io)


# Hexagonal Architecture

This toy project shows how to implement a service using Spring Boot, Kotlin, and the Hexagonal Architecture. The 
represented domain model is the management of a **stocks' portfolio**.

## User Stories

The implemented user stories are the following:
- A user should create a new portfolio.
- A user should buy stocks for an existing portfolio.
- A user should sell stocks that it owns.
- The system should receive the price of a stock via Kafka message
- The system should produce an event if the value of an owned stock falls down below a threshold
