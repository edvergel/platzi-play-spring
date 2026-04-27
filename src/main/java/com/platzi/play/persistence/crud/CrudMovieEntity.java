package com.platzi.play.persistence.crud;

import org.springframework.data.repository.CrudRepository;
import com.platzi.play.persistence.entity.MovieEntity;

public interface CrudMovieEntity extends CrudRepository<MovieEntity, Long> {  // Recibe la entidad y el tipo de dato de su clave primaria
    MovieEntity findFirstByTitulo(String titulo);  // Método personalizado para buscar una película por su título
}
