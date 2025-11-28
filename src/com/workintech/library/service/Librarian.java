package com.workintech.library.service;

import com.workintech.library.base.MemberRecord;
import com.workintech.library.model.Book;
import com.workintech.library.InterFaces.BookStatus;

public class Librarian {
    private String name;
    private Library library;

    public Librarian(String name, Library library) {
        this.name = name;
        this.library = library;
    }

    public String getName() {
        return name;
    }
    public boolean verifyMember(int memberId) {
        MemberRecord member = library.getMembersRecordMap().get(memberId);
        if (member == null) {
            System.out.println("Verification Failed: Member ID " + memberId + " is not registered.");
            return false;
        }
        System.out.println("Verification Successful: Member " + member.getName() + " is registered.");
        return true;
    }

    public void issueBook(int bookId, int memberId){
        Book book = library.getBook(bookId);
        MemberRecord member = library.getMembersRecordMap().get(memberId);
        Integer currentHolder = library.getLoanHolder(bookId);

        if ( book == null|| member == null ){
            throw new IllegalArgumentException("Error: Book or Member not found!");
        }
        if (book.getStatus() != BookStatus.AVAILABLE || currentHolder != null) {
            System.out.println("Transaction Failed: Book (" + book.getTitle() + ") is currently unavailable.");
        } else if (member.getNoBooksIssued() >= member.getMaxBookLimit()) {
            System.out.println("Transaction Failed: Member (" + member.getName() + ") has reached the maximum borrowing limit.");
        } else {
            book.updateStatus(BookStatus.BORROWED);
            member.incBookIssued();
            library.recordLoan(bookId, memberId, book.getPrice());
            System.out.println(book.getTitle() + " successfully issued to member " + member.getName() + ".");
        }
    }

    public void returnBook(int bookId, int memberId) {
        Book book = library.getBook(bookId);
        MemberRecord member = library.getMembersRecordMap().get(memberId);
        Integer currentHolder = library.getLoanHolder(bookId);

        if (book == null || member == null) {
            throw new IllegalArgumentException("Error: Book or Member not found.");
        }

        if (currentHolder == null) {
            System.out.println("Return Failed: Book was not checked out.");
        } else if (!currentHolder.equals(memberId)) {
            System.out.println("Return Failed: Book is checked out to another member.");
        } else if (book.getStatus() == BookStatus.BORROWED) {
            book.updateStatus(BookStatus.AVAILABLE);
            member.deckBookIssued();
            library.clearLoan(bookId, memberId, book.getPrice());
            System.out.println(book.getTitle() + " successfully returned. Member's current issued count: " + member.getNoBooksIssued());
        } else {
            System.out.println("Return Failed: Book was not checked out.");
        }
    }
}
