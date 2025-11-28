package com.workintech.library.InterFaces;

public enum BookCategory {
    STUDY,
    JOURNAL,
    MAGAZINE;

    public static BookCategory fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return BookCategory.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown book category: " + value);
        }
    }
}

