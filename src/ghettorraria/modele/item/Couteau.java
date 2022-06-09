package ghettorraria.modele.item;

public class Couteau extends Arme implements Construire{

	public Couteau(String nom,int attaque, int porter) {
		super("Couteau",15, 5, 1);
	}

	@Override
	public String[] recette() {
		String[] construction= new String[6];
		construction[0]="Bâton";
		construction[2]="Acier";
		construction[4]="Acier";
		return construction;
	}

	

	

}


