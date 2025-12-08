package com.luna.pay.payment;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    private BigDecimal amount;

    private String description;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String gateway;
    private String gatewayPaymentId;
    private String paymentMethod;

    // PIX
    @Column(length = 1000)
    private String pixQrCode;
    @Column(length = 2000)
    private String pixQrCodeBase64;
    @Column(length = 1000)
    private String pixCopyPaste;
    private Instant pixExpiresAt;

    // Boleto
    private String boletoBarCode;
    private String boletoUrl;
    private Instant boletoExpiresAt;

    // Cartão
    private String authorizationCode;
    private String nsu;

    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        createdAt = Instant.now();
        updatedAt = createdAt;
        if (status == null) status = PaymentStatus.PENDING;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }
}
