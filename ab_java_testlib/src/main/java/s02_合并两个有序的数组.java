import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Tools;

import java.util.Arrays;

import test.algorithms.ListNode.ListNode;

public class s02_合并两个有序的数组 {




    static int[] nums1;
    static int[] nums2;

    public static void initArray_toBig() {
        nums1 = new int[]{ 1,2,3,0,0,0 };
        nums2 = new int[]{ 2,5,6 };
    }
    public static void initArray_toSmall() {
        nums1 = new int[]{ 3,2,1,0,0,0 };
        nums2 = new int[]{ 6,5,2 };
    }

    public static void main(String[] args) {

        initArray_toBig();
        initArray_toBig();
        merge_2(nums1,3,nums2,3);
        Log.print("merge_2: ");Tools.printIntArray(nums1);

        initArray_toBig();
        merge_3(nums1,3,nums2,3);
        Log.print("merge_3: ");Tools.printIntArray(nums1);


    }


    //====================================

    ///合并两个有序数组，nums1有足够的长度，m和n为数组的有效位数
    public static void merge_2(int[] nums1, int m, int[] nums2, int n) {
        int [] nums1_copy = new int[m];
        System.arraycopy(nums1, 0, nums1_copy, 0, m);

        int p1 = 0;
        int p2 = 0;
        int p = 0;
        while ((p1 < m) && (p2 < n))
            nums1[p++] = (nums1_copy[p1] < nums2[p2]) ?
                    nums1_copy[p1++] : nums2[p2++];

        if (p1 < m)
            System.arraycopy(nums1_copy, p1, nums1, p , m + n - p1 - p2);
        if (p2 < n)
            System.arraycopy(nums2, p2, nums1, p1 + p2, m + n - p1 - p2);
    }

    //倒序插入,不需新建数组
    public static void merge_3(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;
        while ((p1 >= 0) && (p2 >= 0))
            nums1[p--]=(nums1[p1] < nums2[p2])? nums2[p2--]: nums1[p1--];
        System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
    }

    //====================================



}
