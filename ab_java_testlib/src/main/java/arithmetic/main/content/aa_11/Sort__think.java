package arithmetic.main.content.aa_11;

import com.study.wjw.z_utils.Log;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Sort__think {

    static int num = 0 ;
    static int a[] = {};
    static void initArray() {
        a = new int[]{ 2, 3, 6, 4, 0, 1, 7, 8, 5, 9 };
    }

    /* todo
        冒泡排序
        选择排序
        插入排序
        --快速排序(挖坑填数 + 分治法)
     */

    public static void main(String[] args) {

        //冒泡排序
        if (true)bubbleSort(a);

        //选择排序
        if (false)seletSort_a(a);
        if (false)seletSort_b(a);

        //插入排序
        if (false) insertSort(a);
        if (false) insertSort_b(a);
        if (false) insertSort_c(a);

        //希尔排序
        if (false) shellSort(a);

        //并归排序
        if (false) mergeSortFirst(a);

        //快速排序
        if (false) quickSortFirst(a);
    }

    public static void toString(int[] a) {
        for (int i = 0; i < a.length; i++) {
            ///System.out.println(a[i]);
            System.out.print(a[i] + " ");
        }
        System.out.println("");
    }

    //================================================

    /*


    冒泡排序法效率较低,是交换式排序法的一种
    从前向后(或从后向前)依次比较相邻的元素，若发现逆顺序，则交换
    小的向前换，大的向后换，像水底的气泡逐渐向上冒，
    通俗一点就是把大的往上挪！向冒泡一样。                                                */
    static void bubbleSort(int[] a) {
        a =new int[]{2, 3, 6, 4, 0, 1, 7, 8, 5, 9};
        int length = a.length;
        int temp = 0;
        //for (int j = 0; j < a.length ; j++) {
        for (int j = 0; j < a.length - 1 ; j++) {
            //for (int i = 0; i < a.length - 1; i++) {
            for (int i = 0; i < a.length - 1 - j; i++) {
                if (a[i] > a[i + 1]) {
                    // change
                    temp = a[i + 1];
                    a[i + 1] = a[i];
                    a[i] = temp;
                }
            }
        }
        Log.i("bubbleSort:"+Arrays.toString(a));
    }

    //================================================

    /*


    选择排序法,第一次从R[0]~R[n-1]中选取最小值，与R[0]交换。
    第二次从R[1]~R[n-1]中选取最小值与R[1]交换。。。以此类推。
    通俗点说就是每次找到后面元素的最小值然后与之交换。
    选择排序法效率中。                                                             */
    private static void seletSort_a(int[] a) {
        a =new int[]{2, 3, 6, 4, 0, 1, 7, 8, 5, 9};
        int length = a.length;
        int temp = 0;
        int minIndex;
        for (int j = 0; j <length- 1;j++) {
            minIndex = j;
            for (int i = j; i < length- 1; i++) {
                if (a[minIndex] > a[i + 1]) {
                    minIndex = i+1;
                }
            }
            temp = a[minIndex];
            a[minIndex]= a[j];
            a[j]=temp;
        }
        Log.i("seletSort_b:"+Arrays.toString(a));
    }
    public static void seletSort_b(int arr[]){
        arr =new int[]{2, 3, 6, 4, 0, 1, 7, 8, 5, 9};
        //中间值
        int temp = 0;
        //外循环:我认为最小的数,从0~长度-1
        for(int j = 0; j<arr.length-1;j++){
            //最小值:假设第一个数就是最小的
            int min = arr[j];
            //记录最小数的下标的
            int minIndex=j;
            //内循环:拿我认为的最小的数和后面的数一个个进行比较
            for(int k=j+1;k<arr.length;k++){
                //找到最小值
                if (min>arr[k]) {
                    //修改最小
                    min=arr[k];
                    minIndex=k;
                }
            }
            //当退出内层循环就找到这次的最小值
            //交换位置
            temp = arr[j];
            arr[j]=arr[minIndex];
            arr[minIndex]=temp;
        }
        Log.i("seletSort_b:"+Arrays.toString(arr));
    }

    //================================================

    /*


    插入排序，通过构建有序序列，对于未排序数据，
    在已排序序列中从后向前扫描，找到相应位置并插入                                    */
    public static void insertSort(int[] ins){
        num = 0 ;
        ins =new int[]{2, 3, 6, 4, 0, 1, 7, 8, 5, 9};
        for(int i=1; i<ins.length; i++){
            for(int j=i; j>0; j--){
                if(ins[j]<ins[j-1]){
                    int temp = ins[j-1];
                    ins[j-1] = ins[j];
                    ins[j] = temp;
                }
                num ++;
            }
        }
        Log.i("insertSort: "+Arrays.toString(a));
        Log.i("insertSort循环执行次数num： "+num);
    }
    public static void insertSort_b(int[] ins){
        num = 0 ;
        ins =new int[]{2, 3, 6, 4, 0, 1, 7, 8, 5, 9};
        for(int i=1; i<ins.length; i++){
            int temp = ins[i];//保存每次需要插入的那个数
            int j;
            //这个较上面有一定的优化
            for(j=i; (j>0&&ins[j-1]>temp); j--){
                //把大于需要插入的数往后移动。
                //最后不大于temp的数就空出来j
                ins[j] = ins[j-1];
                num ++;
            }
            //todo 和上面的for循环是不一样的
            /*for(j=i; j>0; j--){
                if (ins[j-1]>temp) {
                    ins[j] = ins[j-1];
                }
                num ++;
            }*/
            ins[j] = temp;//将需要插入的数放入这个位置
        }
        Log.i("insertSort_b: "+Arrays.toString(a));
        Log.i("insertSort_b循环执行次数num： "+num);
    }

    public static void insertSort_c(int[] array) {
        num = 0 ;
        array =new int[]{2, 3, 6, 4, 0, 1, 7, 8, 5, 9};
        //从第二个元素开始遍历即可
        for (int i = 1; i < array.length; i++) {
            //参考值
            int temp = array[i];
            //从参考值前面一个元素开始从后往前查找
            int j = i - 1;
            for (; j >= 0; j--) {
                //假如找到比参考值大，数据往后移
                if (array[j] > temp) {
                    array[j + 1] = array[j];
                } else {
                    // 跳出循环
                    break;
                }
                num ++;
            }
            array[j + 1] = temp;
            //Log.i("第" + i + "趟排序后:" + Arrays.toString(array));
        }
        Log.i("insertSort_c: "+Arrays.toString(a));
        Log.i("insertSort_c循环执行次数num： "+num);
    }

    //================================================

    /*

    希尔排序的实质就是分组插入排序，又称缩小增量法。
    将整个无序序列分割成若干个子序列(由相隔某个“增量”的元素组成的)
    分别进行直接插入排序，然后依次缩减增量再进行排序，
    待整个序列中的元素基本有序时，再对全体元素进行一次直接插入排序。
    因为直接插入排序在元素基本有序的情况下，效率是很高的，
    因此希尔排序在时间效率上有很大提高。
    无序序列：int a[] = {3,1,5,7,2,4,9,6};
    第一趟时：n=8; gap=n/2=4; 把整个序列共分成了4个子序列
    {3，2}、{1，4}、{5，9}、{7，6}
    第二趟时：gap=gap/2=2; 把整个序列共分成了2个子序列
    {2，5，3，9}、{1，6，4，7}
    第三趟时：对整个序列进行直接插入排序
    {1, 2, 3, 4, 5, 6, 7, 9}                                                  */
    private static void shellSort(int[] a){
        a =new int[]{2, 3, 6, 4, 0, 1, 7, 8, 5, 9};
        int n=a.length;
        int i,j,k,gap;
        for(gap=n/2;gap>0;gap/=2){
            for(i=0;i<gap;i++){
                for(j=i+gap;j<n;j+=gap){
                    int temp = a[j];
                    for(k=j-gap;
                        (k>=0 && a[k]>temp);k-=gap){
                        a[k+gap]=a[k];
                    }
                    a[k+gap]=temp;
                }
            }
        }
        Log.i("shellSort: "+Arrays.toString(a));
    }

    //================================================

    //两路归并算法，两个排好序的子序列合并为一个子序列
    static void merge(int []a,int left,int mid,int right){
        //辅助数组
        int []tmp=new int[a.length];
        //p1、p2是检测指针，k是存放指针
        int p1=left,p2=mid+1,k=left;

        while(p1<=mid && p2<=right)
            if(a[p1]<=a[p2])
                tmp[k++]=a[p1++];
            else
                tmp[k++]=a[p2++];

        //若第一个序列未检测完，直接将后面所有元素加到合并的序列中
        while(p1<=mid) tmp[k++]=a[p1++];
        //同上
        while(p2<=right) tmp[k++]=a[p2++];

        //复制回原素组
        for (int i = left; i <=right; i++)
            a[i]=tmp[i];
    }
/*  归并排序，分成最小组(2个或一个)，合并，比较
    与快速排序一样，也是基于分治法的。
    归并排序将待排序的元素序列分成两个长度相等的子序列，
    为每一个子序列排序，后再将他们合并成一个子序列。
    合并两个子序列的过程也就是两路归并                                                    */
    static void mergeSort(int [] a,int start,int end){
        //当子序列中只有一个元素时结束递归
        if(start<end){
            int mid=(start+end)/2;//划分子序列
            //对左，右侧子序列各进行递归排序
            //调用顺序可以更换
            mergeSort(a, start, mid);
            mergeSort(a, mid+1, end);
            merge(a, start, mid, end);//合并
        }
    }
    static void mergeSortFirst(int [] arr){
        arr =new int[]{2, 3, 6, 4, 0, 1, 7, 8, 5, 9};
        mergeSort(arr, 0, arr.length-1);
        Log.i("mergeSort: "+Arrays.toString(arr));
    }

    //================================================

    /*

    快速排序，todo 定基准数，左小右大，重复，分支，合并
    对于给定的一组记录，选择一个基准元素,通常选择第一个
    元素或者最后一个元素,通过一趟扫描，将待排序列分成两部分,一部
    分比基准元素小,一部分大于等于基准元素,此时基准元素在其排好序
    后的正确位置,然后再用同样的方法递归地排序划分的两部分，
    直到序列中的所有记录均有序为止                                                                 */
    static void quickSort(int[] arr,int low,int high){
        if(low>high){
            return;
        }
        int i,j,temp,t;
        i=low;
        j=high;
        //temp就是基准位
        temp = arr[low];
        while (i<j) {
            //todo "先"看右边，依次往左递减
            while (temp<=arr[j]&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp>=arr[i]&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }
        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组,右半数组
        //左右调用次序可以更换
        quickSort(arr, low, j-1);
        quickSort(arr, j+1, high);
    }
    static void quickSortFirst(int[] arr){
        arr =new int[]{2, 3, 6, 4, 0, 1, 7, 8, 5, 9};
        quickSort(arr, 0, arr.length-1);
        Log.i("quickSort: "+Arrays.toString(arr));
    }


    /**
     * 将数组的某一段元素进行划分，小的在左边，大的在右边
     * @param a
     * @param start
     * @param end
     * @return
     */
    public static int divide(int[] a, int start, int end){
        //每次都以最右边的元素作为基准值
        int base = a[end];
        //start一旦等于end，就说明左右两个指针合并到了同一位置，可以结束此轮循环。
        while(start < end){
            while(start < end && a[start] <= base)
                //从左边开始遍历，如果比基准值小，就继续向右走
                start++;
            //上面的while循环结束时，就说明当前的a[start]的值比基准值大，应与基准值进行交换
            if(start < end){
                //交换
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
                //交换后，此时的那个被调换的值也同时调到了正确的位置(基准值右边)，因此右边也要同时向前移动一位
                end--;
            }
            while(start < end && a[end] >= base)
                //从右边开始遍历，如果比基准值大，就继续向左走
                end--;
            //上面的while循环结束时，就说明当前的a[end]的值比基准值小，应与基准值进行交换
            if(start < end){
                //交换
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
                //交换后，此时的那个被调换的值也同时调到了正确的位置(基准值左边)，因此左边也要同时向后移动一位
                start++;
            }

        }
        //这里返回start或者end皆可，此时的start和end都为基准值所在的位置
        return end;
    }

    /**
     * 排序
     * @param a
     * @param start
     * @param end
     */
    public static void sort(int[] a, int start, int end){
        if(start > end){
            //如果只有一个元素，就不用再排下去了
            return;
        }
        else{
            //如果不止一个元素，继续划分两边递归排序下去
            int partition = divide(a, start, end);
            sort(a, start, partition-1);
            sort(a, partition+1, end);
        }

    }




}
