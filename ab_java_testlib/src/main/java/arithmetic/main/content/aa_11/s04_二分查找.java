package arithmetic.main.content.aa_11;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Tools;

import test.algorithms.ListNode.ListNode;

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
        System.out.println(Tools.getIntArray(arr)+"-binarySearch_FirstEqual第一个等于 5 的结果->"+
                binarySearch_FirstEqual(arr, arr.length,5));
        System.out.println(Tools.getIntArray(arr)+"-binarySearch_FirstEqual_reduce第一个等于 5 的结果->"+
                binarySearch_FirstEqual_reduce(arr, arr.length,5));
        System.out.println(Tools.getIntArray(arr)+"-binarySearch_FirstGreaterEqual第一个大于等于 4 的结果->"+
                binarySearch_FirstGreaterEqual(arr, arr.length,5));
    }

    /*todo
    二分查找,的前提是所给数组为有序，这里为升序
    输出在数组中第一个等于查找值的位置，如果数组中不存在这样的数，则输出数组长度加一。
    方法参数，数组，数组长度，目标值，
    还是三步骤，异常判断，初始值，循环体逻辑
    1，数组长度小于1时，返回1；
    2，左索引赋初值0，右索引赋初值为数据有效长度
    for无限循环体，逻辑
    a，当左索引值 大于 右索引值时，break循环，返回数组长度加一
    b，取中间目标索引值为 左索引与右索引之和除2；
    c，取数组中目标索引上的值与和目标值比较，三种情况，
    d，目标索引上的值大于目标值时，右索引赋初值为中间目标索引值减一；
    e，目标索引上的值小于目标值时，左索引赋初值为中间目标索引值加一；
    f，两值相等时，向左依次判断第一个不等于目标值的索引，返回其后一个索引值加1；
      这里面有两种写法，还是习惯用一个 for无限循环体，
    1，当目标索引值大于0，并且数组中目标索引减1上的值，还等于目标值时，目标索引自减一。
    2，其他情况返回目标索引+1
    类似题型，输出在数组中第一个大于等于查找值的位置，判断条件变为两个大于等于和小于，思路类似。


     */
    public static int binarySearch_FirstEqual(int[] arr, int length,int des) {
        if (length <= 0) {
            return 1;
        }
        int left = 0;
        int right = length - 1;
        for (;;) {
            if (left > right){
                break;
            }
            int middle = (left + right) / 2;
            if (arr[middle] == des) {
                if (middle == 0 || arr[middle - 1] != des) {
                    return middle + 1;
                } else {
                    right = middle - 1;
                }
            } else if (arr[middle] < des) {
                left = middle + 1;
            } else /*if (arr[middle] > des)*/{
                right = middle - 1;
            }
        }
        return length + 1;
    }



    public static int binarySearch_FirstEqual_reduce(int[] arr, int length,int des) {
        if (length <= 0) {
            return 1;
        }
        int left = 0;
        int right = length - 1;
        for (;;) {
            if (left > right){
                break;
            }
            int middle = (left + right) / 2;
            if (arr[middle] == des) {
                for (;;) {
                    if (middle > 0 && arr[middle - 1] == des) {
                        middle--;
                    } else {
                        break;
                    }
                }
                return middle + 1;
            } else if (arr[middle] < des) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return length + 1;
    }


    /*

   题目：
   二分查找 请实现有重复数字的有序数组的二分查找。
   输出在数组中第一个大于等于查找值的位置，如果数组中不存在这样的数，则输出数组长度加一。
   输入 5,4,[1,2,4,4,5]
   输出 3（从1开始的哦。不是index）
   */

    public static int binarySearch_FirstGreaterEqual(int[] arr, int length,int des) {
        if (length <= 0) {
            return 1;
        }
        int left = 0;
        int right = length - 1;
        for (;;) {
            if (left > right){
                break;
            }
            int middle = (left + right) / 2;
            if (arr[middle] >= des) {
                if (middle == 0 || arr[middle - 1] < des) {
                    return middle + 1;
                } else {
                    right = middle - 1;
                }
            } else {
                left = middle + 1;
            }
        }
        return length + 1;
    }


    public static int binarySearch__22(int[] arr, int des) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (arr[middle] == des) {
                return middle;
            } else if (arr[middle] < des) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return -1;
    }

    /**
     * @param n 数组的长度
     * @param v 需要查找的值
     * @param a 已知数组
     * @return 所在的位置哦。不是index，是从1开始的位置哦
     */
    public static int findIt(int n, int v, int[] a) {
        //数组为空时返回值
        if (n == 0)
            return 1;
        //定义区间左值、中值和右值
        int left = 0;
        int right = n - 1;
        int index;
        //当left和right重合时进行最后一次比较
        while (left <= right) {
            index = (left + right) / 2;
            if (a[index] >= v) {//查找值小于等于中值时
                //考虑到第一个值就是所求值的情况，防止index-1数组越界（短路运算）
                if (index == 0 || a[index - 1] < v)
                    return index + 1;
                else
                    right = index - 1;
            } else {//查找值大于中值时
                left = index + 1;
            }
        }
        //未查找到在此处返回数组长度加1
        return n + 1;
    }

    /**
     * 请实现有重复数字的有序数组的二分查找。
     * 输出在数组中第一个大于等于查找值的位置，如果数组中不存在这样的数，则输出数组长度加一
     * 输出位置从1开始计算
     * <p>
     * 二分查找
     *
     * @param n int整型 数组长度
     * @param v int整型 查找值
     * @param a int整型一维数组 有序数组
     * @return int整型
     */
    public static int deal(int n, int v, int[] a) {
        if (n < 2) {
            if (a[0] >= v) {
                return 1;
            } else {
                return 2;
            }
        }
//        boolean tobig = true;
//        if (a[0] > a[1]) {
//            tobig = false;
//        }
        int index = n / 2;
        int num = n / 2;
        for (; ; ) {
            if (num < 0) {
                return n + 1;
            }
            if (a[index] >= v && num == 1) {
                return n + 1;
            }
            if (a[index] < v) {
                num = num / 2;
                index = index + num;
            } else {
                index = index - num;
            }

        }


    }


}
