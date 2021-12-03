package ru.madbrains.impl;

import ru.madbrains.simpleList.ArrayIndexOutOfBoundsException;
import ru.madbrains.simpleList.Car;
import ru.madbrains.simpleList.ListOperation;
import ru.madbrains.simpleList.NoEntityException;
import ru.madbrains.simpleList.SimpleList;

import java.util.Comparator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;

public class Program {

    private static ReentrantLock lock = new ReentrantLock();
    private static SimpleList mycar;


    public static SimpleList createIndexList(int count) throws NoEntityException, ArrayIndexOutOfBoundsException {
        SimpleList<Car> indexList = new ListOperation();
        for (int i = 0; i < count; i++) {
            indexList.add(new Car(String.valueOf(i), String.valueOf(count - 1 - i), (float) i));
        }
        return indexList;
    }

    private static void swap(int[] array, int ind1, int ind2) {
        int tmp = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = tmp;
    }

    public static void print(SimpleList list) throws ArrayIndexOutOfBoundsException, NoEntityException {
        for(int i = 0; i<list.size(); i++){
            System.out.println(list.get(i).get());
        }
        System.out.println("\n");
    }


    public static void main(String[] args) {
        System.out.println("hi");
    }
}