package edu.asu.sbs.utilities;

public class Test3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int c ;
		try {
			c= 5 /0;
		}catch(Exception e) {
			System.err.println("Exception");
		}finally {
			System.err.println("Finally");
		}
	}

}
