package RedBox;

import java.io.IOException;
import java.io.PrintWriter;


public class BSTree <E extends Comparable<E>>  {
	protected Node<E> root;

	//Constructor
	public BSTree() {
		root = null;
	}
	
	//Insert Method 
	private Node<E> insert(Node<E> currentNode, String elementToInsert, int available, int rental) {
		//Base case: if root, or currentNode.left or currentNode.right hits null, insert it there
		if (currentNode == null) { 	
			Node<E> newNode = new Node<E>(elementToInsert, available, rental);
			currentNode = newNode;		
		}	
		if (elementToInsert.compareTo(currentNode.title) == 0) {
			return currentNode;
		}
		//Keep moving to find the right parent 
		if (elementToInsert.compareTo(currentNode.title) < 0)	{
			currentNode.left = insert(currentNode.left, elementToInsert, available, rental);
		}
		else {
			currentNode.right = insert(currentNode.right, elementToInsert, available, rental);
		}
		return currentNode;
	}
	public void insert(String title, int available, int rental) {
		root = insert(root, title, available, rental);
	}
	
	//Add Method
	private Node<E> add(Node<E> currentNode, String elementToInsert, int toAdd) {	
		//Base case: if element not found, and currentNode.left or currentNode.right hits null, insert it there
		if (currentNode == null) { 	
			Node<E> newNode = new Node<E>(elementToInsert);
			currentNode = newNode; //Members are added in the next if
		}
		//If Element found in the Node, update data
		if (elementToInsert.compareTo(currentNode.title) == 0) {
			currentNode.available += toAdd;	//Add up pre-existing value or new value for new title
			return currentNode;	
		}	
		//Keep moving to find where is the element located
		if (elementToInsert.compareTo(currentNode.title) < 0)	{
			currentNode.left = add(currentNode.left, elementToInsert, toAdd);
		}
		else {
			currentNode.right = add(currentNode.right, elementToInsert, toAdd);
		}
		return currentNode;
	}
	public void add(String title, int toAdd) {
		root = add(root, title, toAdd);
	}
	
	//Return Method
	private Node<E> Return(Node<E> currentNode, String toReturn) {
		//If Element found in the Node, update data
		if (toReturn.compareTo(currentNode.title) == 0) {
			currentNode.available += 1; // Add 1 for return
			if (currentNode.rental > 0 ) {
				currentNode.rental -= 1; //Take out one from rental
			}
			return currentNode;
		}
		// Keep moving to find where is the element located
		if (toReturn.compareTo(currentNode.title) < 0) {
			currentNode.left = Return(currentNode.left, toReturn);
		} else {
			currentNode.right = Return(currentNode.right, toReturn);
		}
		return currentNode;
	}
	public void Return(String title) {
		root = Return(root, title);
	}
	
	//Search Method
	private boolean search(Node<E> currentNode, String elementToSearch)
	{
		//If Tree is empty / Element not found, return false
		if (currentNode == null) {
			return false;
		} 
		//If found the title in node, return true
		if (elementToSearch.compareTo(currentNode.title) == 0) {
			return true;
		} 
		//Keep Searching next one
		if (elementToSearch.compareTo(currentNode.title)  < 0) {
			return search(currentNode.left, elementToSearch);
		}
		else {
			return search(currentNode.right, elementToSearch);
		}
	}
	public boolean search(String elementToSearch) {
		return search(root, elementToSearch);
	}
		
	//Search for Available value of inventory Method (Deleting Node boolean check)
	private boolean searchAvailableValue(Node<E> currentNode, String elementToSearch, int value)
	{
		//If Tree is empty / Element not found, return false
		if (currentNode == null) {
			return false;
		} 
		//If found, return true. currentNode.available == value (because if they have the same value and since they want to remove, it is like zero)
		if (elementToSearch.compareTo(currentNode.title) == 0 && currentNode.available == value) {
			return true;
		} 
		//Keep Searching next one
		if (elementToSearch.compareTo(currentNode.title)  < 0) {
			return searchAvailableValue(currentNode.left, elementToSearch, value);
		}
		else {
			return searchAvailableValue(currentNode.right, elementToSearch, value);
		}
	}
	public boolean searchAvailableValue(String elementToSearch, int value) {
		return searchAvailableValue(root, elementToSearch, value);
	}
	
	//Search for Rental value of inventory Method (Deleting Node boolean check)
	private boolean searchRentalValue(Node<E> currentNode, String elementToSearch)
	{
		//If Tree is empty / Element not found, return false
		if (currentNode == null) {
			return false;
		} 
		//If found title in node, return true. currentNode.rental == 0 to check for delete node
		if (elementToSearch.compareTo(currentNode.title) == 0 && currentNode.rental == 0) {
			return true;
		} 
		//Keep Searching next one
		if (elementToSearch.compareTo(currentNode.title) < 0) {
			return searchRentalValue(currentNode.left, elementToSearch);
		}
		else {
			return searchRentalValue(currentNode.right, elementToSearch);
		}
	}
	public boolean searchRentalValue(String elementToSearch) {
		return searchRentalValue(root, elementToSearch);
	}
	
	//Remove Method
	public Node<E> remove(Node<E> currentNode, String elementToRemove, int toRemove) {
		//If Element found, update member
		if (elementToRemove.compareTo(currentNode.title) == 0) {
			currentNode.available -= toRemove; //Remove value from pre-existing value
			return currentNode;	
		}	
		//Keep moving to find where is the element located
		if (elementToRemove.compareTo(currentNode.title) < 0)	{
			currentNode.left = remove(currentNode.left, elementToRemove, toRemove);
		}
		else {
			currentNode.right = remove(currentNode.right, elementToRemove, toRemove);
		}
		return currentNode;
	}
	public void remove(String title, int toRemove) {
		root = remove(root, title, toRemove);
	}
	
	//Delete Method
	public boolean delete(String elementToDelete) {
        if (this.root == null) return false;  //tree is empty

        //Locate the element to delete 
        Node<E> parent = null;
        Node<E> currNode = this.root;
        while (currNode != null && elementToDelete.compareTo(currNode.title) != 0) {
            parent = currNode;
            if (elementToDelete.compareTo(currNode.title) < 0) {
                currNode = currNode.left;    //go left
            } else if (elementToDelete.compareTo(currNode.title) > 0) {
                currNode = currNode.right;    //go right
            } 
        }//endWhile
        if (currNode == null) { //If element is not found return false; 
          return false;
        }
        //Delete the found node (at currNode)
        //Case-1:   //currNode has no left-subtree
        if (currNode.left == null) {
            //connect right child of currNode to the parent of currNode 
            if(parent == null) {  //element to delete is the root
                root = currNode.right;               
            } else {
                if (elementToDelete.compareTo(parent.title) < 0) {              
                    parent.left = currNode.right;
                } else {
                    parent.right = currNode.right;
                }
            }
        } else {
            //Case-2:   //currNode has a left-subtree
            //Locate the rightmost node in the left subtree of currNode and its parent
            Node<E> parentOfRightMost  = currNode;
            Node<E> rightMost = currNode.left;
            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }
            //replace the element in the currNode by the element at rightMost
            currNode.title = rightMost.title;
            currNode.available = rightMost.available;
            currNode.rental = rightMost.rental;
            //eliminate the rightmost node
            //rightMost.left  needs to be attached.
            if (parentOfRightMost.right == rightMost) {
                parentOfRightMost.right = rightMost.left;
            } else {
                parentOfRightMost.left = rightMost.left; 
            }
        }
        return true;
    }
		
	//Rent Method
	public Node<E> rent(Node<E> currentNode, String elementToRemove) {
		//If element found in the node, update member
		if (elementToRemove.compareTo(currentNode.title) == 0) {
			if (currentNode.available > 0) {
				currentNode.available -= 1;
				currentNode.rental += 1; 
			}	
			return currentNode;	
		}	
		//Keep moving to find the right parent
		if (elementToRemove.compareTo(currentNode.title) < 0)	{
			currentNode.left = rent(currentNode.left, elementToRemove);
		}
		else {
			currentNode.right = rent(currentNode.right, elementToRemove);
		}
		return currentNode;
	}
	public void rent(String title) {
		root = rent(root, title);
	}
	
	//Write to File
	private void writeFile(Node<E> currentRoot, PrintWriter write) throws IOException {
		boolean flagCheck = false;  
		if (currentRoot == null) { //Base Case for Node
			return;
		}
		if (write == null) { //Base Case for write
			flagCheck = true;
		}
		writeFile(currentRoot.left, write);
		write.printf("%-29s%4d%9d", currentRoot.title, currentRoot.available, currentRoot.rental);
		write.println();
		writeFile(currentRoot.right, write);
		
		if (flagCheck) { //Close writer 
			write.close();
		}
	}
	public void writeFile(PrintWriter write) throws IOException {
		writeFile(root, write);
	}
	
	//Print In order
	private void inOrder (Node<E> currentRoot)  { //Start from the root as parameter
		if (currentRoot == null) { //Base Case
			return;
		}	
		//Recursively trace the elements
		inOrder(currentRoot.left);
		System.out.println(currentRoot.title + " " + currentRoot.available + " " + currentRoot.rental);
		inOrder(currentRoot.right);	
	}

	//Traverse Elements in Order
	public void traverseInOrder(){
		inOrder(root);
	}
	
	
}
