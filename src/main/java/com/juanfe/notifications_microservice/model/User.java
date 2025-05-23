package com.juanfe.notifications_microservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import java.util.HashMap;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private boolean active;
    private String role;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_notification_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "category")
    @Column(name = "enabled")
    private Map<String, Boolean> notificationPreferences = new HashMap<>(Map.of(
        "GAME_EVENT", true,
        "SOCIAL_EVENT", true
    ));
}
