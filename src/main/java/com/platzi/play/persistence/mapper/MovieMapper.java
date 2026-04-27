package com.platzi.play.persistence.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.platzi.play.domain.dto.MovieDto;
import com.platzi.play.domain.dto.UpdateMovieDto;
import com.platzi.play.persistence.entity.MovieEntity;

@Mapper(componentModel = "spring", uses = {GenreMapper.class, StateMapper.class})   // Le dira a Spring que tiene que tambien inyectar esta dependencia 
public interface MovieMapper {

    Object toEntity = null;

    @Mapping(source = "titulo", target = "title")
    @Mapping(source = "duracion", target = "duration")
    @Mapping(source = "genero", target = "genre", qualifiedByName = "stringToGenre")
    @Mapping(source = "fechaEstreno", target = "releaseDate")
    @Mapping(source = "clasificacion", target = "rating")
    @Mapping(source = "estado", target = "status", qualifiedByName = "stringToBoolean")
    MovieDto toDto(MovieEntity entity);                                                     // Me permitira convertir de MovieEntity (terminos de entidad) a MovieDto (terminos del dominio)
    List<MovieDto> toDto(Iterable<MovieEntity> entities);

    @InheritInverseConfiguration                                                            // Indica que esta configuracion es inversa a la anterior.
    @Mapping(source = "genre", target = "genero", qualifiedByName = "genreToString")        // Hace la conversion de genre a genero.
    @Mapping(source = "status", target = "estado", qualifiedByName = "booleanToString")
    MovieEntity toEntity(MovieDto dto);


    @Mapping(source = "title", target = "titulo")
    @Mapping(source = "releaseDate", target = "fechaEstreno")
    @Mapping(source = "rating", target = "clasificacion")
    // Recibe un updateMovieDto y una MovieEntity, actualiza la MovieEntity con los datos del updateMovieDto y no retorna nada porque el resultado se guarda en la MovieEntity que se le paso como parametro.
    void updateEntityFromDto(UpdateMovieDto updateMovieDto, @MappingTarget MovieEntity movieEntity);              // Permite actualizar una entidad existente con los datos de un DTO. El @MappingTarget indica que el segundo parametro es la entidad que se va a actualizar.
}
