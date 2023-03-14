CREATE TABLE IF NOT EXISTS app.product
(
    uuid uuid NOT NULL,
    title text NOT NULL,
    weight integer NOT NULL,
    calories integer NOT NULL,
    proteins double precision NOT NULL,
    fats double precision NOT NULL,
    carbohydrates double precision NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    dt_update timestamp(3) without time zone NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (uuid),
    CONSTRAINT product_title_key UNIQUE (title)
)