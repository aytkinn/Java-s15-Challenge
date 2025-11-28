package com.workintech.library.model;

import com.workintech.library.InterFaces.BookCategory;
import com.workintech.library.InterFaces.BookStatus;

public class Journals extends Book{
    public Journals(int book_ID, String author, String name, double price, BookStatus status) {
        super(book_ID, author, name, price, BookCategory.JOURNAL, status);
    }

    @Override
    public void display() {
        System.out.println("[JOURNAL] ID: " + getBook_ID() + "  Title: " + getTitle() + "  Author: " + getAuthor() + "  Status: " + getStatus());
    }
}
