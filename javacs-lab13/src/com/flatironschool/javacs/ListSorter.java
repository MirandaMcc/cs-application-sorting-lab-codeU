/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Collections;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
		//simple base case
		if (list.size() <= 1)
			return list;

		//split list in half
		List<T> list1 = new LinkedList<T>(list.subList(0,list.size()/2));
		List<T> list2 = new LinkedList<T>(list.subList(list.size()/2,list.size()));

		//Sort the halves using Collections.sort or insertionSort or recursion
		List<T> sorted1 = mergeSort(list1,comparator);
		List<T> sorted2 = mergeSort(list2,comparator);

		//Merge the sorted halves into a complete sorted list
		List<T> sorted = merge(sorted1,sorted2 ,comparator);
        return sorted;
	}

	//helper method for merge functionality
	private List<T> merge(List<T> list1, List<T> list2,Comparator<T> comparator){
		List<T> sorted = new ArrayList<T>();
		int sizeTotal = list1.size()+list2.size();
		while(sorted.size() < sizeTotal){
			if(list2.isEmpty()){
				sorted.addAll(list1);
			}
			else if(list1.isEmpty()){
				sorted.addAll(list2);
			}
			else if(comparator.compare(list2.get(0),list1.get(0)) <= 0) //less than
					sorted.add(list2.remove(0)); //linkedlists make remove(i) = O(1)
			else
					sorted.add(list1.remove(0));
			}
			
		return sorted;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(),comparator);
		//add elements to heap
		for(T item : list)
			heap.add(item);
		//remove in sorted order
		list.clear();
		while(!heap.isEmpty())
			list.add(heap.remove());
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
		//PriorityQueue is a minheap - first element is smallest
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(),comparator); 
		//add elements to heap
		for(T item : list)
		{
			//remove smallest if new item is larger
			if(heap.size() < k)
				heap.add(item);
			else if(comparator.compare(item,heap.peek())>0){
				heap.remove();
				heap.add(item);
			}
		}
		//remove all items in sorted order
		List<T> largest = new ArrayList<T>();
		while(!heap.isEmpty())
			largest.add(heap.remove());

        return largest;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
