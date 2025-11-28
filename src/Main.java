
import com.workintech.library.InterFaces.BookStatus;
import com.workintech.library.base.MemberRecord;
import com.workintech.library.model.*;
import com.workintech.library.service.Librarian;
import com.workintech.library.service.Library;
import com.workintech.library.model.ConsoleMenu;
import com.workintech.library.InterFaces.UserInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Map<Integer, Book> booksMap = new HashMap<>();
        Map<Integer, MemberRecord> membersRecordMap = new HashMap<>();
        Set<String> authorsSet = new HashSet<>();

        Library library = new Library(authorsSet, membersRecordMap, booksMap);
        Librarian librarian = new Librarian("ibrahim aytekin", library);

        seedData(library);

        System.out.println("Librarian: " + librarian.getName());

        UserInterface ui = new ConsoleMenu(library, librarian);
        ui.start();
    }

    private static void seedData(Library library) {
        MemberRecord student1 = new Student(101, "Ahmet", "Student", 5);
        MemberRecord faculty1 = new Faculty(201, "Prof. Dr. Mehmet", "Faculty", 5);
        library.addMember(student1);
        library.addMember(faculty1);

        Book book1 = new StudyBooks(1, "Robert C. Martin", "Clean Code", 55.5, BookStatus.AVAILABLE);
        Book book2 = new Journals(2, "Nature Team", "Nature - Vol 50", 120.0, BookStatus.AVAILABLE);
        Book book3 = new Magazines(3, "Vogue Editors", "Fashion Issue", 15.0, BookStatus.AVAILABLE);
        library.newBook(book1);
        library.newBook(book2);
        library.newBook(book3);
    }

}