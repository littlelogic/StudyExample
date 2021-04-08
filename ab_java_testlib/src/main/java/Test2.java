import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Tools;

import java.util.LinkedList;
import java.util.Queue;

import test.algorithms.ListNode.ListNode;
import test.algorithms.ListNode.TreeNode;

public class Test2 {

    public static void visit(TreeNode node){
//        if (node != null) {
        System.out.print(node.val+" ");
//        }
    }




    public static void main(String[] args) {

        TreeNode root= new TreeNode(1);
        root.left= new TreeNode(2);
        root.right= new TreeNode(3);
        root.left.left= new TreeNode(4);
        root.left.right= new TreeNode(5);
        root.right.left= new TreeNode(6);
        root.right.right= new TreeNode(7);
        root.left.left.left= new TreeNode(8);
        root.left.right.right= new TreeNode(9);

        preOrderRecursion(root);
        System.out.println();
        inOrderRecursion(root);
        System.out.println();
        postOrderRecursion(root);
        System.out.println();

        System.out.println(deep(root));


        System.out.println("层序遍历");
        LevelOrderTraversal(root);//???
        System.out.println("");
        System.out.println("层序遍历2");
        levelTravel(root);

        System.out.println("层序遍历32");
        maxWidth(root);
        System.out.println("层序遍历32last");
    }


    /**
     * 递归先序遍历
     * */
    public static void preOrderRecursion(TreeNode node){
        if(node==null) //如果结点为空则返回
            return;
        visit(node);//访问根节点
        preOrderRecursion(node.left);//访问左孩子
        preOrderRecursion(node.right);//访问右孩子
    }

    public static void inOrderRecursion(TreeNode node){
        if(node==null) //如果结点为空则返回
            return;
        preOrderRecursion(node.left);//访问左孩子
        visit(node);//访问根节点
        preOrderRecursion(node.right);//访问右孩子
    }

    public static void postOrderRecursion(TreeNode node){
        if(node==null) //如果结点为空则返回
            return;
        preOrderRecursion(node.left);//访问左孩子
        preOrderRecursion(node.right);//访问右孩子
        visit(node);//访问根节点
    }


    public static int deep(TreeNode root) {
        if (root == null)
            return 0;
        return Math.max(deep(root.left), deep(root.right)) + 1;
    }

    ///层序遍历，得到深度和最大宽度
    public static void maxWidth(TreeNode root) {
        if (root == null) {
            return ;
        }
        int maxWidth = 0;
        int deep = 0;
        int levelMaxNum = 1;
        int levelTrueNum = 0;
        int levelIndex = 0;
        Queue<TreeNode> list = new LinkedList();
        list.add(root);
        list.add(null);
        //----
        deep = 1;
        maxWidth = 1;
        levelTrueNum = 0;
        levelMaxNum = 2;
        levelIndex = 0;
        while (!list.isEmpty()) {
            TreeNode tempNode = list.poll();
            if (tempNode == null) {
                System.out.println();
                continue;
            }
            System.out.print(tempNode.val+" ");
            if (tempNode.left != null) {
                list.add(tempNode.left);
                levelTrueNum++;
            }
            levelIndex++;
            if (tempNode.right != null) {
                list.add(tempNode.right);
                levelTrueNum++;
            }
            levelIndex++;
            ///------
            if (levelMaxNum == levelIndex && levelTrueNum >= 1) {
                list.add(null);
                levelMaxNum = levelTrueNum * 2;
                if (maxWidth < levelTrueNum) {
                    maxWidth = levelTrueNum;
                }
                deep++;
                levelTrueNum = 0;
                levelIndex = 0;
            }
        }
        Log.i("deep:"+deep+" maxWidth:"+maxWidth);
    }

    public static void maxWidth_b(TreeNode root) {
        if (root == null) {
            return ;
        }
        int maxWidth = 0;
        int deep = 0;
        int levelMaxNum = 1;
        int levelTrueNum = 0;
        int levelIndex = 0;
        Queue<TreeNode> list = new LinkedList();
        list.add(root);
        list.add(null);
        //----
        deep = 1;
        maxWidth = 1;
        levelTrueNum = 0;
        levelMaxNum = 2;
        levelIndex = 0;
        while (!list.isEmpty()) {
            TreeNode tempNode = list.poll();
            if (tempNode == null) {
                if (!list.isEmpty()) {
                    System.out.println();
                }
                continue;
            }
            visit(tempNode);
            if (tempNode.left != null) {
                list.add(tempNode.left);
                levelTrueNum++;
            }
            levelIndex++;
            if (tempNode.right != null) {
                list.add(tempNode.right);
                levelTrueNum++;
            }
            levelIndex++;
            ///------
            if (levelMaxNum == levelIndex) {
                list.add(null);
                levelMaxNum = levelTrueNum * 2;
                if (maxWidth < levelTrueNum) {
                    maxWidth = levelTrueNum;
                }
                deep++;
                levelTrueNum = 0;
                levelIndex = 0;
            }
        }
        ///减去最后的无效的---
        deep--;
        Log.i("deep:"+deep+" maxWidth:"+maxWidth);
    }

    public static void LevelOrderTraversal(TreeNode root) {
        visit(root);
        LevelOrderTraversalUnit(root);
    }

    // 层序遍历
    public static void LevelOrderTraversalUnit(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            visit(root.left);
        }
        if (root.right != null) {
            visit(root.right);
        }
        LevelOrderTraversalUnit(root.left);
        LevelOrderTraversalUnit(root.right);
    }




    public static void levelTravel(TreeNode root){
        if(root==null)return;
        Queue<TreeNode> q=new LinkedList<TreeNode>();
        q.add(root);
        while(!q.isEmpty()){
            TreeNode temp =  q.poll();
            visit(temp);
            if(temp.left!=null)q.add(temp.left);
            if(temp.right!=null)q.add(temp.right);
        }
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

}
