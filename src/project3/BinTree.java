package project3;
//kjc190001
import java.text.DecimalFormat;

public class BinTree<E> {
	Node<?> root; // root of bintree
	boolean definite; // boolean to store whether the equation is definite or indefinite
	int startdf; // storing starting integer if its definite
	int enddf; // storing ending integer if its definite
	Double result = 0.0; // store the value of result if its definite
	 
	 public double calresult(String expression,double antiex, double antico) { // calculating result
		 Double x = (antico*(Math.pow(startdf,antiex)));// plug in startdf instead of x and calculate the result 
		 Double y = (antico*(Math.pow(enddf,antiex))); // plug in enddf instead of x and calculate the result
		 return (x-y); // return x-y;
	 }
	 public static Node[] findrightmost(Node temp,Node cur) { // recursive to find right most
		 if(cur.getRight()!=null) { // if there is still right side of cur
			 temp = cur; // temp = cur
			 findrightmost(temp,cur.getRight());  // insert right child of cur as cur so it can move right till it reaches the rightmost
		 }
		 Node [] n1 = new Node[2];
		 n1[0] = temp;
		 n1[1] = cur;
		return n1;
	 }
	
	 public void Insert(Node roo,Node n) { // inserting function
		 if(root==null) { // if root is null
			 this.root = n; // The node entered is root
		 }
		 else { // if root is not null
			 if(n.getAntiex()>roo.getAntiex()) { // if exponent of the n is larger than exponent of roo
				 if(roo.getRight()==null) { // check right side
					 roo.setRight(n); // if its null, set right to n
				 }
				 else { // if its not empty
					 Insert(roo.getRight(),n); //call insert again to loop till it finds the empty node
				 }
			 }
			 else { // if exponent of the n is smaller than expoenet of roo
				 if(roo.getLeft()==null) {  // check left side
					 roo.setLeft(n); // if its empty , setleft to n
				 }
				 else {
					 Insert(roo.getLeft(),n); // if not empty, call insert again to loop till empty node is found
				 }
			 }
		 }
	 }
	 public boolean Search(Node roo,int ex) { // search if there is a term with the int that was entered
		 		if(roo==null) {return false;}
 // if the entered int does not match the exponent value of roo
				 if(roo.getEx().intValue()<ex) {return Search(roo.getRight(),ex);} // if int is bigger than roo exponent, search right side for it
				 else if(roo.getEx().intValue()>ex) {return Search(roo.getLeft(),ex);} // if int is smaller than roo exponent, search left side of tree for it
				 else {System.out.println("There is a variable x that matches the exponent you entered");return true;} // print it exists}
			 
		 
	 }
	 public void Delete(Node parent,Node cur,int ex) {
		 try { 
		 if(cur.getEx().intValue()==ex) {}
		 else {
			 if(ex>cur.getEx().intValue()) {
				 parent = cur; Delete(parent,cur.getRight(),ex);
			 }
			 else {
				parent = cur; Delete(parent,cur.getLeft(),ex);
			 }
		 }
		 }catch(NullPointerException e) {System.out.println("The expoent you entered doesn't exist");}
		 if(cur.getEx().intValue()==ex) { // ex of cur equals exponent entered
			 if(cur.getLeft()==null&&cur.getRight()==null) { // if cur has no child
				if(parent.getLeft()==cur) { // if left of parent is cur
					parent.setLeft(null);  // setleft to null
				}
				else { // if right of parent is cur
					parent.setRight(null); // setright to null
				}
			 }
			 else if(cur.getLeft()==null) { // if cur had one right child
				if(parent.getLeft()==cur) { // if cur is left child of parent
					parent.setLeft(cur.getRight()); // setleft of parent to right of cur
				}
				else{ // if cur is right child of parent
					parent.setRight(cur.getRight()); // setright of parent to right of cur
				}
			 }
			 else if(cur.getRight()==null) { // if cur has one left child
				 if(parent.getLeft()==cur) { // if cur is left chiid of parent
					 parent.setLeft(cur.getLeft()); // setleft of parent to left of cur
				 }
				 else { // if cur is right child of parent
					 parent.setRight(cur.getLeft()); // setright of parent to right of cur
				 }
			 }
			 else { // if cur has two children
				 parent = cur; // set pointer to node being deleted
				 cur = cur.getLeft(); // set cur to curleft so it can iterate leftside of the brance
				 Node temp = null; // temp pointer to point parent of right most child
				 temp = findrightmost(temp,cur)[0]; // assign findrightmost function to temp
				 cur = findrightmost(temp,cur)[1]; // assign findrightmost function to cur
				 parent.setEx(cur.getEx()); 
				 parent.setCo(cur.getCo());
				 parent.setAntico(cur.getAntico());
				 parent.setAntiex(cur.getAntiex());
				 parent.setExpression(cur.getExpression()); // till here copying all the data of right most node to node being deleted
				 temp.setRight(cur.getLeft()); // if rightmost child had left child, temp.setright to that child
				 cur.setLeft(null); // left child of rightmost node is now null
			 }
		 }
		 
	 }
	 int i=0;
	 public void expression(Node node,double maxvalue,double minvalue, int numofex) { // function to make final expression
		 if(node==null) {} // if node is null, do nothing
		 else { // if not empty
		 i++;
		 expression(node.getRight(),maxvalue,minvalue,numofex); 
			 result+=calresult(node.getExpression(),node.getAntiex(),node.getAntico()); // adding all the result of the terms to get final reesult
		 if(node.getExpression().charAt(0)=='-'||node.getAntico()<0) { // if the term is negative and anticoefficient is less than 0
			 if(node.getAntiex()==minvalue+1&&minvalue!=maxvalue) { // if the term includes smallest exponent value
				 node.setExpression(node.getExpression().replaceFirst("[-]", "- "));System.out.print(node.getExpression());} // add space after first operator
			 else if(node.getAntiex()==maxvalue+1&&minvalue!=maxvalue) {System.out.print(node.getExpression()+" ");} // if the term includes maxex add space after expression
			 else if(maxvalue==minvalue) {System.out.print(node.getExpression());} // if there is only one term, just pirnt out the term only
			 else { node.setExpression(node.getExpression().replaceFirst("[-]", "- "));System.out.print(node.getExpression()+" ");} // eveything else, add space after operator and at the end of expression
		 }
		 else{// if its positive
			 if(node.getAntiex()==maxvalue+1&&numofex!=1) {System.out.print(node.getExpression()+" ");} // if the term includes maxex and the terms is not 1, add space after expression
			 else if(node.getAntiex()==maxvalue+1&&numofex==1) {System.out.print(node.getExpression());}// if the term includes maxex and there is only one term, printout the expression only
			 else if(node.getAntiex()==minvalue+1) {System.out.print("+ ");System.out.print(node.getExpression());} // if the term includes minex add plus sign infront
			 else{System.out.print("+ ");System.out.print(node.getExpression()+" ");}} // everything else, add plus infront and space at the end
		 expression(node.getLeft(),maxvalue,minvalue,numofex);
		 }
	 }
	 public void result(BinTree<E> tree) {// final display
		 DecimalFormat  d2 = new DecimalFormat();
		 d2.setMinimumFractionDigits(3);// to show 3 decimal places
		 if(tree.definite==false) { // if its indefinite equation
			 System.out.print(" + C");System.out.println(); // add C to the end of expression
		 }
		 else { // if its definite
			 System.out.print(", "+tree.enddf+"|"+tree.startdf+" = "+d2.format(result).replaceAll(",",""));System.out.println();  // add the result at the end
		 }
		 
	 }
	

}
