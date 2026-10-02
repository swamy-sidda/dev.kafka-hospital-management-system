package com.hospital.notification.service;

import com.hospital.notification.dto.PaymentCompletedEvent;

public interface NotificationService {

    void savePaymentNotification(PaymentCompletedEvent event);
}