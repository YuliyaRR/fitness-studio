CREATE TABLE IF NOT EXISTS app.ingredients_recipes
(
    recipes_uuid uuid NOT NULL,
    product_uuid uuid NOT NULL,
    weight integer NOT NULL,
    CONSTRAINT ingredients_product_uuid_fkey FOREIGN KEY (product_uuid)
        REFERENCES app.product (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT ingredients_recipe_uuid_fkey FOREIGN KEY (recipes_uuid)
        REFERENCES app.recipes (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)