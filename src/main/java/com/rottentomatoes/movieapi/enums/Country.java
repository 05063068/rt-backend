package com.rottentomatoes.movieapi.enums;

public enum Country {

    UNITED_STATES("us"),
    UNITED_KINGDOM("uk"),
    NEW_ZEALAND("nz"),
    AUSTRALIA("au"),
    CANADA("ca"),
    IRELAND("ie");

    private final String countryCode;

    Country(String countryCode) {
        this.countryCode = countryCode;
    }

    // Only supporting 'first class' countries since these are all we
    // have data for.
    public static Country getCountryEnumFromString(String country) {
        switch(country.toLowerCase()) {
            case "uk":
                return UNITED_KINGDOM;
            case "us":
                return UNITED_STATES;
            case "nz":
                return NEW_ZEALAND;
            case "au":
                return AUSTRALIA;
            case "ca":
                return CANADA;
            case "ie":
                return IRELAND;
            default:
                return UNITED_STATES;
        }
    }
    public String getCountryCode() {
        return countryCode;
    }
    public static Country getDefault() { return Country.UNITED_STATES; }
}
