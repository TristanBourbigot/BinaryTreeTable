package ihm;

import javax.swing.*;
import datastruct.BinaryTreeTable;
import java.awt.*;

public class TreeDraw<E extends Comparable<E>, T> extends JFrame {
	
	private TreeDrawing<E, T> td;

	/**
	 * Constructeur qui instancie un object graphique Canvas dans lequel sera affich� tout
	 * le contenu de l'arbre pass� en param�tre
	 *  
	 * @param node Le noeud de l'arbre � partir duquel se fait l'affichage (i.e. root si l'on
	 * veut afficher l'arbre dans son enti�ret�)
	 */
	public TreeDraw (BinaryTreeTable<T,E>.Node node) {
		
		this.td = new TreeDrawing<> ( node );
	}
	
	/**
	 * Ajout de l'objet Canvas dans la fen�tre graphique
	 */
	public void drawTree () {
		
		Container pane;
		setTitle ("Display Trees");
		setSize (800, 400);
		pane = getContentPane ();
		pane.setLayout(new GridLayout(1,1));

		JPanel treedrawing = new JPanel();
		treedrawing.setLayout(new GridLayout(1,1));
		treedrawing.add(this.td);                
		pane.add(treedrawing);
		setVisible(true);
		
		// Fermeture d�finitive de l'application lorsque la fen�tre se ferme
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
	}
}
