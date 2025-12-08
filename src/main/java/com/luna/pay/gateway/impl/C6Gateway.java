package com.luna.pay.gateway.impl;

import com.luna.pay.common.exception.GatewayException;
import com.luna.pay.gateway.*;
import com.luna.pay.payment.PaymentStatus;
import com.luna.pay.payment.dto.CreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Implementação STUB do gateway C6 Bank.
 * Simula respostas até que as credenciais reais sejam configuradas.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class C6Gateway implements PaymentGateway {

    private final GatewayConfig gatewayConfig;

    @Override
    public String getGatewayName() {
        return "C6";
    }

    @Override
    public boolean isEnabled() {
        return gatewayConfig.getC6().isEnabled();
    }

    @Override
    public GatewayPaymentResult createPayment(CreatePaymentRequest request, String tenantId) {
        log.info("[C6 STUB] Criando pagamento simulado para tenant {}", tenantId);

        if (!isEnabled()) {
            throw new GatewayException("C6", "Gateway C6 não está habilitado");
        }

        // TODO: Implementar integração real quando tiver API Key e Secret do C6

        String gatewayPaymentId = "c6_stub_" + UUID.randomUUID().toString().substring(0, 8);

        GatewayPaymentResult.GatewayPaymentResultBuilder builder = GatewayPaymentResult.builder()
                .success(true)
                .gatewayPaymentId(gatewayPaymentId)
                .paymentMethod(request.getPaymentMethod())
                .amount(request.getAmount());

        // Simula dados específicos por método
        switch (request.getPaymentMethod().toUpperCase()) {
            case "PIX":
                int expirationMinutes = request.getPixExpirationMinutes() != null 
                    ? request.getPixExpirationMinutes() : 30;
                builder.pixQrCode("00020126580014br.gov.bcb.pix0136c6bank" + gatewayPaymentId)
                       .pixCopyPaste("00020126580014br.gov.bcb.pix0136c6bank" + gatewayPaymentId + "5204000053039865802BR")
                       .pixQrCodeBase64("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==")
                       .pixExpiresAt(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES));
                break;

            case "BOLETO":
                builder.boletoBarCode("33691.79001 01043.510047 91020.150008 1 84460000002000")
                       .boletoUrl("https://api.c6bank.com.br/boleto/pdf/" + gatewayPaymentId)
                       .boletoExpiresAt(Instant.now().plus(3, ChronoUnit.DAYS));
                break;

            case "CREDIT_CARD":
            case "DEBIT_CARD":
                builder.authorizationCode("C6AUTH" + UUID.randomUUID().toString().substring(0, 6))
                       .nsu("C6" + UUID.randomUUID().toString().substring(0, 10));
                break;
        }

        log.info("[C6 STUB] Pagamento simulado criado: {}", gatewayPaymentId);
        return builder.build();
    }

    @Override
    public GatewayPaymentStatus getPaymentStatus(String gatewayPaymentId) {
        log.info("[C6 STUB] Consultando status do pagamento: {}", gatewayPaymentId);

        // TODO: Implementar consulta real à API C6

        return GatewayPaymentStatus.builder()
                .gatewayPaymentId(gatewayPaymentId)
                .status(PaymentStatus.PENDING)
                .gatewayStatus("PENDING")
                .message("Aguardando pagamento (STUB)")
                .build();
    }

    @Override
    public boolean cancelPayment(String gatewayPaymentId) {
        log.info("[C6 STUB] Cancelando pagamento: {}", gatewayPaymentId);

        // TODO: Implementar cancelamento real na API C6

        return true;
    }

    @Override
    public boolean validateWebhook(String signature, String payload) {
        log.info("[C6 STUB] Validando webhook (sempre retorna true no STUB)");

        // TODO: Implementar validação real de assinatura do C6

        return true;
    }

    @Override
    public WebhookProcessingResult processWebhook(String payload) {
        log.info("[C6 STUB] Processando webhook: {}", payload);

        // TODO: Implementar processamento real do webhook C6

        return WebhookProcessingResult.builder()
                .success(true)
                .message("Webhook processado (STUB)")
                .build();
    }
}
