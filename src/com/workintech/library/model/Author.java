package com.workintech.library.model;

import com.workintech.library.base.Person;

import java.util.ArrayList;
import java.util.List;

public class Author extends Person {
    List <Book> books;
    public Author(String name) {
        super(name);
        this.books=new ArrayList<>();
    }

    @Override
    public void whoyouare() {
        System.out.println("Author name : " +getName());
    }

    public void newBook(Book book){
        books.add(book);
        System.out.println(getName() + " wrote a new book " + book.getTitle());
    }
    public void showBook(){
        for (Book b : books){
            System.out.println("- "+ b.getTitle());
        }
    }
}
