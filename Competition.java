package siteParis;
import java.util.LinkedList;

public class Competition {
   private String competition;
   long sommeJetons;
   public DateFrancaise dateCloture;
   private LinkedList<Pari> listDesParis;
   private LinkedList<Competiteur> competiteur;
   private String[] competiteurs;
   
   //constructeurs
   public Competition(String competition, DateFrancaise dateCloture, String[] competiteurs){
      this.competition=competition;
      this.dateCloture=dateCloture;
      this.sommeJetons=0;
      this.competiteurs=competiteurs; 
      }
   
   public Competition(String competition, DateFrancaise dateCloture, LinkedList competiteur){
      this.competition=competition;
      this.dateCloture=dateCloture;
      this.sommeJetons=0;
      this.competiteur=competiteur;
      this.listDesParis=new LinkedList<Pari>(); 
      }
   
   //getters	 
	public String getCompetition()  {  
	    return this.competition;}
	 
	public DateFrancaise getDateCloture()  {  
	    return dateCloture.getDate();}
	  
	public String[] getCompetiteurs()  {  
	    return this.competiteurs;}
       
   public long getSommeJetons(){
      return this.sommeJetons;} 
      
   public LinkedList<Competiteur> getCompetiteur(){
      return this.competiteur;}
      
   public LinkedList<Pari> getListParis(){
         return this.listDesParis;}   
	  
   //setters  
	public void setCompetition(String competition){
	    this.competition= competition;}
       
	public void setCompetiteurs(String[] competiteurs){
	    this.competiteurs= competiteurs;}
         
   public void setSommeJetons(long jetonsMises){
      this.sommeJetons=sommeJetons+jetonsMises;}
     
   // ajouter le paris sur la meme competition                       
   public void ajouterPari(Pari pari) { 
         listDesParis.add(pari);}
   
}    

