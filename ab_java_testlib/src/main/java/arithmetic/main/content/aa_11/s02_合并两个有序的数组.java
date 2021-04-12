package arithmetic.main.content.aa_11;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Tools;

import java.util.Arrays;

import test.algorithms.ListNode.ListNode;

public class s02_合并两个有序的数组 {



/*

判断链表是否有环这个问题应该已经算“经典”问题了，就不说了，百度下一堆一堆的。
那么为什么快慢指针一定会相遇？

首先两者要相遇，肯定是在那个环里面（比如最好情况慢的指针一踏入环就和快指针相遇）。
然后我们要明确快慢指针的速度差为1，两者每移动一下，距离减1，而这个环的最小划分单位就是1，所以显然会相遇。

假如说，当快指针刚刚绕到慢指针后面时，快指针离慢指针有n步。那么，对于接下来的每一次“快指针往前走两步、慢指针往前走一步”，
快指针和慢指针之间的距离由n步变成n-1步、由n-1步变成n-2步、……、由3步变成2步、由2步变成1步、由1步变成0步。
所以对于有环的情况，快慢指针一定会相遇。


*/


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
        merge(nums1,3,nums2,3);
        Log.print("merge: ");Tools.printIntArray(nums1);

        initArray_toBig();
        merge_2(nums1,3,nums2,3);
        Log.print("merge_2: ");Tools.printIntArray(nums1);

        initArray_toBig();
        merge_3(nums1,3,nums2,3);
        Log.print("merge_3: ");Tools.printIntArray(nums1);

        initArray_toBig();
        merge_test(nums1,3,nums2,3);
        Log.print("merge_test: ");Tools.printIntArray(nums1);

        initArray_toSmall();
        merge_test(nums1,3,nums2,3);
        Log.print("merge_test toSmall: ");Tools.printIntArray(nums1);

        initArray_toSmall();
        merge_test_2(nums1,3,nums2,3);
        Log.print("merge_test_2 toSmall: ");Tools.printIntArray(nums1);


    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);
    }

    public static void merge_2(int[] nums1, int m, int[] nums2, int n) {
        // Make a copy of nums1.
        int [] nums1_copy = new int[m];
        System.arraycopy(nums1, 0, nums1_copy, 0, m);

        // Two get pointers for nums1_copy and nums2.
        int p1 = 0;
        int p2 = 0;

        // Set pointer for nums1
        int p = 0;

        // Compare elements from nums1_copy and nums2
        // and add the smallest one into nums1.
        while ((p1 < m) && (p2 < n))
            nums1[p++] = (nums1_copy[p1] < nums2[p2]) ? nums1_copy[p1++] : nums2[p2++];

        // if there are still elements to add
        if (p1 < m)
            System.arraycopy(nums1_copy, p1, nums1, p1 + p2, m + n - p1 - p2);
        if (p2 < n)
            System.arraycopy(nums2, p2, nums1, p1 + p2, m + n - p1 - p2);
    }

    public static void merge_3(int[] nums1, int m, int[] nums2, int n) {
        // two get pointers for nums1 and nums2
        int p1 = m - 1;
        int p2 = n - 1;
        // set pointer for nums1
        int p = m + n - 1;

        // while there are still elements to compare
        while ((p1 >= 0) && (p2 >= 0))
            // compare two elements from nums1 and nums2
            // and add the largest one in nums1
            nums1[p--] = (nums1[p1] < nums2[p2]) ? nums2[p2--] : nums1[p1--];

        // add missing elements from nums2
        System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
    }

    //====================================

    public static void merge_test(int[] nums1, int m, int[] nums2, int n) {
        if (nums2 == null || nums1 == null) {
            return ;
        }
        int[] nums1_temp = new int[m];
        System.arraycopy(nums1,0,nums1_temp,0,m);
        int index_m = 0;
        int index_n = 0;
        int index = 0;
        boolean toBig = true;
        if (m > 1) {
            if (nums1[0] > nums1[1]) {
                toBig = false;
            }
        } else if (n > 1) {
            if (nums2[0] > nums2[1]) {
                toBig = false;
            }
        }

        for (;;) {
            if (index_m >= m) {
                System.arraycopy(nums2,index_n,nums1,index,n - index_n);
                break;
            }
            if (index_n >= n) {
                System.arraycopy(nums1_temp,index_m,nums1,index,m - index_m);
                break;
            }

            if (toBig) {
                if (nums1_temp[index_m] < nums2[index_n] ) {
                    nums1[index++] = nums1_temp[index_m++];
                } else {
                    nums1[index++] = nums2[index_n++];
                }
            } else{
                if (nums1_temp[index_m] > nums2[index_n] ) {
                    nums1[index++] = nums1_temp[index_m++];
                } else {
                    nums1[index++] = nums2[index_n++];
                }
            }
        }

    }

    /* todo
    -合并两个有序的数组-一般是将后一个数组合并到前一个数组，前一个数据有充足的空间
    思路：从后向前判断合并
    方法参数：两个数组first和second，以及各自的数量m和n
    1，根据数量判空返回
    2，确定是否是升序，用数组数量大于一的判断
    3，用first数组接纳数组，确定合并的索引初始值为（m+n-1）
       first数组的索引初始值为（m-1）
       second数组的索引初始值为（n-1）
    4, for循环体内逻辑
       if条件first的索引小于0，合并second数组剩余数据，返回。
       else条件second数组的索引小于0直接返回。
       依次比较first数组和second数组的索引对应的位置的值，移动到first数组的接纳索引上，
       使用到的相关数组索引减一。

     */
    public static void merge_test_2(int[] nums1, int m, int[] nums2, int n) {
        if (n <= 0 || m <= 0) {
            return;
        }
        boolean toBig = true;
        if (m > 1) {
            if (nums1[0] > nums1[1]) {
                toBig = false;
            }
        } else if (n > 1) {
            if (nums2[0] > nums2[1]) {
                toBig = false;
            }
        }
        int index_m = m - 1;
        int index_n = n - 1;
        int index = m + n - 1;
        for (;;) {
            if (index_m < 0) {
                System.arraycopy(nums2,0,nums1,0,index_n + 1);
                break;
            }else if (index_n < 0) {
                break;
            }
            if (toBig ? (nums1[index_m] > nums2[index_n]):(nums1[index_m] < nums2[index_n])) {
                nums1[index--] = nums1[index_m--];
            } else {
                nums1[index--] = nums2[index_n--];
            }
        }

    }




    /**
     *
     * 归并两个有序的链表
     *
     * @param head1
     * @param head2
     * @return
     */
    private ListNode mergeSort(ListNode head1, ListNode head2) {
        ListNode p1 = head1, p2 = head2, head;
        //得到头节点的指向
        if (head1.val < head2.val) {
            head = head1;
            p1 = p1.next;
        } else {
            head = head2;
            p2 = p2.next;
        }

        ListNode p = head;
        //比较链表中的值
        while (p1 != null && p2 != null) {

            if (p1.val <= p2.val) {
                p.next = p1;
                p1 = p1.next;
                p = p.next;
            } else {
                p.next = p2;
                p2 = p2.next;
                p = p.next;
            }
        }
        //第二条链表空了
        if (p1 != null) {
            p.next = p1;
        }
        //第一条链表空了
        if (p2 != null) {
            p.next = p2;
        }
        return head;
    }

}
