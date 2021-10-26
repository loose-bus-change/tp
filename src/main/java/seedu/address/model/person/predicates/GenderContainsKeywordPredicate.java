package seedu.address.model.person.predicates;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Gender} matches the gender given.
 */
public class GenderContainsKeywordPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public GenderContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getGender().value.toLowerCase(Locale.ROOT).contains(
                        getGenderValueFromKeyword(keyword)
                ));
    }

    private String getGenderValueFromKeyword(String keyword) {
        String test = keyword.toLowerCase(Locale.ROOT);
        if (test.equals("m") || test.equals("male")) {
            return "m";
        } else if (test.equals("f") || test.equals("female")) {
            return "f";
        } else {
            return "THIS IS NOT A GENDER";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenderContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((GenderContainsKeywordPredicate) other).keywords)); // state check
    }
}
