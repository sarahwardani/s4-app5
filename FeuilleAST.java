package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

private Terminal t;


/**Constructeur pour l'initialisation d'attribut(s)
 */
  public FeuilleAST(Terminal terminal) {  // avec arguments
    t = terminal;
  }


  /** Evaluation de feuille d'AST
   */
  public int EvalAST( ) {
      if (t.type == Etype.nb) return Integer.parseInt(t.chaine);
    return 0;
  }


 /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
    return t.chaine;
  }

}
