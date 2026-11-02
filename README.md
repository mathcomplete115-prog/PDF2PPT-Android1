# PDF to PPT Android app

## Build APK
1. Install Android Studio (latest stable) on a computer.
2. Open this folder in Android Studio.
3. Allow Gradle sync and install the Android SDK platform 35 if prompted.
4. Choose **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
5. Android Studio creates the APK under `app/build/outputs/apk/debug/`.

## Features
- Original Design: renders each PDF page as a full-slide image.
- Editable Text: extracts selectable PDF text into editable PowerPoint text boxes.
- Both: creates two PPTX files.
- PDF selection uses Android's document picker.

## Important limitations
- This is an Android Studio source project, not a precompiled APK.
- Conversion libraries load from CDNs, so the phone needs internet access.
- Scanned PDFs are not OCR processed. Editable mode may need manual layout cleanup.
- PPTX files are saved in the app-specific Downloads directory. Android may hide this folder from ordinary gallery/file views; use a file manager or app storage browser.
- The project has not been built or device-tested in this environment.

## Build APK using only an Android phone (GitHub Actions)
1. Extract this ZIP on your phone.
2. Create a free repository on GitHub in Chrome (enable Desktop site if needed).
3. Upload the extracted project files and folders to the repository root (including the hidden `.github/workflows/build.yml` folder/file).
4. Open the repository's **Actions** tab and select **Build Android APK**. Tap **Run workflow** if it did not start automatically.
5. When the run finishes successfully, open the run, find **Artifacts**, and download `PDF2PPT-debug-APK`. Extract it and install `app-debug.apk`.
6. If Android blocks installation, allow your browser/file manager to install unknown apps for this one install.

You need a free GitHub account and an internet connection. This creates a debug APK, not a Play Store release.
