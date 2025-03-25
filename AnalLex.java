package app6;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

// Attributs
//  ...
public String chaine;
public int readptr = 0;

	
/** Constructeur pour l'initialisation d'attribut(s)
 */
  public AnalLex(String string) {  // arguments possibles
    chaine = string;
  }


/** resteTerminal() retourne :
      false  si tous les terminaux de l'expression arithmetique ont ete retournes
      true s'il reste encore au moins un terminal qui n'a pas ete retourne 
 */
  public boolean resteTerminal( ) {
    if (readptr == chaine.length() -1 && chaine.charAt(readptr) == '_') return false;
    return readptr < chaine.length();
  }

  public boolean isDigit(char c ) {
    return c >= '0' && c <= '9';
  }

  public boolean isOperator(char c ) {
    return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
  }

  public boolean isLetterMaj(char c ) {
    return (c >= 'A' && c <= 'Z');
  }

  public boolean isLetter(char c ) {
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
  public Terminal prochainTerminal( ) {
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
             continu = false;
           }
           else if (isLetterMaj(c)) {
             currTerminal.chaine +=c;
             etat = 1;
           }
           else if (isDigit(c)) {
             currTerminal.chaine +=c;
             etat = 3;
           }
           else {
             ErreurLex("erreur : ni operande, ni chiffre, ni id");
             continu = false;
           }
           break;

         case 1: // verifier si operande valide
           c = readChar();

           if (isLetter(c)) {
             etat = 1;
             currTerminal.chaine +=c;
           }
           else if (c == '_') {
             etat = 2;
             currTerminal.chaine +=c;
           }
           else { // on retourne l'operande
             readptr--;
             continu = false;
           }
           break;

         case 2: // rendu au _ dans l'operande, verifie si __
           c = readChar();

           if (c == '_') { // erreur car 2 _ de suite
             ErreurLex("erreur : identifiant avec __");
          //   currTerminal.chaine.;
             continu = false;
           }
           else if (isLetter(c)) { // on continue de verifier si operande valide
             etat = 1;
             currTerminal.chaine +=c;
           }
           else { // on retourne l'operande valide
             readptr--;
             continu = false;
           }
           break;

         case 3: // verifier si chiffre valide
           c = readChar();

           if (isDigit(c)) {
             etat = 3;
             currTerminal.chaine +=c;
           }
           else { // on retourne le chiffre
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
  public void ErreurLex(String s) {
    System.out.println(s) ;
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
    while(lexical.resteTerminal()){
      t = lexical.prochainTerminal();
      toWrite +=t.chaine + "\n" ;  // toWrite contient le resultat
    }				   //    d'analyse lexicale
    System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    Writer w = new Writer(args[1],toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Fin d'analyse lexicale");
  }
}
