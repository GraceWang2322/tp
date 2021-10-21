package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String costPrice} into a {@code Double}.
     */
    public static Double parseCostPrice(String costPrice) throws ParseException {
        try {
            Double.parseDouble(costPrice);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_COSTPRICE_FORMAT);
        }

        if (Double.parseDouble(costPrice) > 0) {
            return Double.parseDouble(costPrice);
        } else {
            throw new ParseException(Messages.MESSAGE_INVALID_COSTPRICE_FORMAT);
        }
    }

    /**
     * Parses {@code String salesPrice} into a {@code Double}.
     */
    public static Double parseSalesPrice(String salesPrice) throws ParseException {
        try {
            Double.parseDouble(salesPrice);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_SALESPRICE_FORMAT);
        }

        if (Double.parseDouble(salesPrice) > 0) {
            return Double.parseDouble(salesPrice);
        } else {
            throw new ParseException(Messages.MESSAGE_INVALID_SALESPRICE_FORMAT);
        }
    }

    /**
     * Parses {@code String count} into a {@code id}.
     */
    public static Integer parseId(String id) throws ParseException {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_ID_FORMAT);
        }
        if (id.length() == 6 && Integer.parseInt(id) > 45) {
            return Integer.parseInt(id);
        } else {
            throw new ParseException(Messages.MESSAGE_INVALID_ID_LENGTH_AND_SIGN);
        }
    }

    /**
     * Parses {@code String count} into a {@code Integer}.
     */
    public static Integer parseCount(String count) throws ParseException {
        try {
            Integer.parseInt(count);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_COUNT_FORMAT);
        }

        if (Integer.parseInt(count) > 0) {
            return Integer.parseInt(count);
        } else {
            throw new ParseException(Messages.MESSAGE_INVALID_COUNT_INTEGER);
        }
    }
}
