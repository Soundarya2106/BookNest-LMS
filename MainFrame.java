package lib;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.*;
import org.jfree.util.PublicCloneable;

public class MainFrame{
    private static final String url = "jdbc:mysql://localhost:3306/LMS";
    private static final String user = "root";
    private static final String pass = "your password";
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                showGUI();
            }
        });
    }
    
    private static void showGUI(){
        JFrame loginFrame = new JFrame("Library Management System");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(1500,700);
        loginFrame.setLocationRelativeTo(null);
        
        JLabel headingLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Algerian", Font.BOLD, 50));
        
        JLabel typeLabel = new JLabel("Select User Type:", SwingConstants.CENTER);
        typeLabel.setFont(new Font("Bookman Old Style",Font.BOLD, 30));
        
        JButton student = new JButton("Student Login");
        JButton staff = new JButton("Staff Login");
        
        student.setPreferredSize(new Dimension(300, 50));
        student.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
        staff.setPreferredSize(new Dimension(300, 50)); 
        staff.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
        
        Container contentPane = loginFrame.getContentPane();
        contentPane.setLayout(new GridLayout(4,1));
        contentPane.add(headingLabel, BorderLayout.NORTH);
        contentPane.add(typeLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(student);
        buttonPanel.add(staff);
        
        contentPane.add(buttonPanel);
        student.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                loginFrame.dispose();
                studentLogin();
            }
        });
        
        staff.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                loginFrame.dispose();
                staffLogin();
            }
        });
        
        loginFrame.setVisible(true);
    }
    
    private static void studentLogin(){
        JFrame studentLoginFrame = new JFrame("Student Login");
        studentLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentLoginFrame.setSize(1500,700);
        studentLoginFrame.setLocationRelativeTo(null);
        
        JLabel headingLabel = new JLabel("Student Login", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 30));
        
        JLabel regnoLabel = new JLabel("Register Number:");
        regnoLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JTextField regnoTextField = new JTextField(20);
        regnoTextField.setPreferredSize(new Dimension(300, 50));
        regnoTextField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(300, 50));
        passwordField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        
        loginButton.setPreferredSize(new Dimension(300, 50));
        loginButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300, 50)); 
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JPanel headingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headingPanel.add(headingLabel);
        
        JPanel textFieldPanel = new JPanel(); // Panel to hold text fields
        textFieldPanel.setLayout(new GridLayout(2, 1)); // Set layout for text fields
    
        JPanel regnoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel for register number
        regnoPanel.add(regnoLabel);
        regnoPanel.add(regnoTextField);
    
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel for password
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
    
        textFieldPanel.add(regnoPanel); // Add register number panel
        textFieldPanel.add(passwordPanel);
        
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        buttonPanel.add(loginButton);
        
        Container contentPane = studentLoginFrame.getContentPane();
        contentPane.setLayout(new GridLayout(4,0));
        contentPane.add(headingPanel);
        contentPane.add(textFieldPanel);
        contentPane.add(buttonPanel);
        
        loginButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String regno = regnoTextField.getText();
                String password = new String(passwordField.getPassword());
                
                try(Connection con = DriverManager.getConnection(url, user, pass)){
                    String query = "SELECT * FROM studentsLogin WHERE RegNo = ? AND password = ?";
                    PreparedStatement statement = con.prepareStatement(query);
                    statement.setString(1, regno);
                    statement.setString(2, password);
                    ResultSet rs = statement.executeQuery();

                    if (rs.next()) {
                        studentLoginFrame.dispose();
                        ShowStudentOptionsPage();
                    // Proceed with student functionality
                    } else {
                        JOptionPane.showMessageDialog(studentLoginFrame, "Invalid Register Number or Password. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        backButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                studentLoginFrame.dispose();
                showGUI();
            }
        });
        
        studentLoginFrame.setVisible(true);
    }
    
     private static void staffLogin(){
        JFrame staffLoginFrame = new JFrame("Staff Login");
        staffLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        staffLoginFrame.setSize(1500,700);
        staffLoginFrame.setLocationRelativeTo(null);
        
        JLabel headingLabel = new JLabel("Staff Login", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 30));
        
        JLabel staffIdLabel = new JLabel("Staff ID:");
        staffIdLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JTextField staffIDTextField = new JTextField(20);
        staffIDTextField.setPreferredSize(new Dimension(300,50));
        staffIDTextField.setFont(new Font("Bookman Old Style", Font.PLAIN,20));
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(300,50));
        passwordField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        
        loginButton.setPreferredSize(new Dimension(300, 50));
        loginButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300, 50)); 
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JPanel headingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headingPanel.add(headingLabel);
        
        JPanel textFieldPanel = new JPanel(); 
        textFieldPanel.setLayout(new GridLayout(2, 1)); // Set layout for text fields
    
        JPanel staffidPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel for register number
        staffidPanel.add(staffIdLabel);
        staffidPanel.add(staffIDTextField);
    
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel for password
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
    
        textFieldPanel.add(staffidPanel); // Add register number panel
        textFieldPanel.add(passwordPanel);
        
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        buttonPanel.add(loginButton);
        
        Container contentPane = staffLoginFrame.getContentPane();
        contentPane.setLayout(new GridLayout(4,0));
        contentPane.add(headingPanel);
        contentPane.add(textFieldPanel);
        contentPane.add(buttonPanel);
        
        loginButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String staffID = staffIDTextField.getText();
                String password = new String(passwordField.getPassword());
                
                try(Connection con = DriverManager.getConnection(url, user, pass)){
                    String query = "SELECT * FROM staffLogin WHERE staffID = ? AND password = ?";
                    PreparedStatement statement = con.prepareStatement(query);
                    statement.setString(1, staffID);
                    statement.setString(2, password);
                    ResultSet rs = statement.executeQuery();

                    if (rs.next()) {
                        staffLoginFrame.dispose();
                        showStaffOptionsPage();
                    } else {
                        JOptionPane.showMessageDialog(staffLoginFrame, "Invalid Staff ID or Password. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        backButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                staffLoginFrame.dispose();
                showGUI();
            }
        });
        
        staffLoginFrame.setVisible(true);
    }
     
     
    private static void ShowStudentOptionsPage() {
        JFrame optionsFrame = new JFrame("Student Options");
        optionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionsFrame.setSize(1500, 700);
        optionsFrame.setLocationRelativeTo(null); // Center the frame on the screen

        Container contentPane = optionsFrame.getContentPane();
        contentPane.setLayout(new GridLayout(5,0, 30, 10));

        JLabel headingLabel = new JLabel("Student Options", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
        
        JPanel headingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headingPanel.add(headingLabel);
        
        JButton browseCatalogButton = new JButton("Browse Catalog");
        JButton checkOutBooksButton = new JButton("Check Out Books");
        JButton returnBooksButton = new JButton("Return Books");
        JButton renewBooksButton = new JButton("Renew Books");
        JButton viewBorrowingHistoryButton = new JButton("View Borrowing History");
        JButton changePasswordButton = new JButton("Change Password");
        JButton logoutButton = new JButton("Logout");
        
        browseCatalogButton.setPreferredSize(new Dimension(300, 50));
        browseCatalogButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        checkOutBooksButton.setPreferredSize(new Dimension(300, 50)); 
        checkOutBooksButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        returnBooksButton.setPreferredSize(new Dimension(300, 50));
        returnBooksButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        renewBooksButton.setPreferredSize(new Dimension(300, 50)); 
        renewBooksButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        viewBorrowingHistoryButton.setPreferredSize(new Dimension(300, 50));
        viewBorrowingHistoryButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        changePasswordButton.setPreferredSize(new Dimension(300, 50)); 
        changePasswordButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        logoutButton.setPreferredSize(new Dimension(300, 50));
        logoutButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JPanel but1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but1.add(browseCatalogButton);
        but1.add(checkOutBooksButton);
        
        JPanel but2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but2.add(returnBooksButton);
        but2.add(renewBooksButton);
        
        JPanel but3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but3.add(viewBorrowingHistoryButton);
        but3.add(changePasswordButton);
        
        JPanel but4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but4.add(logoutButton);

        contentPane.add(headingPanel);
        contentPane.add(but1);
        contentPane.add(but2);
        contentPane.add(but3);
        contentPane.add(but4);

        browseCatalogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsFrame.dispose();
                ShowBrowseCatalog();
            }
        });

        checkOutBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsFrame.dispose();
                checkoutBooks();
            }
        });
        
        returnBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsFrame.dispose();
                returnBooks();
            }
        });
        
        renewBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsFrame.dispose();
                renewBooks();
            }
        });

        viewBorrowingHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsFrame.dispose();
                BrowsingHistory();
            }
        });
        
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsFrame.dispose();
                StuChangePassword();
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsFrame.dispose();
                showGUI();
            }
        });

        optionsFrame.setVisible(true);
    }
    
    private static void showStaffOptionsPage(){
        JFrame staffOptionsFrame = new JFrame("Staff Options");
        staffOptionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        staffOptionsFrame.setSize(1500, 700);
        staffOptionsFrame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Staff Options", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));

        JButton addEditDeleteBooksButton = new JButton("Add/Edit/Delete Books");
        addEditDeleteBooksButton.addActionListener(e -> {
            staffOptionsFrame.dispose();
            AddEditDeleteBooks();
        });

        JButton manageUserAccountsButton = new JButton("Manage User Accounts");
        manageUserAccountsButton.addActionListener(e -> {
            staffOptionsFrame.dispose();
            ManageUsers();
        });

        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(e -> {
            staffOptionsFrame.dispose();
            generateReport();
        });

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(e -> {
            staffOptionsFrame.dispose();
            StaChangePassword();
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            staffOptionsFrame.dispose(); // Close the staff options window
            showGUI(); // Display the login screen again
        });
        
        addEditDeleteBooksButton.setPreferredSize(new Dimension(300, 50));
        addEditDeleteBooksButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        manageUserAccountsButton.setPreferredSize(new Dimension(300, 50)); 
        manageUserAccountsButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        generateReportButton.setPreferredSize(new Dimension(300, 50));
        generateReportButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        changePasswordButton.setPreferredSize(new Dimension(300, 50)); 
        changePasswordButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        logoutButton.setPreferredSize(new Dimension(300, 50));
        logoutButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        
        JPanel but1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but1.add(addEditDeleteBooksButton);
        but1.add(manageUserAccountsButton);
        
        JPanel but2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but2.add(generateReportButton);
        but2.add(changePasswordButton);
        
        JPanel but3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but3.add(logoutButton);
        
        Container contentPane = staffOptionsFrame.getContentPane();
        contentPane.setLayout(new GridLayout(4, 0, 10, 10));
        contentPane.add(titlePanel);
        contentPane.add(but1);
        contentPane.add(but2);
        contentPane.add(but3);

        staffOptionsFrame.setVisible(true);
    }
    
    private static void ShowBrowseCatalog() {
        JFrame browseCatalogFrame = new JFrame("Browse Catalog");
        browseCatalogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        browseCatalogFrame.setSize(1500, 700);
        browseCatalogFrame.setLocationRelativeTo(null); // Center the frame on the screen

        JLabel headingLabel = new JLabel("Available Books", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
        
        JPanel headingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headingPanel.add(headingLabel);
        
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Book ID");
        tableModel.addColumn("Book Title");
        tableModel.addColumn("Book Author");
        tableModel.addColumn("Book Genre");
        tableModel.addColumn("Book Status");

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for table content
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16)); // Set font for table header
        table.setRowHeight(30); // Set row height 
        table.setDefaultRenderer(Object.class, new CenteredTableCellRenderer());
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Set preferred column width for Book ID
        table.getColumnModel().getColumn(1).setPreferredWidth(250); // Set preferred column width for Book Title
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Set preferred column width for Book Author
        table.getColumnModel().getColumn(3).setPreferredWidth(150); // Set preferred column width for Book Genre
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1200,400));
        
        JPanel jtable = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jtable.add(scrollPane);

        Container contentPane = browseCatalogFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(headingPanel, BorderLayout.NORTH);
        contentPane.add(jtable, BorderLayout.CENTER);

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT * FROM books";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("bookID");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                String status = rs.getString("status");
                tableModel.addRow(new Object[]{id, title, author, genre, status});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(browseCatalogFrame, "Failed to retrieve book data from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(300, 50)); 
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseCatalogFrame.dispose();
                ShowStudentOptionsPage();
            }
        });
        
        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        contentPane.add(but, BorderLayout.SOUTH);

        browseCatalogFrame.setVisible(true);
    }
    
    private static void checkoutBooks(){
        JFrame checkOutFrame = new JFrame("Check Out Book");
        checkOutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        checkOutFrame.setSize(1200, 700);
        checkOutFrame.setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel("Check Out Books", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));

        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel bookTitleLabel = new JLabel("Book Title:");
        bookTitleLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel bookAuthorLabel = new JLabel("Book Author:");
        bookAuthorLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel bookGenreLabel = new JLabel("Book Genre:");
        bookGenreLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));

        JComboBox<Integer> bookIdComboBox = new JComboBox<>();
        bookIdComboBox.setPreferredSize(new Dimension(300, 50));
        bookIdComboBox.setFont(new Font("Bookman Old Style", Font.PLAIN, 16));
        JComboBox<String> bookTitleComboBox = new JComboBox<>();
        bookTitleComboBox.setPreferredSize(new Dimension(300, 50));
        bookTitleComboBox.setFont(new Font("Bookman Old Style", Font.PLAIN, 16));
        JComboBox<String> bookAuthorComboBox = new JComboBox<>();
        bookAuthorComboBox.setPreferredSize(new Dimension(300, 50));
        bookAuthorComboBox.setFont(new Font("Bookman Old Style", Font.PLAIN, 16));
        JComboBox<String> bookGenreComboBox = new JComboBox<>();
        bookGenreComboBox.setPreferredSize(new Dimension(300, 50));
        bookGenreComboBox.setFont(new Font("Bookman Old Style", Font.PLAIN, 16));

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT * FROM books where status = 'Available'";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            System.out.println(rs);
            while (rs.next()) {
                int bookId = rs.getInt("bookID");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");

                bookIdComboBox.addItem(bookId);
                bookTitleComboBox.addItem(title);
                bookAuthorComboBox.addItem(author);
                bookGenreComboBox.addItem(genre);
            }  
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(checkOutFrame, "Failed to retrieve book data from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JButton checkOutButton = new JButton("Check Out");
        JButton backButton = new JButton("Back");

        checkOutButton.setPreferredSize(new Dimension(300,50));
        checkOutButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JPanel headingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headingPanel.add(headingLabel);
        
        JPanel text1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text1.add(bookIdLabel);
        text1.add(bookIdComboBox);
        
        JPanel text2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text2.add(bookTitleLabel);
        text2.add(bookTitleComboBox);
        
        JPanel text3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text3.add(bookAuthorLabel);
        text3.add(bookAuthorComboBox);
        
        JPanel text4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text4.add(bookGenreLabel);
        text4.add(bookGenreComboBox);
        
        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        but.add(checkOutButton);
        
        
        checkOutButton.addActionListener(e -> {
            int selectedBookId = (int)bookIdComboBox.getSelectedItem();
            if (isBookAvailable(selectedBookId)) {
                JOptionPane.showMessageDialog(checkOutFrame, "Check Out Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateBookStatus(selectedBookId);
            } else {
                JOptionPane.showMessageDialog(checkOutFrame, "Book is not available.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
         
        backButton.addActionListener(e -> {
            checkOutFrame.dispose();
            ShowStudentOptionsPage();
        });
        
        Container contentPane = checkOutFrame.getContentPane();
        contentPane.setLayout(new GridLayout(0,1,10,10));
        contentPane.add(headingPanel);
        contentPane.add(text1);
        contentPane.add(text2);
        contentPane.add(text3);
        contentPane.add(text4);
        contentPane.add(but);
        
        checkOutFrame.setVisible(true);
    }
    
    private static boolean isBookAvailable(int bookID){
        boolean available = false;
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "SELECT status FROM books WHERE bookID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, bookID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");
                if (status.equals("Available")) {
                    available = true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return available;
    }
    
    
    private static void updateBookStatus(int bookID){
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String updateQuery = "UPDATE books SET status = ?,due_date = DATE_ADD(NOW(), INTERVAL 7 DAY) WHERE bookID = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, "Not Available");
            updateStatement.setInt(2, bookID);
             
            String insertQuery = "INSERT INTO history (bookID, action, actionDate) VALUES (?, ?, NOW())";
            PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
            insertStatement.setInt(1, bookID);
            insertStatement.setString(2, "Checked Out");
            insertStatement.executeUpdate();
            
            int rowsUpdated = updateStatement.executeUpdate();
            System.out.println(rowsUpdated);
            if (rowsUpdated > 0) {
                System.out.println("Book status updated successfully.");
            } else {
                System.out.println("Failed to update book status.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void returnBooks(){
        JFrame returnFrame = new JFrame("Return Book");
        returnFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        returnFrame.setSize(1200, 700);
        returnFrame.setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel("Return Books", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 50));

        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JComboBox<String> bookIdComboBox = new JComboBox<>();
        bookIdComboBox.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        bookIdComboBox.setPreferredSize(new Dimension(300,50));

        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "SELECT bookID FROM books WHERE status = 'Not Available'";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String bookId = rs.getString("bookID");
                bookIdComboBox.addItem(bookId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(returnFrame, "Failed to retrieve book data from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JButton returnButton = new JButton("Return");
        JButton backButton = new JButton("Back");

        backButton.setPreferredSize(new Dimension(300,50));
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        returnButton.setPreferredSize(new Dimension(300,50));
        returnButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JPanel headingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headingPanel.add(headingLabel);
        
        JPanel text = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text.add(bookIdLabel);
        text.add(bookIdComboBox);
        
        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        but.add(returnButton);
        
        returnButton.addActionListener(e -> {
            String selectedBookId = (String) bookIdComboBox.getSelectedItem();
            returnTheBook(Integer.parseInt(selectedBookId));
            JOptionPane.showMessageDialog(returnFrame, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        backButton.addActionListener(e -> {
            returnFrame.dispose();
            ShowStudentOptionsPage();
        });

        Container contentPane = returnFrame.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1,10,10));
        contentPane.add(headingPanel);
        contentPane.add(text);
        contentPane.add(but);
        
        returnFrame.setVisible(true);
    }
    
    private static void returnTheBook(int bookID){
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String updateQuery = "UPDATE books SET status = ?, due_date = DATE_ADD(NOW(), INTERVAL 7 DAY) WHERE bookID = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, "Available");
            updateStatement.setInt(2, bookID);

            String insertQuery = "INSERT INTO history (bookID, action, actionDate) VALUES (?, ?, NOW())";
            PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
            insertStatement.setInt(1, bookID);
            insertStatement.setString(2, "Returned");
            insertStatement.executeUpdate();
            
            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Failed to return book.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static class CenteredTableCellRenderer extends DefaultTableCellRenderer {
        public CenteredTableCellRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
    
    private static void BrowsingHistory(){
        JFrame historyFrame = new JFrame("View Browsing History");
        historyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        historyFrame.setSize(1200, 700);
        historyFrame.setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel("Browsing History", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
        
        JPanel headingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headingPanel.add(headingLabel);
        
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Book ID");
        tableModel.addColumn("Action");
        tableModel.addColumn("Date");

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for table content
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16)); // Set font for table header
        table.setRowHeight(30); // Set row height 
        table.setDefaultRenderer(Object.class, new CenteredTableCellRenderer());
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Set preferred column width for Book ID
        table.getColumnModel().getColumn(1).setPreferredWidth(250); // Set preferred column width for Book Title
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel jtable = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jtable.add(scrollPane);

        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "SELECT bookID, action, actionDate FROM history";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String bookId = rs.getString("bookID");
                String action = rs.getString("action");
                String date = rs.getString("actionDate");
                tableModel.addRow(new Object[]{bookId, action, date});
           }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(historyFrame, "Failed to retrieve browsing history from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        
        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        
        backButton.addActionListener(e -> {
            historyFrame.dispose();
            ShowStudentOptionsPage();
        });
        
        Container contentPane = historyFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(headingPanel, BorderLayout.NORTH);
        contentPane.add(jtable, BorderLayout.CENTER);
        contentPane.add(but, BorderLayout.SOUTH);
        
        historyFrame.setVisible(true);
    }
    
    private static void renewBooks(){
        JFrame renewFrame = new JFrame("Renew Book");
        renewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        renewFrame.setSize(1200, 700);
        renewFrame.setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel("Renew Books");
        headingLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(headingLabel);
        
        JLabel titleLabel = new JLabel("Select Book to Renew:");
        titleLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
            
        JComboBox<String> bookComboBox = new JComboBox<>();
        bookComboBox.setFont(new Font("Bookman Old Style", Font.PLAIN, 16));
        bookComboBox.setPreferredSize(new Dimension(300,50));
        
        JPanel text = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text.add(titleLabel);
        text.add(bookComboBox);
        
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "SELECT title FROM books WHERE status = 'Not Available'";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String title = rs.getString("title");
            bookComboBox.addItem(title);
        }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(renewFrame, "Failed to retrieve books from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JButton renewButton = new JButton("Renew");
        renewButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        renewButton.setPreferredSize(new Dimension(300,50));
        
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        
        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        but.add(renewButton);
        
        backButton.addActionListener(e -> {
            renewFrame.dispose();
            ShowStudentOptionsPage();
        });
        
        
        renewButton.addActionListener(e -> {
            String selectedBook = (String) bookComboBox.getSelectedItem();
            if (selectedBook != null) {
                renewSelectedBooks(selectedBook);
            } else {
                JOptionPane.showMessageDialog(renewFrame, "Please select a book to renew.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        Container contentPane = renewFrame.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1));
        contentPane.add(titlePanel);
        contentPane.add(text);
        contentPane.add(but);

        renewFrame.setVisible(true);
    }
    
    private static void renewSelectedBooks(String title){
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String updateQuery = "UPDATE books SET due_date = DATE_ADD(due_date, INTERVAL 7 DAY) WHERE title = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, title);
            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Book renewed successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to renew the book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while renewing the book.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static void StuChangePassword(){
        JFrame passwordFrame = new JFrame("Change Password");
        passwordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        passwordFrame.setSize(1200, 700);
        passwordFrame.setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel("Change Password");
        headingLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(headingLabel);
        
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        oldPasswordLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel confirmNewPasswordLabel = new JLabel("Confirm New Password:");
        confirmNewPasswordLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));

        JPasswordField oldPasswordField = new JPasswordField();
        oldPasswordField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        oldPasswordField.setPreferredSize(new Dimension(300,50));
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        newPasswordField.setPreferredSize(new Dimension(300,50));
        JPasswordField confirmNewPasswordField = new JPasswordField();
        confirmNewPasswordField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        confirmNewPasswordField.setPreferredSize(new Dimension(300,50));

        JButton updateButton = new JButton("Update");
        
        updateButton.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmNewPassword = new String(confirmNewPasswordField.getPassword());

            if (!newPassword.equals(confirmNewPassword)) {
                JOptionPane.showMessageDialog(passwordFrame, "New password and confirm new password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            StuUpdatePassword(oldPassword, newPassword);
        
            oldPasswordField.setText("");
            newPasswordField.setText("");
            confirmNewPasswordField.setText("");

            JOptionPane.showMessageDialog(passwordFrame, "Password updated successfully.");
        });
        JButton backButton = new JButton("Back");
        
        updateButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        updateButton.setPreferredSize(new Dimension(300,50));
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        
        JPanel text1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text1.add(oldPasswordLabel);
        text1.add(oldPasswordField);
        
        JPanel text2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text2.add(newPasswordLabel);
        text2.add(newPasswordField);
        
        JPanel text3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text3.add(confirmNewPasswordLabel);
        text3.add(confirmNewPasswordField);
        
        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        but.add(updateButton);
        
        Container contentPane = passwordFrame.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1));
        contentPane.add(titlePanel);
        contentPane.add(text1);
        contentPane.add(text2);
        contentPane.add(text3);
        contentPane.add(but);

        backButton.addActionListener(e -> {
            passwordFrame.dispose();
            ShowStudentOptionsPage();
        });
        passwordFrame.setVisible(true);
    }
    
    private static void StuUpdatePassword(String oldPassword, String newPassword){
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "SELECT regno FROM studentsLogin WHERE password = ?";
            PreparedStatement userIdStatement = conn.prepareStatement(query);
            userIdStatement.setString(1, oldPassword);
            ResultSet userIdResult = userIdStatement.executeQuery();

            if (userIdResult.next()) {
                String userId = userIdResult.getString("regno");
        
                String updateQuery = "UPDATE studentsLogin SET password = ? WHERE regno = ?";
                PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
                updateStatement.setString(1, newPassword);
                updateStatement.setString(2, userId);
            
            
                int rowsUpdated = updateStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Password updated successfully.");
                }
            } else {
                System.out.println("Failed to update password.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void StaChangePassword(){
        JFrame passwordFrame = new JFrame("Change Password");
        passwordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        passwordFrame.setSize(1200, 700);
        passwordFrame.setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel("Change Password");
        headingLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(headingLabel);
        
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        oldPasswordLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel confirmNewPasswordLabel = new JLabel("Confirm New Password:");
        confirmNewPasswordLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));

        JPasswordField oldPasswordField = new JPasswordField();
        oldPasswordField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        oldPasswordField.setPreferredSize(new Dimension(300,50));
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        newPasswordField.setPreferredSize(new Dimension(300,50));
        JPasswordField confirmNewPasswordField = new JPasswordField();
        confirmNewPasswordField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        confirmNewPasswordField.setPreferredSize(new Dimension(300,50));

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmNewPassword = new String(confirmNewPasswordField.getPassword());

            if (!newPassword.equals(confirmNewPassword)) {
                JOptionPane.showMessageDialog(passwordFrame, "New password and confirm new password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            StaUpdatePassword(oldPassword, newPassword);
        
            oldPasswordField.setText("");
            newPasswordField.setText("");
            confirmNewPasswordField.setText("");

            JOptionPane.showMessageDialog(passwordFrame, "Password updated successfully.");
        });

        JButton backButton = new JButton("Back");
        
        updateButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        updateButton.setPreferredSize(new Dimension(300,50));
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        
        JPanel text1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text1.add(oldPasswordLabel);
        text1.add(oldPasswordField);
        
        JPanel text2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text2.add(newPasswordLabel);
        text2.add(newPasswordField);
        
        JPanel text3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text3.add(confirmNewPasswordLabel);
        text3.add(confirmNewPasswordField);
        
        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        but.add(updateButton);
        
        Container contentPane = passwordFrame.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1));
        contentPane.add(titlePanel);
        contentPane.add(text1);
        contentPane.add(text2);
        contentPane.add(text3);
        contentPane.add(but);
        backButton.addActionListener(e -> {
            passwordFrame.dispose();
            showStaffOptionsPage();
        });
        passwordFrame.setVisible(true);
    }
    
    private static void StaUpdatePassword(String oldPassword, String newPassword){
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "SELECT staffID FROM staffLogin WHERE password = ?";
            PreparedStatement userIdStatement = conn.prepareStatement(query);
            userIdStatement.setString(1, oldPassword);
            ResultSet userIdResult = userIdStatement.executeQuery();

            if (userIdResult.next()) {
                String userId = userIdResult.getString("staffID");
        
                String updateQuery = "UPDATE staffLogin SET password = ? WHERE staffID = ?";
                PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
                updateStatement.setString(1, newPassword);
                updateStatement.setString(2, userId);
            
            
                int rowsUpdated = updateStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Password updated successfully.");
                }
            } else {
                System.out.println("Failed to update password.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void AddEditDeleteBooks(){
        JFrame booksFrame = new JFrame("Add/Edit/Delete Books");
        booksFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        booksFrame.setSize(1200, 700);
        booksFrame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Add/Edit/Delete Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> {
            booksFrame.dispose();
            addBook();
        });

        JButton editButton = new JButton("Edit Book");
        editButton.addActionListener(e -> {
            booksFrame.dispose();
            editBook();
        });

        JButton deleteButton = new JButton("Delete Book");
        deleteButton.addActionListener(e -> {
            booksFrame.dispose();
            deleteBook();
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            booksFrame.dispose(); // Close the books window
            showStaffOptionsPage(); // Go back to the staff options screen
        });
        
        addButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        addButton.setPreferredSize(new Dimension(300,50));
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        editButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        editButton.setPreferredSize(new Dimension(300,50));
        deleteButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        deleteButton.setPreferredSize(new Dimension(300,50));

        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(addButton);
        JPanel but1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but1.add(editButton);
        JPanel but2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but2.add(deleteButton);
        JPanel but3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but3.add(backButton);
        
        Container contentPane = booksFrame.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1));
        contentPane.add(titlePanel);
        contentPane.add(but);
        contentPane.add(but1);
        contentPane.add(but2);
        contentPane.add(but3);
        

        booksFrame.setVisible(true);
    }
    
    private static void addBook(){
        JFrame addBookFrame = new JFrame("Add Book");
        addBookFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addBookFrame.setSize(1200, 700);
        addBookFrame.setLocationRelativeTo(null);

        JLabel HeadingLabel = new JLabel("Add Books", SwingConstants.CENTER);
        HeadingLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(HeadingLabel);

        JTextField bookIdField = new JTextField();
        bookIdField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        bookIdField.setPreferredSize(new Dimension(300,50));
        JTextField authorField = new JTextField();
        authorField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        authorField.setPreferredSize(new Dimension(300,50));
        JTextField genreField = new JTextField();
        genreField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        genreField.setPreferredSize(new Dimension(300,50));
        JTextField titleField = new JTextField();
        titleField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        titleField.setPreferredSize(new Dimension(300,50));
        JCheckBox availableCheckBox = new JCheckBox("Available");
        availableCheckBox.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        availableCheckBox.setPreferredSize(new Dimension(300,50));

        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel statusLabel = new JLabel("Title:");
        statusLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            int bookId = Integer.parseInt(bookIdField.getText());
            String author = authorField.getText();
            String genre = genreField.getText();
            String title = titleField.getText();
            boolean available = availableCheckBox.isSelected();
            addBookToDatabase(bookId, author, genre, available, title);
            bookIdField.setText("");
            authorField.setText("");
            genreField.setText("");
            titleField.setText("");
            availableCheckBox.setSelected(false);
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            addBookFrame.dispose(); // Close the add book window
            AddEditDeleteBooks(); // Go back to the staff options screen
        });
        
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        addButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        addButton.setPreferredSize(new Dimension(300,50));

        JPanel bookIDPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookIDPanel.add(bookIdLabel);
        bookIDPanel.add(bookIdField);
        
        JPanel bookTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookTitlePanel.add(titleLabel);
        bookTitlePanel.add(titleField);
        
        JPanel bookAuthorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookAuthorPanel.add(authorLabel);
        bookAuthorPanel.add(authorField);
        
        JPanel bookGenrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookGenrePanel.add(genreLabel);
        bookGenrePanel.add(genreField);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.add(statusLabel);
        statusPanel.add(availableCheckBox);
        
        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        but.add(addButton);
        
        Container contentPane = addBookFrame.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1));
        contentPane.add(titlePanel);
        contentPane.add(bookIDPanel);
        contentPane.add(bookTitlePanel);
        contentPane.add(bookAuthorPanel);
        contentPane.add(bookGenrePanel);
        contentPane.add(statusPanel);
        contentPane.add(but);

        addBookFrame.setVisible(true);
    }
    
    private static void addBookToDatabase(int bookId, String author, String genre, boolean available, String title){
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "INSERT INTO books (bookid, author, genre, status, title) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, bookId); // Set bookId as integer
            statement.setString(2, author);
            statement.setString(3, genre);
            statement.setString(4, available ? "Available" : "Not Available");
            statement.setString(5, title);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Failed to add book.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        // Handle SQLException
        }
    }
    
    private static void editBook(){
        JFrame editBookFrame = new JFrame("Edit Book");
        editBookFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editBookFrame.setSize(1200, 700);
        editBookFrame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Edit Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));

        JTextField bookIdField = new JTextField();
        bookIdField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        bookIdField.setPreferredSize(new Dimension(300,50));
        JTextField authorField = new JTextField();
        authorField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        authorField.setPreferredSize(new Dimension(300,50));
        JTextField genreField = new JTextField();
        genreField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        genreField.setPreferredSize(new Dimension(300,50));
        JTextField titleField = new JTextField();
        titleField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        titleField.setPreferredSize(new Dimension(300,50));

        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel titlelabel = new JLabel("Title:");
        titlelabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel statuslabel = new JLabel("Status:");
        statuslabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));

        JCheckBox availableCheckBox = new JCheckBox("Available");
        availableCheckBox.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        availableCheckBox.setPreferredSize(new Dimension(300,50));

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
           int bookId = Integer.parseInt(bookIdField.getText());
           String author = authorField.getText();
           String genre = genreField.getText();
           String title = titleField.getText();
           boolean available = availableCheckBox.isSelected();

           editBookInDatabase(bookId, author, genre, available, title);

           bookIdField.setText("");
           authorField.setText("");
           genreField.setText("");
           titleField.setText("");
           availableCheckBox.setSelected(false); // Reset checkbox
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            editBookFrame.dispose(); // Close the edit book window
            AddEditDeleteBooks(); // Go back to the staff options screen
        });

        editButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        editButton.setPreferredSize(new Dimension(300,50));
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        
        JPanel bookIdPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookIdPanel.add(bookIdLabel);
        bookIdPanel.add(bookIdField);
        
        JPanel bookAuthorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookAuthorPanel.add(authorLabel);
        bookAuthorPanel.add(authorField);
        
        JPanel bookGenrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookGenrePanel.add(genreLabel);
        bookGenrePanel.add(genreField);
        
        JPanel bookTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookTitlePanel.add(titlelabel);
        bookTitlePanel.add(titleField);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.add(statuslabel);
        statusPanel.add(availableCheckBox);
        
        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        but.add(editButton);
        
        Container contentPane = editBookFrame.getContentPane();
        contentPane.setLayout(new GridLayout(7, 2));
        contentPane.add(titlePanel);
        contentPane.add(bookIdPanel);
        contentPane.add(bookTitlePanel);
        contentPane.add(bookAuthorPanel);
        contentPane.add(bookGenrePanel);
        contentPane.add(statusPanel);
        contentPane.add(but);

        editBookFrame.setVisible(true);
    }
    
    private static void editBookInDatabase(int bookID, String author, String genre, boolean available, String title){
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "UPDATE books SET author = ?, genre = ?, status = ?, title = ? WHERE bookid = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, author);
            statement.setString(2, genre);
            statement.setString(3, available ? "Available" : "Not Available");
            statement.setString(4, title);
            statement.setInt(5, bookID);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Failed to update book.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void deleteBook(){
        JFrame deleteBookFrame = new JFrame("Delete Book");
        deleteBookFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        deleteBookFrame.setSize(1200, 700);
        deleteBookFrame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Delete Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        
        JTextField bookIdField = new JTextField();
        bookIdField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        bookIdField.setPreferredSize(new Dimension(300,50));
        
        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int bookId = Integer.parseInt(bookIdField.getText());
            deleteBookFromDatabase(bookId);
            bookIdField.setText(""); // Clear the text field after deleting
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            deleteBookFrame.dispose(); // Close the delete book window
            AddEditDeleteBooks(); // Go back to the staff options screen
        });
        
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        deleteButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        deleteButton.setPreferredSize(new Dimension(300,50));
        
        JPanel text = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text.add(bookIdLabel);
        text.add(bookIdField);

        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        but.add(deleteButton);
        
        Container contentPane = deleteBookFrame.getContentPane();
        contentPane.setLayout(new GridLayout(4, 2));
        contentPane.add(titlePanel);
        contentPane.add(text);
        contentPane.add(but);

        deleteBookFrame.setVisible(true);
    }
    
    private static void deleteBookFromDatabase(int bookID){
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "DELETE FROM books WHERE bookid = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, bookID);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Failed to delete book.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        // Handle SQLException
        }
    }
    
    private static void ManageUsers(){
        JFrame UsersFrame = new JFrame("Manage Students");
        UsersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UsersFrame.setSize(1200, 700);
        UsersFrame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Manage Students", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 50));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        
        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(e -> {
            UsersFrame.dispose();
            addStudent();
        });

        JButton editButton = new JButton("Edit Student");
        editButton.addActionListener(e -> {
            UsersFrame.dispose();
            editStudent();
        });

        JButton deleteButton = new JButton("Delete Student");
        deleteButton.addActionListener(e -> {
            UsersFrame.dispose();
            deleteStudent();
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            UsersFrame.dispose(); // Close the books window
            showStaffOptionsPage(); // Go back to the staff options screen
        });
        
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        deleteButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        deleteButton.setPreferredSize(new Dimension(300,50));
        editButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        editButton.setPreferredSize(new Dimension(300,50));
        addButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        addButton.setPreferredSize(new Dimension(300,50));

        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(addButton);
        JPanel but1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but1.add(editButton);
        JPanel but2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but2.add(deleteButton);
        JPanel but3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but3.add(backButton);
        
        Container contentPane = UsersFrame.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1));
        contentPane.add(titlePanel);
        contentPane.add(but);
        contentPane.add(but1);
        contentPane.add(but2);
        contentPane.add(but3);

        UsersFrame.setVisible(true);
    }
    
    private static void addStudent(){
        JFrame addUserFrame = new JFrame("Add Student");
        addUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addUserFrame.setSize(1200, 700);
        addUserFrame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Add Student", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 50));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        
        JTextField regnoField = new JTextField();
        regnoField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        regnoField.setPreferredSize(new Dimension(300,50));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        passwordField.setPreferredSize(new Dimension(300,50));

        JLabel regnoLabel = new JLabel("Register Number:");
        regnoLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String regno =regnoField.getText();
            String password = new String(passwordField.getPassword());
            addStudentToDatabase(regno, password);
            regnoField.setText("");
            passwordField.setText("");
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            addUserFrame.dispose(); // Close the add book window
            ManageUsers(); // Go back to the staff options screen
        });
        
        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        addButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        addButton.setPreferredSize(new Dimension(300,50));

        JPanel regnoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        regnoPanel.add(regnoLabel);
        regnoPanel.add(regnoField);
        
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        
        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        but.add(addButton);
        
        Container contentPane = addUserFrame.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1));
        contentPane.add(titlePanel);
        contentPane.add(regnoPanel);
        contentPane.add(passwordPanel);
        contentPane.add(but);

        addUserFrame.setVisible(true);
    }
    
    private static void addStudentToDatabase(String regno, String password){
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "INSERT INTO studentsLogin (regno, password) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, regno); 
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Student added successfully.");
            } else {
                System.out.println("Failed to add Student.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void editStudent(){
        JFrame editUserFrame = new JFrame("Edit Student");
        editUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editUserFrame.setSize(1200, 700);
        editUserFrame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Edit Students", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        
        JTextField regnoField = new JTextField();
        regnoField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        regnoField.setPreferredSize(new Dimension(300,50));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        passwordField.setPreferredSize(new Dimension(300,50));

        JLabel regnoLabel = new JLabel("Register Number:");
        regnoLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));

        JPanel regnoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        regnoPanel.add(regnoLabel);
        regnoPanel.add(regnoField);
        
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
           String regno =regnoField.getText();
           String password = new String(passwordField.getPassword());

           editStudentInDatabase(regno, password);

           regnoField.setText("");
           passwordField.setText("");
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            editUserFrame.dispose(); // Close the edit book window
            ManageUsers(); // Go back to the staff options screen
        });

        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        editButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        editButton.setPreferredSize(new Dimension(300,50));
        
        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        but.add(editButton);
        
        Container contentPane = editUserFrame.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1));
        contentPane.add(titlePanel);
        contentPane.add(regnoPanel);
        contentPane.add(passwordPanel);
        contentPane.add(but);

        editUserFrame.setVisible(true);
    }
    
    private static void editStudentInDatabase(String regno, String password){
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "UPDATE studentsLogin SET password = ? WHERE regno = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, password);
            statement.setString(2, regno);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("Failed to update Student.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void deleteStudent(){
        JFrame deleteUserFrame = new JFrame("Delete Student");
        deleteUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        deleteUserFrame.setSize(1200, 700);
        deleteUserFrame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Delete Students", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 50));

        JTextField regnoField = new JTextField();
        regnoField.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        regnoField.setPreferredSize(new Dimension(300,50));
        JLabel regnoLabel = new JLabel("Register Number:");
        regnoLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            String regno = regnoField.getText();
            deleteStudentFromDatabase(regno);
            regnoField.setText(""); // Clear the text field after deleting
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            deleteUserFrame.dispose(); // Close the delete book window
            ManageUsers(); // Go back to the staff options screen
        });

        backButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(300,50));
        deleteButton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        deleteButton.setPreferredSize(new Dimension(300,50));
        
        JPanel but = new JPanel(new FlowLayout(FlowLayout.CENTER));
        but.add(backButton);
        but.add(deleteButton);
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        
        JPanel text = new JPanel(new FlowLayout(FlowLayout.CENTER));
        text.add(regnoLabel);
        text.add(regnoField);
        
        Container contentPane = deleteUserFrame.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1));
        contentPane.add(titlePanel);
        contentPane.add(text);
        contentPane.add(but);

        deleteUserFrame.setVisible(true);
    }
    
    private static void deleteStudentFromDatabase(String regno){
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "DELETE FROM studentsLogin WHERE regno = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, regno);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Failed to delete Student.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        // Handle SQLException
        }
    }
    
    public static void generateReport(){
        DefaultPieDataset data = new DefaultPieDataset();

        // Retrieve book genre statistics from the database
        try (Connection conn = DriverManager.getConnection(url,user,pass)) {
            String query = "SELECT genre, COUNT(*) AS count FROM books GROUP BY genre";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Populate the dataset with genre statistics
            while (resultSet.next()) {
                String genre = resultSet.getString("genre");
                int count = resultSet.getInt("count");
                data.setValue(genre, count);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database errors
        }

        // Create the chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Library Book Genre Distribution",  // Chart title
                data,                            // Dataset
                true,                               // Include legend
                true,
                false
        );

        // Create a panel to display the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        // Create a frame to display the report
        JButton backbutton = new JButton("Back");
        backbutton.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
        backbutton.setPreferredSize(new Dimension(300,50));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backbutton);
        
        JFrame reportFrame = new JFrame("Library Report");
        reportFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reportFrame.setLayout(new BorderLayout());
        reportFrame.setSize(1200,700);
        reportFrame.add(chartPanel, BorderLayout.CENTER);
        reportFrame.add(buttonPanel, BorderLayout.SOUTH);
        reportFrame.pack();
        reportFrame.setLocationRelativeTo(null); // Center the frame on the screen
        reportFrame.setVisible(true);
        
        backbutton.addActionListener(e -> {
            reportFrame.dispose(); // Close the delete book window
            showStaffOptionsPage(); // Go back to the staff options screen
        });
    }
}
