package com.hospital.dto;

public enum ImagingType {
    EKG("EKG"),
    XRAY("Röntgen"),
    MRI("MR"),
    CT("Tomografi"),
    ULTRASOUND("Ultrason");

    private final String displayName;

    ImagingType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 