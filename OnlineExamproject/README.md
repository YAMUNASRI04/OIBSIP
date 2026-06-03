# 📋 Online Examination System — Java

<p align="center">
  <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/badge/Internship-Oasis%20Infobyte-blue?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Type-Console%20Application-green?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Status-Completed-brightgreen?style=for-the-badge" />
</p>

---

## 📌 About the Project

A **console-based Online Examination System** built using Core Java as part of the **Oasis Infobyte Java Development Internship (Task 4)**.

The system allows students to log in, update their profile, attempt a timed MCQ exam, and logout securely. The exam auto-submits when the timer runs out.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔐 Login System | Username and password authentication with 3-attempt lockout |
| 👤 Update Profile | Update full name, email address, and password |
| 📝 MCQ Exam | 10 Java-based multiple choice questions |
| ⏱️ Timer | 120-second countdown with auto-submit |
| 🏆 Score & Grade | Score calculated with percentage and grade |
| 🔓 Logout | Secure session close and logout |
| ✅ Input Validation | Handles all invalid inputs gracefully |

---

## 🖥️ Screenshots

### 🏠 Home Page
![Home Page](images/01_Home_Page.png)

### 🔐 Login Page
![Login Page](images/02_Login_Page.png)

### ✅ Login Success
![Login Success](images/03_Login_Success.png)

### 📊 Dashboard Menu
![Dashboard Menu](images/04_Dashboard_Menu.png)

### 👤 Profile Update
![Profile Update](images/05_Profile_Update.png)

### 📧 Email Update
![Email Update](images/05_2_Email_UPdate.png)

### 🔑 Password Change
![Password Change](images/05_3_Password_Change.png)

### 🚀 Start Exam
![Start Exam](images/05_Start_Exam.png)

### 📋 Exam Instructions & Q1
![Exam Instructions](images/06_Exam_InstructionsQ1.png)

### 📝 Questions 2–3
![Questions 2 to 3](images/06_Exam_Q2_Q3.png)

### 📝 Questions 4–6
![Questions 4 to 6](images/06_Exam_Q4_Q6.png)

### 📝 Questions 7–9
![Questions 7 to 9](images/06_Exam_Q7_Q9.png)

### 📝 Question 10
![Question 10](images/06_Exam_Q10.png)

### 🏆 Exam Result
![Exam Result](images/07_Exam_Result.png)

### 🔓 Logout
![Logout](images/08_LogOut.png)

---

## 📁 Project Structure
OnlineExamproject/
├── Main.java                  ← Entry point
├── User.java                  ← User data model
├── Question.java              ← MCQ question model
├── LoginSystem.java           ← Login with attempt lockout
├── Dashboard.java             ← Main menu after login
├── ProfileUpdate.java         ← Update name, email, password
├── ExamSystem.java            ← Timer + MCQs + auto-submit + result
├── images/                    ← Output screenshots
│   ├── 01_Home_Page.png
│   ├── 02_Login_Page.png
│   ├── 03_Login_Success.png
│   ├── 04_Dashboard_Menu.png
│   ├── 05_Profile_Update.png
│   ├── 05_1_Profile_Update.png
│   ├── 05_2_Email_UPdate.png
│   ├── 05_3_Password_Change.png
│   ├── 05_Start_Exam.png
│   ├── 06_Exam_InstructionsQ1.png
│   ├── 06_Exam_Q2_Q3.png
│   ├── 06_Exam_Q4_Q6.png
│   ├── 06_Exam_Q7_Q9.png
│   ├── 06_Exam_Q10.png
│   ├── 07_Exam_Result.png
│   └── 08_LogOut.png
└── README.md

---

## ⚙️ How to Run

### Prerequisites
- Java JDK 8 or higher installed
- VS Code or any Java IDE

### Steps

```bash
# Step 1: Clone the repository
git clone https://github.com/YAMUNASRI04/OIBSIP.git

# Step 2: Navigate to the project folder
cd OIBSIP/OnlineExamproject

# Step 3: Compile all Java files
javac *.java

# Step 4: Run the program
java Main
```

---

## 🔑 Demo Login Credentials

| Username | Password |
|---|---|
| yamuni | pass123 |
| admin | admin123 |
| student | stu456 |

---

## 🎮 How to Use

Run the program → Welcome banner appears
Enter username and password to login
Choose from the dashboard menu:
1 → Update Profile & Password
2 → Start Exam
3 → Logout
In Profile Update:
→ Change your name, email, or password
In Exam:
→ Read instructions → Press ENTER to start
→ Answer 10 MCQ questions (enter 1, 2, 3, or 4)
→ Timer runs for 120 seconds
→ Exam auto-submits if time runs out
→ View your score, grade, and answer review
Logout to close your session safely


---

## ⏱️ Exam Details

| Detail | Info |
|---|---|
| Total Questions | 10 |
| Marks per Question | 10 |
| Total Marks | 100 |
| Time Limit | 120 seconds |
| Passing Marks | 40% |
| Negative Marking | None |

---

## 🏆 Grading System

| Percentage | Grade |
|---|---|
| 90% and above | A+ (Outstanding) |
| 80% – 89% | A (Excellent) |
| 70% – 79% | B (Good) |
| 60% – 69% | C (Average) |
| 40% – 59% | D (Pass) |
| Below 40% | F (Fail) |

---

## 📊 Sample Output
╔══════════════════════════════════════════════╗
║       ONLINE EXAMINATION SYSTEM  📋          ║
║        Oasis Infobyte — Java Task 4          ║
╚══════════════════════════════════════════════╝
Enter Username : yamuni
Enter Password : pass123
✅ Login successful! Welcome, Yamuni!
╔══════════════════════════════════════════════╗
║  👤  Logged in as: Yamuni                    ║
╠══════════════════════════════════════════════╣
║  1.  Update Profile & Password               ║
║  2.  Start Exam                              ║
║  3.  Logout                                  ║
╚══════════════════════════════════════════════╝
── Q1 of 10: Which keyword creates an object in Java?
1. create   2. new   3. object   4. make
Your answer (1-4): 2
✅ Answer recorded.
╔══════════════════════════════════════════════╗
║               EXAM  RESULT                   ║
╠══════════════════════════════════════════════╣
║  Score      : 90 / 100                       ║
║  Percentage : 90%                            ║
║  Grade      : A+ (Outstanding)               ║
║  Result     : ✅ PASS                        ║
╚══════════════════════════════════════════════╝

---

## 🧑‍💻 Java Concepts Used

- `HashMap` — in-memory user database
- `Scanner` class — user input handling
- `Thread` — background countdown timer
- `AtomicBoolean` — thread-safe timer flag
- `try-catch` — input validation
- `switch` statement — menu navigation
- `ArrayList` / Arrays — question bank
- OOP — separate classes for each module
- Methods — modular, clean code structure

---

## 👩‍💻 Author

**Yamuni**
- 🌐 GitHub: [@YAMUNASRI04](https://github.com/YAMUNASRI04)
- 💼 Internship: Oasis Infobyte — Java Development

---

## 🏢 Internship Details

| Detail | Info |
|---|---|
| Organization | Oasis Infobyte |
| Domain | Java Development |
| Task | Task 4 — Online Examination System |
| Repository | OIBSIP |

---

## 📄 License

This project was developed as part of an internship program at **Oasis Infobyte**.
---
<p align="center">Made with ❤️ by Yamuni | Oasis Infobyte Java Internship</p>
