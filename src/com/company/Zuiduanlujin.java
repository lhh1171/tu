package com.company;

import java.util.Scanner;

//Floyd算法：
//以无向图G为入口，得出任意两点之间的路径长度length[i][j]，路径path[i][j][k]，
//途中无连接得点距离用0表示，点自身也用0表示

public class Zuiduanlujin {
    // 任意两点之间路径长度
    int[][] llength = null;
    // 任意两点之间的路径
    int[][][] path = null;

    public Zuiduanlujin(int[][] G) {
        int MAX = 100;
        int row = G.length;
        // 图G的行数
        int[][] spot = new int[row][row];
        // 定义任意两点之间经过的点
        int[] onePath = new int[row];
        // 记录一条路径
        llength = new int[row][row];
        path = new int[row][row][];
        for (int i = 0; i < row; i++){
            // 处理图两点之间的路径
            for (int j = 0; j < row; j++) {
                if (G[i][j] == 0){
                    G[i][j] = MAX;
                    // 没有路径的两个点之间的路径为默认最大
                }
                if (i == j){
                    G[i][j] = 0;
                    // 本身的路径长度为0
                }
            }
        }
        for (int i = 0; i < row; i++){
            // 初始化为任意两点之间没有点
            for (int j = 0; j < row; j++){
                spot[i][j] = -1;
            }
        }

        for (int i = 0; i < row; i++){
            // 假设任意两点之间没有路径
            onePath[i] = -1;
        }

        for (int v = 0; v < row; ++v){
            for (int w = 0; w < row; ++w){
                llength[v][w] = G[v][w];
            }
        }

        for (int u = 0; u < row; ++u){
            for (int v = 0; v < row; ++v){
                for (int w = 0; w < row; ++w){
                    if (llength[v][w] > llength[v][u] + llength[u][w]) {
                        // 如果存在更短路径则取更短路径
                        llength[v][w] = llength[v][u] + llength[u][w];
                        // 把经过的点加入
                        spot[v][w] = u;
                    }
                }
            }
        }

        for (int i = 0; i < row; i++) {
            // 求出所有的路径
            int[] point = new int[1];
            for (int j = 0; j < row; j++) {
                point[0] = 0;
                onePath[point[0]++] = i;
                outputPath(spot, i, j, onePath, point);
                path[i][j] = new int[point[0]];
                for (int s = 0; s < point[0]; s++){
                    path[i][j][s] = onePath[s];
                }
            }
        }
    }

    void outputPath(int[][] spot, int i, int j, int[] onePath, int[] point) {
        // 输出i// 到j// 的路径的实际代码，point[]记录一条路径的长度
        if (i == j) {
            return;
        }
        if (spot[i][j] == -1) {
            onePath[point[0]++] = j;
        } else {
            outputPath(spot, i, spot[i][j], onePath, point);
            outputPath(spot, spot[i][j], j, onePath, point);
        }
    }

    public static void main(String[] args) {
        int data[][] = {
                { 0, 27, 44, 17, 11, 27 },
                // 0
                { 27, 0, 31, 27, 49, 0 },
                // 1
                { 44, 31, 0, 19, 0, 27 },
                // 2
                { 17, 27, 19, 0, 14, 0},
                // 3
                { 11, 49, 0, 14, 0, 13 },
                // 4
                { 27, 0, 27, 0, 13, 0 },
                // 5
        };
        
        Zuiduanlujin test = new Zuiduanlujin(data);
        Scanner sc = new Scanner(System.in);

        System.out.println("请输入开始节点(0-5)：");
        int i = sc.nextInt();

        System.out.println("请输入结束节点(0-5)：");
        int j = sc.nextInt();

        System.out.print("从 " + i + " 到 " + j + "的最短路径是: ");
        for (int k = 0; k < test.path[i][j].length; k++) {
            System.out.print(test.path[i][j][k] + " ");
        }

        System.out.println();
        System.out.println("从 " + i + " 到 " + j + " 的长度是 :" + test.llength[i][j]);
    }
}
