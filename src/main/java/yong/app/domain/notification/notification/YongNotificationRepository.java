package yong.app.domain.notification.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YongNotificationRepository extends JpaRepository<YongNotification, Long> {
    List<YongNotification> findByYongUserEmail(String email);
}
