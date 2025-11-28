package com.workintech.library.model;

import com.workintech.library.base.MemberRecord;

public class Student extends MemberRecord {
    public Student(int memberId,String name,  String type, int maxBookLimit) {
        super(name, memberId, "Student", 5);
    }
}
