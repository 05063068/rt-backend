package com.rottentomatoes.movieapi.enums;

public enum MpaaRating {
    UNDEFINED, //0
    G,         //1
    PG,        //2
    PG13,      //3
    R,         //4
    NC17,      //5
    NR;        //6

    public static MpaaRating getMpaaRating(String rating) {
        switch (rating) {
            case "G":
                return G;
            case "PG":
                return PG;
            case "PG13":
            case "PG-13":
                return PG13;
            case "R":
                return R;
            case "NC17":
            case "NC-17":
                return NC17;
            case "NR":
                return NR;
            default:
                return UNDEFINED;
        }
    }
}
