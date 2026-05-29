package com.platzi.platzi_play;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.platzi.play.domain.Genre;
import com.platzi.play.domain.dto.MovieDto;
import com.platzi.play.domain.dto.UpdateMovieDto;
import com.platzi.play.domain.repository.MovieRepository;
import com.platzi.play.domain.service.MovieService;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    @DisplayName("Debería retornar la película correcta cuando se busca por un ID existente")
    void deberiaRetornarPeliculaCuandoElIdExiste() {
        // 1. ARRANGE
        Long idBuscar = 1L;
        MovieDto peliculaSimulada = new MovieDto(
            idBuscar, "Inception", 148, Genre.SCI_FI, LocalDate.of(2010, 7, 16), 4.8, true
        );

        when(movieRepository.getById(idBuscar)).thenReturn(peliculaSimulada);

        // 2. ACT
        MovieDto resultado = movieService.getById(idBuscar);

        // 3. ASSERT
        assertNotNull(resultado);
        assertEquals(idBuscar, resultado.id());
        assertEquals("Inception", resultado.title());
        verify(movieRepository, times(1)).getById(idBuscar);
    }

    @Test
    @DisplayName("Debería guardar y retornar la película cuando los datos son válidos")
    void deberiaGuardarPeliculaExitosamente() {
        // 1. ARRANGE
        // Datos que envía el usuario (sin ID asignado aún, pasamos null)
        MovieDto movieAPedir = new MovieDto(
            null, "Interstellar", 169, Genre.SCI_FI, LocalDate.of(2014, 11, 7), 4.9, true
        );
        
        // Datos que simula retornar la base de datos (ya con su ID generado)
        MovieDto movieGuardada = new MovieDto(
            10L, "Interstellar", 169, Genre.SCI_FI, LocalDate.of(2014, 11, 7), 4.9, true
        );
        
        when(movieRepository.save(any(MovieDto.class))).thenReturn(movieGuardada);

        // 2. ACT
        MovieDto resultado = movieService.save(movieAPedir);

        // 3. ASSERT
        assertNotNull(resultado, "El resultado guardado no debería ser nulo");
        assertEquals(10L, resultado.id(), "Debería retornar el ID generado por el repositorio");
        assertEquals("Interstellar", resultado.title());
        verify(movieRepository, times(1)).save(movieAPedir);
    }

    @Test
    @DisplayName("Debería actualizar los datos de una película existente")
    void deberiaActualizarPeliculaExitosamente() {
        // 1. ARRANGE
        Long idPelicula = 1L;
        
        // Supongamos que UpdateMovieDto solo modifica el título y el rating
        UpdateMovieDto datosActualizacion = new UpdateMovieDto("Inception Remastered", null, 5.0);
        
        // El repositorio debería devolver la película con los datos ya cambiados
        MovieDto peliculaActualizada = new MovieDto(
            idPelicula, "Inception Remastered", 148, Genre.SCI_FI, LocalDate.of(2010, 7, 16), 5.0, true
        );

        // OJO AQUÍ: Cuando el método recibe múltiples parámetros, si usas un Matcher como 'any()', 
        // todos los demás parámetros deben usar obligatoriamente matchers, como 'eq()' para valores exactos.
        when(movieRepository.update(eq(idPelicula), any(UpdateMovieDto.class))).thenReturn(peliculaActualizada);

        // 2. ACT
        MovieDto resultado = movieService.update(idPelicula, datosActualizacion);

        // 3. ASSERT
        assertNotNull(resultado);
        assertEquals("Inception Remastered", resultado.title(), "El título debió cambiar al valor actualizado");
        assertEquals(5.0, resultado.rating(), "El rating debió cambiar al valor actualizado");
        verify(movieRepository, times(1)).update(idPelicula, datosActualizacion);
    }

    @Test
    @DisplayName("Debería ejecutar la eliminación correctamente al invocar delete")
    void deberiaEliminarPeliculaPorId() {
        // 1. ARRANGE
        Long idEliminar = 1L;

        // 2. ACT
        movieService.delete(idEliminar);

        // 3. ASSERT
        verify(movieRepository, times(1)).delete(idEliminar);
    }
}