# Reference Implementation: Shared File Access (SAF)

**Course:** Software Development for Portable Devices (CS 10)  
**Institution:** BITS Pilani - Work Integrated Learning Programs (WILP)  

---

## 📖 Overview
This project serves as the final demonstration for **Contact Session 10**, focusing on **Shared File Access**. In modern Android development, privacy is prioritized by moving away from direct file system scanning. Instead, applications use the **Storage Access Framework (SAF)** to let users choose exactly which files to share with the app.

## 🎓 Instructional Objectives
The goal of this demonstration is to teach students:
* **User-Driven Security:** The app only gains access to files explicitly selected by the user.
* **URI vs. Path:** Why modern apps use `content://` URIs instead of `/sdcard/` paths for shared data.
* **Modern API Contracts:** Utilizing the `ActivityResultLauncher` and `OpenDocument()` for lifecycle-safe file picking.

## 🏛️ Project Architecture
The demo focuses on the "User Selection" storage model:
* **System Intermediary:** The app requests a file type; the system handles the browsing.
* **URI Resolution:** Demonstrates reading file content via the `ContentResolver` and the provided URI.

## 🛠️ Usage for Students
Observe the `MainActivity.kt` to see:
1.  How to launch the system picker for specific MIME types (e.g., `text/plain`).
2.  The method for reading text content from a URI using `InputStream` and `bufferedReader`.
3.  The use of `.use {}` to ensure the app closes system resources automatically.

---

**Topic:** Working with the File System  
**Next Session:** CS 11 - Android Databases (SQLite)
