CREATE TABLE IF NOT EXISTS app.recipes
(
    uuid uuid NOT NULL,
    title text NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    dt_update timestamp(3) without time zone NOT NULL,
    CONSTRAINT recipe_pkey PRIMARY KEY (uuid),
    CONSTRAINT recipes_title_key UNIQUE (title)
)