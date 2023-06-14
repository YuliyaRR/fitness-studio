package by.it_academy.user.listener;

import by.it_academy.user.core.events.VerificationCodeSavingEvent;
import by.it_academy.user.core.events.VerificationCodeVerifyEvent;
import by.it_academy.user.service.api.IVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerificationCodeListener {
    private final IVerificationService verificationService;

    @EventListener
    public void handleVerificationCodeSaving(VerificationCodeSavingEvent event) {
        verificationService.save(event.getVerificationCode());
    }

    @EventListener
    public void handleVerificationCodeVerify(VerificationCodeVerifyEvent event) {
        verificationService.verify(event.getVerificationCode());
    }
}
