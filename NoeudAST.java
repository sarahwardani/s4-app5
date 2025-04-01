package app6;

/** @author Ahmed Khoumsi */

/** Classe representant un noeud d'AST
 */
public class NoeudAST extends ElemAST {

  Terminal op;
  ElemAST filsG;
  ElemAST filsD;

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(Terminal operateur, ElemAST noeud1, ElemAST noeud2) {
    op = operateur;
    filsG = noeud1;
    filsD = noeud2;
  }


  /** Evaluation de noeud d'AST
   */
  public int EvalAST( ) throws Exception {
    int operande1 = filsG.EvalAST();
    int operande2 = filsD.EvalAST();
    int resultat = 0;
    switch (op.chaine) {
      case "+":
        resultat = operande1 + operande2;
        break;
      case "-":
        resultat = operande1 - operande2;
        break;
      case "*":
        resultat = operande1 * operande2;
        break;
      case "/":
        resultat = operande1 / operande2;
        break;
    }
     return resultat;
  }


  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
     return "(" + filsG.LectAST() + " " + op.chaine + " " + filsD.LectAST() + ")";
  }

  /** Lecture de noeud d'AST
   */
  public String PostfixAST( ) {
     return filsG.PostfixAST() + " " + filsD.PostfixAST() + " "+ op.chaine;
  }

}


