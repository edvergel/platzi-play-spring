package com.platzi.play.persistence.mapper;

import org.mapstruct.Named;

import com.platzi.play.domain.Genre;

public class GenreMapper {

    @Named("stringToGenre")
    public static Genre StringToGenre(String genero) {
        if (genero == null) return null;

        return switch (genero.toUpperCase()) {
            case "ACCION" -> Genre.ACTION;
            case "COMEDIA" -> Genre.COMEDY;
            case "DRAMA" -> Genre.DRAMA;
            case "ANIMADA" -> Genre.ANIMATED;
            case "TERROR" -> Genre.HORROR;
            case "CIENCIA_FICCION" -> Genre.SCI_FI;
            default -> null;
        };

    }

    @Named("genreToString")
    public static String GenreToString(Genre genre) {
        if (genre == null) return null;

        return switch (genre) {
            case ACTION -> "ACCION";
            case COMEDY -> "COMEDIA";
            case DRAMA -> "DRAMA";
            case ANIMATED -> "ANIMADA";
            case HORROR -> "TERROR";
            case SCI_FI -> "CIENCIA_FICCION";
            default -> null;
        };
    }
}
