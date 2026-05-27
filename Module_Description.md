# Module Description

The system is divided into three primary modules, each catering to a specific user role.

## 1. Admin Module
The Administrator is the superuser of the system with full control over master data.
*   **Login**: Secure authentication for the admin.
*   **Manage Departments**: Add, update, or delete departments (e.g., Computer Science, Mechanical).
*   **Manage Subjects**: Create subjects and assign them to specific departments and semesters.
*   **Manage Faculty**: Create faculty accounts, assign them to departments, and manage their credentials.
*   **Manage Students**: Register students, assign them to departments/semesters, and maintain their profile details.

## 2. Faculty Module
Faculty members are responsible for the academic assessment of students.
*   **Login**: Secure authentication using credentials provided by the admin.
*   **Subject Selection**: View subjects mapped to the faculty member.
*   **Enter Marks**: Input marks for various components:
    *   **CIA (Continuous Internal Assessment)**: Periodic test scores.
    *   **Assignments**: Marks for submitted assignments.
    *   **Lab/Practical**: Marks for practical sessions (if applicable).
*   **Auto-Calculation**: The system automatically calculates the total internal marks and determines the grade (e.g., Pass/Fail or Grade A/B/C) based on the input.
*   **View Reports**: View class-wise performance lists.

## 3. Student Module
Students have read-only access to their academic data.
*   **Login**: Secure authentication.
*   **View Marks**: Dashboard displaying a subject-wise breakdown of internal marks (CIA, Assignment, Lab).
*   **View Grades**: View calculated grades and total scores.
*   **Profile**: View personal and academic details.
