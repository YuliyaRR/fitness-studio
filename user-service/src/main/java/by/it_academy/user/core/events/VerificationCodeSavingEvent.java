package by.it_academy.user.core.events;

import by.it_academy.user.core.dto.VerificationCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerificationCodeSavingEvent {
    private final VerificationCode verificationCode;
}
