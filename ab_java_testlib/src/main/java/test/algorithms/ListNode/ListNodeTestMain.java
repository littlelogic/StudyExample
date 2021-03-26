package test.algorithms.ListNode;

public class ListNodeTestMain {

    static int num[] = {1,2,3,4,5,6,7,8,9};

    public static void main(String[] args){
        ListNodeTestMain mListNodeTestMain = new ListNodeTestMain();
        ListNode first = new ListNode(num[0]);
        ListNode cur;
        ListNode pre = first;
        for (int i = 1; i < num.length; i++ ) {
            cur = new ListNode(num[i]);
            pre.next = cur;
            pre = cur;
        }

        //-----
        mListNodeTestMain.printListNode(first);
        //--first = mListNodeTestMain.swapPairs(first);
//        first = mListNodeTestMain.swapPairs_2(first);
        System.out.println("----------->");
        //--first = mListNodeTestMain.swapPairs_3(first);
        //---
        first = mListNodeTestMain.reverseKGroup(first,4);
        mListNodeTestMain.printListNode(first);
    }

    int count = 0;

    public void printListNode(ListNode head) {
        ListNode temp = head;
        count = 0;
        while(temp != null){
            System.out.println("-->"+temp.val);
            temp = temp.next;
            /*if (count++ > num.length) {
                return ;
            }*/
        }
    }

    //------------

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode note_1 = head.next;
        ListNode note_2 = head;
        ListNode note_3 = head.next.next.next;
        ListNode note_4 = head.next.next;
        note_1.next = note_2;
        note_2.next = note_3;
        note_3.next = note_4;
        note_4.next = null;

        return note_1;
    }

    public ListNode swapPairs_2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode first;
        ListNode temp;
        temp = head.next.next;
        head.next.next =  head;
        first = head.next;
        head.next = temp;

        head.next = swapPairs_2(head.next);

//        System.out.println("-first->"+first.val);
//        System.out.println("-head->"+head.val);

        return first;
    }

    public ListNode swapPairs_3(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode first = head.next;
        ListNode head_temp;
        ListNode tail_temp;
        ListNode start_temp = head ;

        while (start_temp != null && start_temp.next != null) {

            System.out.println("----------start----------->");
            System.out.println("-1-start_temp.val-->"+start_temp.val);
            System.out.println("-1-start_temp.next.val-->"+start_temp.next.val);

            head_temp = start_temp.next;
            tail_temp = start_temp;

            System.out.println("-2-head_temp.val-->"+head_temp.val);
            System.out.println("-2-tail_temp.next.val-->"+tail_temp.val);

            start_temp = start_temp.next.next;
            head_temp.next = tail_temp;
            tail_temp.next = start_temp;

            System.out.println("--head_temp.val-->"+head_temp.val);
            System.out.println("--head_temp.next.val-->"+head_temp.next.val);
            System.out.println("--head_temp.next.next.val-->"+head_temp.next.next.val);
            System.out.println("--------------------->");
            start_temp = tail_temp.next;

            if (tail_temp.next != null && tail_temp.next.next != null) {
                tail_temp.next = tail_temp.next.next;
            }
            System.out.println("----------end----------->");
        }

        return first;
    }

    //-----
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) {
            return  head;
        }
        ListNode[] reverseArray = new ListNode[k];
        return reverseKGroup(head,k,reverseArray);
    }

    public ListNode reverseKGroup(ListNode head, int k ,ListNode[] reverseArray) {
        if (head ==null || head.next == null) {
            return head;
        }
        ListNode temp = head;
        boolean have = true;
        for (int i = k-1; i >= 0; i--) {
            if (temp == null) {
                reverseArray[i] = null;
                have = false;
                break;
            }
            reverseArray[i] = temp;
            temp = temp.next;
            if (i < k-1) {
                if (i == 0) {
                    temp = reverseArray[0].next;
                    reverseArray[0].next = reverseArray[1];
                    break;
                }
                reverseArray[i].next = reverseArray[i+1];
            }
        }
        if (!have) {
            temp = reverseArray[k-1];
            for (int i = k-1; i >= 1; i--) {
                if (temp == null) {
                    return head;
                }
                reverseArray[i].next = reverseArray[i-1];
                temp = reverseArray[i-1];
            }
            return head;
        }

        ListNode first = reverseArray[0];
        ListNode tail = reverseArray[k-1];

        for (int i = 0; i < reverseArray.length; i++) {
            System.out.println("-------reverseArray["+i+"]---->"+reverseArray[i].val);
        }
        try {
            System.out.println("-------temp---->"+temp.val);
            System.out.println("-------first---->"+first.val);
            System.out.println("-----end------>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        tail.next = reverseKGroup(temp,k,reverseArray);

        return first;
    }



}
