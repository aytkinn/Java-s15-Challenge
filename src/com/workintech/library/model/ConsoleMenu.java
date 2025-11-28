package com.workintech.library.model;

import com.workintech.library.InterFaces.BookCategory;
import com.workintech.library.InterFaces.BookStatus;
import com.workintech.library.InterFaces.UserInterface;
import com.workintech.library.service.Librarian;
import com.workintech.library.service.Library;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu implements UserInterface {
    private final Library library;
    private final Librarian librarian;

    public ConsoleMenu(Library library, Librarian librarian) {
        this.library = library;
        this.librarian = librarian;
    }

    @Override
    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1":
                        createBookFlow(scanner);
                        break;
                    case "2":
                        library.showBook();
                        break;
                    case "3":
                        library.showAuthors();
                        break;
                    case "4":
                        verifyMemberFlow(scanner);
                        break;
                    case "5":
                        issueBookFlow(scanner);
                        break;
                    case "6":
                        returnBookFlow(scanner);
                        break;
                    case "7":
                        searchBookByTitleFlow(scanner);
                        break;
                    case "8":
                        searchBooksByAuthorFlow(scanner);
                        break;
                    case "9":
                        updateBookFlow(scanner);
                        break;
                    case "10":
                        removeBookFlow(scanner);
                        break;
                    case "11":
                        listByCategoryFlow(scanner);
                        break;
                    case "12":
                        library.showCurrentLoans();
                        break;
                    case "13":
                        library.showMemberInvoices();
                        break;
                    case "0":
                        running = false;
                        System.out.println("Exiting system. Goodbye!");
                        break;
                    default:
                        System.out.println("Unknown option, please choose again.");
                }
            }
        }
    }

    private void printMenu() {
        System.out.println("--- MENU ---");
        System.out.println("1) Add book");
        System.out.println("2) List books");
        System.out.println("3) List authors");
        System.out.println("4) Verify member");
        System.out.println("5) Issue book");
        System.out.println("6) Return book");
        System.out.println("7) Search book by title");
        System.out.println("8) Search books by author");
        System.out.println("9) Update book info");
        System.out.println("10) Remove book");
        System.out.println("11) List books by category");
        System.out.println("12) Show active loans");
        System.out.println("13) Show member invoices");
        System.out.println("0) Exit");
        System.out.print("Select option: ");
    }

    private void createBookFlow(Scanner scanner) {
        try {
            System.out.print("Book type (study/journal/magazine): ");
            String type = scanner.nextLine();
            int id = getIntInput(scanner, "Book ID: ");
            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Author: ");
            String author = scanner.nextLine();
            double price = getDoubleInput(scanner, "Price: ");

            BookCategory category = BookCategory.fromString(type);
            Book book = createBookInstance(category, id, author, title, price, BookStatus.AVAILABLE);
            library.newBook(book);
        } catch (IllegalArgumentException e) {
            System.out.println("Operation failed: " + e.getMessage());
        }
    }

    private void verifyMemberFlow(Scanner scanner) {
        int memberId = getIntInput(scanner, "Member ID: ");
        librarian.verifyMember(memberId);
    }

    private void issueBookFlow(Scanner scanner) {
        try {
            int bookId = getIntInput(scanner, "Book ID to issue: ");
            int memberId = getIntInput(scanner, "Member ID: ");
            librarian.issueBook(bookId, memberId);
        } catch (IllegalArgumentException e) {
            System.out.println("Operation failed: " + e.getMessage());
        }
    }

    private void returnBookFlow(Scanner scanner) {
        try {
            int bookId = getIntInput(scanner, "Book ID to return: ");
            int memberId = getIntInput(scanner, "Member ID: ");
            librarian.returnBook(bookId, memberId);
        } catch (IllegalArgumentException e) {
            System.out.println("Operation failed: " + e.getMessage());
        }
    }

    private void searchBookByTitleFlow(Scanner scanner){
        System.out.print("Title to search: ");
        String title = scanner.nextLine();
        List<Book> books = library.getBooksByTitle(title);
        if (books.isEmpty()){
            System.out.println("No books found with the given title.");
        } else {
            books.forEach(Book::display);
        }
    }

    private void searchBooksByAuthorFlow(Scanner scanner){
        System.out.print("Author to search: ");
        String author = scanner.nextLine();
        List<Book> books = library.getBooksByAuthor(author);
        if (books.isEmpty()){
            System.out.println("No books found for that author.");
        } else {
            books.forEach(Book::display);
        }
    }

    private void updateBookFlow(Scanner scanner){
        try{
            int bookId = getIntInput(scanner, "Book ID to update: ");
            Book existing = library.getBook(bookId);
            if (existing == null){
                System.out.println("Book not found.");
                return;
            }
            System.out.print("New title (leave blank to keep '" + existing.getTitle() + "'): ");
            String title = scanner.nextLine();
            System.out.print("New author (leave blank to keep '" + existing.getAuthor() + "'): ");
            String author = scanner.nextLine();
            Double price = getOptionalDoubleInput(scanner, "New price (leave blank to keep " + existing.getPrice() + "): ");
            System.out.print("New status (Available/Borrowed/Maintenance, leave blank to keep '" + existing.getStatus() + "'): ");
            String statusInput = scanner.nextLine();
            System.out.print("New category (study/journal/magazine, leave blank to keep current): ");
            String categoryInput = scanner.nextLine();

            double finalPrice = price == null ? -1 : price;
            BookStatus status = statusInput.isBlank() ? null : BookStatus.fromString(statusInput);
            BookCategory category = categoryInput.isBlank() ? null : BookCategory.fromString(categoryInput);
            library.updateBookDetails(bookId, title, author, finalPrice, status, category);
        } catch (IllegalArgumentException e){
            System.out.println("Operation failed: " + e.getMessage());
        }
    }

    private void removeBookFlow(Scanner scanner){
        try{
            int bookId = getIntInput(scanner, "Book ID to remove: ");
            library.removeBook(bookId);
        } catch (IllegalArgumentException e){
            System.out.println("Operation failed: " + e.getMessage());
        }
    }

    private void listByCategoryFlow(Scanner scanner){
        try {
            System.out.print("Category (study/journal/magazine): ");
            String categoryInput = scanner.nextLine();
            BookCategory category = BookCategory.fromString(categoryInput);
            library.showBooksByCategory(category);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Book createBookInstance(BookCategory category, int id, String author, String title, double price, BookStatus status) {
        switch (category) {
            case JOURNAL:
                return new Journals(id, author, title, price, status);
            case MAGAZINE:
                return new Magazines(id, author, title, price, status);
            default:
                return new StudyBooks(id, author, title, price, status);
        }
    }

    private int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    private double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount, try again.");
            }
        }
    }

    private Double getOptionalDoubleInput(Scanner scanner, String prompt){
        while (true){
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()){
                return null;
            }
            try{
                return Double.parseDouble(input);
            } catch (NumberFormatException e){
                System.out.println("Invalid amount, try again.");
            }
        }
    }
}

