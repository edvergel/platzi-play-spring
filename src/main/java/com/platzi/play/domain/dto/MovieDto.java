package com.platzi.play.domain.dto;

import java.time.LocalDate;

import com.platzi.play.domain.Genre;

public record MovieDto(
    Long id,
    String title,
    Integer duration,
    Genre genre,
    LocalDate releaseDate,
    Double rating,
    Boolean status
) {

}
