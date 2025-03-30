package app6;

/** @author Ahmed Khoumsi */

/** Classe Abstraite dont heriteront les classes FeuilleAST et NoeudAST
 */
public abstract class ElemAST {

  
  /** Evaluation d'AST
   */
  public abstract int EvalAST() throws Exception;


  /** Lecture d'AST
   */
  public abstract String LectAST();


  public abstract String PostfixAST();
}
