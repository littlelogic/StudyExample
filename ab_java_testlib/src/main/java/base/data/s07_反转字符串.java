package base.data;

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
    }


    static char[] reverseStrByStack(char[] array) {
        if (array == null && array.length <= 0)
            return array;
        Stack<Character> stringStack=new Stack<>();
        for(Character c : array){
            stringStack.push(c);
        }
        for(int i = 0 ;i < array.length ;i++){
            array[i] = stringStack.pop();
        }
        return array;
    }

    static char[] reverseStrBySwap(char[] array) {
        if (array == null && array.length <= 0)
            return array;
        char temp;
        for(int i = 0 ;i < array.length/2 ;i++){
            temp = array[i];
            array[i] = array[array.length -1 -i];
            array[array.length -1 -i] = temp;
        }
        return array;
    }



    public static int lengthOfLongestSubstring03(String s) {
        int n = s.length(), ans = 0;
        //创建map窗口,i为左区间，j为右区间，右边界移动
        Map<Character, Integer> map = new HashMap<>();
        for (int right = 0, left = 0; right < n; right++) {
            Character curCharacter = s.charAt(right);
            // 如果窗口中包含当前字符，
            if (map.containsKey(curCharacter)) {
                //左边界移动到 相同字符的下一个位置和i当前位置中更靠右的位置，这样是为了防止i向左移动
                left = Math.max(map.get(curCharacter), left);
            }
            //比对当前无重复字段长度和储存的长度，选最大值并替换
            //j-i+1是因为此时i,j索引仍处于不重复的位置，j还没有向后移动，取的[i,j]长度
            ans = Math.max(ans, right - left );
            // 将当前字符为key，下一个索引为value放入map中
            // value为j+1是为了当出现重复字符时，i直接跳到上个相同字符的下一个位置，if中取值就不用+1了
            map.put(curCharacter, right);
        }
        return ans;
    }


    ///字符串中无重复子串的最大数目
    static int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int right = 0, left = 0; right < n; right++){
            Character curCharacter = s.charAt(right);
            if (map.containsKey(curCharacter)) {
                left = Math.max(map.get(curCharacter), left);
            }
            ans = Math.max(ans, right - left );
            map.put(curCharacter, right);
        }
        return ans;
    }

}
