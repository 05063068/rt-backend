package com.rottentomatoes.movieapi.enums;

/**
 * This enum is used for the movie cast role filter.
 */
public enum MovieCastRole {

    ACTORS("actors", "ACT"),
    DIRECTORS("directors", "DIR"),
    PRODUCERS("producers", "PRO"),
    SCREENWRITERS("screenwriters", "SCR"),
    EXECUTIVE_PRODUCERS("executive_producers", "EPR");

    private final String name;
    private final String code;

    MovieCastRole(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public static String getCodeByName(String name) {
        for (MovieCastRole movieCastRole : MovieCastRole.values()) {
            if (movieCastRole.getName().equalsIgnoreCase(name)) {
                return movieCastRole.getCode();
            }
        }
        return null;
    }
}