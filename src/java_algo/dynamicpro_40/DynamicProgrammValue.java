package java_algo.dynamicpro_40;

/**
 * 动态规划
 * 0-1背包问题
 * 涉及物品数量、总量、以及价值
 */
public class DynamicProgrammValue {

    private int maxV = Integer.MIN_VALUE; // 结果放到maxV中
    private int[] items = {2, 2, 4, 6, 3};  // 物品的重量
    private int[] value = {3, 4, 8, 9, 6}; // 物品的价值
    private int n = 5; // 物品个数
    private int w = 9; // 背包承受的最大重量

    /**
     * 采用回溯法解决0-1背包问题
     *
     * @param i
     * @param cw
     * @param cv
     */
    public void f(int i, int cw, int cv) { // 调用f(0, 0, 0)
        if (cw == w || i == n) { // cw==w表示装满了，i==n表示物品都考察完了
            if (cv > maxV) maxV = cv;
            return;
        }
        f(i + 1, cw, cv); // 选择不装第i个物品
        if (cw + items[i] <= w) {
            f(i + 1, cw + items[i], cv + value[i]); // 选择装第i个物品
        }
    }


    /**
     * 0-1背包问题
     * 使用动态规划解决
     *
     * @param weight
     * @param value
     * @param n
     * @param w
     * @return
     */
    public static int knapsack3(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w + 1];
        for (int i = 0; i < n; ++i) { // 初始化states
            for (int j = 0; j < w + 1; ++j) {
                states[i][j] = -1;
            }
        }
        states[0][0] = 0;
        if (weight[0] <= w) {
            states[0][weight[0]] = value[0];
        }
        for (int i = 1; i < n; ++i) { //动态规划，状态转移
            for (int j = 0; j <= w; ++j) { // 不选择第i个物品
                if (states[i - 1][j] >= 0) states[i][j] = states[i - 1][j];
            }
            for (int j = 0; j <= w - weight[i]; ++j) { // 选择第i个物品
                if (states[i - 1][j] >= 0) {
                    int v = states[i - 1][j] + value[i];
                    if (v > states[i][j + weight[i]]) {
                        states[i][j + weight[i]] = v;
                    }
                }
            }
        }
        // 找出最大值
        int maxvalue = -1;
        for (int j = 0; j <= w; ++j) {
            if (states[n - 1][j] > maxvalue) maxvalue = states[n - 1][j];
        }
        return maxvalue;
    }

    public static void main(String[] args) {

        int[] items = {2, 2, 4, 6, 3};  // 物品的重量
        int[] value = {3, 4, 8, 9, 6}; // 物品的价值
        int n = 5; // 物品个数
        int w = 9; // 背包承受的最大重量
        int maxValue = DynamicProgrammValue.knapsack3(items, value, 5, 9);
        System.out.println(maxValue);

    }
}
