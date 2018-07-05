package siteParis;
import java.util.LinkedList;

public class Competiteur {
   private String nom;
   private long sommeJetons;
	
   //constructeur
   public Competiteur(String nom){
      this.nom=nom;
      this.sommeJetons=0;}

   //getters
	public String getNom() {
		return nom;}
   
   public long getSommeJetons(){
      return this.sommeJetons;}
   
   //setters
   public void setNom(String nom){
	   this.nom=nom;}
	
	public void setSommeJetons(long jetonsMises){
      this.sommeJetons=sommeJetons+jetonsMises;}
	
}
