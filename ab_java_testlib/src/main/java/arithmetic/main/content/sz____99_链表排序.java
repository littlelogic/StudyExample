package arithmetic.main.content;

import com.study.wjw.z_utils.Tools;

import test.algorithms.ListNode.ListNode;

public class sz____99_链表排序 {



    /*
    实际移动的是 "链表的本身元素"
    可以先转为数组，最数组排序，然后再新组织链表的顺序
     */
    public static void main(String[] args) {
        ListNode head1;
        int[] array = new int[]{ 6,4,1,3,4,2,4,2,8,9,70,4,6,5};
        head1 = Tools.convertListNode(array);

        quickSort(head1,null);
        Tools.printListNode(head1);

    }


    //快速排序
    public static void quickSort(ListNode begin, ListNode end){
        if(begin == null || begin == end)
            return;

        ListNode index = paration(begin, end);
        quickSort(begin, index);
        quickSort(index.next, end);
    }

    /**
     * 划分函数，以头结点值为基准元素进行划分
     * @param begin
     * @param end
     * @return
     */
    public static ListNode paration(ListNode begin, ListNode end){
        if(begin == null || begin == end) {
            return begin;
        }
        int val = begin.val;  //基准元素
        ListNode index = begin, cur = begin.next;

        while(cur != end){
            if(cur.val < val){  //交换
                index = index.next;
                int tmp = cur.val;
                cur.val = index.val;
                index.val = tmp;
            }
            cur = cur.next;
        }


        begin.val = index.val;
        index.val = val;

        return index;
    }







}
