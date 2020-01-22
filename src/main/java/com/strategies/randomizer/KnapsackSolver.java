package com.strategies.randomizer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class KnapsackSolver {
    private int maxWeight; // Backpack size
    private int n; // Item type count
    private int[] values;
    private int[] weights;

    private List<Integer> items = new LinkedList<>();

    public KnapsackSolver(int maxWeight, int n, int[] values, int[] weights){
        this.maxWeight = maxWeight;
        this.n = n;
        this.values = values;
        this.weights = weights;
    }

    /**
     * Returns the best item backpack
     * @return
     */
    public List<Integer> getBackpack(){ return this.items; }

    /**
     * Solve the backpack problem when an item can be picked more than once
     */
    public void solveWithDuplicates(){
        var cost = new int[this.maxWeight+1];
        var best = new int[maxWeight+1];

        Arrays.fill(best, -1);

        for(int j = 1; j < this.n+1; j++){

            for (int i = 1; i < this.maxWeight+1; i++){
                if (i - this.weights[j-1] < 0)
                    continue;

                var currentItemMaxValue = cost[i - this.weights[j-1]] + this.values[j-1];

                if (cost[i] < currentItemMaxValue){
                    cost[i] = currentItemMaxValue;
                    best[i] = j-1;
                }
            }
        }

        var i = maxWeight;
        while (i > 0 && best[i] > 0){
            var index = best[i];
            this.items.add(index);
            i-=this.weights[index];
        }
    }

    /**
     * Solve the backpack problem when an item can be picked only once
     */
    public void solve() {
        int B[][] = new int[n + 1][this.maxWeight + 1];

        for (int i = 0; i <= n; i++)
            for (int j = 0; j <= this.maxWeight; j++) {
                B[i][j] = 0;
            }

        for (int i = 1; i <= this.n; i++) {
            for (int j = 0; j <= this.maxWeight; j++) {
                B[i][j] = B[i - 1][j];

                if ((j >= this.weights[i - 1]) && (B[i][j] < B[i - 1][j - this.weights[i - 1]] + this.values[i - 1])) {
                    B[i][j] = B[i - 1][j - this.weights[i - 1]] + this.values[i - 1];
                }

            }
        }

        int i = this.maxWeight;
        int N = this.n;

        while (N != 0) {

            if (B[N][i] != B[N - 1][i]) {

                this.items.add(N-1);
                i = i - this.weights[N - 1];
            }
            N--;
        }
    }
}
