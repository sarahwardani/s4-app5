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
  public int EvalAST( ) throws Exception{
      if (t.type == Etype.nb) return Integer.parseInt(t.chaine);
      else throw new Exception("Can't evaluate a type" + t.type);
  }


 /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
    return t.chaine;
  }

  /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String PostfixAST( ) {
    return t.chaine;
  }

}
