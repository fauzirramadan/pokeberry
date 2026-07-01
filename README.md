# 🍓 PokeBerry App

Simple Android application built with **Kotlin + Jetpack Compose** that consumes the public PokeAPI Berry endpoint.

https://pokeapi.co/api/v2/berry/

This app demonstrates:

- API integration using Retrofit
- Clean & structured architecture
- Pagination (lazy loading)
- Pull to refresh
- Detail screen
- Request & response debugging
- Modern UI with Jetpack Compose

---

# 📱 Features

## ✅ Berry List

- Fetch berry list from API
- Infinite scroll pagination
- Pull to refresh
- Smooth lazy loading

## ✅ Berry Detail

- Navigate to berry detail page
- Display berry information in fun UI cards
- Loading state handling

## ✅ Debug Request & Response

- Show actual API request URL
- Show raw API JSON response
- Accessible from top app bar debug button

---

# 🛠 Tech Stack

- Kotlin
- Jetpack Compose
- Retrofit
- Gson
- Coroutines
- StateFlow
- MVVM Architecture

---

# 📂 Project Structure

```text
com.pokeberry.app
│
├── data
│   ├── remote
│   ├── repository
│   └── mapper
│
├── domain
│   └── model
│
├── network
│
├── presentation
│   ├── screen
│   ├── component
│   └── viewmodel
│
└── ui.theme