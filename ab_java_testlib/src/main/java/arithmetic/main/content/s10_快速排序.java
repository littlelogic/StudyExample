package arithmetic.main.content;

public class s10_快速排序 {

    private static void printArr(int[] arr) {
        for (int anArr : arr) {
            System.out.print(anArr + " ");
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static int partition(int[] arr, int left, int right) {
        int temp = arr[left];
        while (left < right) {
            // 先判断基准数和后面的数依次比较
            while (temp <= arr[right] && left < right) {
                --right;
            }
            // 当基准数大于了 arr[right]，则填坑
            if (left < right) {
                arr[left] = arr[right];
                ++left;
            }
            // 现在是 arr[right] 需要填坑了
            while (temp >= arr[left] && left < right) {
                ++left;
            }
            if (left < right) {
                arr[right] = arr[left];
                --right;
            }
        }
        arr[left] = temp;
        return left;
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (arr == null || left >= right || arr.length <= 1)
            return;
        int mid = partition(arr, left, right);
        quickSort(arr, left, mid);
        quickSort(arr, mid + 1, right);
    }

        /*
    三数取中法实现
    时间复杂度和我们的基准数的选择密不可分。基准数选好了，把序列每次都能分为几近相等的两份，
    我们的快排就跟着吃香喝辣；但一旦选择的基准数很差，那我们的快排也就跟着穷困潦倒。
    使用三数中值分割法消除了预排序输入的不好情形，并且减少快排大约 5% 的比较次数。
     */
    private static int partition_3(int[] arr, int left, int right) {
        // 采用三数中值分割法
        int mid = left + (right - left) / 2;
        // 保证左端较小
        if (arr[left] > arr[right])
            swap(arr, left, right);
        // 保证中间较小
        if (arr[mid] > arr[right])
            swap(arr, mid, right);
        // 保证中间最小，左右最大
        if (arr[mid] > arr[left])
            swap(arr, left, mid);
        int pivot = arr[left];
        while (right > left) {
            // 先判断基准数和后面的数依次比较
            while (pivot <= arr[right] && left < right) {
                --right;
            }
            // 当基准数大于了 arr[right]，则填坑
            if (left < right) {
                arr[left] = arr[right];
                ++left;
            }
            // 现在是 arr[right] 需要填坑了
            while (pivot >= arr[left] && left < right) {
                ++left;
            }
            if (left < right) {
                arr[right] = arr[left];
                --right;
            }
        }
        arr[left] = pivot;
        return left;
    }

    private static void quickSort_3(int[] arr, int left, int right) {
        if (arr == null || left >= right || arr.length <= 1)
            return;
        int mid = partition_3(arr, left, right);
        quickSort_3(arr, left, mid);
        quickSort_3(arr, mid + 1, right);
    }


    /*
     快速排序，todo 定基准数，左小右大，重复，分支，合并
    对于给定的一组记录，选择一个基准元素,通常选择第一个
    元素或者最后一个元素,通过一趟扫描，将待排序列分成两部分,一部
    分比基准元素小,一部分大于等于基准元素,此时基准元素在其排好序
    后的正确位置,然后再用同样的方法递归地排序划分的两部分，
    直到序列中的所有记录均有序为止
    挖坑填数 + 分治法
     */
    public static void main(String[] args) {
        int[] arr = {6, 4, 3, 2, 7, 9, 1, 8, 5};
        quickSort(arr, 0, arr.length - 1);
        printArr(arr);

        int[] arr_3 = {6, 4, 3, 2, 7, 9, 1, 8, 5};
        quickSort_3(arr_3, 0, arr.length - 1);
        printArr(arr_3);
    }

}
