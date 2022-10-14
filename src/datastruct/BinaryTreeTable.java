
package datastruct;

import ihm.*;

/**
 * This class simulates a binary tree table.
 * @author BOURBIGOT Tristan
 */
public class BinaryTreeTable<T,E extends Comparable<E>> implements Table<T, E> {
	
	/**
	 * the root of the tree
	 */
	private Node root;
	
	/**
	 * constructor of the BinaryTreeTable
	 */
	public BinaryTreeTable() {
		this.root =null;
	}
	
	/**
	 * select the value of the key in the tree
	 * @param key the key to search
	 * @return the value of the key
	 */
	@Override
	public T select(E key) {
		if(key == null) throw new IllegalArgumentException("Error - BinaryTreeTable select - key is null");
		
		Node node = this.findNode(root,key);
		System.out.println(node);
		T ret = null;
		if(node != null) ret = node.theValue;
		return ret;
	}

	/**
	 * insert a new value in the tree with the key in the good place, and after that, the tree is balanced
	 * @param key the key of the value
	 * @param data the value to insert
	 * @return true if the value is inserted, false if the key is already in the tree
	 */
	@Override
	public boolean insert(E key, T data) {
		boolean result = false;
		if(key == null || data == null){
			result = false;
		}else if (root == null) {
			root = new Node(key, data,null);
			result = true;
		}else{
			Node father  = this.seekFather(key);
			if(father == null) result = false;
			else if (father.key.compareTo(key) > 0 && father.lSon == null) {
				father.lSon = new Node(key, data,father);
				result = true;
			}else{
				father.rSon = new Node(key, data,father);
				result = true;
			}
			Node f = father;
			while(f != null){
				this.balanceTheTree(f);
				f = f.father;
			}
		}
		
		return result;
	}

	/**
	 * delete a node from the tree by key
	 * @param key the key of the node to delete
	 * @return true if the node was deleted
	 */
	@Override
	public boolean delete(E key) {
		boolean res = false;
		Node node = this.findNode(root,key);
		if(node!=null && this.select(key)!=null){
			this.delete(node);
			if(this.select(key)==null){
				res = true;
			}
		}
		return res;
	}

	/**
	 * delete a node from the tree by node
	 * @param theNode the node to delete
	 * @return true if the node was deleted
	 */
	private void delete(Node theNode){
		if(root==theNode){
			if(theNode.lSon==null && theNode.rSon==null){
				root = null;
			}else if(theNode.lSon==null){
				root = theNode.rSon;
				root.father = null;
			}else if(theNode.rSon==null){
				root = theNode.lSon;
				root.father = null;
			}else{
				Node node = theNode.rSon;
				while(node.lSon!=null){
					node = node.lSon;
				}
				if(node.father!=theNode){
					node.father.lSon = node.rSon;
					if(node.rSon!=null){
						node.rSon.father = node.father;
					}
					node.rSon = theNode.rSon;
					node.rSon.father = node;
				}
				node.lSon = theNode.lSon;
				node.lSon.father = node;
				root = node;
				root.father = null;
			}
		}
		else if(theNode.lSon == null && theNode.rSon == null){
			if(theNode.father.lSon == theNode){
				theNode.father.lSon = null;
				theNode.father = null;
			}else if(theNode.father.rSon == theNode){
				theNode.father.rSon = null;
				theNode.father = null;
			}
		}else if(theNode.lSon == null && theNode.rSon != null){
			if(theNode.father.lSon == theNode){
				theNode.father.lSon = theNode.rSon;
				theNode.rSon.father = theNode.father;
				theNode.father = null;
				theNode.rSon = null;
			}else if(theNode.father.rSon == theNode){
				theNode.father.rSon = theNode.rSon;
				theNode.rSon.father = theNode.father;
				theNode.father = null;
				theNode.rSon = null;
			}
		}else if(theNode.lSon != null && theNode.rSon == null){
			if(theNode.father.lSon == theNode){
				theNode.father.lSon = theNode.lSon;
				theNode.lSon.father = theNode.father;
				theNode.father = null;
			}else if(theNode.father.rSon == theNode){
				theNode.father.rSon = theNode.lSon;
				theNode.lSon.father = theNode.father;
				theNode.father = null;
			}
		}else if(theNode.lSon != null && theNode.rSon != null){
			Node rs = theNode.lSon;
			while(rs.rSon!=null){
				rs = rs.rSon;
			}
			theNode.key = rs.key;
			theNode.theValue = rs.theValue;
			this.delete(rs);
		}
		
		if(root!=null && theNode.father!=null){
			Node f = theNode.father;
			while(f!=null){
				this.balanceTheTree(f);
				f = f.father;
			}
		}
	}

	
	/**
	 * shearch the node in the tree by key
	 * @param theNode the node to search
	 * @param key the key to compare
	 * @return the node if it's found, null if not
	 */
	private Node findNode ( Node theNode, E key ){
		Node res;
		if(theNode == null) res= null;
		else if(theNode.key.compareTo(key)==0) res = theNode;
		else if(theNode.key.compareTo(key) > 0) res = findNode(theNode.lSon, key);
		else res = findNode(theNode.rSon, key);
		return res;
	}
	
	/**
	 * shearch an good father for the key
	 * @param key the key to compare
	 * @return the father if it's found, null if not
	 */
	private Node seekFather ( E key ) {
		Node theNode = root;
		Node father = null;
		while ( theNode != null ) {
			father = theNode;
			if ( theNode.key.compareTo(key) > 0 ) theNode = theNode.lSon;
			else if(theNode.key.compareTo(key) == 0) {
				father = null;
				theNode = null;
			}
			else theNode = theNode.rSon;
		}
		return father;
	}
	
	/**
	 * an string with the key and data of the tree ordered by key
	 */
	public String toString(){
		String ret = this.getInfo(this.root) ;
		return ret;
	}

	/**
	 * an string with the key and data of the tree ordered by key
	 * @param theN the node to add to the string
	 * @return the string with the key and data of the tree ordered by key
	 */
	private String getInfo ( Node theN ) {
		String infosLNode, infosRNode, infosNode ;
		String ret ;

		if ( theN != null ) {
			infosLNode = getInfo ( theN.lSon ) ;
			infosRNode = getInfo ( theN.rSon ) ;
			infosNode = new String ( "\ncle=" + theN.key.toString() + "\tdata=" +
			theN.theValue.toString() ) ;
			ret = new String ( infosLNode + infosNode + infosRNode ) ;
		}else ret = new String ("") ;

		return ret ;
	}

	/**
	 * the height of the node
	 * @param theN
	 * @return
	 */
	private int computeH(Node theN){
		int ret = 0;
		if(theN != null){
			int hL = computeH(theN.lSon);
			int hR = computeH(theN.rSon);
			ret = 1 + Math.max(hL, hR);
		}
		return ret;
	}

	
	/**
	 * make right rotation of the node
	 * @param theN
	 */
	private void rightRotation(Node theN){
		Node father = theN.father;
		Node lSon = theN.lSon;
		Node lSonR = lSon.rSon;
		if(father != null){
			if(father.lSon == theN) father.lSon = lSon;
			else father.rSon = lSon;
		}else{
			root = lSon;
		}
		lSon.father = father;
		lSon.rSon = theN;
		theN.father = lSon;
		theN.lSon = lSonR;
		if(lSonR != null) lSonR.father = theN;		
	}

	/**
	 * make left rotation of the node
	 * @param theN the node to rotate
	 */
	private void leftRotation(Node theN){
		Node father = theN.father;
		Node rSon = theN.rSon;
		Node rSonL = rSon.lSon;
		if(father != null){
			if(father.lSon == theN) father.lSon = rSon;
			else father.rSon = rSon;
		}else{
			root = rSon;
		}
		rSon.father = father;
		rSon.lSon = theN;
		theN.father = rSon;
		theN.rSon = rSonL;
		if(rSonL != null) rSonL.father = theN;		
	}


	/**
	 * make a left rotation of the node and then a right rotation
	 * @param theN the node to rotate
	 */
	private void leftRightRotation (Node theN){
		this.leftRotation(theN.lSon);
		this.rightRotation(theN);
	}

	/**
	 * make a right rotation of the node and then a left rotation
	 * @param theN the node to rotate
	 */
	private void rightLeftRotation (Node theN){
		this.rightRotation(theN.rSon);
		this.leftRotation(theN);
	}

	/**
	 * this method balance the node with the rotation if the node is unbalanced
	 * @param theN the node to balance
	 */
	private void balanceTheTree(Node theN){
		int hL = computeH(theN.lSon);
		int hR = computeH(theN.rSon);
		if(hL - hR > 1){
			int hLL = computeH(theN.lSon.lSon);
			int hLR = computeH(theN.lSon.rSon);
			if(hLL >= hLR){
				this.rightRotation(theN);
			}else{
				this.leftRightRotation(theN);
			}
		}else if(hR - hL > 1){
			int hRR = computeH(theN.rSon.rSon);
			int hRL = computeH(theN.rSon.lSon);
			if(hRR >= hRL){
				this.leftRotation(theN);
			}else{
				this.rightLeftRotation(theN);
			}
		}
	}


	/**
	 * clone the tree
	 */
	public BinaryTreeTable<T, E> clone(){
		BinaryTreeTable<T, E> ret = new BinaryTreeTable<T, E>();
		ret.computeClone(root, null);
		return ret;
	}

	/**
	 * clone the tree with recursion method
	 * @param nodeToCopy the node to copy
	 * @param newFather the father of the node to copy
	 */
	private void computeClone(Node nodeToCopy, Node newFather){
		if(nodeToCopy != null){
			Node newNode = new Node(nodeToCopy.key, nodeToCopy.theValue, newFather);
			if(newFather == null) root = newNode;
			else if(newFather.key.compareTo(newNode.key) > 0) newFather.lSon = newNode;
			else newFather.rSon = newNode;
			computeClone(nodeToCopy.lSon, newNode);
			computeClone(nodeToCopy.rSon, newNode);
		}
	}

	/**
	 * make an display of the tree with the package ihm
	 */
	public void showTree(){
		TreeDraw draw = new TreeDraw(root);
		draw.drawTree();
	}

	/**
	 * the node of the tree
	 * @author BOURBIGOT Tristan
	 */
	public class Node {
		
		// Attributs
		/**
		 * the left son of the node
		 */
		private Node lSon ;

		/**
		 * the right son of the node
		 */
		private Node rSon ;

		/**
		 * the father of the node
		 */
		private Node father ;
		/**
		 * the data of the node
		 */
		private T theValue ;
		/**
		 * the key of the node
		 */
		private E key ;
		
		
		/**
		 * constructor of the node
		 * @param k the key of the node
		 * @param value the value of the node
		 * @param f the father of the node
		 */
		public Node (E k, T value, Node f) {
			if(k== null || value == null) throw new IllegalArgumentException("Error - Node  construct - parameters null");
			else if(f==null){
				this.father = f;
				this.lSon = null;
				this.rSon = null;
				this.key = k;
				this.theValue = value;	
				root = this;
			}else{
				this.father = f;
				this.lSon = null;
				this.rSon = null;
				this.key = k;
				this.theValue = value;
			}
		}
		
		/**
		 * return the key of the node
		 * @return the key of the node
		 */
		public String getLabel() {
			return this.key.toString();
		}
		
		/**
		 * return the left son of the node
		 * @return the left son of the node
		 */
		public Node getLeft(){
			return this.lSon;
		}
		
		/**
		 * return the right son of the node
		 * @return
		 */
		public Node getRight() {
			return this.rSon;
		}
		
		public Node getFather(){
			return this.father;
		}
		
		/**
		 * clone the node
		 * @return the clone of the node
		 */
		public Node clone() {
			Node res = new Node(this.key, this.theValue, this.father);
			return res;
		} 
	}

}
