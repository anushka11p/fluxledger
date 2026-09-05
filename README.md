# FluxLedger

**Offline-first multi-currency expense tracker for Android**

Log expenses in any of four currencies and see them converted to your home currency using live ECB exchange rates. Rates are cached locally, so conversion keeps working with no network at all.

Built as a portfolio project to practise Clean Architecture, offline-first data flow, and both of Android's UI toolkits.

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat&logo=jetpackcompose&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-757575?style=flat&logo=materialdesign&logoColor=white)
![Min API](https://img.shields.io/badge/API-26%2B-3DDC84?style=flat&logo=android&logoColor=white)

---

## Screenshots

**Transactions:-**
<img width="207" height="446" alt="Screenshot 2026-09-05 at 10 51 55 PM" src="https://github.com/user-attachments/assets/983db04b-ea6c-4ffa-bfb7-f2057815f8cd" />

**Edit dashboard:-**
<img width="207" height="446" alt="image" src="https://github.com/user-attachments/assets/5a059ad2-5c43-484f-ac21-9796fc9367c9" />

**Dashboard:-**
<img width="207" height="446" alt="Screenshot 2026-09-05 at 10 52 38 PM" src="https://github.com/user-attachments/assets/f81e6a8f-803b-49c3-8079-e6f67f1aa6ab" />



---

## Features

- Add, edit, and delete transactions with amount, currency, category, note, and date
- Live exchange rates from the [Frankfurter API](https://frankfurter.dev) (European Central Bank data, no API key)
- Automatic conversion to a home currency, with rates cached in Room so conversion works offline
- Monthly dashboard showing total spend and a per-category breakdown
- Material 3 throughout, with light and dark theme support
- Fully functional with no network connection after the first successful rate fetch

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| UI (dashboard) | XML layouts + ViewBinding + RecyclerView |
| Architecture | Clean Architecture + MVVM |
| Local database | Room + Flow |
| Networking | Retrofit + OkHttp + Moshi |
| Dependency injection | Hilt |
| Asynchronous | Coroutines + StateFlow |
| Navigation | Navigation Compose |
| Testing | JUnit, Turbine, Truth |

The dashboard is deliberately built with XML layouts and ViewBinding rather than Compose. Most production Android codebases still run on the View system, so the project covers both toolkits rather than only the newer one.

---

## Architecture

```
Presentation  →  ViewModels + Compose screens + one XML/ViewBinding screen
      ↓
Domain        →  UseCases + repository interfaces + models
      ↓
Data          →  Repository implementations + Room + Retrofit + mappers
```

- **Single source of truth:** the Room database. The UI never reads from the network directly.
- **Unidirectional data flow** using `StateFlow`, collected with `collectAsState` in Compose and `repeatOnLifecycle` in the View-based screen.
- **The domain layer has no Android dependencies**, which makes use cases testable on the JVM without an emulator.
- **Dependency injection** with Hilt, wired through modules for the database, network, and repositories.

### Offline-first rate handling

`RateRepositoryImpl` is the core of the offline story:

1. A single API call fetches EUR-based rates for around 30 currencies.
2. Every rate is written to a Room table, so the cache survives process death.
3. Cached rates are reused for six hours before refetching.
4. If the network call fails, the repository falls back to cached rates **regardless of age** — a rate that is a few hours stale is far more useful than no conversion at all.

Conversion between arbitrary pairs is done as a cross-rate through EUR, so one fetch covers every currency combination rather than one request per pair.

### Frozen conversion rates

`baseAmount` is calculated once when a transaction is saved and then stored. Reading a transaction never recalculates it. A receipt shouldn't change value retroactively because the market moved.

Editing follows the same principle: the rate is only recalculated when the amount or currency changes. Fixing a typo in a note leaves the converted value untouched.

---

## Design notes

The app uses Material 3 design tokens rather than hardcoded values, in both toolkits:

- **Colour** comes from the theme's colour roles (`colorPrimaryContainer`, `colorSurfaceContainerLow`, `colorOnSurfaceVariant`) rather than hex literals, so light and dark themes work without a second set of definitions.
- **Typography** uses the Material type scale (`textAppearanceTitleMedium`, `textAppearanceDisplaySmall`) rather than raw `sp` values.
- The XML screen uses `?attr/` references to reach the same tokens Compose reads from `MaterialTheme`.

A few interaction decisions worth calling out:

- The transaction row leads with **category** rather than amount, because scanning a list of expenses is usually a question of "what did I spend on" before "how much."
- The converted amount is **hidden when the currency matches the home currency**, since showing `₹50.00` twice on the same row is noise.
- The save button disables itself while a write is in flight, and the screen waits for the write to complete before navigating back. An earlier version navigated immediately, which cancelled the coroutine mid-write and silently dropped transactions.

---

## Testing

12 unit tests covering the domain and data layers, run with `./gradlew testDebugUnitTest`.

The rate conversion tests use fake implementations of the API and DAO to cover:

- Cross-rate conversion between two non-base currencies
- The same-currency short circuit (no network call at all)
- Persistence of every fetched rate
- Fallback to cached rates when the network fails
- Behaviour when offline with an empty cache
- Cache reuse within the TTL window

---

## Known limitations

- **A transaction saved offline before any successful rate fetch is stored at a 1:1 rate**, and that value is frozen. This affects a user whose very first action happens with no network. Storing `baseAmount` as nullable and converting lazily on the first successful fetch would fix it.
- The home currency is hardcoded to INR. A settings screen backed by DataStore is the natural next step.
- Frankfurter provides around 30 currencies; the picker exposes four.

---

## Getting Started

### Prerequisites

- Android Studio (latest stable)
- JDK 17 or 21
- Android emulator or physical device (API 26+)

### Run the project

```bash
git clone https://github.com/anushka11p/fluxledger.git
```

Open in Android Studio, sync Gradle, and run. No API key or configuration is needed — the Frankfurter API is free and unauthenticated.

To see the offline behaviour: add a transaction while online, force-stop the app, enable airplane mode, then relaunch and add another. Conversion still works from the cached rates.

---

## Roadmap

- [x] Room database with Clean Architecture and Hilt
- [x] Add, edit, and delete transactions
- [x] Live currency conversion via the Frankfurter API
- [x] Exchange rates persisted in Room for offline conversion
- [x] Dashboard with monthly total and category breakdown (XML + ViewBinding)
- [x] Unit tests for conversion and offline fallback
- [ ] Settings screen for choosing the home currency
- [ ] Lazy conversion for transactions saved before the first rate fetch
- [ ] Date picker on the add/edit screen
- [ ] Instrumented tests for the main user flows

---

## Author

**Anushka Prasad**
Electronics & Computer Engineering, SRMIST

- GitHub: [@anushka11p](https://github.com/anushka11p)
- LinkedIn: [anushka-prasad](https://www.linkedin.com/in/anushka-prasad-731541331/)
