# Hasiru Usiru Mapper 🌳

**Mapping Bangalore's Green Soul, One Tree at a Time.**

Hasiru Usiru Mapper is a community-driven Android application that empowers Bangalore’s citizens to geo-tag urban trees, monitor tree health using AI, and track real-time "Oxygen Scores" for their neighborhoods to drive data-backed urban greening.

## 🚀 Key Features

*   **Live Community Map:** A real-time Google Maps interface showing tree density and health (Green/Yellow/Red) across Bangalore.
*   **AI Tree Tagger:** Uses TensorFlow Lite and Gemini Vision AI to automatically identify tree species and analyze health conditions from a single photo.
*   **Street Oxygen Score:** An innovative dashboard that calculates the oxygen production of individual streets and wards based on tree species and girth.
*   **Green Gap Reporting:** Empowers citizens to mark empty spaces where trees are missing, generating data for municipal authorities (BBMP) to take action.
*   **Bilingual Species Guide:** A comprehensive encyclopedia of native Bangalore trees with descriptions in both English and Kannada.
*   **Gamified Conservation:** Earn "Forest Guardian" badges and climb the community leaderboard for every tree tagged and protected.

## 🛠️ Technology Stack

*   **Language:** 100% Kotlin (v2.0.21)
*   **Frontend:** Jetpack Compose, Material 3, Google Maps SDK for Android
*   **Backend:** Firebase Firestore (Real-time DB), Firebase Storage, Firebase Auth
*   **Intelligence:** Gemini 1.5 Flash (Vision & Chat), TensorFlow Lite
*   **Architecture:** MVVM (Model-View-ViewModel) with Repository Pattern
*   **Other:** Room DB (Offline support), Coil Compose (Image loading), Kotlin Coroutines & Flow

## 📖 How it Works

1.  **Tag a Tree:** Take a photo of a tree. The AI automatically detects the species and health.
2.  **Geo-Location:** The app auto-fills the GPS coordinates and street name.
3.  **Community Map:** The tree is added to the live map for everyone to see.
4.  **Oxygen Score:** Your street's green ranking is updated based on your contribution.

## 🌿 The Problem it Solves

Most urban greening efforts fail because of a lack of granular data. City authorities often don't know which trees are dying or where pits are empty. **Hasiru Usiru Mapper** solves this by putting a powerful monitoring tool in every citizen’s pocket, creating a transparent, verifiable, and real-time database of our urban forest to ensure Bangalore breathes again.

---

Created with ❤️ for Bangalore.
