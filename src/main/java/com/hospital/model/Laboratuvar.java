package com.hospital.model;




public enum Laboratuvar {
    TAM_KAN_SAYIMI("Tam Kan Sayımı"),
    HORMON_TESTLERI("Hormon Testleri"),
    BIYOKIMYA("Biyokimya"),
    IDRAR_TAHLILI("İdrar Tahli"),
    KOAGULASYON("Koagülasyon"),
    SEDIMANTASYON("Sedimantasyon"),
    CRP("CRP");
    // Bu enum değerlerini veritabanında saklayarak database'e ekle
    private final String displayName;

    Laboratuvar(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    public static Laboratuvar getByDisplayName(String displayName) {
        for (Laboratuvar laboratuvar : values()) {
            if (laboratuvar.getDisplayName().equalsIgnoreCase(displayName)) {
                return laboratuvar;
            }
        }
        throw new IllegalArgumentException("Geçersiz laboratuvar alanı: " + displayName);
    }
}

