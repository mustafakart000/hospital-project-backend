package com.hospital.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import com.hospital.enums.Priority;
import com.hospital.entity.LabTestPdf;
import com.hospital.enums.FastingStatus;
import com.hospital.enums.Status;

@Entity
@Table(name = "lab_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabRequest {

    @JsonManagedReference
    @OneToMany(mappedBy = "labRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabTestPdf> testPdfs = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "doctor_id")
    private String doctorId;

    @Column(name = "reservation_id")
    private String reservationId;

    @Column(name = "test_panel")
    private String testPanel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "fasting_status")
    @Enumerated(EnumType.STRING)
    private FastingStatus fastingStatus;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "technician_id")
    private String technicianId;

    @Column(name = "results", columnDefinition = "TEXT")
    private String results;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        status = Status.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void setTestPdfs(List<LabTestPdf> testPdfs) {
        this.testPdfs.clear();
        if (testPdfs != null) {
            testPdfs.forEach(pdf -> pdf.setLabRequest(this));
            this.testPdfs.addAll(testPdfs);
        }
    }

    public void addTestPdf(LabTestPdf pdf) {
        testPdfs.add(pdf);
        pdf.setLabRequest(this);
    }

    public void removeTestPdf(LabTestPdf pdf) {
        testPdfs.remove(pdf);
        pdf.setLabRequest(null);
    }
} 