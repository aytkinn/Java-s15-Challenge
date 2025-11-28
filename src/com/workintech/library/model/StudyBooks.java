package com.workintech.library.model;

import com.workintech.library.InterFaces.BookCategory;
import com.workintech.library.InterFaces.BookStatus;

public class StudyBooks extends Book{
    public StudyBooks(int book_ID, String author, String name, double price, BookStatus status) {
        super(book_ID, author, name, price, BookCategory.STUDY, status);
    }

    @Override
    public void display() {
        System.out.println("[STUDY BOOK] ID: " + getBook_ID() + "  Title: " + getTitle() + "  Author: " + getAuthor() + "  Price:" + getPrice() + "  Status: " + getStatus());
    }
}
