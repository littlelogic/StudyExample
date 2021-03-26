package arithmetic.main.content;

import com.study.wjw.z_utils.Tools;

import java.util.Stack;

public class s07_反转字符串 {


     /*


    */

    public static void main(String[] args) {
        String text_str = "123456789";
        char[] array = text_str.toCharArray();
        System.out.println(array);
        reverseString(array);
        System.out.println(array);
        reverseString2(array);
        System.out.println(array);
    }


    public static char[] reverseString(char[] array) {
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

    public static char[] reverseString2(char[] array) {
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


}
