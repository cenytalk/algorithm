package java_algo.backtracking_39;

/**
 * 回溯算法
 * 1、8皇后问题
 */
public class BackTracking {
    //全局或成员变量，下标表示行，值表示queen存储在哪一列
    int[] result = new int[8];

    //总的放置方法数目
    int num = 0;

    public void cal8queens(int row) {
        if (row == 8) {//8个棋子位置放好了，打印结果
            printQueens(result);
            return;//8行棋子都放置好了，没办法再递归，所以return
        }

        for (int column = 0; column < 8; ++column) {//每一行都有8种方法
            if (isOk(row, column)) {//有些方法不满足要求
                result[row] = column;//第row行的棋子放到了column列
                //考察下一行
                cal8queens(row + 1);
            }
        }
    }

    //判断row行column列房子是否合适
    private boolean isOk(int row, int column) {
        //左上对角线
        int lefttop = column - 1;
        //右上对角线
        int righttop = column + 1;
        //逐行往上考察每一行
        for (int i = row - 1; i >= 0; --i) {
            //第i行的column列有棋子吗
            if (result[i] == column) {
                return false;
            }
            //考察左对角线，第i行lefttop列有棋子吗
            if (lefttop >= 0) {
                if (result[i] == lefttop) {
                    return false;
                }
            }
            //考察右对角线，第i行的righttop列有棋子吗
            if (righttop < 8) {
                if (result[i] == righttop) {
                    return false;
                }
            }
            --lefttop;
            --righttop;
        }
        return true;
    }

    //打印二维矩阵
    private void printQueens(int[] result) {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (result[row] == column) {
                    System.out.print("Q ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println();
        num++;
    }

    public static void main(String[] args) {
        BackTracking tracking = new BackTracking();
        tracking.cal8queens(0);
        System.out.println(tracking.num);
    }

}
