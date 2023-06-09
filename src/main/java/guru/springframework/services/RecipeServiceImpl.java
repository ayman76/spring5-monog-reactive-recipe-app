package guru.springframework.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepo;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by jt on 6/13/17.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepo recipeReactiveRepo;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepo recipeReactiveRepo, RecipeCommandToRecipe recipeCommandToRecipe,
            RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepo = recipeReactiveRepo;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");

        return recipeReactiveRepo.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {

        return recipeReactiveRepo.findById(id);
    }

    @Override
    @Transactional
    public Mono<RecipeCommand> findCommandById(String id) {

        return recipeReactiveRepo.findById(id)
                .map(recipe -> {
                    RecipeCommand command = recipeToRecipeCommand.convert(recipe);
                    command.getIngredients().forEach(rc -> {
                        rc.setRecipeId(command.getId());
                    });
                    return command;
                });

    }

    @Override
    @Transactional
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {

        return recipeReactiveRepo.save(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert);

    }

    @Override
    public void deleteById(String idToDelete) {
        recipeReactiveRepo.deleteById(idToDelete).block();
    }
}
