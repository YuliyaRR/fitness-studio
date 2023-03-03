package by.it_academy.fitnessstudio.service;

import by.it_academy.fitnessstudio.core.dto.*;
import by.it_academy.fitnessstudio.core.dto.error.ErrorCode;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceMultiException;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceSingleException;
import by.it_academy.fitnessstudio.entity.IngredientEntity;
import by.it_academy.fitnessstudio.entity.RecipeEntity;
import by.it_academy.fitnessstudio.repositories.api.RecipeEntityRepository;
import by.it_academy.fitnessstudio.service.api.IProductService;
import by.it_academy.fitnessstudio.service.api.IRecipeService;
import by.it_academy.fitnessstudio.validator.api.IValidator;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeService implements IRecipeService {
    private final RecipeEntityRepository repository;
    private final IProductService productService;
    private final ConversionService conversionService;
    private final IValidator<RecipeCreate> validator;

    public RecipeService(RecipeEntityRepository repository, IProductService productService, ConversionService conversionService, IValidator<RecipeCreate> validator) {
        this.repository = repository;
        this.productService = productService;
        this.conversionService = conversionService;
        this.validator = validator;
    }

    @Override
    public void save(RecipeCreate recipeCreate) {
        if(recipeCreate == null) {
            throw new InvalidInputServiceSingleException("Recipe information not submitted for update", ErrorCode.ERROR);
        }

        validator.validate(recipeCreate);
        checkUniqueTitle(recipeCreate);
        checkProductExist(recipeCreate);

        String title = recipeCreate.getTitle();

        List<IngredientEntity> comp = recipeCreate.getComposition().stream()
                .map(ingredient -> new IngredientEntity(
                        productService.getEntity(ingredient.getProduct().getUuid()),
                        ingredient.getWeight()))
                .toList();

        LocalDateTime dtCreate = LocalDateTime.now();
        RecipeEntity recipeEntity = RecipeEntity.RecipeEntityBuilder.create()
                .setUuid(UUID.randomUUID())
                .setDtCreate(dtCreate)
                .setDtUpdate(dtCreate)
                .setTitle(title)
                .setComposition(comp).build();
        repository.save(recipeEntity);
    }

    @Override
    public OnePage<Recipe> getRecipesPage(Integer page, Integer size) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);

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

        List<Recipe> recipes = all.get().map(entity -> calculateRecipe(entity)).toList();

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
    public void update(UUID uuid, Long dtUpdate, RecipeCreate recipeCreate) {//д/приходить LDT во второй параметр
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);

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

        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }

        validator.validate(recipeCreate);

        String recipeCreateTitle = recipeCreate.getTitle();

        Optional<RecipeEntity> recipeById = repository.findById(uuid);

        if(recipeById.isEmpty()) {
            throw new InvalidInputServiceSingleException("Recipe with this uuid doesn't exist", ErrorCode.ERROR);
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
            throw new InvalidInputServiceSingleException("Recipe with this version doesn't exist", ErrorCode.ERROR);
        }
    }

    private void checkUniqueTitle(RecipeCreate recipeCreate) {
        if(repository.existsByTitle(recipeCreate.getTitle())) {
            throw new InvalidInputServiceSingleException("Not a unique recipe name", ErrorCode.ERROR);
        }
    }

    private void checkProductExist(RecipeCreate recipeCreate) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);

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

    private Recipe calculateRecipe(RecipeEntity entity) {
        BigInteger totalWeight = BigInteger.valueOf(0);
        BigInteger totalCalories = BigInteger.valueOf(0);
        BigDecimal totalProteins = BigDecimal.valueOf(0.0);
        BigDecimal totalFats = BigDecimal.valueOf(0.0);
        BigDecimal totalCarbohydrates = BigDecimal.valueOf(0.0);

        Recipe.RecipeBuilder recipeBuilder = Recipe.RecipeBuilder.create();

        recipeBuilder.setUuid(entity.getUuid())
                .setDtCreate(conversionService.convert(entity.getDtCreate(), Long.class))
                .setDtUpdate(conversionService.convert(entity.getDtUpdate(), Long.class))
                .setTitle(entity.getTitle());

        List<IngredientEntity> composition = entity.getComposition();

        List<IngredientCalculated> compositionDTO = new ArrayList<>();

        for (IngredientEntity ingredientEntity : composition) {

            IngredientCalculated ingredientCalculated = calculateIngredient(ingredientEntity);

            compositionDTO.add(ingredientCalculated);

            totalWeight = totalWeight.add(BigInteger.valueOf(ingredientCalculated.getWeight()));
            totalCalories = totalCalories.add(BigInteger.valueOf(ingredientCalculated.getCalories()));
            totalProteins = totalProteins.add(BigDecimal.valueOf(ingredientCalculated.getProteins()));
            totalFats = totalFats.add(BigDecimal.valueOf(ingredientCalculated.getFats()));
            totalCarbohydrates = totalCarbohydrates.add(BigDecimal.valueOf(ingredientCalculated.getCarbohydrates()));
        }

        int scale = 2;
        return recipeBuilder.setComposition(compositionDTO)
                .setWeight(totalWeight.intValue())
                .setCalories(totalCalories.intValue())
                .setProteins(totalProteins.setScale(scale, RoundingMode.HALF_UP).doubleValue())
                .setFats(totalFats.setScale(scale, RoundingMode.HALF_UP).doubleValue())
                .setCarbohydrates(totalCarbohydrates.setScale(scale, RoundingMode.HALF_UP).doubleValue())
                .build();
    }

    private IngredientCalculated calculateIngredient(IngredientEntity ingredientEntity){
        return conversionService.convert(ingredientEntity, IngredientCalculated.class);
    }

}
