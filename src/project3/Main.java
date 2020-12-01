package project3;
// kjc190001
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
	public static boolean isnumber(char c) { // function to check if the char is number
		if (c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9') {
			return true;// return true if that char is number
		}
		else { //else return false
			return false;
		}
	}
	public static String replaceChar(String str,String replace, int index) {//function to plug in the string between the string 
		return str.substring(0, index) + replace + str.substring(index+1); // get a substring from the 0 to index that string is being inserted and add string and the rest
		}
	
	public static int digits(int a) { // funcion to find the digits of the number
		int digits=0; // initialize to 0 first
		while(a !=0) { // while a is not 0 do under
			a = a/10; // since its dividing by integer  if the number is lower than 10 it will return 0;
			digits++; // when a/10 != 0 add 1 to digits 
		}
		return digits; // return digits
	}
	public static boolean containsint (String line) {
		int [] num = {1,2,3,4,5,6,7,8,9}; // array of num containing 1-9
		boolean conint = false; // boolean conint;
		for(int x = 0;x<num.length;x++) { // for the length of line 
			if(line.contains(Integer.toString(num[x]))) { // if lines contains any of then number from 1-9, it will return true;
				 conint = true;
			}
		} 
		return conint;// else return false
	}

	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in); // scanner to scan the name of the input file
		String inputfile = "C:\\Users\\kippumchang\\Desktop\\computer science1\\project3\\src\\project3\\sample.txt";
		//String inputfile = scan.nextLine(); // scaning user input
		File f1 = new File(inputfile);
		try {
		BufferedReader br = new BufferedReader(new FileReader(f1)); // buffered reader and file reader to read file
		if(f1.exists()!=true) {System.out.println("The file does not exist");} // if the file does not exist , print file doesnt exist
		else { // if file exist
		String line; // string line that will contain the content of the lines;
		while((line = br.readLine())!=null) { // while line exist
		BinTree<Object> tree = new BinTree<Object>(); // tree to link all the nodes
		if(line.charAt(0)=='|') {tree.definite=false;} // if the first char of the line is | , its indefinite integral
		else {tree.definite=true; // else its definite integral
		if(line.charAt(0)=='-') {tree.enddf=Integer.parseInt(line.substring(0,line.indexOf('|')));} // if first char is - , enddf is from the start to the index of |
		else{tree.enddf=Character.getNumericValue(line.charAt(0));} // if the first char is not - , first char of the line is the enddf
		if(line.charAt(line.indexOf('|')+1)=='-') {tree.startdf=Integer.parseInt(line.substring(line.indexOf('|')+1,line.indexOf(' ')));} // if startdf is negative, change substring from - sign to index of space to integer
		else{tree.startdf=Character.getNumericValue(line.charAt(line.indexOf('|')+1));}}//if startdf is positive, char after | is startdf
		if(digits(tree.enddf)>=2) {line = line.substring(line.indexOf('|')+digits(tree.enddf)+1).replaceAll(" ", "");} // if enddf has more than 3 digits new line is from index of '|' +1 to the end
		else{line = line.substring(line.indexOf('|')+2).replaceAll(" ", "");}// if enddf has less than 2 digits assign substring from '|' +2 to end to line, and also delete all the spaces between using replaceall 
		line = line.replaceAll("\\+"," +"); // adding space infront every + signs in line
		line = line.replaceAll("dx",""); // deleting dx in line
		for(int x= 0;x<line.length();x++) {
			if(line.charAt(x)=='-'&&x==0) {} // if first term is negative, dont do anything because there should be no space between - and coefficient
			else if(line.charAt(x)=='-'&&line.charAt(x-1)!='^') {line = replaceChar(line," -",x);x=x+1;} // if its not first the first term, add space between - and  coefficient
			
		}
		if(line.charAt(0)!='-') {line= "+"+line.substring(0);}  // if the first term is not negative ,add plus sign in front of the first element
		ArrayList<String> linearray = new ArrayList<String>(); // line array that will store all the elements after spliting line by spaces
		ArrayList<Integer> exarray = new ArrayList<Integer>(); // integer array that will store all the exponent
		ArrayList<Integer> coarray = new ArrayList<Integer>(); // integer array that will store all the coefficient
		ArrayList<Integer> coarray2 = new ArrayList<Integer>(); // integer array that will store second coefficient value of all the trig equation
		ArrayList<String> cosorsin = new ArrayList<String>(); // String array that stores the anti derivative of trig 
		int tempex = -100; // temp exponent for trig equations, so it can be displayed after displaying all the antiderivatives of regular equations
		linearray.addAll(Arrays.asList(line.split(" "))); // after spliting line by space, add all the terms in linearray
		for(String e : linearray) {// for String e is element of lienarray
			if(e.contains("sin")) { // if e contains "sin" do below
				if(e.charAt(0)=='-') { // if first character of the term is - do below
					if(containsint(e)==false) { // if the term does not contain any integers
						coarray.add(1); // add 1 to coarray to make it positive because antiderivative of sin which is -cos times negative coefficient becomes positive
						coarray2.add(1); // add 1 to coarray2
						cosorsin.add("cos"); // because antiderivative of sin is cos, add cos to cosorsin
						exarray.add(tempex); // add tempex to exarray
						tempex--; // subtract 1 from tempex, so terms of trig can be displayed in a same order as it was inputted
					}
					else {// if the term does contain integers
						if(isnumber(e.charAt(e.indexOf('n')+1))==true) { // if char after n is number
							coarray.add(Integer.parseInt(e.substring(0,e.indexOf('s')))); // change substring from 0 to index of s to integer and assign it to coarray
							coarray2.add(Integer.parseInt(e.substring(e.indexOf('n')+1,e.indexOf('x')))); // change substring from n+1 to x to integer and assign it to coarray2
							cosorsin.add("cos"); // because antiderivative of sin is cos, add cos to cosorsin
							exarray.add(tempex);// add tempex to exarray
							tempex--;// subtract 1 from tempex, so terms of trig can be displayed in a same order as it was inputted
						}
						else {
							coarray.add(Integer.parseInt(e.substring(0,e.indexOf('s'))));
							coarray2.add(1);// add 1 to coarray to make it positive because antiderivative of sin which is -cos times negative coefficient becomes positive
							cosorsin.add("cos");// because antiderivative of sin is cos, add cos to cosorsin
							exarray.add(tempex);// add tempex to exarray
							tempex--;// subtract 1 from tempex, so terms of trig can be displayed in a same order as it was inputted
							
						}
					}
				}
				else { // if first char of the term is not - 
					if(containsint(e)==false) { // if the term does not conatin any integer
						coarray.add(-1); // add -1 because antiderivative of cos is negative
						coarray2.add(1); //if there is not integer, it means the all coeffiients are 1, therefore add 1 to coarray2
						cosorsin.add("cos");// add cos to cosorsin array
						exarray.add(tempex); // // add tempex to exarray
						tempex--;// subtract 1 from tempex, so terms of trig can be displayed in a same order as it was inputted
					}
					else { // if the term contains number
						if(isnumber(e.charAt(e.indexOf('n')+1))==true) { // if the char after n is number
							coarray.add(-Integer.parseInt(e.substring(0,e.indexOf('s')))); // change substring from 0 to s into integer and assign it to coarray, also - sign in the front because antiderivative of sin which is -cos times negative coefficient becomes positive
							coarray2.add(Integer.parseInt(e.substring(e.indexOf('n')+1,e.indexOf('x'))));
							cosorsin.add("cos");// add cos to cosorsin array
							exarray.add(tempex);// add tempex to exarray
							tempex--;// subtract 1 from tempex, so terms of trig can be displayed in a same order as it was inputted
						}
						else { // if the char after n is not number
							coarray.add(-Integer.parseInt(e.substring(0,e.indexOf('s'))));// change substring from 0 to s into integer and assign it to coarray, also - sign in the front because antiderivative of sin which is -cos times negative coefficient becomes positive
							coarray2.add(1); //coefficient is 1 since there is no number after n
							cosorsin.add("cos");// add cos to cosorsin array
							exarray.add(tempex);// add tempex to exarray
							tempex--;// subtract 1 from tempex, so terms of trig can be displayed in a same order as it was inputted
							
						}
					}
				}
			}
			else if(e.contains("cos")) { // if the integer contains "cos"
				if(e.charAt(0)=='-') { // if the char is -
					if(containsint(e)==false) { // if the term does not contain any integers
						coarray.add(-1);  // add -1 to coarray
						coarray2.add(1); // add 1 to coarray2
						cosorsin.add("sin"); // add sin to cosorsinarray because antiderivative of cos is sin
						exarray.add(tempex); // add tempex to exarray
						tempex--;// subtract 1 from tempex, so terms of trig can be displayed in a same order as it was inputted
					}
					else {
						if(isnumber(e.charAt(e.indexOf('s')+1))==true) { // char right after s is number
							coarray.add(Integer.parseInt(e.substring(0,e.indexOf('c')))); // convert substring from 0 to c to integer and assing to coarray
							coarray2.add(Integer.parseInt(e.substring(e.indexOf('s')+1,e.indexOf('x')))); // convert substring from s+1 to x to integer and assign to coarray2
							cosorsin.add("sin");// add sin to cosorsinarray because antiderivative of cos is sin
							exarray.add(tempex);// add tempex to exarray
							tempex--;// subtract 1 from tempex, so terms of trig can be displayed in a same order as it was inputted
						}
						else { // if there is no number after char s 
							coarray.add(Integer.parseInt(e.substring(0,e.indexOf('c')))); // convert substring from 0 to c to integer and assign to coarry
							coarray2.add(1); // add 1 to coarray2 because if theres no number after char s, it means the coefficient is 1
							cosorsin.add("sin");// add sin to cosorsinarray because antiderivative of cos is sin
							exarray.add(tempex);// add tempex to exarray
							tempex--;// subtract 1 from tempex, so terms of trig can be displayed in a same order as it was inputted
							
						}
					}
				}
				else { // if the term is positive
					if(containsint(e)==false) { // if the term does not contain any integers
						coarray.add(1); // first coefficient is 1
						coarray2.add(1); // second coefficient is also 1
						cosorsin.add("sin");// add sin to cosorsinarray because antiderivative of cos is sin
						exarray.add(tempex);// add tempex to exarray
						tempex--;// subtract 1 from tempex, so terms of trig can be displayed in a same order as it was inputted
					}
					else { // if the term contains integer
						if(isnumber(e.charAt(e.indexOf('s')+1))==true) { // if char after s is number
							coarray.add(Integer.parseInt(e.substring(0,e.indexOf('c')))); // convert substring from 0 to c to integer and assign to coarry
							coarray2.add(Integer.parseInt(e.substring(e.indexOf('s')+1,e.indexOf('x'))));// convert substring from s to x to integer and assign to coarry2
							cosorsin.add("sin");// add sin to cosorsinarray because antiderivative of cos is sin
							exarray.add(tempex);// add tempex to exarray
							tempex--;// subtract 1 from tempex, so terms of trig can be displayed in a same order as it was inputted
						}
						else { // if there is no number after char s
							coarray.add(Integer.parseInt(e.substring(0,e.indexOf('c'))));// convert substring from 0 to c to integer and assign to coarry
							coarray2.add(1); //coefficent 2 is 1 because there is no number after char s
							cosorsin.add("sin");// add sin to cosorsinarray because antiderivative of cos is sin
							exarray.add(tempex);// add tempex to exarray
							tempex--;// subtract 1 from tempex, so terms of trig can be displayed in a same order as it was inputted
							
						}
					}
					
				}
				
			}
			else { // if the term is not trig 
				if(e.indexOf('^')!=-1&&e.indexOf('x')!=-1&&e.charAt(1)!='x') { // if the term contains ^,x and char at index 1 is not x
					coarray.add(Integer.parseInt(e.substring(0,e.indexOf('x')))); // convert substring 0 to x to integer and add it to coarray
					exarray.add(Integer.parseInt(e.substring(e.indexOf('^')+1))); // convert substring from ^ to the end to integer and add it to exarray
					}
				else if(e.indexOf('^')==-1&&e.indexOf('x')!=-1&&e.charAt(1)!='x') { // if the term does not contain ^ and index 1 is not x
					coarray.add(Integer.parseInt(e.substring(0,e.indexOf('x')))); // // convert substring 0 to x to integer and add it to coarray
					exarray.add(1); // exponent is 1 since char '^' does not exist
				}
				else if(e.indexOf('^')!=-1&&e.indexOf('x')!=-1&&e.charAt(1)=='x') { // if the term contains ^,x and the index of 1 is 'x'
					if(e.charAt(0)=='-') { // if the first char is negative
						coarray.add(-1); // coefficient is -1 because the term is negative and nothing exist between operator and char 'x'
						exarray.add(Integer.parseInt(e.substring(e.indexOf('^')+1))); //convert substring from ^ to the end of term to integer and add it to exarray
					}
					else {// if the term is positive
					coarray.add(1); // coefficient is 1 because nothing exist between operator and char 'x'
					exarray.add(Integer.parseInt(e.substring(e.indexOf('^')+1))); // //convert substring from ^ to the end of term to integer and add it to exarray
					}
					
				}
				else if(e.indexOf('^')==-1&&e.indexOf('x')!=-1&&e.charAt(1)=='x'&&e.charAt(0)!='-') { // if the term has no ^,char at 1 is x and char at 0 is not -
					coarray.add(1); //since char at index 1 is x and char at 0 is '+' , coefficient is 1
					exarray.add(1); // exponent is 1 since there is no ^ sign
				}
				else if(e.indexOf('^')==-1&&e.indexOf('x')!=-1&&e.charAt(1)=='x'&&e.charAt(0)=='-') { // if the term has no ^,char at 1 is x and char at 0 is -
					coarray.add(-1);  //since char at index 1 is x and char at 0 is '-' , coefficient is -1
					exarray.add(1); // exponent is 1 because there is no ^ sign
				}
				else { // when none of above apply which is the term with coefficient only
					coarray.add(Integer.parseInt(e)); // the whole term is coefficient
					exarray.add(0); // 0 is the exponent because there is no 'x^0' is 1 
					}
				
			}
			}
		int numofex=linearray.size(); // assign size of array to numofex
		int i=linearray.size(); // assign size of array to i
		for(int H=0;H<i;H++) {// for size of array
			for(int x=0;x<i-1;x++) { // for size of array -1
			for(int y=x+1;y<exarray.size();y++) { // for size of expoent array
			if(exarray.get(x)==exarray.get(y)&&exarray.get(x)!=Integer.MIN_VALUE) { // if the value of the exponents are the same and the exponet is not integer.minvalue
				coarray.set(x, coarray.get(x)+coarray.get(y)); // add two exponents
				exarray.set(y, Integer.MIN_VALUE); // set the exponent of the term that had same exponent value to integer.minvalue
				numofex--; // everytime theres a tie in exponent value , subtract 1 from numofex 
			}
			}
			}
		}
		
		int z=0;
		int numoftrig =0; // to store the number of trig terms
		double maxex = -1000; // to store the value of highest exponent
		double minex = 1000; // to store the value of lowest exponent
		double max = -15;
		for(int c =0;c<exarray.size();c++) {// for size of exponent array
			if(exarray.get(c)>max) {max = exarray.get(c);} // finding the highest exponent
		}
		double [] earray = new double[numofex]; // earray with the size of numofexponents
		for(int x=0;z<numofex;x++) { // for num of exponents
			if(exarray.get(z)==Integer.MIN_VALUE) {z++;} // if the value is integer.minvalue, add 1 to z to skip the term with Integer.minvalue as exponent
			Payload p1 = new Payload(); // paylaod class
			earray[x] = exarray.get(z); // assigning exponent to earray
			if(exarray.get(z)>=-10) { // if the term is not trig
			p1.setExponent(exarray.get(z)); // assign values from exarray to exponent attribute of payload
			p1.setCoefficient(coarray.get(z));} // assign values from coarray to coefficient of payload
			else {	// if its trig
			p1.setExponent(exarray.get(z));// assign values from exarray to exponent attribute of payload
			p1.setCoefficient(coarray.get(z)); // assign values from coarray to coefficient of payload
			p1.setCoefficient2(coarray2.get(numoftrig)); // assign values from coarray2 to coefficient2 of payload
			p1.setCossin(cosorsin.get(numoftrig)); // // assign values from coaorsingarray to Cossin of payload
			numoftrig++; // add 1 everytime there is a trig term
			}
			Node<Number> n1 = new Node<Number>(p1.getCoefficient(),p1.getExponent(),p1.getCoefficient2(),p1.getCossin(),max);// node with all the data
			tree.Insert(tree.root,n1);// insert node into tree
			z++; // add 1 to z
		}
		for(int x=0;x<numofex;x++) {
		if(earray[x]>maxex) {maxex = earray[x];} // finding highest exponent
		if(earray[x]<minex) {minex = earray[x];} // finding lowest exponent
		}
		System.out.println(tree.Search(tree.root,4)); // searching function to see if the term with certain exponent already exists in tree
		//tree.Delete(tree.root,tree.root,6); // deleting the term with the exponent you entered
		tree.expression(tree.root,maxex,minex,numofex);tree.result(tree); // displaying results and expression
		}
		}
		
		
		
		
		} catch (FileNotFoundException e) {
			System.out.println("The file has not been founded"); // if file has not been founded, print the file has not been founded
		}catch (IOException e) {
		}
	}
}
