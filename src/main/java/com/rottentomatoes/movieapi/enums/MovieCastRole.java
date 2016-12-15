package com.rottentomatoes.movieapi.enums;

import com.rottentomatoes.movieapi.domain.model.MovieCast;

/**
 * This enum is used for the movie cast role filter.
 */
public enum MovieCastRole {

    // Executive Producer
    EXECUTIVE_PRODUCER("EPR", "Executive Producer"),

    // Producers
    PRODUCER("PRO", "Producer"),
    CO_PRODUCER("COP", "Co-producer"),

    // Creators
    CREATOR("CRE", "Creator"),
    CO_CREATOR("COC", "Co-creator"),

    // Screenwriters
    SCREENWRITER("SCR", "Screenwriter"),

    // Directors
    DIRECTOR("DIR", "Director"),
    CO_DIRECTOR("COD", "Co-director"),

    // Performers
    ACTOR("ACT", "Actor"),
    ANCHOR("ANC", "Anchor"),
    ANNOUNCER("ANN", "Announcer"),
    APPEARING("APP", "Appearing"),
    COMMENTATOR("COM", "Commentator"),
    GUEST("GUE", "Guest"),
    HOST("HOS", "Host"),
    JUDGE("JUD", "Judge"),
    MODERATOR("MOD", "Moderator"),
    NARRATOR("NAR", "Narrator"),
    PANELIST("PAN", "Panelist"),
    PERFORMER("PER", "Performer"),
    PLAY_BY_PLAY("PLA", "Play-by-play"),
    REALITY_CAST_MEMBER("REA", "Reality cast member"),
    REPORTER("REP", "Reporter"),
    SIDEKICK("SID", "Sidekick"),
    SPEAKER("SPE", "Speaker"),
    SUBJECT("SUB", "Subject"),
    VOICE("VOC", "Voice");

    private static final MovieCastRole[] CREATORS = new MovieCastRole[] {
            CREATOR, CO_CREATOR
    };
    private static final MovieCastRole[] DIRECTORS = new MovieCastRole[] {
            DIRECTOR, CO_DIRECTOR
    };
    private static final MovieCastRole[] PRODUCERS = new MovieCastRole[] {
            PRODUCER, CO_PRODUCER
    };
    private static final MovieCastRole[] PERFORMERS = new MovieCastRole[] {
            ACTOR, ANCHOR, ANNOUNCER, APPEARING, COMMENTATOR, GUEST, HOST, JUDGE, MODERATOR, NARRATOR, PANELIST,
            PERFORMER, PLAY_BY_PLAY, REALITY_CAST_MEMBER, REPORTER, SIDEKICK, SPEAKER, SUBJECT, VOICE
    };

    private final String name;
    private final String code;

    MovieCastRole(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public boolean sameRole(MovieCast cast) {
        return (getName().equalsIgnoreCase(cast.getRole()) || getCode().equalsIgnoreCase(cast.getRole()));
    }

    public static String getCodeByName(String name) {
        for (MovieCastRole movieCastRole : MovieCastRole.values()) {
            if (movieCastRole.getName().equalsIgnoreCase(name)) {
                return movieCastRole.getCode();
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (MovieCastRole movieCastRole : MovieCastRole.values()) {
            if (movieCastRole.getCode().equalsIgnoreCase(code)) {
                return movieCastRole.getName();
            }
        }
        return null;
    }

    public static boolean isExecutiveProducer(MovieCast cast) {
        return EXECUTIVE_PRODUCER.sameRole(cast);
    }

    public static boolean isProducer(MovieCast cast) {
        for (MovieCastRole role : PERFORMERS) {
            if (role.sameRole(cast)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCreator(MovieCast cast) {
        for (MovieCastRole role : CREATORS) {
            if (role.sameRole(cast)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isScreenwriter(MovieCast cast) {
        return SCREENWRITER.sameRole(cast);
    }

    public static boolean isDirector(MovieCast cast) {
        for (MovieCastRole role : DIRECTORS) {
            if (role.sameRole(cast)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPerformer(MovieCast cast) {
        for (MovieCastRole role : PERFORMERS) {
            if (role.sameRole(cast)) {
                return true;
            }
        }
        return false;
    }

}