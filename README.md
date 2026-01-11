# Recipe Viewer 🍳

A modern **Kotlin Multiplatform** + **Compose Multiplatform** recipe browser using TheMealDB API.

Runs on **Android**, **iOS**, **Desktop (JVM)**, and **Web (WASM)** with shared business logic & UI.

![Light & Dark Theme](docs/screenshots/light-dark-split.png)
![Search & Categories](docs/screenshots/search-categories.gif)
![Favorites Tab](docs/screenshots/favorites-tab.png)

### Features
- Browse recipes by category or search
- View full recipe details (ingredients, instructions, video link)
- Favorites (persisted locally)
- Pull-to-refresh + shimmer loading
- Real-time search with debounce
- Light/Dark theme (system + Material You dynamic colors on Android)
- Accessibility: content descriptions, large text support, touch targets
- Clean architecture (Repository + ViewModel + Result pattern)

### Tech Stack
| Category              | Technologies                                                                 |
|-----------------------|-----------------------------------------------------------------------------|
| Language              | Kotlin 2.1.x                                                                |
| UI                    | Compose Multiplatform 1.8+ (Material3)                                      |
| Networking            | Ktor Client + kotlinx-serialization                                        |
| Storage               | AndroidX DataStore (Preferences)                                            |
| Navigation            | Voyager (tabs + screen navigator)                                           |
| Image Loading         | Kamel (with placeholders, errors, caching)                                  |
| Architecture          | MVVM + Repository pattern + clean multiplatform separation                 |
| Platforms             | Android • iOS • Desktop (JVM) • Web (WASM – optional)                       |

### Screenshots & Demo

**Light / Dark mode**  
![Light & Dark](docs/screenshots/light-dark-split.png)

**Search + Category filtering**  
![Search & Filter GIF](docs/screenshots/search-filter.gif)

**Favorites tab**  
![Favorites](docs/screenshots/favorites-empty.png) → ![With items](docs/screenshots/favorites-filled.png)

**Accessibility (TalkBack reading recipe card)**  
![TalkBack demo](docs/screenshots/talkback-demo.png)

### Architecture Overview

App (Compose Multiplatform)
            ↓ 
Voyager (Tabs + Navigation)
            ↓
ViewModels (StateFlow + ViewModelScope)
            ↓
Repository (Result<T> wrapper)
           ↓
     Data (Ktor API)

### Build & Run

```bash
# Android
./gradlew androidApp:installDebug

# Desktop (JVM)
./gradlew composeApp:run

# iOS (macOS only)
./gradlew :composeApp:iosSimulatorArm64Run

# Check all targets
./gradlew check