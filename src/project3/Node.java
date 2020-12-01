package project3;
//kjc190001
import java.text.DecimalFormat;

public class Node<T extends Number>{
	private T co;
	private T ex;
	private T trig;
	private double antico;
	private double antiex;
	private String expression;
	private Node left;
	private Node right;
	public T getCo() {
		return co;
	}
	public void setCo(T co) {
		this.co = co;
	}
	public T getEx() {
		return ex;
	}
	public void setEx(T ex) {
		this.ex = ex;
	}
	public double getAntico() {
		return antico;
	}
	public void setAntico(double antico) {
		this.antico = antico;
	}
	public double getAntiex() {
		return antiex;
	}
	public void setAntiex(double antiex) {
		this.antiex = antiex;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}// setters and getters

	
	Node(){
	}
	public static Double GCD(double num1, double num2){ // finding greatest common denominator
		double i=1.0; 
		double gcd = 1.0;
		   while(i<=num1&&i<=num2) { // till its lower than both number
			   if(num1%i==0.0&&num2%i==0.0) { // if the both number divide with i leaves no remainder
				   gcd = i; // store the value
			   }
			   i++;
		   }
		return gcd; // return gcd
		}

	public static <T extends Number> Double[] anti(T co, T ex){
	    Double antie  = (ex.doubleValue()+1); // calculating antiex
	    Double antic  = (co.doubleValue()/(ex.doubleValue()+1)); // calculating antico
	    Double []anti= new Double[2];
	    anti[0] = antic; 
	    anti[1] = antie;
	      return anti; // return array with antico and antiex
	     }
	
	Node(T coefficient,T exponent,int co2,String cossin, Double max){
		DecimalFormat d1 = new DecimalFormat();
		d1.setMaximumFractionDigits(0); // to not display decimal places
		this.co = coefficient; // assign coefficeint to co of node 
		this.ex = exponent; // assign exponent to ex of node
		antico = (anti(coefficient,exponent)[0]); // assign first element of the array to antico
		antiex = (anti(coefficient,exponent)[1]); // assign second element of the array to antiex
		if(this.antico==0) {expression = "0";} // if the result of adding all the term with the same exponent equals zero, print 0
		else if(exponent.intValue()<-10) { // if its not trig function
			if(coefficient.intValue()<0) { // if coefficient is negative
				if(co2==1) {expression = (coefficient.intValue()/co2)+cossin+" "+"x";} // if second coefficient is 1, dont display 1
				else{expression = (coefficient.intValue()/co2)+cossin+" "+co2+"x";} // else  display co2 
				}
			
			else { // if its positive
				if(co2==1) {expression = "+ "+(coefficient.intValue()/co2)+""+cossin+" "+"x";} // if second coefficient is 1 , dont display 1
				else{expression = "+ "+(coefficient.intValue()/co2)+""+cossin+" "+co2+"x";} //else display co2
			}
		}
		else{expression = (int)antico + "x" +"^"+ d1.format(antiex);} // if its not trig term, display inorder of antico,x,^,and antiex at the end 
		if(exponent.intValue()>=-10) { // if its not trig term
			if(antico==1&&antiex!=1&&(!(co.doubleValue()<0)&&(antiex<0))) { // when antico is 1 and antiex is not
				expression = expression.replaceAll(Integer.toString((int) antico),"");} // delete antico from expression
			else if(antico==1&&antiex ==1) {expression = "x"; } // if both antiex and antico are 1, just display x
			else if(antico!=1&&antiex==1) {expression = (int)antico + "x";} // if antiex is 1 and antico is not , do not display value of ex
			else if(antico==1&&antiex!=1) {expression = expression.replaceFirst("1x","x");} // if aitico is 1 and antiex is not , replace 1x with x
			else if((co.doubleValue()<0)&&(antiex<0)&&antico==1) {expression = expression.replaceFirst("1","");} // if co is less than 0,antiex <0 and antico ==1,delete first 1
			else if(antico==-1) {expression = expression.replaceAll("-1x","-x"); } // if antico is -1, replace -1 with -
			else if(antico<0) {} // if antico is less than 0, do nothing
			}
		else {// if its trig term
			if(coefficient.intValue()/co2==-1) {// if the antico is -1
				expression = expression.replaceFirst("-1","-"); // replace -1 with -
			}
			else if(coefficient.intValue()/co2==1) { // if the antico is 1
				expression = expression.replaceFirst("1", ""); // delete 1 from expression
			}
			if(exponent.intValue()==-100&&-100==max) { // if only the trig terms are on the inputfile
				expression = expression.replaceFirst(" ",""); // delete the space infront of first term
			}
			if(coefficient.doubleValue()/co2<1) {// if the antico is fraction
				if(coefficient.doubleValue()<0) {expression =expression.replaceFirst(Integer.toString((coefficient.intValue()/co2)),"-"+"("+Math.abs(coefficient.intValue())+"/"+co2+")");}
				else{expression =expression.replaceFirst(Integer.toString((coefficient.intValue()/co2)),"("+coefficient.intValue()+"/"+co2+")");}
				// delete the coefficient.invalue/co2 from expression and rewrite in string format
			}
		}
		Double gcd =  GCD(Math.abs(co.doubleValue()),Math.abs(antiex)); // assign calculated gcd value to gcd variable
		if(exponent.intValue()>=-10) { // if not trig function
			if((co.doubleValue()/(ex.doubleValue()+1))%1!=0||(co.doubleValue()/(ex.doubleValue()+1))%1!=-0){ // if  are not fractions
				 if(antico<0) { // if antico is negative
					 if(antiex ==max+1) { // if its the term with largest exponent value, keep the operator inside parenthesis, if not keep it outside
						 expression =  expression.replaceFirst(Integer.toString((int) antico),"(" +d1.format(co.doubleValue()/gcd)+"/"+d1.format((ex.doubleValue()+1)/gcd)+")");}
					 else{expression = expression.replaceFirst(Integer.toString((int) antico),"- "+"(" +d1.format(Math.abs(co.doubleValue())/gcd)+"/"+d1.format(Math.abs((ex.doubleValue()+1)/gcd))+")");}
				}	
				else {// if antico is positive print out in string format, and delete comma from the expression
					expression =expression.replaceFirst(Integer.toString((int) antico),"("+d1.format(Math.abs(co.doubleValue())/gcd).replaceFirst(",","")+"/"+d1.format(Math.abs((ex.doubleValue()+1)/gcd))+")");	
				}	
				expression =  expression.replaceAll("- ", "-");
			}
		}
		expression.replaceAll("  "," "); // replacing all the double space to space if theres one
	}

	

}

