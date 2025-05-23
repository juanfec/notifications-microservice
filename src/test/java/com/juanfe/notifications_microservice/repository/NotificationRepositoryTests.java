package com.juanfe.notifications_microservice.repository;

import com.juanfe.notifications_microservice.model.Notification;
import com.juanfe.notifications_microservice.service.NotificationTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class NotificationRepositoryTests {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    @DisplayName("Should save and retrieve a notification by id")
    void testSaveAndFindById() {
        Notification notification = NotificationTestData.createLevelUpNotification();
        Notification saved = notificationRepository.save(notification);
        assertThat(saved.getId()).isNotNull();

        Notification found = notificationRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getContent()).isEqualTo(notification.getContent());
    }

    @Test
    @DisplayName("Should return empty when notification not found")
    void testFindByIdNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThat(notificationRepository.findById(randomId)).isEmpty();
    }

    @Test
    @DisplayName("Should save and retrieve multiple notifications")
    void testSaveAndFindAll() {
        Notification n1 = NotificationTestData.createLevelUpNotification();
        Notification n2 = NotificationTestData.createItemAcquiredNotification();

        notificationRepository.save(n1);
        notificationRepository.save(n2);

        List<Notification> all = notificationRepository.findAll();
        assertThat(all).hasSizeGreaterThanOrEqualTo(2);
    }
}
