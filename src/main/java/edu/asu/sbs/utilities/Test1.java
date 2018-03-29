package edu.asu.sbs.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int pathLength = 5;
		int[][] floristIntervals = new int[][]{
				{0,5},{0,5},{0,5},{0,5},{0,5},{5,6},{6,7}
		};
		
		
		List<int[]> list = new ArrayList<int[]>();
		
		Arrays.sort(floristIntervals, new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1] - o2[1];
			}
		});
		
		int maxFlorists = 0;
		int oldest = 0;
		
		for( int i = 0; i < floristIntervals.length;i++ ) {
			
			if( list.size() < 3) {
				list.add(floristIntervals[i]);
				maxFlorists++;
			}else if(list.size() == 3){
				int[] curr = floristIntervals[i];
				int overLap = checkOverlap(list, curr);
				int nonOverLap = list.size() - overLap;
				// all overlaps with curr
				if(overLap == 3){
					continue;
				}else if( nonOverLap == 3) {
					System.out.println("all");
					list.set(oldest, curr);
					oldest = (oldest+1)%3;
					//maxFlorists++;
				}else if(nonOverLap > 1) {
					System.out.println("farthest");
					int index = getIndexOfFarthest(list,curr);
					maxFlorists++;
				}
			}
		}
		
		System.out.println(maxFlorists);
	}
	
	private static int getIndexOfFarthest(List<int[]> list, int[] curr) {
		// TODO Auto-generated method stub
		int farthest = Integer.MIN_VALUE;
		int index = 0;
		for(int i =0; i < list.size(); i++) {
			int[] temp = list.get(i);
			if(temp[1] >= farthest) {
				index = i;
			}
		}
		return index;
	}

	public static int checkOverlap(List<int[]> list, int[] curr) {
		
		int overLap = 0;
		for( int[] temp : list) {
			if(curr[0] < temp[1]) {
				overLap++;
			}
		}
		return overLap;
		
	}
}
