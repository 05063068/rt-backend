package com.rottentomatoes.movieapi.enums;

public enum Country {

    UNITED_STATES("us", "usa"),
    UNITED_KINGDOM("uk", "gbr"),
    NEW_ZEALAND("nz", "nzl"),
    AUSTRALIA("au", "aus"),
    CANADA("ca", "can"),
    IRELAND("ie", "irl");

    private final String countryCode;
    private final String countryCodeThreeLetter;

    Country(String countryCode, String countryCodeThreeLetter) {
        this.countryCode = countryCode;
        this.countryCodeThreeLetter = countryCodeThreeLetter;
    }

    // Only supporting 'first class' countries since these are all we
    // have data for.
    public static Country getCountryEnumFromString(String country) {
        switch(country.toLowerCase()) {
            case "uk":
            case "ie": // Ireland uses UK data as of 12/15/2016.
                return UNITED_KINGDOM;
            case "us":
            case "ca": // Canada is using US data as of 11/22/2016 for all objects.
                return UNITED_STATES;
            case "nz": // New Zealand using Austraila data as of 12/29/2016
            case "au":
                return AUSTRALIA;
            default:
                return UNITED_STATES;
        }
    }
    public String getCountryCode() {
        return countryCode;
    }

    public String getThreeLetterCountryCode() {
        return countryCodeThreeLetter;
    }

    public static Country getDefault() { return Country.UNITED_STATES; }
}
