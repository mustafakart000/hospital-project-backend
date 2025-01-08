package com.hospital.dto;

public enum Priority {
    NORMAL("Normal"),
    URGENT("Acil");

    private final String displayName;

    Priority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 