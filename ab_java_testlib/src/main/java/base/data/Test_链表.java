package base.data;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Tools;

import java.util.Stack;

import others.SingletonDemo;
import test.algorithms.ListNode.ListNode;

public class Test_链表 {

    static {
        int[] array = new int[]{ 1,2,3,4,5,6,7,8,9 };
    }
    static ListNode head;
    public static void main(String[] args) {
        final int[] array = new int[]{ 1,2,3,4,5,6,7,8,9 };
        head = Tools.convertListNode(array);
        Tools.printListNode(head);

        Log.print("reverse->");
        Tools.printListNode(reverse(head));
        Log.print("test22-99>");

        int[] array41 = new int[]{ 1,4,9};
        int[] array42 = new int[]{ 2,5,6};
        ListNode  ListNode41 = Tools.convertListNode(array41);
        Tools.printListNode(ListNode41);
        ListNode  ListNode42 = Tools.convertListNode(array42);
        Tools.printListNode(ListNode42);
        Log.print("归并两个有序的链表>");
        Tools.printListNode(mergeSort(ListNode41,ListNode42));
    }

    ///todo 翻转，三个指针，以未变的向下设置
    public static ListNode reverse(ListNode head) {
        if (head== null || head.next == null)
            return head;
        ListNode pre = null;
        ListNode cur = head;
        ListNode next = head.next;
        for (;;) {
            cur.next = pre;
            pre = cur;
            cur = next;
            if (next != null) {
                next = next.next;
            } else {
                return pre;
            }
        }
    }

    ///todo 快慢指针
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        for (;;) {
            //仅用判断fast，因为快，无环一定更先为null
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow)
                return true;
        }
    }

    ///todo 有间隔的双指针
    public ListNode removeNthFromEnd(ListNode head, int n) {
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

    //两个链表相加，链表1为 9->3->7，链表2为 6->3，最后生成新链表为 1->0->0->0
    public static ListNode addInListByStack(ListNode head1, ListNode head2) {
        if (head1 == null) return head2;
        if (head2 == null) return head1;
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
            if (!s1.isEmpty())
                value += s1.pop().val;
            if (! s2.isEmpty())
                value += s2.pop().val;
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

    //合并两个有序链表、除了头结点以外，都是改变next指针的值
    static ListNode mergeSort(ListNode head1,ListNode head2){
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
        if (p1 != null)//链2表空了
            p.next = p1;
        if (p2 != null) //链1表空了
            p.next = p2;
        return head;
    }

    // 归并两个有序的链表
    static ListNode mergeSort_error(ListNode head1, ListNode head2) {
        ListNode p1 = head1, p2 = head2, p = null;
        ListNode head = null;
        //比较链表中的值
        while (p1 != null && p2 != null) {
            boolean first  = (p == null);
            if (p1.val <= p2.val) {
                p = p1;
                p1 = p1.next;
            } else {
                p = p2;
                p2 = p2.next;
            }
            if (first) {
                head = p;
            }
            p = p.next;
        }
        //第二条链表空了
        if (p1 != null) {
            p = p1;
        }
        //第一条链表空了
        if (p2 != null) {
            p = p2;
        }
        return head;
    }

}
