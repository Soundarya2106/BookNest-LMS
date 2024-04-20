Library Management System



Overview:  
This is a simple Library Management System implemented using Java Swing for the user interface, MySQL database for data storage, and JFreeChart library for generating pie charts.

Features:    
1)User Authentication:   Allows users to log in as either students or staff members.    
2)Browse Catalog: Displays a list of available books in the library catalog.    
3)Check Out Books: Allows users to borrow books from the library.   
4)Return Books: Enables users to return books they have borrowed.    
5)View Browsing History: Shows the user's history of borrowed books.    
6)Renew Books: Allows users to extend the borrowing period for their books.     
7)View Account Information: Displays user account details and allows for editing.    
8)Change Password: Enables users to change their login password.    
9)Manage User Accounts: Staff members can manage user accounts, including creating, editing, and deleting accounts.    
10)Generate Reports: Staff members can generate reports, including statistical data about the library's inventory and borrowing trends.

Requirements:    
1)Java Development Kit (JDK)   
2)MySQL Server    
3)JFreeChart library (JAR files included in the project)

Setup:    
1)Database Setup: Create a MySQL database and create various tables such as studentsLogin, staffLogin, books, browsing_history to set store the data in the table.     
2)Project Configuration: Update the database connection settings in the Java code (Database.java) to match your MySQL database credentials.   
3)Library Dependencies: Make sure to include the JFreeChart library JAR files (jfreechart-1.5.3.jar and jcommon-1.0.23.jar) in your project's build path.   
4)Compile and Run: Compile the Java source files and run the application. Make sure the MySQL server is running before launching the application.

Usage:   
1)Launch the application and log in with your user credentials (either as a student or staff member).  
2)Explore the available features from the main menu.   
3)Perform actions such as browsing the catalog, checking out books, returning books, and managing user accounts as needed.

Credits:
JFreeChart: [Official Website](https://jfree.org/jfreechart/download.html)   
MySQL: [Official Website](https://www.mysql.com/)   
Java Swing: [Documentation](https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html)

License:
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

