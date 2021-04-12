package arithmetic.main.content.aa_11;

import test.algorithms.ListNode.ListNode;

public class s03_判断链表是否有环 {





    /*todo

--判断链表是否有环--
判断链表是否有环这个问题应该已经算“经典”问题了，
那么为什么快慢指针一定会相遇？
首先两者要相遇，肯定是在那个环里面（比如最好情况慢的指针一踏入环就和快指针相遇）。
然后我们要明确快慢指针的速度差为1，两者每移动一下，距离减1，而这个环的最小划分单位就是1，所以显然会相遇。
当然用公式可以演算出来，并且，快慢指针的首次相遇是在，慢指针走过一圈之前。

方法参数，链表，返回布尔类型，
1，创建快、慢指针节点，并赋值头结点。
2，创建for无线循环体，
3，初始条件判断，快指针为空或快指针的next为空，返回false；
4，慢指针被赋值为慢指针的下一个节点
   快指针被赋值为快指针的下一个节点的下一个节点
5，条件判断，当快指针等于慢指针时，返回true；

在一个无线循环体总，所有的跳出都是return，的情况，循环体外的return无意义，会报错

     */
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        for (;;) {
            //仅用判断fast，因为fast快，无环一定比slow先为null
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                return true;
            }
        }
    }

    public boolean hasCycle_b(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        //仅用判断fast，因为fast快，无环一定比slow先为null
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }










}
