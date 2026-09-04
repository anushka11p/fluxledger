# FluxLedger

**Offline-first Multi-Currency Expense Tracker**

A modern Android application built with Clean Architecture, MVVM, Jetpack Compose, Room, Hilt, and Coroutines. Designed as a production-style portfolio project focused on clean code, offline-first behaviour, and real-world architecture.

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat&logo=jetpackcompose&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-757575?style=flat&logo=materialdesign&logoColor=white)
![Min API](https://img.shields.io/badge/API-26%2B-3DDC84?style=flat&logo=android&logoColor=white)

---

## Features

- Add, view, and delete transactions
- Multi-currency support (INR, USD, EUR, GBP)
- Offline-first architecture using Room as the single source of truth
- Clean Architecture + MVVM
- Jetpack Compose + Material 3 UI
- Hilt for dependency injection
- Kotlin Coroutines + Flow
- Simple and clean navigation

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | Clean Architecture + MVVM |
| Local Database | Room + Flow |
| Dependency Injection | Hilt |
| Asynchronous | Coroutines + StateFlow |
| Navigation | Navigation Compose |

---

## Architecture

The project follows Clean Architecture with a clear separation of concerns:

```
Presentation  →  ViewModels + Compose Screens
      ↓
Domain        →  UseCases + Repository Interfaces + Models
      ↓
Data          →  Repository Implementations + Room + Mappers
```

- **Single source of truth:** the Room database
- **Unidirectional data flow** using `StateFlow`
- **Dependency injection** with Hilt
- **UseCases** hold the business logic and keep ViewModels thin

---

## Project Structure

```
com.anushka.fluxledger
├── data
│   ├── local               # Room Entity, DAO, Database
│   ├── repository          # Repository implementations
│   └── mapper
├── domain
│   ├── model
│   ├── repository          # Repository interfaces
│   └── usecase
├── presentation
│   ├── ui
│   │   ├── screens
│   │   ├── components
│   │   └── theme
│   └── viewmodel
└── di                      # Hilt modules
```

---

## Getting Started

### Prerequisites

- Android Studio (latest stable)
- JDK 17 or 21
- Android emulator or physical device (API 26+)

### Run the project

1. Clone the repository:

   ```bash
   git clone https://github.com/anushka11p/fluxledger.git
   ```

2. Open the project in Android Studio
3. Sync Gradle
4. Run on an emulator or device

---

## Roadmap

**Completed**

- [x] Project setup with Version Catalog
- [x] Room database (Entity, DAO, Database)
- [x] Domain models + UseCases
- [x] Repository pattern
- [x] Hilt dependency injection
- [x] Transaction list screen
- [x] Add transaction screen
- [x] Basic navigation

**In progress / planned**

- [ ] Live currency conversion (Frankfurter API)
- [ ] Dashboard with category breakdown
- [ ] Edit transaction
- [ ] Better empty states & polish
- [ ] Unit tests

---

## Author

**Anushka Prasad**
Electronics & Computer Engineering, SRMIST

- GitHub: [@anushka11p](https://github.com/anushka11p)
- LinkedIn: [anushka-prasad](https://www.linkedin.com/in/anushka-prasad-731541331/)
