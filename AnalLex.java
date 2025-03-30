package app6;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

public String chaine;
public int readptr;
public int chaineLongueur;

	
/** Constructeur pour l'initialisation d'attribut(s)
 */
  public AnalLex(String string) {  // arguments possibles
    chaine = string;
    chaineLongueur = chaine.length();
    readptr = 0;
  }


/** resteTerminal() retourne :
      false  si tous les terminaux de l'expression arithmetique ont ete retournes
      true s'il reste encore au moins un terminal qui n'a pas ete retourne 
 */
  public boolean resteTerminal( ) {
    if (readptr == chaineLongueur-1 && chaine.charAt(readptr) == '_') return false;
    return readptr < chaine.length();
  }

  private boolean isDigit(char c ) {
    return c >= '0' && c <= '9';
  }

  private boolean isOperator(char c ) {
    return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
  }

  private Etype typeOp(char c){
    Etype type;
      type = switch (c) {
          case '+' -> Etype.op_add;
          case '-' -> Etype.op_sub;
          case '*' -> Etype.op_mult;
          case '/' -> Etype.op_div;
          case '(' -> Etype.op_par_ouvrante;
          case ')' -> Etype.op_par_fermante;
          default -> Etype.autre;
      };
      return type;
  }
  private boolean isLetterMaj(char c ) {
    return (c >= 'A' && c <= 'Z');
  }

  private boolean isLetter(char c ) {
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
  }

  public char readChar(){
    int temp = readptr;
    readptr++;
    return chaine.charAt(temp);
  }

  
/** prochainTerminal() retourne le prochain terminal
      Cette methode est une implementation d'un AEF
 */  
  public Terminal prochainTerminal( ) throws Exception {
     int etat = 0;
     Terminal currTerminal = new Terminal();
     char c;
     boolean continu = true;

     while(continu && resteTerminal()){
       switch (etat) {
         case 0:
           c = readChar();

           if (isOperator(c)) {
             currTerminal.chaine +=c;
             currTerminal.type = typeOp(c);
             continu = false;
           }
           else if (isLetterMaj(c)) {
             currTerminal.chaine +=c;
             currTerminal.type = Etype.id;
             etat = 1;
           }
           else if (isDigit(c)) {
             currTerminal.chaine +=c;
             currTerminal.type = Etype.nb;
             etat = 3;
           }
           else {
             ErreurLex("Sequence before error: " + currTerminal.chaine + "\nCause: needed a letter, number, operator or identifer, received " + c);
           }
           break;

         case 1: // verifier si operande valide
           c = readChar();

           if (isLetter(c)) {
             //etat = 1;
             currTerminal.chaine +=c;
           }
           else if (c == '_') {
             etat = 2;
             currTerminal.chaine +=c;
           }
           else { // return the operand
             readptr--;
             continu = false;
           }
           break;

         case 2: // current char is _, verify if another _ is after
           c = readChar();

           if (c == '_') {
             ErreurLex("Sequence before error: " + currTerminal.chaine + "\n Cause: needed a letter, received a _");
           }
           else if (isLetter(c)) { // on continue de verifier si operande valide
             etat = 1;
             currTerminal.chaine +=c;
           }
           else { // return valid operand
             readptr--;
             continu = false;
           }
           break;

         case 3: // verify when the number ends and return it
           c = readChar();

           if (isDigit(c)) {
             //etat = 3;
             currTerminal.chaine +=c;
           }
           else {
             readptr--;
             continu = false;
           }
           break;
       }
     }
     return currTerminal;
  }

 
/** ErreurLex() envoie un message d'erreur lexicale
 */ 
  public void ErreurLex(String s) throws Exception {
    throw new Exception("Erreur lexicale:\n" + s);
  }

  
  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) {
    String toWrite = "";
    System.out.println("Debut d'analyse lexicale");
    if (args.length == 0){
    args = new String [2];
            args[0] = "ExpArith.txt";
            args[1] = "ResultatLexical.txt";
    }
    Reader r = new Reader(args[0]);

    AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

    // Execution de l'analyseur lexical
    Terminal t = null;
    Boolean error = false;
    while(lexical.resteTerminal() && !error){
      try{
        t = lexical.prochainTerminal();
        toWrite +=t.chaine + " type: " + t.type + "\n" ;  // toWrite contient le resultat
      }
      catch(Exception e){
        System.out.println(e.getMessage());
        error = true;
      }
    }				   //    d'analyse lexicale
    System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    Writer w = new Writer(args[1],toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Fin d'analyse lexicale");
  }
}
