package com.fb;

class CipherTest {
    private static final int TOTAL_NUMBER_OF_ALPHABETS = 26;
    private static final int TOTAL_NUMBER_OF_DIGITS = 10;
    private static final int LOWEST_NUMBER_DIGIT = 0;
    private static final int HIGHEST_NUMBER_DIGIT = 9;
    private static final int NOTHING_TO_SHIFT = 0;

    private String doCipher(String input, int shift) {
        StringBuilder builder = new StringBuilder();

        for (int x = 0; x < input.length(); x++) {
            char character = input.charAt(x);

            if (Character.isDigit(character)) {
                mapNumberByShifter(builder, character, shift);
            } else if (Character.isLetter(character)) {
                mapLetterByShifter(builder, character, shift);
            } else {
                builder.append(character);
            }
        }

        return builder.toString();

    }

    private void mapNumberByShifter(StringBuilder builder,  char charInput, int shift) {
        int numberInput = Character.getNumericValue(charInput);
        int output;

        if (shift == NOTHING_TO_SHIFT  || shift == TOTAL_NUMBER_OF_DIGITS) {
            builder.append(charInput);
        } else {
            output = numberInput + (shift % TOTAL_NUMBER_OF_DIGITS);

            // If outside the numeric range [0-9] adjust it
            if (output > HIGHEST_NUMBER_DIGIT) {
                output = output - TOTAL_NUMBER_OF_DIGITS;
            } else if (output < LOWEST_NUMBER_DIGIT) {
                output = output + TOTAL_NUMBER_OF_DIGITS;
            }
            builder.append(output);
        }
    }

    private void mapLetterByShifter(StringBuilder builder,  char charInput, int shift) {
        char output;

        if (shift == NOTHING_TO_SHIFT  || shift == TOTAL_NUMBER_OF_ALPHABETS) {
            builder.append(charInput);
        } else {
            output = (char)(charInput + (shift % TOTAL_NUMBER_OF_ALPHABETS));

            // If outside the alphabet range adjust for:
            // 1. case-mismatch between input/output
            // 2. non-alpha output or reverse case-mismatch between input/output
            if (Character.isLowerCase(charInput) && Character.isUpperCase(output)) {
                output = (char) (output + TOTAL_NUMBER_OF_ALPHABETS);
            } else if (!Character.isLetter(output) || (Character.isUpperCase(charInput) && Character.isLowerCase(output)) ) {
                output = (char) (output - TOTAL_NUMBER_OF_ALPHABETS);
            }

            builder.append(output);
        }
    }

    private void testAlphaNumericWordWithSpecialCharacters(String input, int rotationFactor, String expected)  {
        String output = doCipher(input, rotationFactor);

        if (expected.equals(output)) {
            System.out.println("PASSED - output: " + output + ", expected: " + expected);
        } else {
            System.out.println("FAILED - output: " + output + ", expected: " + expected);
        }
    }

    private void run() {
        testAlphaNumericWordWithSpecialCharacters("All-convoYs-9-be:Alert1.", 4, "Epp-gsrzsCw-3-fi:Epivx5.");
        testAlphaNumericWordWithSpecialCharacters("abcdZXYzxy-999.@", 200, "stuvRPQrpq-999.@");

    }

    public static void main(String[] args) {
        new CipherTest().run();
    }
}