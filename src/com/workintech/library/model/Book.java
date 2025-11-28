package com.workintech.library.model;

import com.workintech.library.InterFaces.BookCategory;
import com.workintech.library.InterFaces.BookStatus;

import java.util.Date;

public abstract class Book {
    private final int book_ID;
    private String author;
    private String name;
    private double price;
    private BookStatus status;
    private final BookCategory category;
    private String edition;
    private final Date dateOfPurchase;

    public Book(int book_ID, String author, String name, double price, BookCategory category, BookStatus status) {
        this.book_ID = book_ID;
        this.author = author;
        this.name = name;
        this.price = price;
        this.category = category;
        this.status = status;
        this.dateOfPurchase = new Date();
    }
    public int getBook_ID(){
        return book_ID;
    }
    public String getTitle(){
        return name;
    }

    public String  getAuthor(){
        return author;
    }

    public double getPrice() {
        return price;
    }

    public BookStatus getStatus() {
        return status;
    }

    public BookCategory getCategory() {
        return category;
    }

    public String getEdition() {
        return edition;
    }

    public void updateStatus(BookStatus status){
        this.status = status;
    }

    public void setTitle(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public abstract void display();

}
