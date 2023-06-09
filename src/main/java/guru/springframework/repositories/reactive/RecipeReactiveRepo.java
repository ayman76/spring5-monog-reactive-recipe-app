package guru.springframework.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import guru.springframework.domain.Recipe;

public interface RecipeReactiveRepo extends ReactiveMongoRepository<Recipe, String> {

}
