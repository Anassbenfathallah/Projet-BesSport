package siteParis;
import java.util.LinkedList;

public class Pari {
   private long id;
   private long jetonsMises;
   private Joueur joueur;
   private Competition competition;
   private Competiteur competiteur=null;
   
   //constructeur
   public Pari( long jetonsMises, Joueur joueur, Competition competition, Competiteur competiteur){
      this.jetonsMises=jetonsMises;
      this.joueur=joueur;
      this.competition=competition;
      this.competiteur=competiteur;
      }
   
   //setters
   public void setJetonsMises(long jetonsMises){
      this.jetonsMises=jetonsMises;}
   
   public void setCompetition(Competition competition){
      this.competition=competition;}
   
   public void setJoueur(Joueur joueur){
      this.joueur=joueur;}
   
   public void setCompetiteur(Competiteur competiteur){
      this.competiteur=competiteur;}
   
   //getters
   public long getJetonsMises(){ 
      return this.jetonsMises;}
   
   public Joueur getJoueur() {
      return this.joueur;}
  
   public Competiteur getCompetiteur(){
      return this.competiteur;}
  
   public Competition getCompetition(){
      return this.competition;}
}
   
   
   

