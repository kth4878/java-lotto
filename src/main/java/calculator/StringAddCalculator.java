package calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringAddCalculator {

    private final static String DEFAULT_SPLITTER_START = "[,";
    private final static String DEFAULT_SPLITTER_END = ":]";
    private final static int REGEX_SPLITTER_GROUP = 1;
    private final static int REGEX_STRING_INPUT_GROUP = 2;
    private final static String STRING_ZERO_NUMBER = "0";
    private final static Pattern REG_EXPRESSION_PATTERN = Pattern.compile("//(.)\n(.*)");

    private String splitter;
    private String targetString;

    private List<Number> numbers = new ArrayList<>();

    private StringAddCalculator(String splitter, String targetString) {
        this.splitter = splitter;
        this.targetString = targetString;
    }

    public static StringAddCalculator execute(String inputValue) {
        validateInputValue(inputValue);

        Matcher matcher = REG_EXPRESSION_PATTERN.matcher(inputValue);

        if (matcher.find()) {
            String customSplitter = DEFAULT_SPLITTER_START  + matcher.group(REGEX_SPLITTER_GROUP) + DEFAULT_SPLITTER_END;
            return new StringAddCalculator(customSplitter, matcher.group(REGEX_STRING_INPUT_GROUP));
        }

        return new StringAddCalculator(DEFAULT_SPLITTER_START + DEFAULT_SPLITTER_END, inputValue);
    }

    private static void validateInputValue(String inputValue) {
        if (inputValue == null || inputValue.isBlank()) {
            throw new RuntimeException("값이 null 이거나 비어있습니다.");
        }
    }

    public int sum() {
        saveSplitNumbers();
        return this.numbers.stream().reduce(new Number(STRING_ZERO_NUMBER), Number::add).getValue();
    }

    private void saveSplitNumbers() {
        this.numbers = Arrays.stream(this.targetString.split(this.splitter))
                .map(Number::new)
                .collect(Collectors.toList());
    }
}