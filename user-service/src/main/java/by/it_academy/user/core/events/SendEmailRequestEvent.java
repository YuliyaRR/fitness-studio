package by.it_academy.user.core.events;

import by.it_academy.user.core.dto.mail.EmailDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class SendEmailRequestEvent {
    private final EmailDetails emailDetails;
}
