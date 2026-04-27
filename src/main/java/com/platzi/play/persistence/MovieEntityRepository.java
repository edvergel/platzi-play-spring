package com.platzi.play.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.platzi.play.domain.dto.MovieDto;
import com.platzi.play.domain.dto.UpdateMovieDto;
import com.platzi.play.domain.exception.MovieAlreadyExistsException;
import com.platzi.play.domain.exception.MovieNotFoundException;
import com.platzi.play.domain.repository.MovieRepository;
import com.platzi.play.persistence.crud.CrudMovieEntity;
import com.platzi.play.persistence.entity.MovieEntity;
import com.platzi.play.persistence.mapper.MovieMapper;

@Repository
public class MovieEntityRepository implements MovieRepository {
    private final CrudMovieEntity crudMovieEntity;
    private final MovieMapper movieMapper;

    public MovieEntityRepository(CrudMovieEntity crudMovieEntity, MovieMapper movieMapper) {
        this.crudMovieEntity = crudMovieEntity;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDto> getAll() {
        return this.movieMapper.toDto(this.crudMovieEntity.findAll());
    }

    @Override
    public MovieDto getById(Long id) {
        MovieEntity movieEntity = this.crudMovieEntity.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(id));       // Lanza excepción si no existe
        return this.movieMapper.toDto(movieEntity);                    // Lo convierto y retorno en terminos de MovieDto  
    }

    @Override
    public MovieDto save(MovieDto movieDto) {
        if (this.crudMovieEntity.findFirstByTitulo(movieDto.title()) != null) {
            throw new MovieAlreadyExistsException(movieDto.title());
        }

        MovieEntity movieEntity = this.movieMapper.toEntity(movieDto);                   // Convertir MovieDto a MovieEntity
        return this.movieMapper.toDto(this.crudMovieEntity.save(movieEntity));           // Guardar la entidad en la base de datos y convertirla de nuevo a MovieDto para retornarla
    }

    @Override
    public MovieDto update(Long id, UpdateMovieDto updateMovieDto) {
        MovieEntity movieEntity = this.crudMovieEntity.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(
                "No es posible actualizar la pelicula [" + updateMovieDto.title() + "] ya que no esta registrada en BD"));       // Lanza excepción si no existe

        this.movieMapper.updateEntityFromDto(updateMovieDto, movieEntity);

        // Convertimos de Entity a Dto para la respuesta de la petición
        MovieDto movieResponseDto = this.movieMapper.toDto(movieEntity);

        // Guardamos la informacion de la entidad
        this.crudMovieEntity.save(movieEntity);
        return movieResponseDto;
    }

    @Override
    public void delete(Long id) {
        // Validar que la película existe antes de eliminarla
        this.crudMovieEntity.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(id));
        this.crudMovieEntity.deleteById(id);
    }
}
