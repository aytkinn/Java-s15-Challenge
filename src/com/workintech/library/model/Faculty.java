package com.workintech.library.model;

import com.workintech.library.base.MemberRecord;

public class Faculty extends MemberRecord {
    public Faculty(int memberId,String name,  String type, int maxBookLimit) {
        super(name, memberId, "Faculty", 5);
    }
}
