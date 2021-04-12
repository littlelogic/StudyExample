package arithmetic.main.content.aa_11;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Tools;

import test.algorithms.ListNode.ListNode;

public class s08_删除链表的倒数第n个节点 {


    static ListNode head;
    public static void main(String[] args) {
        int[] array = new int[]{ 9,8,7,6,5,4,3,2,1};
        head = Tools.convertListNode(array);
        Tools.printListNode(head);

        head = removeNthFromEnd(head, 8);
        System.out.print("删除链表的倒数第8个节点: ");
        Tools.printListNode(head);
    }


    /*
    基本思路：双指针法。
    简短、易读性很高有木有..
    不用stack
     */
    ///todo 有间隔的双指针
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) return head;
        ListNode first = head;
        ListNode second = head;
        for(int i = 0; i < n; i++) {
            first = first.next;
            if (first == null) return head;
        }
        //同时移动两个指针
        while(first.next != null){
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return head;
    }


}
