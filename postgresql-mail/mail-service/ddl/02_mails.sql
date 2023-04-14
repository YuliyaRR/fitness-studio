CREATE TABLE IF NOT EXISTS app.mails
(
    uuid uuid NOT NULL,
    message text NOT NULL,
    email text NOT NULL,
    is_send boolean NOT NULL,
    attempt_of_sending integer NOT NULL,
    limit_of_attempts integer NOT NULL DEFAULT 5,
    theme text NOT NULL,
    CONSTRAINT mails_pkey PRIMARY KEY (uuid)
)