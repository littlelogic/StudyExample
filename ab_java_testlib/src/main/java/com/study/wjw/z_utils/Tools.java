package com.study.wjw.z_utils;

import java.util.ArrayList;

import test.algorithms.ListNode.ListNode;

public class Tools {

    public static void printListNode(ListNode head){
        if (head == null) {
            Log.i("{}");
            return;
        }
        ListNode temp = head;
        int index = 0;
        for (;;) {
            if (temp == null) {
                System.out.print("}");
                break;
            } else {
                if (index == 0) {
                    System.out.print(" {"+temp.val);
                } else {
                    System.out.print(","+temp.val);
                }
                temp = temp.next;
            }
            index++;
        }
        System.out.println();
    }

    public static ListNode convertListNode(int[] array){
        ListNode head = null;
        ListNode cur = null;
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                cur = head = new ListNode(array[i]);
            } else {
                ListNode node = new ListNode(array[i]);
                cur.next = node;
                cur = node;
            }
        }
        return head;
    }

    ///------

    public static void printIntArray(int[] array){
        if (array == null) {
            Log.i("{}");
            return;
        }
        for (int i = 0; i < array.length; i++ ) {
            if (i == 0) {
                System.out.print("{ ");
            }
            if (i == array.length - 1) {
                System.out.print(array[i]);
                System.out.print(" }");
            } else {
                System.out.print(array[i] + ",");
            }
        }
        System.out.println();
    }

    public static String getIntArray(int[] array){
        if (array == null) {
            return "{}";
        }
        StringBuffer hStringBuffer = new StringBuffer();
        for (int i = 0; i < array.length; i++ ) {
            if (i == 0) {
                hStringBuffer.append("{ ");
            }
            if (i == array.length - 1) {
                hStringBuffer.append(array[i]+" }");
            } else {
                hStringBuffer.append(array[i] + ",");
            }
        }
        return hStringBuffer.toString();
    }

    public static String getIntArray(ArrayList<Integer> list){
        if (list == null) {
            return "{}";
        }
        StringBuffer hStringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++ ) {
            if (i == 0) {
                hStringBuffer.append("{ ");
            }
            if (i == list.size() - 1) {
                hStringBuffer.append(list.get(i)+" }");
            } else {
                hStringBuffer.append(list.get(i) + ",");
            }
        }
        return hStringBuffer.toString();
    }

    public static void printIntArray(ArrayList<Integer> list){
        if (list == null || list.size() == 0 ) {
            Log.i("{}");
            return;
        }
        for (int i = 0; i < list.size(); i++ ) {
            if (i == 0) {
                System.out.print("{ ");
            }
            if (i == list.size() - 1) {
                System.out.print(list.get(i));
                System.out.print(" }");
            } else {
                System.out.print(list.get(i) + ",");
            }
        }
    }


}
