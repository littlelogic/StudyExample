package arithmetic.main.content.aa_11;

import com.study.wjw.z_utils.Tools;

import java.util.Stack;

import test.algorithms.ListNode.ListNode;

public class s09_两个链表生成相加链表 {



    public static void main(String[] args) {
        ListNode head1;
        ListNode head2;
        int[] array = new int[]{ 6,4,1};
        head1 = Tools.convertListNode(array);
        Tools.printListNode(head1);

        int[] array2 = new int[]{ 3,5,9};
        head2 = Tools.convertListNode(array2);
        Tools.printListNode(head2);


        System.out.println("两个链表生成相加链表: ");
        System.out.print("反转链表改变了原链表的结构: ");
        Tools.printListNode(addInListByStack(head1, head2));

        ListNode result = addInList(head1, head2);
        System.out.print("借助Stack没有改变原链表的结构: ");
        Tools.printListNode(result);

        int[] array41 = new int[]{ 1,4,9};
        int[] array42 = new int[]{ 2,5,6};
        ListNode  ListNode41 = Tools.convertListNode(array41);
        Tools.printListNode(ListNode41);
        ListNode  ListNode42 = Tools.convertListNode(array42);
        Tools.printListNode(ListNode42);



    }


    //链表1为 9->3->7，链表2为 6->3，最后生成新的结果链表为 1->0->0->0
    public static ListNode addInListByStack(ListNode head1, ListNode head2) {
        if (head1 == null)
            return head2;
        if (head2 == null)
            return head1;
        ListNode cur1 = head1;
        ListNode cur2 = head2;
        // 存储链表
        Stack<ListNode> s1 = new Stack<>();
        Stack<ListNode> s2 = new Stack<>();
        while (cur1 != null) {
            s1.push(cur1);
            cur1 = cur1.next;
        }
        while (cur2 != null) {
            s2.push(cur2);
            cur2 = cur2.next;
        }

        int left = 0;
        int ad_num = 0;
        int value = 0;
        ListNode last = null;
        while (!s1.isEmpty() || !s2.isEmpty()) {
            value = 0;
            if (!s1.isEmpty()) {
                value += s1.pop().val;
            }
            if (! s2.isEmpty()) {
                value += s2.pop().val;
            }
            value += ad_num;
            if (value >= 10) {
                left = value - 10;
                ad_num = 1;
            } else {
                left = value;
                ad_num = 0;
            }
            if (last == null) {
                last = new ListNode(left);
            } else {
                ListNode temp = new ListNode(left);
                temp.next = last;
                last = temp;
            }
        }
        if (ad_num > 0) {
            ListNode temp = new ListNode(ad_num);
            temp.next = last;
            last = temp;
        }
        return last;
    }

    /*
    假设链表中每一个节点的值都在 0 - 9 之间，那么链表整体就可以代表一个整数。
    给定两个这种链表，请生成代表两个整数相加值的结果链表。
    例如：链表 1 为 9->3->7，链表 2 为 6->3，最后生成新的结果链表为 1->0->0->0。
     */
    public static ListNode addInList (ListNode head1_, ListNode head2_) {
        // write code here
        if (head1_ == null) {
            return head2_;
        }
        if (head2_ == null) {
            return head1_;
        }

        ListNode nowNode1 = reverse(head1_);
        ListNode nowNode2 = reverse(head2_);;
        int left = 0;
        int ad_num = 0;
        int value = 0;

        ListNode result_head = null;
        ListNode result = null;
        while (nowNode1 != null || nowNode2 != null) {
            value = 0;
            if (nowNode1 != null) {
                value += nowNode1.val;
                nowNode1 = nowNode1.next;
            }
            if (nowNode2 != null) {
                value += nowNode2.val;
                nowNode2 = nowNode2.next;
            }
            value += ad_num;

            if (value >= 10) {
                left = value - 10;
                ad_num = 1;
            } else {
                left = value;
                ad_num = 0;
            }
            ListNode temp = new ListNode(left);
            if (result_head == null ) {
                result_head = temp;
                result = temp;
            } else {
                result.next = temp;
                result = result.next;
            }
        }

        if (ad_num > 0) {
            ListNode temp = new ListNode(ad_num);
            result.next = temp;
        }

        return reverse(result_head);

    }

    public static ListNode reverse(ListNode head){
        if(head == null) return head;
        ListNode pre = new ListNode(-1);
        while(head!= null){
            ListNode tmp = head.next;
            head.next = pre.next;
            pre.next = head;
            head = tmp;
        }
        return pre.next;
    }
}
