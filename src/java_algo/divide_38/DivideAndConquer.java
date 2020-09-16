package java_algo.divide_38;

/**
 * 分治算法
 * 第38课
 */
public class DivideAndConquer {
    private int num = 0;//全局变量或者成员变量

    public int count(int[] a, int n) {
        num = 0;
        mergeSortCounting(a, 0, n - 1);
        return num;
    }

    //递归调用函数
    private void mergeSortCounting(int[] a, int p, int r) {
        // 递归终止条件
        if (p >= r) {
            return;
        }

        // 取p到r之间的中间位置q,防止（p+r）的和超过int类型最大值
        int q = p + (r - p) / 2;

        //分治递归
        mergeSortCounting(a, p, q);
        mergeSortCounting(a, q + 1, r);

        //将A[p...q]和A[q+1...r]合并为A[p...r]
        merge(a, p, q, r);
    }

    //这段代码看不懂
    private void merge(int[] a, int p, int q, int r) {
        // 初始化变量i, j, k
        int i = p, j = q + 1, k = 0;
        // 申请一个大小跟a[p...r]一样的临时数组
        int[] tmp = new int[r - p + 1];
        while (i <= q && j <= r) {
            if (a[i] <= a[j]) {
                tmp[k++] = a[i++];// i++等于i:=i+1
            } else {
                num += (q - i + 1); // 统计p-q之间，比a[j]大的元素个数
                tmp[k++] = a[j++];
            }
        }
        while (i <= q) { // 处理剩下的
            tmp[k++] = a[i++];
        }
        while (j <= r) { // 处理剩下的
            tmp[k++] = a[j++];
        }
        for (i = 0; i <= r - p; ++i) { // 从tmp拷贝回a
            a[p + i] = tmp[i];
        }
    }

}