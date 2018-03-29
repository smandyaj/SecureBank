package edu.asu.sbs.utilities;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input = "traY";
		String prevStr = "";
		String currStr = input;
		
		//long l = 100000;
		//int d = l;
		
		//while(!prevStr.equals(currStr)){
			prevStr = currStr;
			//rule 1
			currStr = currStr.replaceAll("s","5");
			currStr = currStr.replaceAll("S","5");
			System.out.println("Rule1 " + currStr.length());
			//rule 2 and 3
			if( currStr.length() > 1 && currStr.length() % 2 != 0){
				int mid = currStr.length()/2;
				if(Character.isDigit(currStr.charAt(mid))){
					// double it
					String leftStr = currStr.substring(0, mid);
					String rightStr = currStr.substring(mid+1, currStr.length());
					int digit = Integer.valueOf(currStr.charAt(mid) - '0');
					System.out.println(leftStr + " " + digit + " "  + rightStr);
					digit = digit * 2;
					currStr = leftStr + digit + rightStr;
				}
				System.out.println("Rule2 " + currStr.length());
			}
			
			
			if(currStr.length() > 1 && currStr.length() % 2 == 0){
			// even length
				currStr = swapChars(currStr);
				System.out.println("Rule3 " + currStr.length());
			}
			
			// rule 4
			if(currStr.toLowerCase().contains("nextcapital")) {
				int startIndex = currStr.toLowerCase().indexOf("next");
				int endIndex = startIndex + 3;
				System.out.println(startIndex + " " + endIndex);
				char[] charArray = currStr.toCharArray();
				for(int i = startIndex,j = endIndex; i <= j ;i++, j--) {
					char temp = charArray[i];
					charArray[i] = charArray[j];
					charArray[j] = temp;
				}
				
				currStr = String.valueOf(charArray);
				System.out.println("Rule4 " + currStr.length());
			}
			
			System.out.println(currStr);
		//}
		
		//System.out.println(currStr);
	}
	
	public static String swapChars(String str) {
		
		print(null);
		char[] charArray = str.toCharArray();
		char firstCh = charArray[0];
		char lastCh =  charArray[str.length()-1];
		charArray[0] =(Character.isLowerCase(lastCh) ? Character.toUpperCase(lastCh): Character.toLowerCase(lastCh));
		charArray[str.length()-1] = (Character.isLowerCase(firstCh) ? Character.toUpperCase(firstCh): Character.toLowerCase(firstCh));
		return String.valueOf(charArray);
	}
	
	
	public static void print(Integer s) {
		System.out.println("sumne "+ s);
	}
}
