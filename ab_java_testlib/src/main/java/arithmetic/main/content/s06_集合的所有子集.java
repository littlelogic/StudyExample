package arithmetic.main.content;

import com.study.wjw.z_utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class s06_集合的所有子集 {

    public static void main(String[] args) {
        /*
        输入 [1,2,3]
        返回值 [[],[1],[2],[3],[1,2],[1,3],[2,3],[1,2,3]]
         */
//        final int[] array = new int[]{ 1,2,3,4,5,6,7,8,9 };
        final int[] array = new int[]{ 1,2,3};
        Tools.printIntArray(array);

//        ArrayList<ArrayList<Integer>> lists =  subsets(array);
        ArrayList<ArrayList<Integer>> lists =  getSubset(array);

        for (ArrayList<Integer> list : lists) {
            Tools.printIntArray(list);
        }


    }

    public static ArrayList<ArrayList<Integer>> getSubset(int[] array) {
        if (array != null && array.length > 0) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<>();
            // 集合子集个数=2的该集合长度的乘方
            for (int i = 0; i < Math.pow(2, array.length); i++) {
                ArrayList<Integer> subSet = new ArrayList<>();
                // 索引从0一直到2的集合长度的乘方-1
                int index = i;
                for (int j = 0; j < array.length; j++) {
                    // 通过逐一位移，判断索引那一位是1，如果是，再添加此项
                    // 位与运算，判断最后一位是否为1
                    if ((index & 1) == 1) {
                        subSet.add(array[j]);
                    }
                    // 索引右移一位并赋值
                    index >>= 1;
                }
                // 把子集存储起来
                result.add(subSet);
            }
            return result;
        } else {
            return null;
        }
    }



    public static ArrayList<ArrayList<Integer>> subsets_no(int[] array) {
        if (array == null) {
            return null;
        }
        ArrayList<ArrayList<Integer>> listSets = new ArrayList<ArrayList<Integer>>();
        if (array.length == 0) {
            listSets.add(new ArrayList<Integer>());
            return listSets;
        }
        listSets.add(new ArrayList<Integer>());

        for (int i = 1; i <= array.length; i++) {
            for (int j = 0; j <= array.length  - i; j++) {
                ArrayList list = new ArrayList<Integer>();
                for (int h = 0; h < i; h++) {
                    list.add(array[j + h]);
                }
                listSets.add(list);
            }
        }
        return listSets;
    }


}
