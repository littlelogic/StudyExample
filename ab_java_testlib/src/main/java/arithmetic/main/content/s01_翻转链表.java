package arithmetic.main.content;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Tools;

import test.algorithms.ListNode.ListNode;

public class s01_翻转链表 {

    static {
        int[] array = new int[]{ 1,2,3,4,5,6,7,8,9 };
    }
    static ListNode head;
    public static void main(String[] args) {
        final int[] array = new int[]{ 1,2,3,4,5,6,7,8,9 };
        head = Tools.convertListNode(array);
        Tools.printListNode(head);

        head = reverseListByLoop(head);
        Tools.printListNode(head);

        head = reverseListiteration(null,head);
        Tools.printListNode(head);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Log.print("reverseListByLoop_test->");
                head = reverseListByLoop_test(head);
                Tools.printListNode(head);
            }
        }.start();
    }

    ///-----

    public static ListNode reverseListByLoop(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        ListNode cur = head;
        ListNode next = head.next;
        ListNode temp ;
        head.next = null;
        for (;;) {
            if (cur == null || next == null) {
                return cur;
            } else {
                //反转指针域的指向
                temp = next.next;
                next.next = cur;
                //指针往下移动
                cur = next;
                next = temp;
            }
        }
    }

    public static ListNode reverseListiteration(ListNode pre, ListNode cur) {
        if (cur != null && cur.next == null) {
            cur.next = pre;
            return cur;
        } else {
            ListNode head = reverseListiteration(cur,cur.next);
            cur.next = pre;
            return head;
        }
    }

    ///-----

    /** todo
    初始条件判空返回
    初始逻辑赋值
    if条件跳出循环返回
    else逻辑执行
     */

    /**
     -翻转链表-
     方法参数：需要翻转的列表
     方法返回：已经翻转的列表
     循环实现

     判空反空
     初始化"当前变量"和"下一个变量"并赋值。
     头结点的下一个"变量赋空
     创建"当前临时变量"和"下一个临时变量"

     循环体中逻辑
     条件"下一个变量"为空时返回"当前变量"
     给"当前临时变量"和"下一个临时变量"赋值
     "当前变量"赋给"下一个变量"的Next
     "当前临时变量"和"下一个临时变量"赋值给"当前变量"和"下一个变量"
     循环

     */
    public static ListNode reverseListByLoop_test(ListNode head) {

        if (head == null) {
            return null;
        }
        ListNode cur = head;
        ListNode next = head.next;
        head.next = null;
        ListNode tempCur;
        ListNode tempNext;
        for (;;) {

            if (next == null) {
                return cur;
            }
            tempCur = next;
            tempNext = next.next;

            next.next = cur;

            cur = tempCur;
            next = tempNext;
        }
    }



    public static ListNode reverseListByLoop_test_3(ListNode head) {

        if (head == null) {
            return null;
        }
        ListNode cur = head;
        ListNode next = head.next;
        head.next = null;
        ListNode temp_cur;
        ListNode temp_next;
        for (;;) {
            if (next == null) {
                return cur;
            } else {
                temp_cur = next;
                temp_next = next.next;
                //---
                next.next = cur;
                //---
                cur = temp_cur;
                next = temp_next;
            }
        }
    }

    public static ListNode reverseListByLoop_test_2(ListNode head) {

        if (head == null) {
            return null;
        }
        ListNode cur = head;
        ListNode next = head.next;
        head.next = null;
        ListNode temp;
        for (;;) {
            if (next == null) {

                return cur;
            } else {
                temp = next.next;
                next.next = cur;

                cur = next;
                next = temp;
            }
        }
    }


}
