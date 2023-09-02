package app.piper.piper.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SlugGeneratorTest {

    private final SlugGenerator slugGenerator = new SlugGenerator();

    @Test
    void testGenerateSlugWithoutMaxLength() {
        String input = "Hello World! without max length()_)) this is a super long function name, maybe it's even more than 50 chars you know. such a long input";
        int defaultMaxLength = 50;
        String slug = slugGenerator.generateSlug(input);

        assertNotNull(slug);
        assertTrue(slug.length() <= defaultMaxLength);

        // Check if slug contains only lowercase alphanumeric characters and hyphens
        assertTrue(slug.matches("^[a-z0-9\\-]+$"));
    }

    @Test
    void testGenerateSlug() {
        String input = "Hello World! This is a test input.";
        int maxLength = 50;
        String slug = slugGenerator.generateSlug(input, maxLength);

        assertNotNull(slug);
        assertTrue(slug.length() <= maxLength);

        // Check if slug contains only lowercase alphanumeric characters and hyphens
        assertTrue(slug.matches("^[a-z0-9\\-]+$"));
    }

    @Test
    void testGenerateSlugWithShortMaxLength() {
        String input = "Short input";
        int maxLength = 5; // Less than SLUG_BUFFER
        assertThrows(IllegalArgumentException.class, () -> slugGenerator.generateSlug(input, maxLength));
    }

    @Test
    void testGenerateSlugWithEmptyInput() {
        String input = "";
        int maxLength = 20;
        String slug = slugGenerator.generateSlug(input, maxLength);

        assertNotNull(slug);
        assertTrue(slug.length() <= maxLength);
        assertTrue(slug.length() > 1);
    }

    @Test
    void testGenerateSlugWithSpecialCharacters() {
        String input = "!@#$%^&*()_+";
        int maxLength = 20;
        String slug = slugGenerator.generateSlug(input, maxLength);

        assertNotNull(slug);
        assertTrue(slug.length() <= maxLength);
        assertTrue(slug.matches("^[a-z0-9\\-]+$"));
    }

}