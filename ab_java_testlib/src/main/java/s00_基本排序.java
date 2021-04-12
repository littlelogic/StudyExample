import com.study.wjw.z_utils.Log;

import java.util.Arrays;

public class s00_基本排序 {

    static int num = 0 ;
    static int a[] = {};
    static void initArray() {
        a = new int[]{ 2, 3, 6, 4, 0, 1, 7, 8, 5, 9 };
    }

    /*todo
       冒泡排序
       选择排序
       插入排序
       快速排序(挖坑填数 + 分治法)

最终结果（100000个元素的数组排序时间）
冒泡排序： 19446
选择排序： 5309
插入排序： 1589
最终结论显示，插入排序的效率要高于选择排序和冒泡排序。

    */
    public static void main(String[] args) {
        initArray();
        bubbleSort(a);
        Log.i("bubbleSort:"+ Arrays.toString(a));
        ///----
        initArray();
        seletSort(a);
        Log.i("seletSort_b:"+Arrays.toString(a));
        ///----
        initArray();
        insertSort(a);
        Log.i("insertSort: "+Arrays.toString(a));
    }

/*todo  冒泡排序,效率较低,是交换式排序法的一种
        从前向后(或从后向前)依次比较相邻的元素，若发现逆顺序，则交换,
        小的向前换，大的向后换，像水底的气泡逐渐向上冒，
        通俗一点就是把大的往上挪！向冒泡一样。                                           */
        static void bubbleSort(int[] a) {
            int temp = 0;
            for (int j = 0; j < a.length - 1 ; j++) {
                for (int i = 0; i < a.length - 1 - j; i++) {
                    if (a[i] > a[i + 1]) {// change
                        temp = a[i + 1];
                        a[i + 1] = a[i];
                        a[i] = temp;
                    }
                }
            }
        }

/*todo  选择排序,效率中等，每次查找的是目标值的索引
        第一次从数组索引0到(n-1)中选取最小值，与数组索引0交换。
        第二次从数组索引1到(n-1)中选取最小值与数组索引1交换,以此类推                                      */
        static void seletSort(int[] a) {
            int length = a.length;
            int temp = 0;
            int inIndex;
            for (int j = 0; j <length- 1;j++) {
                inIndex = j;
                for (int i = j; i < length- 1; i++) {
                    if (a[inIndex] > a[i + 1]) {
                        inIndex = i+1;
                    }
                }
                temp = a[inIndex];
                a[inIndex] = a[j];
                a[j] = temp;
            }
        }

/*todo  插入排序，通过构建有序序列，对于未排序数据，
        在已排序序列中从后向前扫描，找到相应位置并插入
        具体实现如下：
        1.两层for循环，外层循环i从大于等于1到小于n
        2.内层循环j从大于等于 外层循环的索引值i 到大于0，递减。
        3.内层循环中，比较索引j和j+1的数组值，逆序则交换数组值。           */
        static void insertSort(int[] a){
            for(int i = 1; i < a.length; i++){
                for(int j = i; j > 0; j--){
                    if(a[j] < a[j-1]){
                        int temp = a[j-1];
                        a[j-1] = a[j];
                        a[j] = temp;
                    }
                }
            }
        }



}
