package garages;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter; // Import nécessaire pour les Streams
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Représente une voiture qui peut être stationnée dans des garages.
 */
@RequiredArgsConstructor
@ToString
public class Voiture {

	@Getter
	@NonNull
	private final String immatriculation;
	@ToString.Exclude // On ne veut pas afficher les stationnements dans toString
	private final List<Stationnement> myStationnements = new LinkedList<>();

	/**
	 * Fait rentrer la voiture au garage
	 * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws IllegalStateException Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws IllegalStateException {
		// Et si la voiture est déjà dans un garage ?
		if (estDansUnGarage()) {
			throw new IllegalStateException("La voiture est déjà dans un garage.");
		}

		Stationnement s = new Stationnement(this, g);
		myStationnements.add(s);
	}

	/**
	 * Fait sortir la voiture du garage
	 * Précondition : la voiture doit être dans un garage
	 *
	 * @throws IllegalStateException si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() throws IllegalStateException {
		// TODO: Implémenter cette méthode
		// Trouver le dernier stationnement de la voiture
		if (!estDansUnGarage()) {
			throw new IllegalStateException("La voiture n'est pas dans un garage.");
		}
		
		// Le dernier stationnement est celui en cours
		Stationnement dernierStationnement = myStationnements.getLast();
		// Terminer ce stationnement
		dernierStationnement.terminer();
	}

	/**
	 * Calcule l'ensemble des garages visités par cette voiture
	 *
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
		// TODO: Implémenter cette méthode
		// On utilise les Streams pour transformer la liste de stationnements
		// en un ensemble (Set) de garages uniques.
		return myStationnements.stream()
				.map(Stationnement::getGarageVisite) // On extrait le garage de chaque stationnement
				.collect(Collectors.toSet()); // On les collecte dans un Set (qui gère les doublons)
	}

	/**
	 * Détermine si la voiture est actuellement dans un garage
	 *
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() {
		// TODO: Implémenter cette méthode
		if (myStationnements.isEmpty()) {
			return false;
		}
		// Vrai si il y a des stationnements et le dernier stationnement est en cours
		Stationnement dernierStationnement = myStationnements.getLast(); // On prend le dernier
		return dernierStationnement.estEnCours(); // On vérifie s'il est en cours
	}

	/**
	 * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste des
	 * stationnements dans ce garage
	 * <br>
	 * Exemple :
	 *
	 * <pre>
	 * Garage(name=Universite Champollion Albi):
	 * Stationnement{ entree=13/11/2024, sortie=13/11/2024 }
	 * Garage(name=ISIS Castres):
	 * Stationnement{ entree=13/11/2024, sortie=13/11/2024 }
	 * Stationnement{ entree=13/11/2024, en cours }
	 * </pre>
	 *
	 * @param out l'endroit où imprimer (ex: System.out pour imprimer dans la
	 * console)
	 */

	public void imprimeStationnements(PrintStream out) {
		// TODO: Implémenter cette méthode
		// Utiliser les méthodes toString() de Garage et Stationnement

		// On groupe les stationnements par garage en utilisant un Stream
		Map<Garage, List<Stationnement>> stationnementsParGarage = myStationnements.stream()
				.collect(Collectors.groupingBy(Stationnement::getGarageVisite));

		// On itère sur chaque entrée (Garage + sa liste de stationnements) de la Map
		for (Map.Entry<Garage, List<Stationnement>> entry : stationnementsParGarage.entrySet()) {
			Garage garage = entry.getKey();
			List<Stationnement> stationnements = entry.getValue();

			// Imprime le nom du garage (via sa méthode toString())
			out.println(garage.toString());

			// Imprime chaque stationnement pour ce garage (via la méthode toString() de Stationnement)
			for (Stationnement s : stationnements) {
				out.println("\t" + s.toString());
			}
		}
	}
}