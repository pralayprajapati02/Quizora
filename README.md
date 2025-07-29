
# 📱🧠 Quizora – A Gamified Quiz App with AI Assistant

Quizora is a feature-rich Android quiz application designed to make learning engaging and rewarding. It offers users a customizable quiz experience along with a gamified reward system using coins, an AI assistant for support, and clean UI interactions.


## 🚀 Features of Quizora

🧠 Quiz Functionality
 - Customizable quizzes based on:

     - Number of questions

     - Category

     - Difficulty level

     - Question type (multiple choice / true-false)

 - One-attempt-per-question system (no going back!)

 - Timer for added challenge

 - No skipping — each question must be answered to proceed

🎯 Scoring & Coins System
 - 1 point awarded per correct answer

 - No negative marking

 - Earn coins based on score percentage (≥ 80%)

 - More questions answered = more coins!

🤖 In-Quiz AI ChatBot
 - Use coins to unlock a ChatBot assistant

 - ChatBot gives:

     - Help answering tough questions

 - Access restricted if coin balance < 500 (with helpful tip shown)

📊 Result & Feedback
 - Instant feedback after each answer (if enabled)

 - Final score breakdown at the end

 - Correct answers shown at the result screen

🔊 Music Settings
 - Music Only While Playing: background music plays only in QuizActivity

 - Music All Time: plays throughout the entire app

 - Mutually exclusive switches to avoid confusion

💡 Quick Tips & Onboarding
 - Feature discovery tooltips (e.g. Skydoves Balloon or Spotlight)

 - First-time onboarding guide to explain all key features

🌐 API Integration
 - Fetch quiz data from OpenTDB

 - Robust error handling and user-friendly fallbacks (e.g. no quiz found message)

🎨 UI/UX
 - Modern Material3 theming

 - Splash screen using Android SplashScreen API

 - Smooth user experience with edge-to-edge layouts

 - Theme support with custom fonts and colors


## 📸 Screenshots

**Light Mode**
| Splash Screen | Intro Screen | Avatar Selection Screen | Home Screen | Quiz Screen | Custom Quiz Screen | Result Screen | Setting Screen |
|--------|---------|--------|--------|---------|--------|--------|---------| 
|![Splash Screen](https://github.com/user-attachments/assets/676a4566-5553-4f36-a004-b0dcfa376778)|![Intro Screen](https://github.com/user-attachments/assets/972ea05c-e2d9-47d0-8ddf-3debcc654d88)|![Avatar Selection Screen](https://github.com/user-attachments/assets/7dedd4c3-9ae3-4cee-ad7b-3efc246c4d06)|![Home Screen](https://github.com/user-attachments/assets/fa1e5ec1-94a4-4250-b0ff-c4a244e4527e)|![Quiz Screen](https://github.com/user-attachments/assets/3b8a859c-5d1d-4bfd-a2e8-92247e818b19)|![Custom Quiz Screen](https://github.com/user-attachments/assets/4dd09965-0fc5-4b8c-99f1-8948bcdaeebf)|![Result Screen](https://github.com/user-attachments/assets/e5ef8371-7b96-4891-87a7-8ea6a98afc77)|![Setting Screen](https://github.com/user-attachments/assets/42e283c9-5bb4-499a-a425-c6e9aad05aa9)|

**Dark Mode**
| Splash Screen | Intro Screen | Avatar Selection Screen | Home Screen | Quiz Screen | Custom Quiz Screen | Result Screen | Setting Screen |
|--------|---------|--------|--------|---------|--------|--------|---------| 
|![Splash Screen](https://github.com/user-attachments/assets/ec392cdd-22fb-437f-9234-11afe7ebff26)|![Intro Screen](https://github.com/user-attachments/assets/68316d58-26a8-4f19-a5e2-1672ad01c1fd)|![Avatar Selection Screen](https://github.com/user-attachments/assets/97bb13ab-dc61-4484-b143-f632bac2a855)|![Home Screen](https://github.com/user-attachments/assets/4c21e3b3-968c-45aa-9e32-b60f0f82fd99)|![Quiz Screen](https://github.com/user-attachments/assets/a599f320-ca32-45d9-afa0-1e4531230608)|![Custom Quiz Screen](https://github.com/user-attachments/assets/a7508254-7563-4351-be6d-37a698d7c131)|![Result Screen](https://github.com/user-attachments/assets/8ebb31bd-c378-4960-bdbc-a548b46f0e52)|![Setting Screen](https://github.com/user-attachments/assets/65598c2d-9a0b-4c8c-9a7b-ef1956db5ce1)|

## 🚀 Run Locally

**Clone the project**

    git clone https://github.com/pralayprajapati02/quizora.git

**Open the project in Android Studio:**

 - Launch Android Studio

 - Click on "Open" and select the cloned project directory

 - Let it sync Gradle and build the project

 - Run the app on an emulator or physical device:

 - Connect your device or launch an emulator

 - Click Run ▶️ or use:





## 🎯 API Reference

#### Get all items

```http
  GET https://opentdb.com/api.php
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |
| `amount` | `integer` | **Required**. Number of questions to return (`max 50`)|
| `category` | `integer` | **Optional**. Category ID (e.g., 9 for General Knowledge). |
| `difficulty` | `string` | **Optional**. Accepts `easy`, `medium`, or `hard`. |
| `type` | `string` | **Optional**. Accepts `multiple` or `boolean`. |
| `encode` | `string` | **Optional**. Encoding method (`url3986`, `base64`, or `none`). |
| `token` | `string` | **Optional**. Session token to avoid duplicate questions.|

#### Get Categories

```http
  GET https://opentdb.com/api_category.php
```

Returns a list of available trivia categories with their corresponding IDs.

#### Get Session Token

```http
  GET https://opentdb.com/api_token.php?command=request
```

Returns a unique session token to help prevent duplicate questions.

#### Reset Session Token

```http
  GET https://opentdb.com/api_token.php?command=reset&token=YOUR_TOKEN
```

Resets the session associated with your token.

#### API Configuration

```http
  GET https://opentdb.com/api_config.php
```

Returns global configuration data such as category counts, question distribution, etc.


## 📚  Lessons Learned

Building this project gave me practical, hands-on experience in full-scale Android app development. Some of the key lessons I learned include:

✅ App Architecture
 - Implemented MVVM architecture for better separation of concerns and testability.

 - Learned how to efficiently manage data using ViewModels and LiveData.

✅ User Experience Enhancements
 - Integrated Material Design 3 components and theming for a modern UI.

 - Used libraries like Skydoves Balloon for tooltips and quick tips to guide users.

✅ Media Handling
 - Learned to handle background music playback using MediaPlayer, with user preferences to control music throughout the app.

✅ Shared Preferences
 - Managed user settings like music toggles, coin storage, and quiz progress using DataStore and SharedPreferences.

✅ API Integration
 - Fetched questions using OpenTDB API, parsed JSON, and displayed quizzes dynamically.

✅ Splash & Intro Screens
 - Used the Splash Screen API effectively and implemented a feature discovery dialog for first-time users.

⚠️ Challenges Faced
 - Splash Screen Logo Cut-off: Initially faced vector scaling issues on the splash screen. Resolved by adjusting the viewport and using appropriate size units (dp).

 - Music Playback Management: Ensured that music stops properly when the app is backgrounded or destroyed.

 - Coin Logic: Designed logic to calculate and store coins based on quiz performance and restricted chatbot access based on coin count.

 - Theme Incompatibility: Encountered a Theme.AppCompat crash and resolved it by correctly applying Theme.Material3.DayNight.




## 🙏 Acknowledgements

I would like to express my heartfelt gratitude to all who supported and inspired me throughout the development of Quizora – a gamified quiz application.

This project would not have been possible without the help of:

 - Open Source Libraries and Frameworks
    
    Special thanks to the creators and maintainers of Android Jetpack, Retrofit, LiveData, ViewModel, and Material Components which made the app structure scalable and interactive.


 - Skydoves Balloon & Feature Discovery Tools
    
    For enabling smooth user onboarding and interactive tip displays.


 - Free & Copyright-Free Resources
    
    Including music and icons sourced from trusted open platforms that enhanced the user experience.


 - Google Developers and Android Community
    
    For detailed documentation and forums that guided implementation of modern features like SplashScreen API, MediaPlayer integration, and MVVM architecture.



## 📎 Appendix

A. Technologies Used

| Technology | Description     |
| :-------- | :------- |
| Kotlin | Primary language for Android app development. |
| Android Jetpack (ViewModel, LiveData) | Architecture components used to manage UI-related data lifecycle-consciously. |
| Retrofit | Used for making HTTP requests to the OpenTDB API. |
| MediaPlayer | Used to play background music in the app. |
| SharedPreferences | For saving user preferences |
| Material Components | Used for modern UI elements like switches, dialogs, and bottom sheets. |
| Skydoves Balloon | For tooltips and onboarding/quick tips. |
| SplashScreen API | Implements animated splash screen on app launch (Android 12+ supported natively). |


B. API Reference

    Data is fetched from the Open Trivia Database API. See API reference for all available endpoints.


C. Terminology
| Term | Description     |
| :-------- | :------- |
| Quiz | A collection of questions fetched based on user preferences. |
| Category | 	A thematic grouping of questions (e.g., Science, Sports). |
| Coins | Virtual reward points earned by users based on quiz performance. |
| AI ChatBot | A helper tool (locked/unlocked via coins) that provides hints or answers during the quiz. |
| Quick Tips | Informational tooltips shown to users during app onboarding or feature use. |

D. Known Limitations
 - AI chatbot functionality depends on available coins.

 - Music may continue if the app is not properly removed from memory (requires service lifecycle management).

 - Requires internet connection to fetch questions.
## ✍️ Author

- Name: Pralay Prajapati
- Role: Android App Developer
- Project: Quizora – Quiz App with AI Assistance & Coin Reward System
- Email: pralayprajapati02@gmail.com
- GitHub: https://github.com/pralayprajapati02

“This project was developed as a personal initiative to blend engaging quiz gameplay with AI-powered support, aiming to enhance both fun and learning for users.”


## 🤝 Contributing

Contributions to this project are welcome and appreciated! Whether you’re a developer, designer, or a tester, there are many ways you can help improve the project.

**Ways to Contribute:**
- 🐛 Report Bugs: If you find any bugs or issues, feel free to open an issue.

- 🌟 Suggest Features: Have an idea to enhance the app? Open a discussion or feature request.

- 🧑‍💻 Improve Code: Fork the repository, make improvements, and submit a pull request.

- 📝 Improve Documentation: Help make the README or in-app guides better.

- 🎨 Design Feedback: Share feedback or suggestions on UI/UX.

**Contribution Guidelines:**

    1. Fork the repository
    2. Create a new branch (git checkout -b feature-branch)
    3. Commit your changes (git commit -m 'Add some feature')
    4. Push to the branch (git push origin feature-branch)
    5. Create a pull request

This project follows the Contributor Covenant for a welcoming and respectful open-source community.


## 💬 Feedback

If you have any feedback, suggestions, or encounter any bugs, feel free to reach out:

📧 Email: pralayprajapati02@gmail.com

🐞 GitHub Issues: [Submit here](https://github.com/pralayprajapati02/Quizora/issues)

We appreciate your thoughts and are always looking to improve the experience!




## 🚀About Me
I'm an Android Application Developer passionate about building smooth, functional, and user-friendly mobile experiences. I love turning ideas into real, interactive apps using Kotlin, Material Design, and modern Android architecture components. I'm always eager to explore new tools and best practices to make robust and scalable apps.

📱 Skills:

 - Kotlin, Java

 - Android Jetpack (ViewModel, LiveData, Room, Navigation)

 - Firebase, Retrofit

 - Material Design 3

 - XML

 - Git & GitHub

🌱 Currently learning advanced AI integrations in mobile apps.

🚀 Always open to collaboration and new challenges.


## 🔗 Links

[![Email](https://img.shields.io/badge/email-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:pralayprajapati02@gmai.com)

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/pralay-prajapati-6a4889265 )

[![twitter](https://img.shields.io/badge/twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://x.com/PralayPrajapati?t=T23Lc-Smw1ffMvZXnCQylA&s=09)


## 📛Badges

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)

[![Platform](https://img.shields.io/badge/platform-Android-blue.svg)](https://developer.android.com) 

[![UI Framework](https://img.shields.io/badge/UI-Material3-orange.svg)](https://m3.material.io/)

[![Language](https://img.shields.io/badge/Kotlin-7f52ff?logo=kotlin&logoColor=white)](https://kotlinlang.org/) 

[![API](https://img.shields.io/badge/API-OpenTDB-orange.svg)](https://opentdb.com/)

[![Gradle](https://img.shields.io/badge/Build-Gradle-blue.svg)](https://gradle.org/)
