package arithmetic.main.content.aa_11;

import com.study.wjw.z_utils.Tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class s07_反转字符串 {


     /*


    */

    public static void main(String[] args) {
        String text_str = "123456789";
        char[] array = text_str.toCharArray();
        System.out.println(array);
        reverseStrByStack(array);
        System.out.println(array);
        reverseStrBySwap(array);
        System.out.println(array);

        String text_str2= "abcderffff";
        System.out.println(text_str2);
        System.out.println(lengthOfLongestSubstring(text_str2));
//        lengthOfLongestSubstring(text_str2);
    }


    public static char[] reverseStrByStack(char[] array) {
        if (array == null && array.length <= 0) {
            return array;
        }
        Stack<Character> stringStack = new Stack<>();
        for(Character c : array){
            stringStack.push(c);
        }
        for(int i = 0 ;i < array.length ;i++){
            array[i] = stringStack.pop();
        }
        return array;
    }

    public static char[] reverseStrBySwap(char[] array) {
        if (array == null && array.length <= 0) {
            return array;
        }
        char temp;
        for(int i = 0 ;i < array.length/2 ;i++){
            temp = array[i];
            array[i] = array[array.length -1 -i];
            array[array.length -1 -i] = temp;
        }
        return array;
    }

    static int lengthOfLongestSubstring(String s) {
        int temp = 0;
        if (s == null || s.length() == 0) {
        } else {
            Map<Character, Integer> myMap = new HashMap<Character, Integer>();
            // 用以保存不重复字符串的长度
            int[] strlength = new int[s.length() + 1];
            // 用字符数组表示字符串
            char[] str = s.toCharArray();
            for (int i = 0; i < str.length; i++) {
                Integer lastPosOfChar = myMap.get(str[i]);
                if (lastPosOfChar == null) {
                    // 更新最长无重复子串的长度
                    strlength[i] = i == 0 ? 1 : strlength[i - 1] + 1;
                    // 果map添加该字符和位置
                    myMap.put(str[i], i);
                } else {
                    int aPos = lastPosOfChar + 1;
                    int unRepeatLen = strlength[i - 1];
                    int bPos = i - unRepeatLen;
                    if (aPos >= bPos) {
                        // 当前位置的最长无重复子串长度
                        strlength[i] = i - aPos + 1;
                    } else {
                        // 当前位置的最长无重复子串长度
                        strlength[i] = i - bPos + 1;
                    }
                    // 跟新当前字符出现的位置
                    myMap.put(str[i], i);
                }
            }
            for (int i : strlength) {
                temp = Math.max(temp, i);
            }
        }
        return temp;
    }


}
