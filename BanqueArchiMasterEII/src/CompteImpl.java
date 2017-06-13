import banque.ComptePOA;

/**
 * Created by nico on 4/12/17.
 */
public class CompteImpl extends ComptePOA{


    private String titulaire;
    private float solde;

    public CompteImpl(String titulaire) {
        System.out.println("Création d'un compte");
        this.titulaire = titulaire;
        this.solde = 0;
    }

    public CompteImpl(String titulaire, float solde) {
        System.out.println("Création d'un compte");
        this.titulaire = titulaire;
        this.solde = solde;
    }

    @Override
    public String titulaire() {
        return titulaire;
    }

    @Override
    public void titulaire(String newTitulaire) {
        System.out.println("Modification du titulaire");
        titulaire = newTitulaire;
    }

    @Override
    public float solde() {
        System.out.println("Récupération du solde");
        return solde;
    }

    @Override
    public boolean crediter(float somme) {
        System.out.println("Crédit du compte");
        if(somme > 0) {
            solde += somme;
            return true;
        } else
            return false;
    }

    @Override
    public boolean debiter(float somme) {
        System.out.println("Débit du compte");
        if(somme > 0 && somme <= solde) {
            solde -= somme;
            return true;
        } else
            return false;    }

    @Override
    public String infosCompte() {
        return titulaire  + " " + solde;
    }
}
