package app.piper.piper.util;

import lombok.NonNull;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SlugGenerator {

    private static final int SLUG_BUFFER = 10;

    private static final int DEFAULT_MAX_LENGTH = 50;

    public String generateSlug(@NonNull String input) {
        return this.generateSlug(input, DEFAULT_MAX_LENGTH);
    }

    public String generateSlug(@NonNull String input, int maxLength) {
        if (maxLength <= SLUG_BUFFER) {
            throw new IllegalArgumentException(String.format("maxLength must be higher than %s", SLUG_BUFFER));
        }

        int maxCharsToTakeFromInput = maxLength - 10;

        // Take the first maxLength characters from the input
        String truncatedInput = StringUtils.hasText(input)
                ? input.substring(0, Math.min(input.length(), maxCharsToTakeFromInput)) : "";

        // Remove all characters other than alphanumeric and spaces
        String truncatedInputWithOnlyAlphanumericAndSpaces = truncatedInput.replaceAll("[^a-zA-Z0-9\\s]", "");

        // Replace spaces with hyphens and make it all lowercase
        String slugPrefix = truncatedInputWithOnlyAlphanumericAndSpaces.replaceAll("\\s+", "-").toLowerCase();

        String slugSuffix = RandomStringUtils.randomAlphanumeric(SLUG_BUFFER).toLowerCase();

        return slugPrefix + slugSuffix;
    }

}
