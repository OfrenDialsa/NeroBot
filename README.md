# 🦎✨ NeroBot  

NeroBot is an AI-powered assistant developed using **Jetpack Compose** and **Gemini API**. It offers a **fun and lively chat experience** while also keeping users updated with the **latest news**. Built with **Clean Architecture**, NeroBot ensures a modular, maintainable, and scalable codebase.  

---

## ✨ Features  

- 🤖 **Engage with a fun AI assistant** – Chat with an interactive and entertaining AI.  
- 📰 **Stay updated with the latest news** – Fetch trending news and updates.  
- 🎨 **Modern UI with Jetpack Compose** – A smooth and delightful user experience.  
- 🏷 **Clean Architecture** – Ensures scalability and maintainability.  
- ⚡ **Fast and lightweight** – Optimized for performance and responsiveness.  

---

## 🚀 Tech Stack  

| Category         | Technology       |
|-----------------|-----------------|
| **Language**    | Kotlin          |
| **UI Framework** | Jetpack Compose |
| **AI Model**    | Gemini API       |
| **News API**    | newsapi.org |
| **Architecture** | Clean Architecture (Use Case, Repository, ViewModel) |
| **Dependency Injection** | Koin |
| **Data Storage** | Room / DataStore (if applicable) |
| **Networking**  | Retrofit + OkHttp |

---

## 📸 Screenshots  

_Include screenshots of the app here._  

---

## 🛠️ Installation  

1. **Open the project in Android Studio.**  
2. **Add your API keys** in the `local.properties` or appropriate config file:  
   ```properties
   GEMINI_API_KEY=your_gemini_api_key
   NEWS_API_KEY=your_news_api_key
   ```  
3. **Run the app** on an emulator or physical device.  

---

## 🔧 Architecture Overview  

NeroBot follows **Clean Architecture**, ensuring separation of concerns and scalability:  

```bash
📂 NeroBot
 ├── 📂 data             # Data layer (repositories, API services, Room DB)
 │   ├── 📂 remote       # API interactions (Retrofit)
 │   ├── 📂 local        # Local database (Room, DataStore)
 │   ├── 📂 repository   # Repository implementations
 │   └── 📂 model        # Data models (DTOs, Entity)
 │
 ├── 📂 domain           # Business logic (Use Cases, Models)
 │   ├── 📂 usecase      # Application use cases
 │   ├── 📂 repository   # Repository interfaces
 │   └── 📂 model        # Domain models
 │
 ├── 📂 presentation     # UI layer (Jetpack Compose, ViewModel)
 │   ├── 📂 ui           # Screens and components
 │   ├── 📂 viewmodel    # ViewModels for managing UI state
 │   └── 📂 navigation   # App navigation (Jetpack Navigation)
 │
 ├── 📂 di               # Dependency Injection (Koin modules)
 ├── 📂 utils            # Helper functions, constants
 └── 📂 core             # Core components, such as base classes
```

---

## ⚙️ API Configuration  

NeroBot uses the **Gemini API** for AI interactions and a **News API** for fetching the latest news.  

### 🔑 Setting up API Keys  

1. **Gemini API:** Sign up and get your API key from [Gemini AI](https://aistudio.google.com/).  
2. **News API:** Obtain an API key from [NewsAPI](https://newsapi.org) (or another source).  
3. **Store the keys securely** in `local.properties`:  
   ```properties
   GEMINI_API_KEY=your_gemini_api_key
   NEWS_API_KEY=your_news_api_key
   ```

---

## 📌 How It Works  

### 1️⃣ Chatting with the AI  
- Users can send messages to the AI assistant.  
- The assistant responds in a lively and engaging manner using the **Gemini API**.  

### 2️⃣ Checking Latest News  
- The app fetches news articles from the **News API**.  
- Users can browse headlines, read summaries, and open full articles in a web browser.  

---

## 🏟️ License  

This project is licensed under the **MIT License**.  

```text
MIT License

Copyright (c) 2025 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
...
```

---

## 🌟 Acknowledgments  

- **Google Gemini API** – For powering the AI chatbot.  
- **News API** – For providing the latest news updates.  
- **Jetpack Compose** – For making UI development seamless.  

🚀 **Enjoy chatting with NeroBot!** 🦎✨
