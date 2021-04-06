package com.fb;

import java.util.HashMap;

/*

Note: ideally we want to perform a linear operation for benefit of time and space

Note: we need to establish how we move the tail and the head of the window by use of counters as we traverse through the word

Note: we update frequency of this (found) character

Note: when the window is qualified (contains what we want), we will move the head, or-else we move the tail to advance

*/


public class Solution {
    //String res = "";

    private String minWindow(String s, String t) {
        if (s == null || t == null) {
            return "";
        }

        int[] letterCount = new int[128];
        int left = 0, count = 0, minLen = Integer.MAX_VALUE;

        for (char c: t.toCharArray()) {
            ++letterCount[c];
        }

        Integer finalLeft = null;
        Integer finalRight = null;

        for (int right = 0; right < s.length(); right++) {
            if (--letterCount[s.charAt(right)] >= 0) {
                ++count;
            }

            while (count == t.length()) {
                if (minLen > right - left + 1) {
                    minLen = right - left + 1;
                    finalLeft = left;
                    finalRight = right + 1;
                }
                if (++letterCount[s.charAt(left)] > 0) {
                    --count;
                }
                ++left;
            }
        }

        if (finalLeft != null) {
            return s.substring(finalLeft, finalRight);
        } else {
            return "";
        }
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