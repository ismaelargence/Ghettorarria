package ghettorraria.modele;

import java.util.ArrayList;

public class BFS {
	
	private Coord depart;
	private Terrain terrain;
	private ArrayList<Coord> file;
	private int[][] tabBfs;
	private final int sautMax;
	private final static int VIDE = -2;
	private final static int IMPOSSIBLE = -9;
	private final static int PHISIQUE = -5;
	private Acteur acteur;

	public BFS(Acteur a, Terrain terrain, int sautMax){
		this.terrain = terrain;
		this.file = new ArrayList<Coord>();
		this.tabBfs = new int[terrain.getHauteur()][terrain.getLargeur()];
		this.sautMax = sautMax;
		this.acteur = a;

	}

	public void algoBfs(){
		innitBfs();
		file.add(depart);
		tabBfs[depart.getCoordY()][depart.getCoordX()] = 0;
		while (!file.isEmpty()){
			for (Coord c : adjacent(file.get(0))){
				if (estValable(c)){
					tabBfs[c.getCoordY()][c.getCoordX()] = tabBfs[file.get(0).getCoordY()][file.get(0).getCoordX()] + 1;
					file.add(c);
				}
			}
			file.remove(0);
		}
	}

	public ArrayList<Coord> adjacent(Coord c){
		ArrayList<Coord> listeRet = new ArrayList<Coord>();
		if (c.getCoordX() > 0){
			listeRet.add(new Coord(c.getCoordX() - 1, c.getCoordY()));
		}
		if (c.getCoordX() < terrain.getLargeur() - 1){
			listeRet.add(new Coord(c.getCoordX() + 1, c.getCoordY()));
		}
		if (c.getCoordY() > 0){
			listeRet.add(new Coord(c.getCoordX(), c.getCoordY() - 1));
		}
		if (c.getCoordY() < terrain.getHauteur() - 1){
			listeRet.add(new Coord(c.getCoordX(), c.getCoordY() + 1));
		}
		return listeRet;
	}

	public boolean estValable(Coord c){
		if (c.getCoordY() < terrain.getHauteur() - 1 && tabBfs[c.getCoordY() + 1][c.getCoordX()] != IMPOSSIBLE){
			if (tabBfs[c.getCoordY()][c.getCoordX()] == IMPOSSIBLE){
				return possibleHaut(c) || possibleGauche(c) || possibleDroite(c);
			}
			return tabBfs[c.getCoordY()][c.getCoordX()] == VIDE;
		}
		return false;
	}

	private boolean possibleHaut(Coord c){
		return c.getCoordY() > 0 && tabBfs[c.getCoordY() - 1][c.getCoordX()] != VIDE && tabBfs[c.getCoordY() - 1][c.getCoordX()] != IMPOSSIBLE && tabBfs[c.getCoordY() - 1][c.getCoordX()] != PHISIQUE;
	}

	private boolean possibleGauche(Coord c){
		if (c.getCoordX() > 0 && c.getCoordY() < terrain.getHauteur() - 1){
			return tabBfs[c.getCoordY() + 1][c.getCoordX() - 1] == PHISIQUE;
		}
		return false;
	}

	private boolean possibleDroite(Coord c){
		if (c.getCoordX() < terrain.getLargeur() - 1 && c.getCoordY() < terrain.getHauteur() - 1){
			return tabBfs[c.getCoordY() + 1][c.getCoordX() + 1] == PHISIQUE;
		}
		return false;
	}

	public void innitBfs(){
		for (int i = 0; i < terrain.getHauteur(); i++){
			for (int j = 0; j < terrain.getLargeur(); j++){
				if (terrain.getBloc(i,j).estSolide()){
					tabBfs[i][j] = PHISIQUE;
				}
				else{
					tabBfs[i][j] = VIDE;
				}
			}
		}
		depart = new Coord(acteur.getX() / 32, (acteur.getY() / 32) * terrain.getLargeur());
		innitSaut();
		file.clear();
	}

	public void innitSaut(){
		for (int i = 0; i < terrain.getHauteur(); i++){
			for (int j = 0; j < terrain.getLargeur(); j++){
				if (tabBfs[i][j] == PHISIQUE){
					if (i > sautMax && sautPossible(i, j)){
						tabBfs[i - sautMax][j] = IMPOSSIBLE;
					}
				}
			}
		}
	}

	public boolean sautPossible(int i, int j){
		int compteur = 1;
		while (compteur <= sautMax -1 && tabBfs[i - compteur][j] != PHISIQUE){
			compteur++;
		}
		return compteur > sautMax - 1;
	}

	public boolean droite(int x, int y){
		if (x < terrain.getLargeur() - 1){
			return tabBfs[y][x] - 1 == tabBfs[y][x + 1];
		}
		return false;
	}

	public boolean gauche(int x, int y){
		if (x > 0){
			return tabBfs[y][x] - 1 == tabBfs[y][x - 1];
		}
		return false;
	}

	public boolean saute(int x, int y){
		if(y < terrain.getHauteur() - 1){
			return tabBfs[y][x] - 1 == tabBfs[y][x];
		}
		return false;
	}

	public boolean estProche (Acteur a, int val){
		return tabBfs[terrain.getIndiceHauteur(a.getY())][terrain.getIndiceLargeur(a.getX())] <= val;
	}

	public int[][] getBFS() {
		return tabBfs;
	}
	
	public void afficherBFS(){
		for (int[] t : tabBfs){
			for (int x : t){
				for (int i = String.valueOf(x).length() ; i < 2; i++){
					System.out.print(" ");
				}
				System.out.print(x + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
