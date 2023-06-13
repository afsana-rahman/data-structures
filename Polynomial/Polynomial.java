package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		
		Node ans = null;
		while (poly1 != null && poly2 != null) {
			if (poly1.term.degree == poly2.term.degree) {
				float newCoeff = poly1.term.coeff + poly2.term.coeff;
				if (newCoeff != 0.0) {
					Node node = new Node(newCoeff, poly1.term.degree, ans);
					ans = node;
				}
				poly1 = poly1.next;
				poly2 = poly2.next;
			} else if (poly1.term.degree < poly2.term.degree) {
				Node node = new Node(poly1.term.coeff, poly1.term.degree, ans);
				ans = node;
				poly1 = poly1.next;
			} else if (poly1.term.degree > poly2.term.degree) {
				Node node = new Node(poly2.term.coeff, poly2.term.degree, ans);
				ans = node;
				poly2 = poly2.next;
			}
		}
		while (poly1 != null) {
			Node node = new Node(poly1.term.coeff, poly1.term.degree, ans);
			ans = node;
			poly1 = poly1.next;
		}
		while (poly2 != null) {
			Node node = new Node(poly2.term.coeff, poly2.term.degree, ans);
			ans = node;
			poly2 = poly2.next;
		}
	
		Node toReturn = null;
		for (Node ptr = ans; ptr != null; ptr = ptr.next) {
			Node node = new Node(ptr.term.coeff, ptr.term.degree, toReturn);
			toReturn = node;
		}
		return toReturn;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		
		if (poly1 == null || poly2 == null) { return null; }
		
		Node ans = null;
		for (Node ptr1 = poly1; ptr1 != null; ptr1 = ptr1.next) {
			for (Node ptr2 = poly2; ptr2 != null; ptr2 = ptr2.next) {
				float newCoeff = ptr1.term.coeff * ptr2.term.coeff;
				int newDegree = ptr1.term.degree + ptr2.term.degree;
				Node node = new Node(newCoeff, newDegree, ans);
				ans = node;
			}
		}
		
		Node toReturn = null;
		for (int i = ans.term.degree; i >= 0; i--) {
			float newCoeff = 0;
			for (Node ptr = ans; ptr != null; ptr = ptr.next) {
				if (ptr.term.degree == i) {
					newCoeff += ptr.term.coeff;
				}
			}
			if (newCoeff != 0) {
				Node node = new Node(newCoeff, i, toReturn);
				toReturn = node;
			}
		}
		return toReturn;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {

		if (poly == null) { return 0; }
		
		float toReturn = 0;
		for (Node ptr = poly; ptr != null; ptr = ptr.next) {
			toReturn += ptr.term.coeff * Math.pow(x, ptr.term.degree);
		}
		return toReturn;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
	
}