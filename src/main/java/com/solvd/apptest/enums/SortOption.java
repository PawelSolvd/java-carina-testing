package com.solvd.apptest.enums;

import java.util.Comparator;

public enum SortOption {
    BY_NAME_ASC("(?i)(?=.*name)(?=.*ascending)"),
    BY_NAME_DSC("(?i)(?=.*name)(?=.*descending)", Comparator.reverseOrder()),
    BY_PRICE_ASC("(?i)(?=.*price)(?=.*ascending)", Comparator.comparing(s -> Float.parseFloat(s))),
    BY_PRICE_DSC("(?i)(?=.*price)(?=.*descending)", Comparator.<String, Float>comparing(s -> Float.parseFloat(s)).reversed());

    private final String regex;
    private Comparator<String> comparator = Comparator.naturalOrder();

    private SortOption(String regex) {
        this.regex = regex;
    }

    private SortOption(String regex, Comparator<String> comparator) {
        this.regex = regex;
        this.comparator = comparator;
    }

    public String getRegex() {
        return regex;
    }

    public Comparator<String> getComparator() {
        return comparator;
    }
}
