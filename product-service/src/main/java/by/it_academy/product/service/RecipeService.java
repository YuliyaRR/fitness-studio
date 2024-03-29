package by.it_academy.product.service;

import by.it_academy.product.audit.AspectAudit;
import by.it_academy.product.audit.AuditAction;
import by.it_academy.product.audit.EssenceType;
import by.it_academy.product.core.dto.error.ErrorCode;
import by.it_academy.product.core.dto.ingredient.Ingredient;
import by.it_academy.product.core.dto.ingredient.IngredientCalculated;
import by.it_academy.product.core.dto.recipe.Recipe;
import by.it_academy.product.core.dto.recipe.RecipeCreate;
import by.it_academy.product.core.exception.ConversionTimeException;
import by.it_academy.product.core.exception.InvalidInputServiceMultiException;
import by.it_academy.product.core.exception.InvalidInputServiceSingleException;
import by.it_academy.product.entity.IngredientEntity;
import by.it_academy.product.entity.RecipeEntity;
import by.it_academy.product.repositories.api.RecipeEntityRepository;
import by.it_academy.product.service.api.IProductService;
import by.it_academy.product.service.api.IRecipeService;
import by.it_academy.product.validator.api.IValidator;
import by.it_academy.product.core.dto.OnePage;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class RecipeService implements IRecipeService {
    private final RecipeEntityRepository repository;
    private final IProductService productService;
    private final ConversionService conversionService;
    private final IValidator<RecipeCreate> validator;

    @Override
    @Transactional
    @AspectAudit(action = AuditAction.CREATE_RECIPE, type = EssenceType.RECIPE)
    public UUID save(@NotNull RecipeCreate recipeCreate) {
        validator.validate(recipeCreate);

        checkUniqueTitle(recipeCreate);
        checkProductExist(recipeCreate);

        String title = recipeCreate.getTitle();

        List<IngredientEntity> comp = recipeCreate.getComposition().stream()
                .map(ingredient -> new IngredientEntity(
                        productService.getEntity(ingredient.getProduct().getUuid()),
                        ingredient.getWeight()))
                .collect(Collectors.toList());

        UUID uuid = UUID.randomUUID();
        LocalDateTime dtCreate = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        RecipeEntity recipeEntity = RecipeEntity.builder()
                .setUuid(uuid)
                .setDtCreate(dtCreate)
                .setDtUpdate(dtCreate)
                .setTitle(title)
                .setComposition(comp).build();

        repository.saveAndFlush(recipeEntity);

        return uuid;
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
    @Transactional
    @AspectAudit(action = AuditAction.UPDATED_RECIPE, type = EssenceType.RECIPE)
    public UUID update(@NotNull UUID uuid, @NotNull @Past LocalDateTime dtUpdate, @NotNull RecipeCreate recipeCreate) {
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

            repository.saveAndFlush(entity);

        } else {
            throw new InvalidInputServiceSingleException("Recipe with this version doesn't exist", ErrorCode.ERROR);
        }
        return uuid;
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

        Recipe.RecipeBuilder recipeBuilder = Recipe.builder();

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
