package siteParis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

/**
 * 
 * @author Bernard Prou et Julien Mallet
 * <br><br>
 * La classe qui contient toutes les méthodes "Métier" de la gestion du site de paris. 
 * <br><br>
 * Dans toutes les méthodes :
 * <ul>
 * <li>un paramètre de type <code>String</code> est invalide si il n'est pas instancié.</li>
 *  <li>pour la validité d'un password de gestionnaire et d'un password de joueur :
 * <ul>
 * <li>       lettres et chiffres sont les seuls caractères autorisés </li>
 * <li>       il doit avoir une longueur d'au moins 8 caractères </li>
 * </ul></li>       
 * <li>pour la validité d'un pseudo de joueur  :
 * <ul>
 * <li>        lettres et chiffres sont les seuls caractères autorisés  </li>
 * <li>       il doit avoir une longueur d'au moins 4 caractères</li>
 * </ul></li>       
 * <li>pour la validité d'un prénom de joueur et d'un nom de joueur :
 *  <ul>
 *  <li>       lettres et tiret sont les seuls caractères autorisés  </li>
 *  <li>      il  doit avoir une longueur d'au moins 1 caractère </li>
 * </ul></li>
 * <li>pour la validité d'une compétition  :       
 *  <ul>
 *  <li>       lettres, chiffres, point, trait d'union et souligné sont les seuls caractères autorisés </li>
 *  <li>      elle  doit avoir une longueur d'au moins 4 caractères</li>
 * </ul></li>       
 * <li>pour la validité d'un compétiteur  :       
 *  <ul>
 *  <li>       lettres, chiffres, trait d'union et souligné sont les seuls caractères autorisés </li>
 *  <li>      il doit avoir une longueur d'au moins 4 caractères.</li>
 * </ul></li></ul>
 */

public class SiteDeParisMetier {
   private String passwordGestionnaire;
   private LinkedList<Joueur> listJoueur;
   private LinkedList<Competition> listCompetition;

    


	/**
	 * constructeur de <code>SiteDeParisMetier</code>. 
	 * 
	 * @param passwordGestionnaire   le password du gestionnaire.   
	 * 
	 * @throws MetierException  levée 
	 * si le <code>passwordGestionnaire</code>  est invalide 
	 */
	public SiteDeParisMetier(String passwordGestionnaire) throws MetierException {
   
      this.passwordGestionnaire=passwordGestionnaire;
      this.listJoueur= new LinkedList<Joueur>();
      this.listCompetition=new LinkedList<Competition>();
      validitePasswordGestionnaire(passwordGestionnaire);
   
	}





	// Les méthodes du gestionnaire (avec mot de passe gestionnaire)



	/**
	 * inscrire un joueur. 
	 * 
	 * @param nom   le nom du joueur 
	 * @param prenom   le prénom du joueur   
	 * @param pseudo   le pseudo du joueur  
	 * @param passwordGestionnaire  le password du gestionnaire du site  
	 * 
	 * @throws MetierException   levée  
	 * si le <code>passwordGestionnaire</code> proposé est invalide,
	 * si le <code>passwordGestionnaire</code> est incorrect.
	 * @throws JoueurExistantException   levée si un joueur existe avec les mêmes noms et prénoms ou le même pseudo.
	 * @throws JoueurException levée si <code>nom</code>, <code>prenom</code>, <code>pseudo</code> sont invalides.
	 * 
	 * @return le mot de passe (déterminé par le site) du nouveau joueur inscrit.
	 */
	public String inscrireJoueur(String nom, String prenom, String pseudo, String passwordGestionnaire) throws MetierException, JoueurExistantException, JoueurException {
		
      Joueur j = new Joueur(nom,prenom,pseudo); //le joueur qu'on va inscrire
      
      validitePasswordGestionnaire(passwordGestionnaire);// test pass
      
      testValiditeJoueur(nom, prenom, pseudo);// test validité joueur
                
      //tester si le joueur existe
      if (!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();   
      for(int i=0;i<listJoueur.size();i++) {
		   if ((listJoueur.get(i)).getNom().equals(nom) && (listJoueur.get(i)).getPrenom().equals(prenom) && (listJoueur.get(i)).getPseudo().equals(pseudo)) throw new JoueurExistantException();
	}
	   for(int i=0;i<listJoueur.size();i++) {
		   if ((listJoueur.get(i)).getNom().equals(nom) &&  (listJoueur.get(i)).getPrenom().equals(prenom)) throw new JoueurExistantException();
		
	} 	
	   for(int i=0;i<listJoueur.size();i++) {
		
		   if ((listJoueur.get(i)).getPseudo().equals(pseudo)){ throw new  JoueurExistantException();}
			
		}
      listJoueur.add(j); //ajout du joueur
      
	 //générer le mot de passe du joueur
	   String passwordJoueur= randomPass();
      j.setPassword(passwordJoueur);
    
      return passwordJoueur;
	}

	/**
	 * supprimer un joueur. 
	 * 
	 * @param nom   le nom du joueur 
	 * @param prenom   le prénom du joueur   
	 * @param pseudo   le pseudo du joueur  
	 * @param passwordGestionnaire  le password du gestionnaire du site  
	 * 
	 * @throws MetierException
	 * si le <code>passwordGestionnaire</code>  est invalide,
	 * si le <code>passwordGestionnaire</code> est incorrect.
	 * @throws JoueurInexistantException   levée si il n'y a pas de joueur  avec le même <code>nom</code>, <code>prenom</code>  et <code>pseudo</code>.
	 * @throws JoueurException levée 
	 * si le joueur a des paris en cours,
	 * si <code>nom</code>, <code>prenom</code>, <code>pseudo</code> sont invalides.
	 * 
	 * @return le nombre de jetons à rembourser  au joueur qui vient d'être désinscrit.
	 * 
	 */
    ArrayList<String> joueurDesinscrit = new ArrayList<String>(); //liste des joueurs desinscrits 
	
   public long desinscrireJoueur(String nom, String prenom, String pseudo, String passwordGestionnaire) throws MetierException, JoueurInexistantException, JoueurException {
		
      validitePasswordGestionnaire(passwordGestionnaire);
      if (this.passwordGestionnaire.equals(passwordGestionnaire)==false){ throw new MetierException();} // test pass
      
      testValiditeJoueur(nom, prenom, pseudo);// test validité joueur
      
     
      // test joueur inexistant
      int c=0;
      for(int i=0;i<listJoueur.size();i++) {
		if ((listJoueur.get(i)).getNom().equals(nom) && (listJoueur.get(i)).getPrenom().equals(prenom) && (listJoueur.get(i)).getPseudo().equals(pseudo)){c=1;}}
      if(c==0){ throw new JoueurInexistantException();}
	
      
      
      // joueur deja retiré
      for(String joue : joueurDesinscrit){
      if(joue.equals(nom)==true){throw new JoueurInexistantException();}}
   joueurDesinscrit.add(nom);
      
      //on  supprime le joueur et on lui rend son solde 
      Joueur joueur=getJoueur(pseudo);
      long solde=joueur.getSolde();
      LinkedList<Pari> paris =joueur.getParis();
      for(Pari pari : paris){
      if(pari.getCompetition().dateCloture.estDansLePasse()==false){ throw new JoueurException();}
      }
      listJoueur.remove(joueur);
      
      
      
      return solde;
	}



	/**
	 * ajouter une compétition.  
	 * 
	 * @param competition le nom de la compétition
	 * @param dateCloture   la date à partir de laquelle il ne sera plus possible de miser  
	 * @param competiteurs   les noms des différents compétiteurs de la compétition 
	 * @param passwordGestionnaire  le password du gestionnaire du site 
	 * 
	 * @throws MetierException levée si le tableau des
	 * compétiteurs n'est pas instancié, si le
	 * <code>passwordGestionnaire</code> est invalide, si le
	 * <code>passwordGestionnaire</code> est incorrect.
	 * @throws CompetitionExistanteException levée si une compétition existe avec le même nom. 
	 * @throws CompetitionException levée si le nom de la
	 * compétition ou des compétiteurs sont invalides, si il y a
	 * moins de 2 compétiteurs, si un des competiteurs n'est pas instancié,
	 * si deux compétiteurs ont le même nom, si la date de clôture 
	 * n'est pas instanciée ou est dépassée.
	 */
      
	public void ajouterCompetition(String competition, DateFrancaise dateCloture, String [] competiteurs, String passwordGestionnaire) throws MetierException, CompetitionExistanteException, CompetitionException  {
      
      validitePasswordGestionnaire(passwordGestionnaire);
      if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException(); //test pass
      
      //verifier les tests sur compétitions
      if(competition==null) throw new CompetitionException();
      if(dateCloture==null) throw new CompetitionException();
      if(dateCloture.estDansLePasse()==true) throw new CompetitionException();
      for(int i=0 ;i<competition.length();i++){
         if(competition.charAt(i)==' '|| competition.charAt(i)=='|' || competition.length()<4) throw new CompetitionException();
         }
      for(int i=0;i<listCompetition.size();i++) {
   		if ((listCompetition.get(i)).getCompetition().equals(competition)) throw new CompetitionExistanteException();
   	}
      
      //verifier les tests sur compétiteurs
      if(competiteurs==null) throw new MetierException();
      if(competiteurs.length==1) throw new CompetitionException();
      for(int i=0; i<competiteurs.length; i++){
         if(competiteurs[i]==null  || competiteurs[i].length()<4 )throw new CompetitionException();
         for(int j=0; j<competiteurs[i].length(); j++){
           if(competiteurs[i].charAt(j)==' ' || competiteurs[i].charAt(j)=='*') throw new CompetitionException();
         for( int k=i+1; k<competiteurs.length;k++){
            if(competiteurs[i].equals(competiteurs[k])){
            throw new CompetitionException();}
            }       
   }   
   }  
      
      //l'ajout des compétitions
      LinkedList<Competiteur> listeCompetiteurs= new LinkedList<Competiteur>();
      for (String competiteur : competiteurs){listeCompetiteurs.add(new Competiteur(competiteur));}
      Competition competition1= new Competition(competition,dateCloture,listeCompetiteurs);
      listCompetition.add(competition1); 
     
      
   	}


	/**
	 * solder une compétition vainqueur (compétition avec vainqueur).  
	 * 
	 * Chaque joueur ayant misé sur cette compétition
	 * en choisissant ce compétiteur est crédité d'un nombre de
	 * jetons égal à :
	 * 
	 * (le montant de sa mise * la somme des
	 * jetons misés pour cette compétition) / la somme des jetons
	 * misés sur ce compétiteur.
	 *
	 * Si aucun joueur n'a trouvé le
	 * bon compétiteur, des jetons sont crédités aux joueurs ayant
	 * misé sur cette compétition (conformément au montant de
	 * leurs mises). La compétition est "supprimée" si il ne reste
	 * plus de mises suite à ce solde.
	 * 
	 * @param competition   le nom de la compétition  
	 * @param vainqueur   le nom du vainqueur de la compétition 
	 * @param passwordGestionnaire  le password du gestionnaire du site 
	 * 
	 * @throws MetierException   levée 
	 * si le <code>passwordGestionnaire</code>  est invalide,
	 * si le <code>passwordGestionnaire</code> est incorrect.
	 * @throws CompetitionInexistanteException   levée si il n'existe pas de compétition de même nom.
	 * @throws CompetitionException levée 
	 * si le nom de la compétition ou du vainqueur est invalide, 
	 * si il n'existe pas de compétiteur du nom du vainqueur dans la compétition,
	 * si la date de clôture de la compétition est dans le futur.
	 * 
	 */	
   ArrayList<String> competitionSoldee = new ArrayList<String>(); // liste des compétitions déjà soldées
	
   public void solderVainqueur(String competition, String vainqueur, String passwordGestionnaire) throws MetierException, CompetitionInexistanteException, CompetitionException, JoueurException, JoueurInexistantException {
 
      if(passwordGestionnaire==null) throw new MetierException();
      if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException(); // test pass
       
     //Competition non terminée
      if(competition==null) throw new CompetitionException();
      Competition comp=getCompetition(competition);
      if (comp.dateCloture.estDansLePasse()==false) throw new CompetitionException();
      
     //vainqueur inexistan, si le competiteur n'existe pas, la methode getCompetiteur renvoie l'exception
      Competiteur competiteur=getCompetiteur(comp, vainqueur);
     
     //solder competition deja soldée
      for(String comp2 : competitionSoldee){
         if(comp2.equals(competition)==true){throw new CompetitionInexistanteException();}}
      competitionSoldee.add(competition);
      
      //solder la competition
      boolean aucunGagnant=true; // supposons qu'aucun gagnant n'existe 
           for (Pari pari : comp.getListParis()){ //les paris sur la competition
       	  if (competiteur.equals(pari.getCompetiteur())){ //si un pari sur le vainqueur existe, cad un gagnant existe
       		  aucunGagnant=false; // false
       		  Joueur gagnant= pari.getJoueur();// on recupère le joueur(gagnant)
       		  long sommeGagnee=pari.getJetonsMises()*comp.getSommeJetons()/(competiteur.getSommeJetons());
       		  crediterJoueur(gagnant.getNom(),gagnant.getPrenom(),gagnant.getPseudo(),sommeGagnee,passwordGestionnaire) ;
       	  }
         }
      if (aucunGagnant==true) { //aucun gagnant existe 
     	  for (Pari pari :comp.getListParis()){
          Joueur joueur=pari.getJoueur();
       	 crediterJoueur(joueur.getNom(),joueur.getPrenom(),joueur.getPseudo(),pari.getJetonsMises(),passwordGestionnaire); // on rend chaque joueur sa mise
       	  }
         }  
         boolean supprime=listCompetition.remove(comp);// supprime la competition
      
   	}
   



	/**
	 * créditer le compte en jetons d'un joueur du site de paris.
	 * 
	 * @param nom   le nom du joueur 
	 * @param prenom   le prénom du joueur   
	 * @param pseudo   le pseudo du joueur  
	 * @param sommeEnJetons   la somme en jetons à créditer  
	 * @param passwordGestionnaire  le password du gestionnaire du site  
	 * 
	 * @throws MetierException   levée 
	 * si le <code>passwordGestionnaire</code>  est invalide,
	 * si le <code>passwordGestionnaire</code> est incorrect,
	 * si la somme en jetons est négative.
	 * @throws JoueurException levée  
	 * si <code>nom</code>, <code>prenom</code>,  <code>pseudo</code> sont invalides.
	 * @throws JoueurInexistantException   levée si il n'y a pas de joueur  avec les mêmes nom,  prénom et pseudo.
	 */
	public void crediterJoueur(String nom, String prenom, String pseudo, long sommeEnJetons, String passwordGestionnaire) throws MetierException, JoueurException, JoueurInexistantException {
     
      //test validité joueur
      if (sommeEnJetons<0) throw new MetierException();
      if(nom==null) throw new JoueurException();
      if(prenom==null) throw new JoueurException(); 
      if(pseudo==null) throw new JoueurException();
      testValiditeJoueur(nom,prenom,pseudo);
      
      //test password
      if(passwordGestionnaire==null) throw new MetierException();
      if(passwordGestionnaire.length()<8) throw new MetierException();
      if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();
      
      //joueurInexistant
       boolean joueurExists=false;
       for (Joueur joue : listJoueur){
         if(joue.getPseudo().equals(pseudo)){ joueurExists=true; break;}
        }
       if (joueurExists==false){ throw new JoueurInexistantException();}
      
      //traitement  
      Joueur joueur=getJoueur(pseudo);
      joueur.setSolde(joueur.getSolde()+sommeEnJetons);
   	}


	/**
	 * débiter le compte en jetons d'un joueur du site de paris.
	 * 
	 * @param nom   le nom du joueur 
	 * @param prenom   le prénom du joueur   
	 * @param pseudo   le pseudo du joueur  
	 * @param sommeEnJetons   la somme en jetons à débiter  
	 * @param passwordGestionnaire  le password du gestionnaire du site  
	 * 
	 * @throws MetierException   levée 
	 * si le <code>passwordGestionnaire</code>  est invalide,
	 * si le <code>passwordGestionnaire</code> est incorrect,
	 * si la somme en jetons est négative.
	 * @throws JoueurException levée  
	 * si <code>nom</code>, <code>prenom</code>,  <code>pseudo</code> sont invalides 
	 * si le compte en jetons du joueur est insuffisant (il deviendrait négatif).
	 * @throws JoueurInexistantException   levée si il n'y a pas de joueur  avec les mêmes nom,  prénom et pseudo.
	 * 
	 */

	public void debiterJoueur(String nom, String prenom, String pseudo, long sommeEnJetons, String passwordGestionnaire) throws  MetierException, JoueurInexistantException, JoueurException {
   
      //test validité joueur
      if (sommeEnJetons<0) throw new MetierException();
      if(nom==null) throw new JoueurException();
      if(prenom==null) throw new JoueurException();
      if(pseudo==null) throw new JoueurException();
      testValiditeJoueur(nom,prenom,pseudo);
      
      //test password
      if(passwordGestionnaire==null) throw new MetierException();
      if(passwordGestionnaire.length()<8) throw new MetierException();
      if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();
      
      //joueurInexistant
       boolean joueurExists=false;
       for (Joueur joue : listJoueur){
         if(joue.getPseudo().equals(pseudo)){ joueurExists=true; break;}
        }
       if (joueurExists==false){ throw new JoueurInexistantException();}
      
      //tester si le compte en jetons du joueur est insuffisant
      Joueur joueur=getJoueur(pseudo);
      if (joueur.getSolde()<sommeEnJetons)throw new JoueurException();
      
      //traitement
      joueur.setSolde(joueur.getSolde()-sommeEnJetons);
       
   	}



	/** 
	 * consulter les  joueurs.
	 * 
	 * @param passwordGestionnaire  le password du gestionnaire du site de paris 

	 * @throws MetierException   levée  
	 * si le <code>passwordGestionnaire</code>  est invalœide,
	 * si le <code>passwordGestionnaire</code> est incorrect.
	 * 
	 * @return une liste de liste dont les éléments (de type <code>String</code>) représentent un joueur avec dans l'ordre : 
	 *  <ul>
	 *  <li>       le nom du joueur  </li>
	 *  <li>       le prénom du joueur </li>
	 *  <li>       le pseudo du joueur  </li>
	 *  <li>       son compte en jetons restant disponibles </li>
	 *  <li>       le total de jetons engagés dans ses mises en cours. </li>
	 *  </ul>
	 */
	public LinkedList <LinkedList <String>> consulterJoueurs(String passwordGestionnaire) throws MetierException {
   	//test password
      if(passwordGestionnaire==null) throw new MetierException();
      if(passwordGestionnaire.length()<8) throw new MetierException();
      if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();
   
      //retourne la liste
      LinkedList <LinkedList<String>> listDesJoueurs = new LinkedList <LinkedList<String>>();
      for (Joueur joueur : listJoueur){
		   LinkedList<String> Joueur1 = new LinkedList<String>();
		   Joueur1.add(joueur.getNom());
	      Joueur1.add(joueur.getPrenom()); 
         Joueur1.add(joueur.getPseudo());
         Joueur1.add(Long.toString(joueur.getSolde()));
         long jetonsEngages=0;
         for (Pari pari : joueur.getParis()){
           if (pari.getCompetition().dateCloture.estDansLePasse()==false){jetonsEngages=jetonsEngages+pari.getJetonsMises();}}
           Joueur1.add(Long.toString(jetonsEngages));
           listDesJoueurs.add(Joueur1);    
	   }
	   return listDesJoueurs;
     	}








	// Les méthodes avec mot de passe utilisateur



	/**
	 * miserVainqueur  (parier sur une compétition, en désignant un vainqueur).
	 * Le compte du joueur est débité du nombre de jetons misés.
	 * 
	 * @param pseudo   le pseudo du joueur  
	 * @param passwordJoueur  le password du joueur  
	 * @param miseEnJetons   la somme en jetons à miser  
	 * @param competition   le nom de la compétition  relative au pari effectué
	 * @param vainqueurEnvisage   le nom du compétiteur  sur lequel le joueur mise comme étant le  vainqueur de la compétition  
	 * 
	 * @throws MetierException levée si la somme en jetons est négative.
	 * @throws JoueurInexistantException levée si il n'y a pas de
	 * joueur avec les mêmes pseudos et password.
	 * @throws CompetitionInexistanteException   levée si il n'existe pas de compétition de même nom. 
	 * @throws CompetitionException levée 
	 * si <code>competition</code> ou <code>vainqueurEnvisage</code> sont invalides,
	 * si il n'existe pas un compétiteur de  nom <code>vainqueurEnvisage</code> dans la compétition,
	 * si la compétition n'est plus ouverte (la date de clôture est dans le passé).
	 * @throws JoueurException   levée 
	 * si <code>pseudo</code> ou <code>password</code> sont invalides, 
	 * si le <code>compteEnJetons</code> du joueur est insuffisant (il deviendrait négatif).
	 */
    public void miserVainqueur(String pseudo, String passwordJoueur, long miseEnJetons, String competition, String vainqueurEnvisage) throws MetierException, JoueurInexistantException, CompetitionInexistanteException, CompetitionException, JoueurException  {
       
       //verification sur le joueur  
       if(pseudo==null) throw new JoueurException();
       if (!pseudo.matches("[0-9A-Za-z]{4,}"))  throw new JoueurException();
       if (passwordJoueur==null) throw new JoueurException();
   	 if (!passwordJoueur.matches("[0-9A-Za-z]{8,}")) throw new JoueurException();
       if(miseEnJetons<0){throw new MetierException();}
       Joueur joueur1 =getJoueur(pseudo);
       if (!passwordJoueur.equals(joueur1.getPassword())==true) throw new JoueurException();
      
            //joueurInexistant
       boolean joueurExists=false;
       for (Joueur joue : listJoueur){
         
         if(joue.getPseudo().equals(pseudo)){ joueurExists=true; break;}
        }
        if (joueurExists==false){ throw new JoueurInexistantException();}
       
            //joueur avec meme pseudo et meme mdp
       for(int i=0; i<listJoueur.size(); i++){
         for( int k=i+1; k<listJoueur.size();k++){
            if(listJoueur.get(i).getPseudo().equals(listJoueur.get(k).getPseudo()) && listJoueur.get(i).getPassword().equals(listJoueur.get(k).getPassword()) ){
            throw new JoueurException();}
        
            }  }
       // competition inexistante 
      if (competition==null) throw new CompetitionException();
      
   	boolean competitionExists=false;
       for (Competition comp : listCompetition){
         
         if(comp.getCompetition().equals(competition)){ competitionExists=true; break;}
        }
        if (competitionExists==false){ throw new CompetitionInexistanteException();}
      
      // vainqueurEnvisage n'existe pas
      if (vainqueurEnvisage==null) throw new CompetitionException();
      boolean vainqueurExists=false;
      Competition comp=getCompetition(competition);
       for (Competiteur competi : comp.getCompetiteur()){
         
         if(competi.getNom().equals(vainqueurEnvisage)){ vainqueurExists=true; break;}
        }
        if (vainqueurExists==false){ throw new CompetitionException();}
      
      // competition est dans le passé  
      if(comp.dateCloture.estDansLePasse()==true){ throw new CompetitionException();}
      
      //solde invalide
      Joueur joue=getJoueur(pseudo);
      if(joue.getSolde()<miseEnJetons){throw new JoueurException();}
      
      //traitement: joueur qui pari sera débité 
      Competiteur compet=getCompetiteur(comp,vainqueurEnvisage);
      Pari pari= new Pari(miseEnJetons,joue,comp,compet);
      joue.setSolde(joue.getSolde()-miseEnJetons);
      comp.setSommeJetons(miseEnJetons);
      comp.ajouterPari(pari);
      joue.ajouterPari(pari);
      compet.setSommeJetons(miseEnJetons);       
      
      }
      
    

	// Les méthodes sans mot de passe


	/**
	 * connaître les compétitions en cours.
	 * 
	 * @return une liste de liste dont les éléments (de type <code>String</code>) représentent une compétition avec dans l'ordre : 
	 *  <ul>
	 *  <li>       le nom de la compétition,  </li>
	 *  <li>       la date de clôture de la compétition. </li>
	 *  </ul>
	 */
	public LinkedList <LinkedList <String>> consulterCompetitions(){
		LinkedList <LinkedList <String>> listDesCompetitions = new LinkedList <LinkedList <String>>();
	   for (Competition compete :  listCompetition  ){
		     LinkedList<String> comp = new LinkedList<String>();
		     String nomc=compete.getCompetition();
           comp.add(nomc);
	        String date1=compete.dateCloture.toString();
           
	        comp.add(date1);
	        listDesCompetitions.add(comp);
	   }
	   return listDesCompetitions; } 

	/**
	 * connaître  la liste des noms des compétiteurs d'une compétition.  
	 * 
	 * @param competition   le nom de la compétition  
	 * 
	 * @throws CompetitionException   levée  
	 * si le nom de la compétition est invalide.
	 * @throws CompetitionInexistanteException   levée si il n'existe pas de compétition de même nom. 
	 * 
	 * @return la liste des compétiteurs de la  compétition.
	 */
   
	public LinkedList <String> consulterCompetiteurs(String competition) throws CompetitionException, CompetitionInexistanteException{
   
	   LinkedList<String> competiteurs = new LinkedList<String>();	
      if (competition== null) throw new CompetitionException();
	   if (!competition.matches("[-_.0-9A-Za-z]{4,}"))  throw new CompetitionException();
      Competition compete= getCompetition(competition);
      
      for (Competiteur compet : compete.getCompetiteur()){
        
         competiteurs.add(compet.getNom());

         
         
         }
      return competiteurs;}



	/**
	 * vérifier la validité du password du gestionnaire.
	 * 
	 * @param passwordGestionnaire   le password du gestionnaire à vérifier
	 * 
	 * @throws MetierException   levée 
	 * si le <code>passwordGestionnaire</code> est invalide.  
	 */
	protected void validitePasswordGestionnaire(String passwordGestionnaire) throws MetierException {
	    if (passwordGestionnaire==null) throw new MetierException();
	    if (!passwordGestionnaire.matches("[0-9A-Za-z]{8,}")) throw new MetierException();
	}
  //test validite joueur
   public void testValiditeJoueur(String nom, String prenom, String pseudo) throws JoueurException{
   	 if(nom==null) throw new JoueurException();
       if (!nom.matches("[-A-Za-z]{1,}"))  throw new JoueurException();
        
       if(prenom==null) throw new JoueurException();
       if (!prenom.matches("[-A-Za-z]{1,}"))  throw new JoueurException();
    
       if(pseudo==null) throw new JoueurException();
       if (!pseudo.matches("[0-9A-Za-z]{4,}"))  throw new JoueurException();
      }
  //renvoyer une compétition 
   public Competition getCompetition(String competition) throws CompetitionInexistanteException {
   	 boolean competitionIndex=true;
       Competition variaCompeti=null;
       for( Competition comp: listCompetition){
          String nomCompetition=comp.getCompetition();
           if (competition.equals(nomCompetition)) {competitionIndex=false; variaCompeti=comp; break;  }
   	   }
   	 if (competitionIndex==true){throw new CompetitionInexistanteException();}
       return variaCompeti;
   } 
   //renvoyer un competiteur
   public Competiteur getCompetiteur(Competition competition, String competiteur)throws CompetitionException {
		boolean competiteurIndex=true;
      Competiteur compet=null;
		for (Competiteur comp: competition.getCompetiteur()){
            if(competiteur.equals(comp.getNom())){competiteurIndex=false; compet=comp; break;}
		}
		if (competiteurIndex==true){throw new CompetitionException();}
      return compet;
   }
   //renvoyer un joueur
   public Joueur getJoueur(String pseudo) throws JoueurInexistantException{
      boolean joueurIndex=true;
      Joueur variaJoueur=null;
      for( Joueur joue:listJoueur){
         String pseudoJoueur=joue.getPseudo();
         if(pseudo.equals(pseudoJoueur)){
         joueurIndex=false; 
         variaJoueur=joue;break;}}
      if (joueurIndex==true){ throw new JoueurInexistantException();}
      return variaJoueur;}
    
   //générer un mot de passe du joueur 
   public String randomPass(){
       String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; 	    String pass = "";
	    for(int x=0;x<8;x++)
	    {
	       int i = (int)Math.floor(Math.random() * 62); 
	       pass += chars.charAt(i);
	    }
	    return pass;


}
}


