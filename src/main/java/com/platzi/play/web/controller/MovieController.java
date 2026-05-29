package com.platzi.play.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platzi.play.domain.dto.MovieDto;
import com.platzi.play.domain.dto.SuggestRequestDto;
import com.platzi.play.domain.dto.UpdateMovieDto;
import com.platzi.play.domain.service.MovieService;
import com.platzi.play.domain.service.PlatziPlayAiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movies", description = "Endpoints for managing movies and generating movie suggestions based on user preferences")
public class MovieController {

    /*
    - Forma tradicional de inyectar dependencias sin usar Spring Data JPA
    private final CrudMovieEntity crudMovieEntity;

    public MovieController (CrudMovieEntity crudMovieEntity) {      // Inyección de dependencias a través del constructor
        this.crudMovieEntity = crudMovieEntity;
    }

    - Otra forma que permite inyectar dependencia mediante anotaciones de Spring Data JPA
    @Autowired
    private CrudMovieEntity crudMovieEntity;
    */

    private final MovieService movieService;
    private final PlatziPlayAiService aiService;

    public MovieController (MovieService movieService, PlatziPlayAiService aiService) {      // Inyección de dependencias a través del constructor
        this.movieService = movieService;
        this.aiService = aiService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll() {
        return ResponseEntity.ok(this.movieService.getAll());
    }

    @GetMapping("/{id}")
        @Operation(
        summary = "Obtiene una película por su identificador",
        description = "Retorna la película que coincida con el identificador enviado.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Película encontrada exitosamente"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Película no encontrada",
                content = @Content                              // Si se deja vacio sigifica que no se retorna ningún contenido en caso de error
            )
        }
    )
    public ResponseEntity<MovieDto> getById(@Parameter(description = "Identificador de la película", example = "9") @PathVariable Long id) {
        MovieDto movieDto = this.movieService.getById(id);
        return ResponseEntity.ok(movieDto);
    }

    @PostMapping("/suggest")
    public ResponseEntity<String> generateMovieSuggestion(@RequestBody SuggestRequestDto suggestRequestDto) {
        return ResponseEntity.ok(this.aiService.generateMoviesSuggestion(suggestRequestDto.userPreferences()));

    }

    @PostMapping
    public ResponseEntity<MovieDto> add(@RequestBody @Valid MovieDto movieDto) {
        MovieDto savedMovieDto = this.movieService.save(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovieDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> update(@PathVariable Long id, @RequestBody @Valid UpdateMovieDto updateMovieDto) {
        return ResponseEntity.ok(this.movieService.update(id, updateMovieDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.movieService.delete(id);
        return ResponseEntity.ok().build();
    }
}
