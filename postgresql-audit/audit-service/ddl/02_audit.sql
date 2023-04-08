CREATE TABLE IF NOT EXISTS app.audit
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    user_uuid uuid,
    user_mail text,
    user_fio text,
    user_role text,
    text text NOT NULL,
    type text NOT NULL,
    id text NOT NULL,
    CONSTRAINT audit_pkey PRIMARY KEY (uuid)
)