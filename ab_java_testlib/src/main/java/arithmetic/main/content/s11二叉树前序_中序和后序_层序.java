package arithmetic.main.content;

import com.study.wjw.z_utils.Tools;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import test.algorithms.ListNode.ListNode;
import test.algorithms.ListNode.TreeNode;

public class s11二叉树前序_中序和后序_层序 {

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

        System.out.println(height2(root));


        System.out.println("层序遍历");
        LevelOrderTraversal(root);//???
        System.out.println("");
        System.out.println("层序遍历2");
        levelTravel(root);
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


    /**
     * 计算二叉树的深度，两行递归即可搞定。
     * @param root
     * @return
     */
    public static int height2(TreeNode root) {
        if (root == null)
            return 0;

        return Math.max( height2(root.left) , height2(root.right) ) + 1;
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



    /**
     *
     * @param root 树根节点
     * 层序遍历二叉树，用队列实现，先将根节点入队列，只要队列不为空，然后出队列，并访问，接着讲访问节点的左右子树依次入队列
     */
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






}
