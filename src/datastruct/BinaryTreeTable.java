
package datastruct;

import ihm.*;


public class BinaryTreeTable<T,E extends Comparable<E>> implements Table<T, E> {
	
	private Node root;
	
	public BinaryTreeTable() {
		this.root =null;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public T select(E key) {
		if(key == null) throw new IllegalArgumentException("Error - BinaryTreeTable select - key is null");
		
		Node node = this.findNode(root,key);
		T ret = null;
		if(node != null) ret = node.theValue;
		return ret;
	}

	/**
	 * 
	 * @param key
	 * @param data
	 * @return
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
		if(node!=null){
			this.delete(node);
			if(this.select(key)==null){
				res = true;
				if(root!=null && root.lSon!=null && root.rSon!=null) this.balanceTheTree(root);
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
		if(theNode.lSon == null && theNode.rSon == null){
			if(theNode.father.lSon == theNode){
				theNode.father.lSon = null;
			}else if(theNode.father.rSon == theNode){
				theNode.father.rSon = null;
			}
		}else if(theNode.lSon == null && theNode.rSon != null){
			if(theNode.father.lSon == theNode){
				theNode.father.lSon = theNode.rSon;
			}else if(theNode.father.rSon == theNode){
				theNode.father.rSon = theNode.rSon;
			}
		}else if(theNode.lSon != null && theNode.rSon == null){
			if(theNode.father.lSon == theNode){
				theNode.father.lSon = theNode.lSon;
			}else if(theNode.father.rSon == theNode){
				theNode.father.rSon = theNode.lSon;
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
	}

	
	/**
	 * 
	 * @param theNode
	 * @param key
	 * @return
	 */
	private Node findNode ( Node theNode, E key ){
		Node res;
		if(theNode == null) res= null;
		else if(theNode.key == key) res = theNode;
		else if(theNode.key.compareTo(key) > 0) res = findNode(theNode.lSon, key);
		else res = findNode(theNode.rSon, key);
		return res;
	}
	
	/**
	 * 
	 * @param key
	 * @return
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
	
	public String toString(){
		String ret = this.getInfo(this.root) ;
		return ret;
	}

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
	 * 
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
	 * 
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
	 * 
	 * @param theN
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
	 * 
	 * @param theN
	 */
	private void leftRightRotation (Node theN){
		this.leftRotation(theN.lSon);
		this.rightRotation(theN);
	}

	/**
	 * 
	 * @param theN
	 */
	private void rightLeftRotation (Node theN){
		this.rightRotation(theN.rSon);
		this.leftRotation(theN);
	}

	/**
	 * 
	 * @param theN
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



	public BinaryTreeTable<T, E> clone(){
		BinaryTreeTable<T, E> ret = new BinaryTreeTable<T, E>();
		ret.computeClone(root, null);
		return ret;
	}

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


	public void showTree(){
		TreeDraw draw = new TreeDraw(root);
		draw.drawTree();
	}

	/**
	 * 
	 */
	public class Node {
		
		// Attributs
		private Node lSon ; // fils gauche (null si pas de fils gauche)
		private Node rSon ; // fils droit (null si pas de fils droit)
		private Node father ; // père (null si le nœud est root)
		private T theValue ; // donnée stockée
		private E key ; // clé unique
		
		
		// Constructeur
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
		

		public String getLabel() {
			return this.key.toString();
		}
		
		public Node getLeft(){
			return this.lSon;
		}
		
		// accesseur du fils droit
		public Node getRight() {
			return this.rSon;
		}
		
		
		// Duplication
		// duplication mémoire du nœud courant
		public Node clone() {
			Node res = new Node(this.key, this.theValue, this.father);
			return res;
		} 
	}

}
