package com.fb;

import java.util.HashMap;

/*

Note: ideally we want to perform a linear operation for benefit of time and space

Note: we need to establish how we move the tail and the head of the window by use of counters as we traverse through the word

Note: we update frequency of this (found) character

Note: when the window is qualified (contains what we want), we will move the head, or-else we move the tail to advance

*/


public class Solution {

    private String minWindow(String s, String t) {
        HashMap<Character, Integer> charFrequencyMap = new HashMap<>();

        // populate the frequency map with the unique characters from string 's'
        // we don't care about the number of appearances that are in the 's' string.
        for (char c: s.toCharArray()) {
            charFrequencyMap.put(c, 0);
        }

        // we update the frequency map upon finding a match with a character in string 't'
        // if there is a character that in t not found in the frequency map we exit
        for (char key: t.toCharArray()) {
            if (charFrequencyMap.containsKey(key)) {
                charFrequencyMap.put(key, charFrequencyMap.get(key) + 1 ); // we increase the frequency of the char found
            } else {
                // if no match found end of story - go home (exit)
                return "";
            }
        }

        // Next create the window properties: we will use these to substring the string later when we know the positions
        int minStart = 0;
        int minLength = Integer.MAX_VALUE;

        int numberOfTargets = t.length();
        int head = 0;

        // ***** we need 2 loops ****
        // 1. outer loop  to iterate over the search string
        // 2. inner loop is the window

        for (int tail = 0; tail < s.length(); tail++) {
            char current = s.charAt(tail);

            // if this is one of the required characters we decrease the target by one
            if (charFrequencyMap.get(current) > 0) {
                numberOfTargets--;
            }

            // same time we update frequency of this character (because we have already iterated through this character)
            charFrequencyMap.put(current, charFrequencyMap.get(current) - 1);

            // window:  We now need to determine do we move the head or the tail (expand or contract)
            while (numberOfTargets == 0) {
                int nextPosition = (tail - head) + 1;

                if (minLength > nextPosition ) {
                    minStart = head;
                    minLength = nextPosition;
                }

                char charAtHead = s.charAt(head);

                // this is the required character
                if (charFrequencyMap.get(charAtHead) >= 0) {
                    numberOfTargets++;
                }

                // whilst the window satisfies the requirement we will move the head of the window
                charFrequencyMap.put(charAtHead, charFrequencyMap.get(charAtHead) + 1);

                head++;
            }
        }

        // we check if min length has been established and then do teh substring right at the end
        return minLength == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLength);
    }

    private void testSolution(String s, String t, String expected)  {
        String output = minWindow(s, t);

        if (expected.equals(output)) {
            System.out.println("PASSED - output: " + output + ", expected: " + expected);
        } else {
            System.out.println("FAILED - output: " + output + ", expected: " + expected);
        }
    }

    public static void main(String[] args) {
        new Solution().testSolution("ADOBECODEBANC", "ABC", "BANC");
        new Solution().testSolution("dcbefebce", "fd", "dcbef");
        new Solution().testSolution("bfbeadbcbcbfeaaeefcddcccbbbfaaafdbebedddf", "cbccfafebccdccebdd", "");
    }
}