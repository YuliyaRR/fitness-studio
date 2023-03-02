package by.it_academy.fitnessstudio.service;

import by.it_academy.fitnessstudio.core.dto.*;
import by.it_academy.fitnessstudio.core.dto.product.Product;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceMultiException;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceSingleException;
import by.it_academy.fitnessstudio.entity.IngredientEntity;
import by.it_academy.fitnessstudio.entity.ProductEntity;
import by.it_academy.fitnessstudio.entity.RecipeEntity;
import by.it_academy.fitnessstudio.repositories.api.RecipeEntityRepository;
import by.it_academy.fitnessstudio.service.api.IRecipeService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeService implements IRecipeService {
    private final RecipeEntityRepository repository;
    private final ProductService productService;
    private final ConversionService conversionService;

    public RecipeService(RecipeEntityRepository repository, ProductService productService, ConversionService conversionService) {
        this.repository = repository;
        this.productService = productService;
        this.conversionService = conversionService;
    }

    @Override
    public void save(RecipeCreate recipeCreate) {
        if(recipeCreate == null) {
            throw new InvalidInputServiceSingleException("Recipe information not submitted for update");
        }

        validate(recipeCreate);
        checkUniqueTitle(recipeCreate);
        checkProductExist(recipeCreate);
        String title = recipeCreate.getTitle();

        //без конвертера
        List<IngredientEntity> comp = recipeCreate.getComposition().stream()
                .map(ingredient -> new IngredientEntity(
                        productService.getEntity(ingredient.getProduct().getUuid()),
                        ingredient.getWeight()))
                .toList();

        RecipeEntity recipeEntity = RecipeEntity.RecipeEntityBuilder.create()
                .setUuid(UUID.randomUUID())
                .setDtCreate(LocalDateTime.now())
                .setDtUpdate(LocalDateTime.now())
                .setTitle(title)
                .setComposition(comp).build();
        repository.save(recipeEntity);

       //используя конвертер для ингредиента
       /*List<IngredientEntity> comp = recipeCreate.getComposition().stream()
                .map(ingredient -> conversionService.convert(ingredient, IngredientEntity.class)).toList();
       repository.save(new RecipeEntity(title, comp));*/

        //используя конвертер для рецепта
        //repository.save(conversionService.convert(recipeCreate, RecipeEntity.class));
    }

    @Override
    public OnePage<Recipe> getRecipesPage(Integer page, Integer size) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException("structured_error");

        if(page == null || page < 0) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Invalid field value. Field must be 0 or greater", "page"));
        }

        if(size == null || size <= 0) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Invalid field value. Field must be greater than 0", "size"));
        }

        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }

        Page<RecipeEntity> all = repository.findAll(PageRequest.of(page, size));

        List<Recipe> recipes = all.get().map(entity -> createRecipe(entity)).toList();

        int number = all.getNumber();
        int sizePage = all.getSize();
        int totalPages = all.getTotalPages();
        long totalElements = all.getTotalElements();
        boolean first = all.isFirst();
        int numberOfElements = all.getNumberOfElements();
        boolean last = all.isLast();

        return new OnePage<>(number, sizePage, totalPages, totalElements, first, numberOfElements, last, recipes);
        //return createOnePage(all, recipes);
    }

    @Override
    public void update(UUID uuid, Long dtUpdate, RecipeCreate recipeCreate) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException("Invalid input");

        if(uuid == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("UUID not entered", "uuid"));
        }

        if(dtUpdate == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("No latest update date", "dt_update"));
        }
        if(dtUpdate != null) {
            if (dtUpdate <= 0) {
                multiException.addSuppressed(new InvalidInputServiceMultiException("Field must be a positive number", "dt_update"));
            }
        }

        if(recipeCreate == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Recipe information not submitted for update", "recipe"));
        }

        //TODO или сначала валидация рецепта, если он не налл, а потом уже выкидывать все искл-я??
        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }

        validate(recipeCreate);

        String recipeCreateTitle = recipeCreate.getTitle();

        Optional<RecipeEntity> recipeById = repository.findById(uuid);

        if(recipeById.isEmpty()) {
            throw new InvalidInputServiceSingleException("Recipe with this uuid doesn't exist");
        }

        checkProductExist(recipeCreate);

        RecipeEntity entity = recipeById.get();

        Long timeUpdate = conversionService.convert(entity.getDtUpdate(), Long.class);

        if(timeUpdate.equals(dtUpdate)) {
            if(!entity.getTitle().equals(recipeCreateTitle)) {
                checkUniqueTitle(recipeCreate);
            }
            entity.setTitle(recipeCreate.getTitle());

            List<IngredientEntity> comp = recipeCreate.getComposition().stream()
                .map(ingredient -> new IngredientEntity(
                        productService.getEntity(ingredient.getProduct().getUuid()),
                        ingredient.getWeight()))
                .collect(Collectors.toList());

            entity.setComposition(comp);
            repository.save(entity);
        } else {
            throw new InvalidInputServiceSingleException("Recipe with this version doesn't exist");
        }
    }
    
    private void validate(RecipeCreate recipeCreate) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException("Invalid input");
        String title = recipeCreate.getTitle();
        
        if(title == null || title.isEmpty() || title.isBlank()) {
             multiException.addSuppressed(new InvalidInputServiceMultiException("Title not entered", "title"));
        }

        List<Ingredient> composition = recipeCreate.getComposition();

        if (composition == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Composition not entered", "composition"));
            throw multiException;
        }

        for (Ingredient ingredient : composition) {
            if (ingredient == null) {
                multiException.addSuppressed(new InvalidInputServiceMultiException("Ingredient not entered", "ingredient"));
            } else {
                Product product = ingredient.getProduct();
                if(product == null) {
                    multiException.addSuppressed(new InvalidInputServiceMultiException("Product not entered", "product"));
                } else {
                    UUID uuid = product.getUuid();
                    if(uuid == null) {
                        multiException.addSuppressed(new InvalidInputServiceMultiException("UUID not entered", "uuid"));
                    }
                }
                int weight = ingredient.getWeight();
                if(weight <= 0) {
                    multiException.addSuppressed(new InvalidInputServiceMultiException("Incorrect weight", "weight"));
                }
            }
        }

        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }
    }

    private void checkUniqueTitle(RecipeCreate recipeCreate) {
        if(repository.existsByTitle(recipeCreate.getTitle())) {
            throw new InvalidInputServiceSingleException("Not a unique recipe name");
        }
    }

    private void checkProductExist(RecipeCreate recipeCreate) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException("structured_error");

        List<Ingredient> composition = recipeCreate.getComposition();
        for (Ingredient ingredient : composition) {
            UUID uuid = ingredient.getProduct().getUuid();
            try {
                productService.getEntity(uuid);
            } catch (InvalidInputServiceSingleException e) {
                multiException.addSuppressed(new InvalidInputServiceMultiException(e.getMessage(), "uuid"));
            }
        }
        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }
    }

    private Recipe createRecipe (RecipeEntity entity) {
        int totalWeight = 0;
        int totalCalories = 0;
        double totalProteins = 0;
        double totalFats = 0;
        double totalCarbohydrates = 0;

        Recipe.RecipeBuilder recipeBuilder = Recipe.RecipeBuilder.create();//TODO: или лучше вынести в поле??

        recipeBuilder.setUuid(entity.getUuid())
                .setDtCreate(conversionService.convert(entity.getDtCreate(), Long.class))
                .setDtUpdate(conversionService.convert(entity.getDtUpdate(), Long.class))
                .setTitle(entity.getTitle());

        List<IngredientEntity> composition = entity.getComposition();

        List<IngredientCalculated> compositionDTO = new ArrayList<>();

        for (IngredientEntity ingredientEntity : composition) {

            IngredientCalculated ingredientCalculated = createIngredient(ingredientEntity);

            compositionDTO.add(ingredientCalculated);

            totalWeight += ingredientCalculated.getWeight();
            totalCalories += ingredientCalculated.getCalories();
            totalProteins += ingredientCalculated.getProteins();
            totalFats += ingredientCalculated.getFats();
            totalCarbohydrates += ingredientCalculated.getCarbohydrates();

        }

        return recipeBuilder.setComposition(compositionDTO)
                .setWeight(totalWeight)
                .setCalories(totalCalories)
                .setProteins(totalProteins)
                .setFats(totalFats)
                .setCarbohydrates(totalCarbohydrates)
                .build();
    }


    private IngredientCalculated createIngredient(IngredientEntity ingredientEntity){
        /*Product.ProductBuilder productBuilder = Product.ProductBuilder.create();
        IngredientCalculated.IngredientCalculatedBuilder ingredientBuilder
                = IngredientCalculated.IngredientCalculatedBuilder.create();

        ProductEntity product = ingredientEntity.getProduct();
        int productWeight = product.getWeight();
        int productCalories = product.getCalories();
        double productProteins = product.getProteins();
        double productFats = product.getFats();
        double productCarbohydrates = product.getCarbohydrates();
        Product prodDTO = productBuilder
                .setUuid(product.getUuid())
                .setDtCreate(product.getDtCreate())
                .setDtUpdate(product.getDtUpdate())
                .setTitle(product.getTitle())
                .setWeight(product.getWeight())
                .setCalories(productCalories)
                .setProteins(productProteins)
                .setFats(productFats)
                .setCarbohydrates(productCarbohydrates)
                .build();

        int weight = ingredientEntity.getWeight();
        int calIngr = (int) Math.round(productCalories * 1.0 / productWeight * weight);
        double protIngr = Precision.round(productProteins / productWeight * weight, 2);
        double fatsIngr = Precision.round(productFats / productWeight * weight, 2);
        double carbIngr = Precision.round(productCarbohydrates / productWeight * weight, 2);

        return ingredientBuilder
                .setProduct(prodDTO).setWeight(weight)
                .setCalories(calIngr).setProteins(protIngr)
                .setFats(fatsIngr).setCarbohydrates(carbIngr)
                .build();*/
        return conversionService.convert(ingredientEntity, IngredientCalculated.class);
    }

    private OnePage<Recipe> createOnePage (Page<RecipeEntity> all, List<Recipe> recipes) {//TODO: builder
        int number = all.getNumber();
        int sizePage = all.getSize();
        int totalPages = all.getTotalPages();
        long totalElements = all.getTotalElements();
        boolean first = all.isFirst();
        int numberOfElements = all.getNumberOfElements();
        boolean last = all.isLast();
        return new OnePage<>(number, sizePage, totalPages, totalElements, first, numberOfElements, last, recipes);
    }

}
