package com.workintech.library.InterFaces;

public enum BookStatus {
    AVAILABLE,
    BORROWED,
    MAINTENANCE;

    public static BookStatus fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return BookStatus.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown book status: " + value);
        }
    }
}

