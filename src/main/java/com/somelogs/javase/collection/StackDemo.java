package com.somelogs.javase.collection;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;

/**
 * [()]{} 字符判断
 *
 * @author LBG - 2018/12/13 0013
 */
public class StackDemo {

    public static void main(String[] args) {
        System.out.println(valid("[())]{}")); // false
        System.out.println(valid("[()]{}")); // true
        System.out.println(valid("([]{})")); // true
    }

    private static Boolean valid(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            if (c == '[' || c == '(' || c == '{') {
                stack.push(c);
            } else {
                Character pop = stack.pop();
                if ((c == ']' && pop == '[')
                    || (c == ')' && pop == '(')
                    || (c == '}' && pop == '{')) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }
}
