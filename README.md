# Notely

[![Kotlin](https://img.shields.io/badge/kotlin-2.1.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Compose](https://img.shields.io/badge/compose-1.7.0-blue.svg?logo=jetpackcompose)](https://www.jetbrains.com/lp/compose-multiplatform)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

A modern, cross-platform note-taking application built with Compose Multiplatform

## Features

🔍 **Simple Search** - Find your notes instantly with text search

📊 **Simple Filtering** - Filter notes by type (Starred, Voice Notes, Recent)

🎙️ **Audio Recording** - Record voice notes and play them back within the app

✏️ **Minimal Text Formatting** - Format your notes with:
- Headers and sub-headers
- Title styling
- Bold, italic, and underline text
- Text alignment (left, right, center)

🌓 **Theming** - Switch between dark and light themes based on your preference or system settings

💻 **Cross-Platform** - Built with Compose Multiplatform for seamless experience across Android & iOS

## Built With 🛠

- **[Kotlin](https://kotlinlang.org/)** - Official programming language for Android development
- **[Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)** - UI toolkit for building native applications
- **[Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** - For asynchronous programming
- **[Clean Architecture](https://developer.android.com/topic/architecture)** - Ensures scalability and testability.
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)** - Stores and manages UI-related data
- **[Dagger-Hilt](https://dagger.dev/hilt/)** - Dependency injection for Android
- **[Material 3](https://m3.material.io/)** - Design system for modern UI
- **Native Compose Navigation** - No third-party navigation libraries
- **Custom Text Editor** - Built from scratch without external editing libraries

## Android Dark Theme Screenshots

<img src="assets/screenshot1.jpg" alt="screenshot2" width="250"> <img src="assets/screenshot2.jpg" alt="screenshot2" width="250"> <img src="assets/screenshot3.jpg" alt="screenshot3" width="250">

<img src="assets/screenshot11.jpg" alt="screenshot3" width="250"> <img src="assets/screenshot4.jpg" alt="screenshot4" width="250">

## iOS Light Theme Screenshots

<img src="assets/screenshot5.png" alt="screenshot5" width="250"> <img src="assets/screenshot6.png" alt="screenshot6" width="250"> <img src="assets/screenshot10.jpg" alt="screenshot7" width="250">

<img src="assets/screenshot7.png" alt="screenshot7" width="250"> <img src="assets/screenshot8.png" alt="screenshot8" width="250">

## Architecture

Notely is built with Clean Architecture principles, separating the app into distinct layers:

- **UI Layer**: Compose UI components
- **Presentation Layer**: Platform Independent ViewModels
- **Domain Layer**: Business logic and use cases
- **Data Layer**: Repositories and data sources

<img src="assets/layered_architecture_diagram.png" alt="Logo" width="70%">

## Project Structure
`shared/`: Contains shared business logic and UI code.

`androidApp/`: Contains Android-specific code.

`iosApp/`: Contains iOS-specific code.

## Contributing
Contributions are welcome! Please follow these steps:

- Fork the repository.
- Create a new branch for your feature or bug fix.
- Submit a pull request with a clear description of your changes.

## Getting Started

### Prerequisites

- Android Studio Ladybug or newer
- XCode 16.1
- JDK 11 or higher
- Kotlin 2.0.21 or higher

### Installation

1. Clone the repository
   ```sh
   git clone https://github.com/tosinonikute/Notely.git
   ```

2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Run the app on an emulator or physical device

### License

```
Copyright 2024 Notely, Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
