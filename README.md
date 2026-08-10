# Budget Management System

Учебное Android-приложение Анны Енгалычевой для управления личными финансами.

## Возможности

- регистрация и авторизация;
- хранение access- и refresh-токенов;
- просмотр и создание финансовых операций;
- категории и теги;
- статистика доходов и расходов;
- локальное хранение данных через Room;
- интерфейс на Jetpack Compose.

## Стек

Kotlin · Android SDK 34 · Jetpack Compose · Retrofit · OkHttp · Room · DataStore · Coroutines

## Настройка API

Адрес API не хранится в исходном коде. По умолчанию debug-сборка обращается к локальному компьютеру из Android Emulator:

```text
http://10.0.2.2:7000/
```

Другой адрес можно передать через Gradle:

```bash
./gradlew assembleDebug -PAPI_BASE_URL=https://api.example.com/
```

В release-сборке незашифрованный HTTP запрещён. Не добавляйте реальные внутренние IP-адреса в репозиторий.

## Запуск

1. Установите Android Studio и JDK 17.
2. Откройте корень проекта.
3. Дождитесь синхронизации Gradle.
4. Запустите приложение на эмуляторе с Android 7.0 или новее.

## Проверка

```bash
./gradlew test
./gradlew assembleDebug
```

GitHub Actions автоматически проверяет unit-тесты и debug-сборку.

## Автор

Анна Енгалычева — Android-интерфейс, интеграция с REST API, локальное хранение и управление состоянием.
