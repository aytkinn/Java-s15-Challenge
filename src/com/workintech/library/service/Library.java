package com.workintech.library.service;

import com.workintech.library.base.MemberRecord;
import com.workintech.library.model.Book;
import com.workintech.library.InterFaces.BookCategory;
import com.workintech.library.InterFaces.BookStatus;
import com.workintech.library.model.Journals;
import com.workintech.library.model.Magazines;
import com.workintech.library.model.StudyBooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Library {
    private Map<Integer, Book> booksMap;
    private Map<Integer, MemberRecord> membersRecordMap;
    private Set<String> authorsSet;
    private Map<Integer, Integer> bookLoans;
    private Map<Integer, Double> memberInvoices;

    public Library(Set<String> authorsSet, Map<Integer, MemberRecord> membersRecordMap, Map<Integer, Book> booksMap) {
        this.authorsSet = authorsSet;
        this.membersRecordMap = membersRecordMap;
        this.booksMap = booksMap;
        this.bookLoans = new HashMap<>();
        this.memberInvoices = new HashMap<>();
    }

    public Book getBook(int id){
        return booksMap.get(id);
    }

    public Integer getLoanHolder(int bookId){
        return bookLoans.get(bookId);
    }

    public List<Book> getBooksByTitle(String title){
        List<Book> result = new ArrayList<>();
        for (Book book : booksMap.values()){
            if (book.getTitle().equalsIgnoreCase(title)){
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> getBooksByAuthor(String author){
        List<Book> result = new ArrayList<>();
        for (Book book : booksMap.values()){
            if (book.getAuthor().equalsIgnoreCase(author)){
                result.add(book);
            }
        }
        return result;
    }

    public Map<Integer, MemberRecord> getMembersRecordMap() {
        return membersRecordMap;
    }

    public void newBook(Book book){
        if (booksMap.containsKey(book.getBook_ID())){
            throw new IllegalArgumentException("Error: Book ID already exists!");
        }

        booksMap.put(book.getBook_ID(),book);
        authorsSet.add(book.getAuthor());
        System.out.println("Book Added " + book.getTitle());
    }

    public void updateBookDetails(int bookId, String title, String author, double price, BookStatus status, BookCategory category){
        Book existing = booksMap.get(bookId);
        if (existing == null){
            throw new IllegalArgumentException("Error: Book not found!");
        }
        String finalTitle = (title == null || title.isBlank()) ? existing.getTitle() : title;
        String finalAuthor = (author == null || author.isBlank()) ? existing.getAuthor() : author;
        double finalPrice = price < 0 ? existing.getPrice() : price;
        BookStatus finalStatus = (status == null) ? existing.getStatus() : status;
        BookCategory finalCategory = (category == null) ? existing.getCategory() : category;

        Book updated = createBookInstance(finalCategory, bookId, finalAuthor, finalTitle, finalPrice, finalStatus);
        booksMap.put(bookId, updated);
        rebuildAuthorsSet();
        System.out.println("Book Updated " + updated.getTitle());
    }

    public void removeBook(int bookId){
        Book removed = booksMap.remove(bookId);
        if (removed == null){
            throw new IllegalArgumentException("Error: Book not found!");
        }
        Integer borrower = bookLoans.remove(bookId);
        if (borrower != null){
            memberInvoices.put(borrower, Math.max(0.0, memberInvoices.getOrDefault(borrower,0.0) - removed.getPrice()));
        }
        rebuildAuthorsSet();
        System.out.println("Book Removed " + removed.getTitle());
    }

    public void addMember(MemberRecord memberRecord){
        membersRecordMap.put(memberRecord.getMemberId(),memberRecord);
    }

    public void showBook(){
        System.out.println("*** Library Books ***");
        for (Book book:booksMap.values()){
            book.display();
        }
    }

    public void showBooksByCategory(BookCategory category){
        System.out.println("*** Books in Category: " + category + " ***");
        for (Book book : booksMap.values()){
            if (book.getCategory() == category){
                book.display();
            }
        }
    }

    public void showBooksByAuthor(String author){
        System.out.println("*** Books by Author: " + author + " ***");
        for (Book book : booksMap.values()){
            if (book.getAuthor().equalsIgnoreCase(author)){
                book.display();
            }
        }
    }

    public void showAuthors(){
        System.out.println("*** Authors in Library ***");
        for (String author : authorsSet){
            System.out.println(author);
        }
    }

    public void showCurrentLoans(){
        if (bookLoans.isEmpty()){
            System.out.println("No active loans.");
            return;
        }
        System.out.println("*** Active Loans ***");
        for (Map.Entry<Integer, Integer> entry : bookLoans.entrySet()){
            Book book = booksMap.get(entry.getKey());
            MemberRecord member = membersRecordMap.get(entry.getValue());
            if (book != null && member != null){
                System.out.println(book.getTitle() + " -> " + member.getName());
            }
        }
    }

    public void showMemberInvoices(){
        if (memberInvoices.isEmpty()){
            System.out.println("No invoices at the moment.");
            return;
        }
        System.out.println("*** Member Invoices ***");
        for (Map.Entry<Integer, Double> entry : memberInvoices.entrySet()){
            MemberRecord member = membersRecordMap.get(entry.getKey());
            if (member != null){
                System.out.println(member.getName() + " owes $" + entry.getValue());
            }
        }
    }

    public void recordLoan(int bookId, int memberId, double invoiceAmount){
        bookLoans.put(bookId, memberId);
        memberInvoices.put(memberId, memberInvoices.getOrDefault(memberId, 0.0) + invoiceAmount);
        System.out.println("Invoice created for member " + memberId + ": $" + invoiceAmount);
    }

    public void clearLoan(int bookId, int memberId, double refundAmount){
        bookLoans.remove(bookId);
        double updatedInvoice = Math.max(0.0, memberInvoices.getOrDefault(memberId, 0.0) - refundAmount);
        if (updatedInvoice == 0.0){
            memberInvoices.remove(memberId);
        } else {
            memberInvoices.put(memberId, updatedInvoice);
        }
        System.out.println("Refund processed for member " + memberId + ": $" + refundAmount);
    }

    private Book createBookInstance(BookCategory category, int id, String author, String title, double price, BookStatus status){
        switch (category){
            case JOURNAL:
                return new Journals(id, author, title, price, status);
            case MAGAZINE:
                return new Magazines(id, author, title, price, status);
            default:
                return new StudyBooks(id, author, title, price, status);
        }
    }

    private void rebuildAuthorsSet(){
        authorsSet.clear();
        for (Book book : booksMap.values()){
            authorsSet.add(book.getAuthor());
        }
    }
}
