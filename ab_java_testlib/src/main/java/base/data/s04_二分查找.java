package base.data;

import com.study.wjw.z_utils.Tools;

public class s04_二分查找 {


    /*

    题目：
    二分查找 请实现有重复数字的有序数组的二分查找。
    输出在数组中第一个大于等于查找值的位置，如果数组中不存在这样的数，则输出数组长度加一。
    输入 5,4,[1,2,4,4,5]
    输出 3（从1开始的哦。不是index）
    */

    public static void main(String[] args) {

        int[] arr = {1, 2, 2, 3, 5, 5, 5, 6, 7, 8, 11, 15, 17};
        System.out.println(Tools.getIntArray(arr)+"-binarySearch_FirstEqual第一个等于 5 索引值的结果->"+
                binarySearch_FirstEqual(arr,5));
    }


    //二分查找,数组有序，返回第一个等于目标值的索引，没有返回-1
    static int binarySearch_FirstEqual(int[] arr,int des) {
        if (arr == null || arr.length <= 0)
            return -1;
        int left = 0;
        int right = arr.length - 1;
        for (;;) {
            if (left > right)
                break;
            int middle = (left + right) / 2;
            if (arr[middle] == des) {
                if (middle == 0 || arr[middle - 1] != des) {
                    return middle;
                } else {/**<循环向前>索引也可行，但是在相同数据
                    较多的情况<效率不高>，总体考虑是还继续<二分>*/
                    right = middle - 1;
                }
            } else if (arr[middle] < des) {
                left = middle + 1;
            } else /*if (arr[middle] > des)*/{
                right = middle - 1;
            }
        }
        return -1;
    }


}
