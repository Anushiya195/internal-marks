# Database Schema & ER Diagram

## Entity-Relationship (ER) Diagram Explanation

The database design follows a relational model connecting Users, Academic Structures, and Marks.

1.  **Departments**: The root entity. Each department (e.g., "CS", "IT") has a unique ID.
2.  **Subjects**: Linked to Departments. A subject belongs to one department.
3.  **Users (Admin, Faculty, Student)**:
    *   **Faculty**: Linked to a Department.
    *   **Student**: Linked to a Department.
4.  **Marks**: The core transactional entity. It links a **Student** and a **Subject**. It stores the score components (CIA, Assignment, Lab) and the calculated Total/Grade.

### Relationships:
*   **Department (1) ---- (M) Faculty**: One department has many faculty members.
*   **Department (1) ---- (M) Student**: One department has many students.
*   **Department (1) ---- (M) Subject**: One department has many subjects.
*   **Student (1) ---- (M) Marks**: One student has marks in many subjects.
*   **Subject (1) ---- (M) Marks**: One subject has marks for many students.

## Database Schema (SQL Structure)

### List of Tables:
1.  **departments**: Stores department details.
2.  **users**: Stores login credentials and roles (Admin, Faculty, Student).
3.  **subjects**: Stores subject names and codes.
4.  **students**: Stores student profiles, linked to users and departments.
5.  **faculty**: Stores faculty profiles, linked to users and departments.
6.  **marks**: Stores the internal marks for each student-subject pair.

*See `Database/schema.sql` for the actual SQL creation scripts.*
