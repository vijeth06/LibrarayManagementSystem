import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class LibraryManagementSystemGUI extends Frame implements ActionListener {
    private Library library;
    private Label titleLabel;
    private TextField bookTitleField, authorField, memberIdField, searchField;
    private TextArea displayArea;
    private Button addBookButton, removeBookButton, searchBookButton, displayBooksButton, lendBookButton, returnBookButton;
    private Button addMemberButton, removeMemberButton, displayMembersButton, searchMemberButton, clearDisplayButton;
    
    public LibraryManagementSystemGUI() {
        library = new Library();

        setTitle("Library Management System");
        setSize(700, 600);
        setLayout(new BorderLayout());

        // Title Label
        titleLabel = new Label("Library Management System", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Panel for Book and Member Fields
        Panel inputPanel = new Panel(new GridLayout(3, 2, 10, 10));

        bookTitleField = new TextField(20);
        authorField = new TextField(20);
        memberIdField = new TextField(20);

        inputPanel.add(new Label("Book Title:"));
        inputPanel.add(bookTitleField);

        inputPanel.add(new Label("Author:"));
        inputPanel.add(authorField);

        inputPanel.add(new Label("Member ID:"));
        inputPanel.add(memberIdField);

        add(inputPanel, BorderLayout.WEST);

        // Display Area for Actions and Lists
        displayArea = new TextArea(20, 60);
        displayArea.setEditable(false);
        add(displayArea, BorderLayout.CENTER);

        // Panel for Buttons
        Panel buttonPanel = new Panel(new GridLayout(10, 1, 10, 10));

        addBookButton = new Button("Add Book");
        addBookButton.addActionListener(this);
        buttonPanel.add(addBookButton);

        removeBookButton = new Button("Remove Book");
        removeBookButton.addActionListener(this);
        buttonPanel.add(removeBookButton);

        searchBookButton = new Button("Search Book");
        searchBookButton.addActionListener(this);
        buttonPanel.add(searchBookButton);

        displayBooksButton = new Button("Display All Books");
        displayBooksButton.addActionListener(this);
        buttonPanel.add(displayBooksButton);

        lendBookButton = new Button("Lend Book");
        lendBookButton.addActionListener(this);
        buttonPanel.add(lendBookButton);

        returnBookButton = new Button("Return Book");
        returnBookButton.addActionListener(this);
        buttonPanel.add(returnBookButton);

        addMemberButton = new Button("Add Member");
        addMemberButton.addActionListener(this);
        buttonPanel.add(addMemberButton);

        removeMemberButton = new Button("Remove Member");
        removeMemberButton.addActionListener(this);
        buttonPanel.add(removeMemberButton);

        displayMembersButton = new Button("Display All Members");
        displayMembersButton.addActionListener(this);
        buttonPanel.add(displayMembersButton);

        searchMemberButton = new Button("Search Member by ID");
        searchMemberButton.addActionListener(this);
        buttonPanel.add(searchMemberButton);

        add(buttonPanel, BorderLayout.EAST);

        // Search Field for Book/Member
        searchField = new TextField(20);
        Panel searchPanel = new Panel();
        searchPanel.add(new Label("Search (Title/ID):"));
        searchPanel.add(searchField);

        clearDisplayButton = new Button("Clear Display");
        clearDisplayButton.addActionListener(this);
        searchPanel.add(clearDisplayButton);

        add(searchPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals("Add Book")) {
            String title = bookTitleField.getText().trim();
            String author = authorField.getText().trim();
            if (!title.isEmpty() && !author.isEmpty()) {
                library.addBook(title, author);
                displayArea.append("Book added: " + title + " by " + author + "\n");
                clearFields();
            } else {
                displayArea.append("Error: Title and author are required.\n");
            }
        } else if (command.equals("Remove Book")) {
            String title = bookTitleField.getText().trim();
            if (!title.isEmpty()) {
                library.removeBook(title);
                displayArea.append("Book removed: " + title + "\n");
                clearFields();
            } else {
                displayArea.append("Error: Title is required.\n");
            }
        } else if (command.equals("Search Book")) {
            String title = searchField.getText().trim();
            if (!title.isEmpty()) {
                Book book = library.searchBook(title);
                displayArea.append(book != null ? book.toString() + "\n" : "Book not found.\n");
            } else {
                displayArea.append("Error: Search field is empty.\n");
            }
        } else if (command.equals("Display All Books")) {
            library.displayBooks();
        } else if (command.equals("Lend Book")) {
            String title = bookTitleField.getText().trim();
            String memberIdText = memberIdField.getText().trim();
            if (!title.isEmpty() && !memberIdText.isEmpty()) {
                int memberId = Integer.parseInt(memberIdText);
                library.lendBook(title, memberId);
                displayArea.append("Book lent: " + title + " to Member ID " + memberId + "\n");
                clearFields();
            } else {
                displayArea.append("Error: Book title and Member ID are required.\n");
            }
        } else if (command.equals("Return Book")) {
            String title = bookTitleField.getText().trim();
            if (!title.isEmpty()) {
                library.returnBook(title);
                displayArea.append("Book returned: " + title + "\n");
                clearFields();
            } else {
                displayArea.append("Error: Book title is required.\n");
            }
        } else if (command.equals("Add Member")) {
            String memberIdText = memberIdField.getText().trim();
            String memberName = authorField.getText().trim();  // Reusing author field for simplicity
            if (!memberIdText.isEmpty() && !memberName.isEmpty()) {
                int memberId = Integer.parseInt(memberIdText);
                library.addMember(memberId, memberName);
                displayArea.append("Member added: " + memberName + " with ID " + memberId + "\n");
                clearFields();
            } else {
                displayArea.append("Error: Member ID and name are required.\n");
            }
        } else if (command.equals("Remove Member")) {
            String memberIdText = memberIdField.getText().trim();
            if (!memberIdText.isEmpty()) {
                int memberId = Integer.parseInt(memberIdText);
                library.removeMember(memberId);
                displayArea.append("Member removed: ID " + memberId + "\n");
                clearFields();
            } else {
                displayArea.append("Error: Member ID is required.\n");
            }
        } else if (command.equals("Display All Members")) {
            library.displayMembers();
        } else if (command.equals("Search Member by ID")) {
            String memberIdText = searchField.getText().trim();
            if (!memberIdText.isEmpty()) {
                int memberId = Integer.parseInt(memberIdText);
                Member member = library.searchMemberById(memberId);
                displayArea.append(member != null ? member.toString() + "\n" : "Member not found.\n");
            } else {
                displayArea.append("Error: Search field is empty.\n");
            }
        } else if (command.equals("Clear Display")) {
            displayArea.setText("");
        }
    }

    private void clearFields() {
        bookTitleField.setText("");
        authorField.setText("");
        memberIdField.setText("");
        searchField.setText("");
    }

    public static void main(String[] args) {
        new LibraryManagementSystemGUI();
    }
}
