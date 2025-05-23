# Notifications Microservice

A Spring Boot microservice for handling user notifications with support for real-time WebSocket delivery, Kafka event consumption, notification categories, and user preferences.

## Features

- **Event-driven notifications**: Consumes events from Kafka topics (e.g., `level-up`, `item-acquired`).
- **Notification categories**: Supports categorization (e.g., GAME_EVENT, SOCIAL_EVENT) via `NotificationCategory` enum.
- **User preferences**: Users can set notification preferences for different categories.
- **UUID-based IDs**: Uses UUIDs for sender and recipient identification.
- **WebSocket real-time delivery**: Sends notifications to users over WebSocket using Spring's `SimpMessagingTemplate`.
- **Robust error handling**: Global exception handler and custom exceptions for notification flow.
- **Integration & unit tests**: Includes full end-to-end tests for Kafka-to-WebSocket flow, repository tests, and service unit tests.

## Architecture

- **Kafka Integration**: Listens to notification events on specific topics. Processes and persists notifications, then triggers delivery.
- **NotificationService**: Core logic for handling and saving notifications.
- **NotificationSenderService**: Handles delivery logic, including user preferences.
- **RealtimeNotificationService**: Sends notifications to users via WebSocket.
- **User Preferences**: Stored as a JPA-mapped map of category to enabled/disabled.

## Technologies

- Java 17
- Spring Boot 3
- Spring Data JPA (H2 for tests)
- Spring Kafka
- Spring WebSocket (STOMP)
- JUnit 5, Mockito, AssertJ


## Testing

Run all tests:
```sh
./gradlew test
```

## Example Notification Events (Kafka, each of these events should be sent to the right topic)

### Player Level Up
```json
{
  "eventType": "level-up",
  "recipient": "<uuid>",
  "sender": null,
  "eventDetails": "5"
}
```
**Resulting Notification String:**
```
Congratulations! You've reached level 5!
```

### Item Acquired
```json
{
  "eventType": "item-acquired",
  "recipient": "<uuid>",
  "sender": "<uuid>",
  "eventDetails": "Excalibur Sword"
}
```
**Resulting Notification String:**
```
Awesome! You've obtained:  Excalibur Sword
```

### Friend Request
```json
{
  "eventType": "friend-request",
  "recipient": "<uuid>",
  "sender": "<uuid>",
  "eventDetails": "JohnDoe"
}
```
**Resulting Notification String:**
```
You have a new friend request from: JohnDoe
```

### PvP Event
```json
{
  "eventType": "pvp-event",
  "recipient": "<uuid>",
  "sender": "<uuid>",
  "eventDetails": "You won a PvP match!"
}
```
**Resulting Notification String:**
```
PvP Alert: You won a PvP match!
```



## Repository Structure

- `model/` - JPA entities and enums
- `service/` - Business logic and delivery
- `dto/` - Data transfer objects (Kafka payloads)
- `exception/` - Custom exceptions
- `util/` - Global exception handler
- `config/` - WebSocket and Kafka configuration
- `test/` - Unit and integration tests

## License

MIT
