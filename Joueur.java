package siteParis;
import java.util.LinkedList;

public class Joueur {

	private String nom;
	private String prenom;
	private String pseudo;
   private long solde=0;
   private String password="";
   private LinkedList<Pari> paris;
	
   //constructeur
	public Joueur(String nom , String prenom, String pseudo) {
		this.nom=nom;
		this.prenom=prenom;
		this.pseudo=pseudo;
      this.paris=new LinkedList<Pari>();
	}
	
	//méthode equals
   public boolean equals(Object obj) {
	      return (obj instanceof Joueur) && 
		  ((Joueur)obj).getNom().equals(nom) && 
		  ((Joueur)obj).getPrenom().equals(prenom) &&
		  ((Joueur)obj).getPseudo()==pseudo ;
	    }
	 
   //getters
	  public String getNom()  {  
	    return this.nom;}
	  
	  public String getPrenom()  {  
	    return this.prenom;}
	  
	  public String getPseudo()  {  
	    return this.pseudo;}
	 
     public long getSolde(){
      return this.solde;}
     
     public LinkedList<Pari> getParis(){
       return this.paris;}    
   

   //setters
	  public void setNom(String nom){
	    this.nom = nom;}

	  public void setPrenom(String prenom){
	    this.prenom = prenom;}
       
     public String getPassword(){
       return this.password;}

     public void setPseudo(String pseudo){
	    this.pseudo= pseudo;}  
	  
     public void setSolde(long solde){
       this.solde=solde;}
      
     public void setPassword(String password){
       this.password=password;}
     
     //ajouter un pari d'un joueur
     public void ajouterPari(Pari pari) { 
       paris.add(pari);}
     
                	  
}

