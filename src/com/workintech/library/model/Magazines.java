package com.workintech.library.model;

import com.workintech.library.InterFaces.BookCategory;
import com.workintech.library.InterFaces.BookStatus;

public class Magazines extends Book{
    public Magazines(int book_ID, String author, String name, double price, BookStatus status) {
        super(book_ID, author, name, price, BookCategory.MAGAZINE, status);
    }

    @Override
    public void display() {
        System.out.println("[MAGAZINE] ID: " + getBook_ID() + "  Title: " + getTitle() + "  Issue: " + getEdition() + "  Status: " + getStatus());
    }
}
