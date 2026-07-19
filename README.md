# Aperio Photography App 📸

Aperio is a modern Android photography application built entirely in Kotlin. It allows users to organize their personal photos by custom categories and view albums through a sleek, social-media-inspired layout.

## ✨ Key Features
* **Categorized Storage:** Easily group, sort, and organize photos into distinct custom categories.
* **Instagram-Styled Albums:** View album collections through a rich, interactive details page inspired by modern social media layouts.
* **Sleek Layouts:** Clean grid views for browsing categories alongside a beautiful, immersive media presentation layer.
* **Built 100% in Kotlin:** Leverages modern Android development practices for fast performance and structural stability.

## 🛠️ Tech Stack & Architecture
* **Language:** [Kotlin](https://kotlinlang.org)
* **Build System:** Gradle (Kotlin DSL)
* **Minimum SDK:** Android 8.0 (API 26) or higher

## 🚀 Getting Started

### Prerequisites
Before running this project, ensure you have the following installed:
* [Android Studio](https://android.com) (Ladybug or newer)
* JDK 17 or higher

### Installation
1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com
   ```
2. Open Android Studio.
3. Select **File > Open** and choose the `S8116504Assignment2` project folder.
4. Allow Gradle to sync and build the project files automatically.
5. Run the application on an Android Emulator or a connected physical testing device.

## 📂 Core Project Components
* `/app/src/main/java` - Contains the Kotlin source files for managing categories and album detail logic.
* `/app/src/main/res` - Houses the XML/Compose layout files responsible for the Instagram-style user interface.
## ⚠️ Troubleshooting & Known Issues

### API Connection Error ("Unable to resolve host")
When first opening the app or clicking **'Enter Studio'**, you may see a red error message stating:
`Unable to resolve host 'nit3213api.onrender.com': No address associated with hostname`

#### Step 1: Wake up the Server
* **Why this happens:** The backend server is hosted on a free Render tier, which automatically spins down (goes to sleep) after periods of inactivity.
* **How to fix:** Simply wait **30 to 60 seconds** for the server to wake up, then press the **'Enter Studio'** button again. The app will connect cleanly once the server is active.

#### Step 2: Test Emulator Internet Connection (If Step 1 fails)
If you have waited and the error still won't disappear, the Android Virtual Device (AVD) may have lost its network path:
1. Minimize the app and open the **Chrome browser** inside your emulator.
2. Navigate to: `https://nit3213api.onrender.com/`
3. Check the behavior:
   * **If it loads a message or page:** The internet is working. Completely restart your assignment application and press **'Enter Studio'** again.
   * **If it fails to load:** The emulator itself has no network access. Wipe data or cold-boot your emulator inside Android Studio's Device Manager, check your computer's internet, and try again.
