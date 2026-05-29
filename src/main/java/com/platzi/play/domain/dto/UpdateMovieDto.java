package com.platzi.play.domain.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

public record UpdateMovieDto(
    @NotBlank(message = "El titulo es obligatorio")
    String title,

    @PastOrPresent(message = "La fecha de lanzamiento no puede ser futura")
    LocalDate releaseDate,

    @Min(value = 0, message = "El rating no puede ser mayor que 0")
    @Max(value = 5, message = "El rating no puede ser mayor que 5")
    Double rating
) {

}
