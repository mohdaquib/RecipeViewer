# Recipe Viewer 🍳

Kotlin Multiplatform + Compose Multiplatform application showing recipes from [TheMealDB API](https://www.themealdb.com/).

## Platforms

- Android
- iOS
- Desktop (JVM)
- (planned) Web (Wasm)

## Tech stack

- Kotlin 2.1.x
- Compose Multiplatform ~1.8–1.10
- Ktor client + kotlinx.serialization
- Coroutines + Flow
- (later) MVVM + clean architecture

## Build & Run

```bash
# Android
./gradlew androidApp:installDebug

# Desktop
./gradlew composeApp:run

# iOS (from macOS)
./gradlew :composeApp:iosSimulatorArm64Run

# All targets check
./gradlew check