# Internal Marks Management System (IMMS)

**A Web-based Academic Management System built with Java, HTML, CSS, JS, and MySQL.**

## 📂 Project Structure

*   **/Documentation**: Contains the project Abstract, Module Descriptions, and Database Schema/ER explanations.
*   **/Database**: Contains the `schema.sql` file to set up the MySQL database.
*   **/WebContent**: Contains the Frontend UI (HTML, CSS, JS). Open `index.html` to view the design.
*   **/src**: Contains the Java Backend source code (Models, DAOs, Servlets).

## 🚀 How to Run the Project (Setup Guide)

Since this is a college project, standard Setup involves a Java Web Server (like Apache Tomcat) and a MySQL Database.

### Step 1: Database Setup
1.  Install **MySQL Server**.
2.  Open your MySQL Workbench or Command Line.
3.  Run the script located at `Database/schema.sql`. This will:
    *   Create the database `internal_marks_db`.
    *   Create all necessary tables (users, students, faculty, marks, etc.).
    *   Insert sample data (Admin user, dummy students, etc.).

### Step 2: Backend Configuration
1.  Open `src/com/ims/dao/DBConnection.java`.
2.  Update the `PASSWORD` field with your local MySQL root password.

### Step 3: Server Deployment (Eclipse/IntelliJ)
1.  Import this folder as a **Dynamic Web Project**.
2.  Add `mysql-connector-java.jar` (JDBC Driver) to your project's `lib` folder.
3.  Deploy the project on **Apache Tomcat 9.0+**.
4.  Run the server and access `http://localhost:8080/Internal_Marks_System`.

## 🖥 Frontend Demo (Quick View)
If you just want to view the **User Interface**, you can directly open the HTML files in your browser:
1.  Go to `WebContent/` folder.
2.  Open `index.html`.
3.  **Login Credentials (Mock)**:
    *   **Admin**: Select 'Administrator' role -> Click Login.
    *   **Faculty**: Select 'Faculty' role -> Click Login.
    *   **Student**: Select 'Student' role -> Click Login.
*(Note: The HTML files have simple JavaScript to simulate redirection for demonstration purposes. In the real deployed app, the LoginServlet handles this securely.)*

## 📚 Deliverables Included
*   **Abstract**: See `Documentation/Abstract.md`
*   **Modules**: See `Documentation/Module_Description.md`
*   **ER Diagram & Schema**: See `Documentation/Database_Schema_ER.md`
*   **Source Code**: Full HTML/CSS frontend and Java Backend logic samples.

## 👥 Roles & Features
*   **Admin**: Manage Departments, Subjects, and Users.
*   **Faculty**: Select Subjects and Enter Marks (Auto-calculated Total & Grade).
*   **Student**: View Subject-wise Marks and Grades.
