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
 * Implementação STUB do gateway Asaas.
 * Simula respostas até que as credenciais reais sejam configuradas.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AsaasGateway implements PaymentGateway {

    private final GatewayConfig gatewayConfig;

    @Override
    public String getGatewayName() {
        return "ASAAS";
    }

    @Override
    public boolean isEnabled() {
        return gatewayConfig.getAsaas().isEnabled();
    }

    @Override
    public GatewayPaymentResult createPayment(CreatePaymentRequest request, String tenantId) {
        log.info("[ASAAS STUB] Criando pagamento simulado para tenant {}", tenantId);

        if (!isEnabled()) {
            throw new GatewayException("ASAAS", "Gateway Asaas não está habilitado");
        }

        // TODO: Implementar integração real quando tiver API Key
        // Por enquanto, simula resposta baseada no método de pagamento

        String gatewayPaymentId = "asaas_stub_" + UUID.randomUUID().toString().substring(0, 8);

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
                builder.pixQrCode("00020126580014br.gov.bcb.pix0136" + gatewayPaymentId)
                       .pixCopyPaste("00020126580014br.gov.bcb.pix0136" + gatewayPaymentId + "5204000053039865802BR")
                       .pixQrCodeBase64("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==")
                       .pixExpiresAt(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES));
                break;

            case "BOLETO":
                builder.boletoBarCode("34191.79001 01043.510047 91020.150008 1 84460000002000")
                       .boletoUrl("https://sandbox.asaas.com/b/pdf/" + gatewayPaymentId)
                       .boletoExpiresAt(Instant.now().plus(3, ChronoUnit.DAYS));
                break;

            case "CREDIT_CARD":
            case "DEBIT_CARD":
                builder.authorizationCode("AUTH" + UUID.randomUUID().toString().substring(0, 6))
                       .nsu(UUID.randomUUID().toString().substring(0, 10));
                break;
        }

        log.info("[ASAAS STUB] Pagamento simulado criado: {}", gatewayPaymentId);
        return builder.build();
    }

    @Override
    public GatewayPaymentStatus getPaymentStatus(String gatewayPaymentId) {
        log.info("[ASAAS STUB] Consultando status do pagamento: {}", gatewayPaymentId);

        // TODO: Implementar consulta real à API Asaas

        return GatewayPaymentStatus.builder()
                .gatewayPaymentId(gatewayPaymentId)
                .status(PaymentStatus.PENDING)
                .gatewayStatus("PENDING")
                .message("Aguardando pagamento (STUB)")
                .build();
    }

    @Override
    public boolean cancelPayment(String gatewayPaymentId) {
        log.info("[ASAAS STUB] Cancelando pagamento: {}", gatewayPaymentId);

        // TODO: Implementar cancelamento real na API Asaas

        return true;
    }

    @Override
    public boolean validateWebhook(String signature, String payload) {
        log.info("[ASAAS STUB] Validando webhook (sempre retorna true no STUB)");

        // TODO: Implementar validação real de assinatura

        return true;
    }

    @Override
    public WebhookProcessingResult processWebhook(String payload) {
        log.info("[ASAAS STUB] Processando webhook: {}", payload);

        // TODO: Implementar processamento real do webhook Asaas

        return WebhookProcessingResult.builder()
                .success(true)
                .message("Webhook processado (STUB)")
                .build();
    }
}
