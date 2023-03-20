CREATE TABLE IF NOT EXISTS app.verification
(
    code uuid NOT NULL,
    email text NOT NULL,
    CONSTRAINT verification_pkey PRIMARY KEY (code),
    CONSTRAINT verification_email_key UNIQUE (email)
)