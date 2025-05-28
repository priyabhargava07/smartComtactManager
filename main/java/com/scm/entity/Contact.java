package com.scm.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Contact {
    @Id
    private String id;

    private String name;
    private String email;
    private String phoneNumber;
    private String picture;

    @Column(length = 500)
    private String description;
    private String address;
    private boolean favourite = false;
    private String websiteLink;
    private String linkedInLink;
    private String cloudinaryImagePublicId; // This field for image transformation.

    // Use @JsonManagedReference for parent-side of the bidirectional relationship
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference // Prevents StackOverflowError by managing serialization on the parent side
    private List<SocialLink> socialLinks;

    @ManyToOne
    @JsonIgnore // Ignores this field during serialization to prevent circular reference issues
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @CreationTimestamp // Automatically sets the creation date
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @UpdateTimestamp // Automatically updates on modification
    private Date updatedAt;

    @Override
    public String toString() {
        return "Contact{}";
    }
}
