package app6;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

    public String chaine;
    Reader r;
    AnalLex lexical;
    Terminal currTerminal;

/** Constructeur de DescenteRecursive :
      - recoit en argument le nom du fichier contenant l'expression a analyser
      - pour l'initalisation d'attribut(s)
 */
public DescenteRecursive(String in) {
    r = new Reader(in);
    lexical = new AnalLex(r.toString());
}


/** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
 *    Elle retourne une reference sur la racine de l'AST construit
 */
public ElemAST AnalSynt( ) throws Exception {
    ElemAST racineAST;
    try{
        readTerminal();
    } catch (Exception e) {
     //   System.out.println(e.getMessage());
        throw new Exception(e);
    }

    try{
        racineAST = E();
    } catch (Exception e) {
       // System.out.println(e.getMessage());
        throw new Exception(e);
    }
    return racineAST;
}

private void readTerminal() throws Exception {

    try{
        if (lexical.resteTerminal()) currTerminal = lexical.prochainTerminal();
    }
    catch(Exception e){
      //  System.out.println(e.getMessage());
        throw new Exception(e);
    }
}

private ElemAST E() throws Exception {
    ElemAST noeud1 = T();
    if (currTerminal.type == Etype.op){
        Terminal operateur = currTerminal;
        readTerminal();
        ElemAST noeud2 = E();
        noeud1 = new NoeudAST(operateur, noeud1, noeud2);
    }
    return noeud1;
}

private ElemAST T() throws Exception {
    ElemAST noeud1;
    if (currTerminal.type != Etype.nb && currTerminal.type != Etype.id) {
        ErreurSynt("erreur syntaxe");
    }
    noeud1 = new FeuilleAST(currTerminal);
    readTerminal();
    return noeud1;
}


    /** ErreurSynt() envoie un message d'erreur syntaxique
 */
public void ErreurSynt(String s) throws Exception
{
    throw new Exception(s);
}




  //Methode principale a lancer pour tester l'analyseur syntaxique
  public static void main(String[] args) {
    String toWriteLect = "";
    String toWriteEval = "";

    System.out.println("Debut d'analyse syntaxique");
    if (args.length == 0){
      args = new String [2];
      args[0] = "ExpArith.txt";
      args[1] = "ResultatSyntaxique.txt";
    }
    DescenteRecursive dr = new DescenteRecursive(args[0]);
    try {
      ElemAST RacineAST = dr.AnalSynt();
      toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
      System.out.println(toWriteLect);
      toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
      System.out.println(toWriteEval);
      Writer w = new Writer(args[1],toWriteLect+toWriteEval); // Ecriture de toWrite
                                                              // dans fichier args[1]
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
      System.exit(51);
    }
    System.out.println("Analyse syntaxique terminee");
  }



}

