# CBMoney - Personal Finance Manager

**CBMoney** is a modern Android application designed to help users track their personal finances effectively. Built with the latest Android technologies, it provides a comprehensive suite of tools for managing income, expenses, and budgets with a focus on a clean user experience.

## 🚀 Key Features

* **Financial Overview (Home):** A central dashboard to monitor current balances and view transaction summaries.
* **Transaction Tracking:** Easily record income and expenses across various categories.
* **Budget Management:** Set monthly spending limits for specific categories to ensure financial discipline.
* **Detailed Reporting:** Visualized reports to help users analyze their spending habits and financial health.
* **Secure Authentication:** Supports multiple sign-in methods, including Email/Password and Google Identity.
* **Cloud Synchronization:** Real-time data storage and sync across devices using Firebase (Auth & Firestore).
* **Localization:** Built-in support for multiple languages, including English and Vietnamese.

## 🛠 Tech Stack

The application leverages a high-performance, modern stack:

| Category | Technology |
| :--- | :--- |
| **Language** | Kotlin (JVM 11) |
| **UI Framework** | Jetpack Compose |
| **Architecture** | Clean Architecture with MVI (Model-View-Intent) |
| **Dependency Injection** | Koin |
| **Local Database** | Room Persistence Library |
| **Preferences** | Jetpack DataStore |
| **Networking/Backend** | Firebase Auth & Cloud Firestore |
| **Navigation** | Navigation3 (Experimental) & Compose Navigation |
| **Image Loading** | Coil |
| **Animations** | Lottie |
| **UI Components** | Skydoves ColorPicker |

## 📂 Project Structure

CBMoney is organized following **Clean Architecture** principles to ensure modularity and testability:

* **`data/`**: Handles all data operations, including Repository implementations, Local DataSources (Room, DataStore), and Remote DataSources (Firebase).
* **`domain/`**: Contains core business logic, including Domain Models and UseCases (e.g., `AddCategoryUseCase`, `UpsertBudgetsUseCase`).
* **`presentation/`**: Manages the UI layer using ViewModels (MVI-based) and Jetpack Compose screens.
* **`di/`**: Centralized Dependency Injection modules using Koin.
* **`utils/`**: Shared utility classes and extension functions.

## ⚙️ Setup & Installation

To run this project locally:

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/chi2911ks/cbmoney.git](https://github.com/chi2911ks/cbmoney.git)
    ```
2.  **Firebase Setup:**
    * Create a project in the [Firebase Console](https://console.firebase.google.com/).
    * Add an Android app with the package name `com.cbmoney`.
    * Download `google-services.json` and place it in the `app/` directory.
    * Enable **Authentication** and **Firestore** in the Firebase dashboard.
3.  **Build:**
    * Open the project in **Android Studio** (Jellyfish or newer).
    * Sync Gradle and ensure you are using **JDK 11**.
    * Run on a device or emulator with **API Level 24+**.

---

**Author:** [chi2911ks](https://github.com/chi2911ks)