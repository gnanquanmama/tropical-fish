package com.liby.search.code;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import java.util.PriorityQueue;
import java.util.Random;

public class DsMain {

    public static void main(String[] args) {
        Random random = new Random();

        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for(int i=0;i<9;i++){
            queue.add(random.nextInt());
        }

        int size = queue.size();

        for(int i=0;i<size;i++){
            System.out.println(queue.poll());
        }

    }

}
