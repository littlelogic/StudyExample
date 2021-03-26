package android.view;


//import android.util.Log;

public class Sort {

    static int num = 0 ;
    static int a[] = {};
    static void initArray() {
        a = new int[]{ 2, 3, 6, 4, 0, 1, 7, 8, 5, 9 };
    }

    public static void main(String[] args) {

//        initArray();
//        //冒泡排序
//        bubbleSort(a);
//
//        initArray();
//        //选择排序
//        seletSort_a(a);
//        seletSort_b(a);
//
//        //插入排序
//        insertSort(a);
//        insertSort_b(a);
//        insertSort_c(a);
//
//        initArray();
//        //希尔排序
//        shellSort2(a);
//
//        initArray();
//        //并归排序
//        mergeSortFirst(a);

        //快速排序
        quickSortFirst(a);
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

    从前向后(或从后向前)依次比较相邻的元素，若发现逆顺序，则交换。
    小的向前换，大的向后换，像水底的气泡逐渐向上冒，顾名思义冒泡排序法。
    通俗一点就是把大的往上挪！向冒泡一样。
    是交换式排序法的一种。冒泡排序法效率较低。                                                */
    private static void bubbleSort(int[] a) {
        int length = a.length;
        int temp = 0;
        /*for (int j = 0; j < a.length - 1; j++) {
            for (int i = 0; i < a.length - 1 - j; i++) {
                if (a[i] > a[i + 1]) {
                    // change
                    temp = a[i + 1];
                    a[i + 1] = a[i];
                    a[i] = temp;
                }
            }
        }*/
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
        toString(a);
    }

    //================================================

    /*
    选择排序法,第一次从R[0]~R[n-1]中选取最小值，与R[0]交换。
    第二次从R[1]~R[n-1]中选取最小值与R[1]交换。。。以此类推。
    通俗点说就是每次找到后面元素的最小值然后与之交换。
    选择排序法效率中。                                                             */
    private static void seletSort_a(int[] a) {
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
        toString(a);
    }

    public static void seletSort_b(int arr[]){
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
        toString(arr);
    }

    //================================================

    /*
    插入排序（Insertion-Sort）
    通过构建有序序列，对于未排序数据，
    在已排序序列中从后向前扫描，找到相应位置并插入。     */
    public static void insertSort(int[] ins){
        num = 0 ;
        initArray();
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
        toString(ins);
//        Log.i("","insertSort循环执行次数num： "+num);
    }

    public static void insertSort_b(int[] ins){
        num = 0 ;
        initArray();
        for(int i=1; i<ins.length; i++){
            int temp = ins[i];//保存每次需要插入的那个数
            int j;
            for(j=i; j>0&&ins[j-1]>temp; j--){//这个较上面有一定的优化
                ins[j] = ins[j-1];//吧大于需要插入的数往后移动。最后不大于temp的数就空出来j
                num ++;
            }
            /*for(j=i; j>0; j--){//这个较上面有一定的优化
                if (ins[j-1]>temp) {
                    ins[j] = ins[j-1];//吧大于需要插入的数往后移动。最后不大于temp的数就空出来j
                }
                num ++;
            }*/
            ins[j] = temp;//将需要插入的数放入这个位置
            //Log.i("第" + i + "趟排序后:" + Arrays.toString(ins));
        }
        toString(ins);
//        Log.i("insertSort_b循环执行次数num： "+num);
    }

    public static void insertSort_c(int[] array) {
        num = 0 ;
        initArray();
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
        toString(array);
    }

    //================================================

    /*

    希尔排序的实质就是分组插入排序，又称缩小增量法。
　　将整个无序序列分割成若干个子序列（由相隔某个“增量”的元素组成的）分别进行直接插入排序，
    然后依次缩减增量再进行排序，待整个序列中的元素基本有序时，再对全体元素进行一次直接插入排序。
　　因为直接插入排序在元素基本有序的情况下，效率是很高的，因此希尔排序在时间效率上有很大提高。
    无序序列：int a[] = {3,1,5,7,2,4,9,6};
    第一趟时：n=8; gap=n/2=4; 把整个序列共分成了4个子序列{3，2}、{1，4}、{5，9}、{7，6}
    第二趟时：gap=gap/2=2; 把整个序列共分成了2个子序列{2，5，3，9}、{1，6，4，7}
    第三趟时：对整个序列进行直接插入排序

     */
    private static void shellSort(int[] a){
        int n=a.length;
        int gap=n/2;
        while(gap>=1){
            for(int i=gap;i<a.length;i++){
                int j=0;
                int temp = a[i];
                for(j=i-gap;j>=0 && temp<a[j];j=j-gap){
                    a[j+gap] = a[j];
                }
                a[j+gap] = temp;
            }
            gap = gap/2;
        }
    }

    private static void shellSort2(int[] a){
        initArray();
        int n=a.length;
        int i,j,k,gap;
        for(gap=n/2;gap>0;gap/=2){
            for(i=0;i<gap;i++){
                for(j=i+gap;j<n;j+=gap){
                    int temp = a[j];
                    for(k=j-gap;k>=0 && a[k]>temp;k-=gap){
                        a[k+gap]=a[k];
                    }
                    a[k+gap]=temp;
                }
            }
            toString(a);
        }
//        toString(a);
        ///Log.i("shellSort2循环执行次数num： "+num);
    }

    //================================================

    //两路归并算法，两个排好序的子序列合并为一个子序列
    static void merge(int []a,int left,int mid,int right){
        int []tmp=new int[a.length];//辅助数组
        int p1=left,p2=mid+1,k=left;//p1、p2是检测指针，k是存放指针

        while(p1<=mid && p2<=right)
            if(a[p1]<=a[p2])
                tmp[k++]=a[p1++];
            else
                tmp[k++]=a[p2++];

        while(p1<=mid) tmp[k++]=a[p1++];//如果第一个序列未检测完，直接将后面所有元素加到合并的序列中
        while(p2<=right) tmp[k++]=a[p2++];//同上

        //复制回原素组
        for (int i = left; i <=right; i++)
            a[i]=tmp[i];
    }

    /*
    归并排序是一种概念上最简单的排序算法，与快速排序一样，归并排序也是基于分治法的。
    归并排序将待排序的元素序列分成两个长度相等的子序列，为每一个子序列排序，
    然后再将他们合并成一个子序列。合并两个子序列的过程也就是两路归并
     */
    static void mergeSort(int [] a,int start,int end){
        if(start<end){//当子序列中只有一个元素时结束递归
            int mid=(start+end)/2;//划分子序列
            mergeSort(a, start, mid);//对左侧子序列进行递归排序
            mergeSort(a, mid+1, end);//对右侧子序列进行递归排序
            merge(a, start, mid, end);//合并
        }
    }

    static void mergeSortFirst(int [] a){
        mergeSort(a, 0, a.length-1);
        toString(a);
    }

    //================================================

    /*
    对于给定的一组记录，选择一个基准元素,通常选择第一个元素或者最后一个元素,
    通过一趟扫描，将待排序列分成两部分,一部分比基准元素小,一部分大于等于基准元素,
    此时基准元素在其排好序后的正确位置,然后再用同样的方法递归地排序划分的两部分，
    直到序列中的所有记录均有序为止
     */
    public static void quickSort(int[] arr,int low,int high){
        int i,j,temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        //temp就是基准位
        temp = arr[low];

        while (i<j) {
            //先看右边，依次往左递减
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
        //递归调用左半数组
        quickSort(arr, low, j-1);
        //递归调用右半数组
        quickSort(arr, j+1, high);
    }


    public static void quickSortFirst(int[] arr){
        quickSort(arr, 0, arr.length-1);
        toString(a);
    }



}
