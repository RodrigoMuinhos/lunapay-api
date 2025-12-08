package com.luna.pay.payment;

import com.luna.pay.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    @GetMapping("/ping")
    public String ping(@AuthenticationPrincipal UserContext user) {
        return "LunaPay OK para tenant " + user.getTenantId() +
                " (user: " + user.getUserId() + ")";
    }
}
