CREATE TABLE IF NOT EXISTS app.audit
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    user_uuid uuid NOT NULL,
    user_mail text NOT NULL,
    user_fio text NOT NULL,
    user_role text NOT NULL,
    text text NOT NULL,
    type text NOT NULL,
    id text NOT NULL,
    CONSTRAINT audit_pkey PRIMARY KEY (uuid)
)