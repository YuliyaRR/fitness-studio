package by.it_academy.fitnessstudio.service;

import by.it_academy.fitnessstudio.core.dto.*;
import by.it_academy.fitnessstudio.core.dto.error.ErrorCode;
import by.it_academy.fitnessstudio.core.dto.ingredient.Ingredient;
import by.it_academy.fitnessstudio.core.dto.ingredient.IngredientCalculated;
import by.it_academy.fitnessstudio.core.dto.recipe.Recipe;
import by.it_academy.fitnessstudio.core.dto.recipe.RecipeCreate;
import by.it_academy.fitnessstudio.core.exception.ConversionTimeException;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceMultiException;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceSingleException;
import by.it_academy.fitnessstudio.entity.IngredientEntity;
import by.it_academy.fitnessstudio.entity.RecipeEntity;
import by.it_academy.fitnessstudio.repositories.api.RecipeEntityRepository;
import by.it_academy.fitnessstudio.service.api.IProductService;
import by.it_academy.fitnessstudio.service.api.IRecipeService;
import by.it_academy.fitnessstudio.validator.api.IValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeService implements IRecipeService {
    private final RecipeEntityRepository repository;
    private final IProductService productService;
    private final ConversionService conversionService;
    private final IValidator<RecipeCreate> validator;

    public RecipeService(RecipeEntityRepository repository,
                         IProductService productService,
                         ConversionService conversionService,
                         IValidator<RecipeCreate> validator) {
        this.repository = repository;
        this.productService = productService;
        this.conversionService = conversionService;
        this.validator = validator;
    }

    @Override
    public void save(@NotNull RecipeCreate recipeCreate) {
        validator.validate(recipeCreate);

        checkUniqueTitle(recipeCreate);
        checkProductExist(recipeCreate);

        String title = recipeCreate.getTitle();

        List<IngredientEntity> comp = recipeCreate.getComposition().stream()
                .map(ingredient -> new IngredientEntity(
                        productService.getEntity(ingredient.getProduct().getUuid()),
                        ingredient.getWeight()))
                .collect(Collectors.toList());

        LocalDateTime dtCreate = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        RecipeEntity recipeEntity = RecipeEntity.RecipeEntityBuilder.create()
                .setUuid(UUID.randomUUID())
                .setDtCreate(dtCreate)
                .setDtUpdate(dtCreate)
                .setTitle(title)
                .setComposition(comp).build();
        repository.save(recipeEntity);
    }

    @Override
    public OnePage<Recipe> getRecipesPage(@NotNull Pageable pageable) {

        Page<RecipeEntity> all = repository.findAll(pageable);

        List<Recipe> recipes = all.get().map(entity -> calculateRecipe(entity)).toList();

        return OnePage.OnePageBuilder.create(recipes)
                .setNumber(all.getNumber())
                .setSize(all.getSize())
                .setTotalPages(all.getTotalPages())
                .setTotalElements(all.getTotalElements())
                .setFirst(all.isFirst())
                .setNumberOfElements(all.getNumberOfElements())
                .setLast(all.isLast())
                .build();
    }

    @Override
    public void update(@NotNull UUID uuid, @NotNull @Past LocalDateTime dtUpdate, @NotNull RecipeCreate recipeCreate) {
        validator.validate(recipeCreate);

        String recipeCreateTitle = recipeCreate.getTitle();

        RecipeEntity entity = repository.findById(uuid)
                .orElseThrow(() -> new InvalidInputServiceSingleException("Recipe with this uuid doesn't exist", ErrorCode.ERROR));

        checkProductExist(recipeCreate);

        if(entity.getDtUpdate().equals(dtUpdate)) {
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

    private void checkUniqueTitle(@NotNull RecipeCreate recipeCreate) {
        if(repository.existsByTitle(recipeCreate.getTitle())) {
            throw new InvalidInputServiceSingleException("Not a unique recipe name", ErrorCode.ERROR);
        }
    }

    private void checkProductExist(@NotNull RecipeCreate recipeCreate) {
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
                .setDtCreate(entity.getDtCreate())
                .setDtUpdate(entity.getDtUpdate())
                .setTitle(entity.getTitle());

        List<IngredientEntity> composition = entity.getComposition();

        List<IngredientCalculated> compositionDTO = new ArrayList<>();

        if(!conversionService.canConvert(IngredientEntity.class, IngredientCalculated.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

        for (IngredientEntity ingredientEntity : composition) {

            IngredientCalculated ingredientCalculated = conversionService.convert(ingredientEntity, IngredientCalculated.class);

            compositionDTO.add(ingredientCalculated);

            totalWeight = totalWeight.add(BigInteger.valueOf(ingredientCalculated.getWeight()));
            totalCalories = totalCalories.add(BigInteger.valueOf(ingredientCalculated.getCalories()));
            totalProteins = totalProteins.add(BigDecimal.valueOf(ingredientCalculated.getProteins()));
            totalFats = totalFats.add(BigDecimal.valueOf(ingredientCalculated.getFats()));
            totalCarbohydrates = totalCarbohydrates.add(BigDecimal.valueOf(ingredientCalculated.getCarbohydrates()));
        }

        int scale = 2;
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        return recipeBuilder.setComposition(compositionDTO)
                .setWeight(totalWeight.intValue())
                .setCalories(totalCalories.intValue())
                .setProteins(totalProteins.setScale(scale, roundingMode).doubleValue())
                .setFats(totalFats.setScale(scale, roundingMode).doubleValue())
                .setCarbohydrates(totalCarbohydrates.setScale(scale, roundingMode).doubleValue())
                .build();
    }
}
