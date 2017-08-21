package com.company.H321;

import java.util.ArrayList;
import java.util.List;

class Combinator {
    private List<Coords> input;  // input array
    private int setSize = 3;         // sequence length
    private List<List<Coords>> subsets = new ArrayList<>();
    private int inpSize;

    public Combinator(List<Coords> input) {
        this.input = input;
        this.inpSize = input.size();
    }

    int[] s = new int[setSize];
    // here we'll keep indices
    // pointing to elements in input array

    public List<List<Coords>> generate(int k) {
        if (k == this.setSize) {
            k = setSize;
        }

        if (k <= inpSize) {
            // first index sequence: 0, 1, 2, ...
            for (int i = 0; (s[i] = i) < k - 1; i++) ;
            subsets.add(getSubset(input, s));
            for (; ; ) {
                int i;
                // find position of item that can be incremented
                for (i = k - 1; i >= 0 && s[i] == inpSize - k + i; i--) ;
                if (i < 0) {
                    break;
                }
                s[i]++;                    // increment this item
                for (++i; i < k; i++) {    // fill up remaining items
                    s[i] = s[i - 1] + 1;
                }
                subsets.add(getSubset(input, s));
            }
        }
        return subsets;
    }

    // generate actual subset by index sequence
    private List<Coords> getSubset(List<Coords> input, int[] subset) {
        List<Coords> result = new ArrayList<>();
        for (int i = 0; i < subset.length; i++) {
            result.add(input.get(subset[i]));
        }
        return result;
    }
}