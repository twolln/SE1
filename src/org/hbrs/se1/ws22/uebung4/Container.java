package org.hbrs.se1.ws22.uebung4;

import org.hbrs.se1.ws22.uebung4.exception.PersistenceException;
import org.hbrs.se1.ws22.uebung4.persistence.PersistenceStrategy;
import org.hbrs.se1.ws22.uebung4.exception.ContainerException;
import org.hbrs.se1.ws22.uebung4.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws22.uebung4.view.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;



public class Container {

	//ArrayList als Datenstruktur
	private List<Employee> liste = null;

	//Statische Klassen-Variable, um die Referenz
	//auf das einzige Container-Objekt abzuspeichern
	// Dynamische Belegung: nur falls Methode getInstance geladen
	// wird, dann wird nach Bedarf die Variable mit einer Referenz gefüllt
	private static Container instance = null; // = new Container();

	// Reference to the internal strategy (e.g. MongoDB or Stream)
	private PersistenceStrategy strategy = null;

	// Flag to see, if a connection is opened
	private boolean connectionOpen = false;

	/*
	 * Statische Methode um die einzige Instanz der Klasse
	 * Container zu bekommen. Das Keyword synchronized bewirkt,
	 * dass garantiert nur ein Objekt den alleinigen Zugriff
	 * auf diese Methode hat. Anonsten koennte es passieren, dass
	 * zwei parallel zugreifende Objekte zwei unterschiedliche
	 * Objekte erhalten (vgl. auch Erlaeuterung in Uebung)
	 *
	 */
	public static synchronized Container getInstance() {
		if (instance == null) {
			instance = new Container();
			//System.out.println("Objekt vom Typ Container wurde instanziiert!");
		}
		return instance;
	}

	// Der statische Initialisierungsblock. Dient nur für Debug-Zwecke
	// zur Verdeutlichung, wann eine Klasse geladen wird.
	/*static {
		System.out.println("Klasse Container wurde geladen!");
		// instance = new Container();
	}*/



	/*
	 * Ueberschreiben des Konstruktors. Durch die Sichtbarkeit private
	 * kann man von aussen die Klasse nicht mehr instanziieren,
	 * sondern nur noch kontrolliert ueber die statische Methode
	 * der Klasse Container!
	 */
	private Container(){
		//System.out.println("Container ist instanziiert (Konstruktor)!");
		this.liste = new ArrayList<Employee>();
	}


	/**
	 * Method for getting the internal list. e.g. from a View-object
	 * @return
	 */
	public List getCurrentList() {
		return this.liste;
	}

	/**
	 * Method for adding Employee-objects
	 * @param r
	 * @throws ContainerException
	 */
	public void addEmployee ( Employee r ) throws ContainerException {

		if ( contains( r ) == true ) {
			System.out.println("Duplikat: " + r.toString() );
			ContainerException ex = new ContainerException( ContainerException.ExceptionType.DuplicateEmployee);
			ex.addID ( r.getPid() );
			throw ex;
		}
		liste.add(r);
	}

	/**
	 * Methode zur Ueberpruefung, ob ein Employee-Objekt in der Liste enthalten ist
	 *
	 */
	private boolean contains( Employee r) {
		Integer ID = r.getPid();
		for ( Employee rec : liste) {
			if ( rec.getPid() == ID ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method for deleting an object with a given id.
	 *
	 */
	public String deleteEmployee(Integer id ) {
		Employee rec = getEmployee( id );
		if (rec == null) return "Employee nicht enthalten - ERROR"; else {
			liste.remove(rec);
			return "Employee mit der ID " + id + " konnte geloescht werden";
		}
	}

	/*
	 * Method for getting the number of currently stored objects
	 *
	 */
	public int size(){

		return liste.size();
	}


	/*
	 * Interne Methode zur Ermittlung eines Employee
	 *
	 */
	private Employee getEmployee(Integer id) {
		for ( Employee rec : liste) {
			if (id == rec.getPid().intValue() ){
				return rec;
			}
		}
		return null;
	}


	/**
	 * Method for loading objects. Uses the internally stored strategy object
	 * @throws PersistenceException
	 */
	public void load() throws PersistenceException {
		if (this.strategy == null)
			throw new PersistenceException( PersistenceException.ExceptionType.NoStrategyIsSet,  "Strategy not initialized");

		if (connectionOpen == false) {
			this.openConnection();
			connectionOpen = true;
		}
		List<Employee> liste = this.strategy.load();
		this.liste = liste; // MayBe merge
	}

	/**
	 * Method for loading objects. Uses the internally stored strategy object
	 * @throws PersistenceException
	 */
	public void loadMerge() throws PersistenceException {
		load();
	}

	/**
	 * Method for setting the Persistence-Strategy from outside.
	 * If a new strategy is set, then the old one is closed before (if available)
	 * ToDo here: delegate the exception to the client in case of problems when closing the connection
	 * (not really relevant in the context of this assignment)
	 *
	 * @param persistenceStrategy
	 */
	public void setPersistenceStrategy(PersistenceStrategy persistenceStrategy) {
		if (connectionOpen == true) {
			try {
				this.closeConnection();
			} catch (PersistenceException e) {
				// ToDo here: delegate to client (next year maybe ;-))
				e.printStackTrace();
			}
		}
		this.strategy = persistenceStrategy;
	}


	/**
	 * Method for storing objects. Uses the internally stored strategy object
	 * @throws PersistenceException
	 */
	public void store() throws PersistenceException {
		if (this.strategy == null)
			throw new PersistenceException( PersistenceException.ExceptionType.NoStrategyIsSet,
					"Strategy not initialized");

		if (connectionOpen == false) {
			this.openConnection();
			connectionOpen = true;
		}
		this.strategy.save( this.liste  );
	}

	private void openConnection() throws PersistenceException {
		try {
			this.strategy.openConnection();
			connectionOpen = true;
		} catch( UnsupportedOperationException e ) {
			throw new PersistenceException( PersistenceException.ExceptionType.ImplementationNotAvailable , "Not implemented!" );
		}
	}

	private void closeConnection() throws PersistenceException {
		try {
			this.strategy.closeConnection();
			connectionOpen = false;
		} catch( UnsupportedOperationException e ) {
			throw new PersistenceException( PersistenceException.ExceptionType.ImplementationNotAvailable , "Not implemented!" );
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void startEingabe() throws ContainerException, Exception {
		PersistenceStrategy<Employee> ps = new PersistenceStrategyStream<>();
		this.setPersistenceStrategy(new PersistenceStrategyStream<>());

		String strInput = null;


		// Initialisierung des Eingabe-View
		Scanner scanner = new Scanner( System.in );

		while ( true ) {

			// Ausgabe eines Texts zur Begruessung
			System.out.println();
			System.out.println();
			System.out.println("[Employee-Tool gestartet...]");
			System.out.println("enter: Befehl um einen neuen Mitarbeiter hinzuzufügen");
			System.out.println("dump: Befehl um eine Übersicht über die Daten aller Mitarbeiter zu erhalten");
			System.out.println("store: Befehl um die Liste abzuspeichern");
			System.out.println("search: Befehl um nach Mitarbeitern zu suchen welche eine bestimmte Expertise besitzen");
			System.out.println("help: Befehl um eine Übersicht aller möglichen Befehle zu erhalten");
			System.out.println("exit: Befehl um die Anwendung zu verlassen");


			System.out.print( "> "  );

			strInput = scanner.nextLine();

			// Extrahiert ein Array aus der Eingabe
			String[] strings = strInput.split(" ");

			// 	Falls 'help' eingegeben wurde, werden alle Befehle ausgedruckt
			if ( strings[0].equals("help") ) {
				System.out.println("Folgende Befehle stehen zur Verfuegung: help, dump, store und search");

			}
			// Auswahl der bisher implementierten Befehle:
			if ( strings[0].equals("dump") ) {
				startAusgabe();
			}
			// Auswahl der bisher implementierten Befehle:
			if ( strings[0].equals("enter") ) {
				System.out.println("ID des neuen Mitarbeiters:");
				int intInput = Integer.parseInt(scanner.nextLine());

				EmployeeConcrete neuerEmployee = new EmployeeConcrete(intInput);

				System.out.println("Vorname:");
				neuerEmployee.setVorname(scanner.nextLine());

				System.out.println("Nachname");
				neuerEmployee.setName(scanner.nextLine());

				System.out.println("Rolle im Unternehmen:");
				neuerEmployee.setRolle(scanner.nextLine());

				System.out.println("Abteilung (Optional!!! Falls nicht vorhanden enter drücken)");
				String abteilung = scanner.nextLine();
				if (!Objects.equals(abteilung, "")) {
					neuerEmployee.setAbteilung(abteilung);
				}

				System.out.println("Ab jetzt können mehrere Expertisen dem Bewerber hinzugefügt werden. Geben Sie nun die Erste die Sie hinzufügen wollen ein.");

				String expertise = scanner.nextLine();

				System.out.println("Auf welchem Level befindet sich die Expertise ? 1= Beginner, 2= Experte, 3= Top-Performer ");
				neuerEmployee.addExpertise(expertise, Integer.parseInt(scanner.nextLine()));

				System.out.println("Folgende Expertise wurde vermerkt: " + neuerEmployee.getExpertisen());
				System.out.println("Möchtest du eine weitere Expertise hinzufügen ? Antworte mit y für ja oder n für nein");
				if (Objects.equals(scanner.nextLine(), "y")) {
					System.out.println("Wie lautet die nächste Expertise?");
					expertise = scanner.nextLine();
					System.out.println("Auf welchem Level befindet sich die Expertise ? 1= Beginner, 2= Experte, 3= Top-Performer ");
					neuerEmployee.addExpertise(expertise, Integer.parseInt(scanner.nextLine()));

					System.out.println("Folgende Expertisen wurden bisher vermerkt: " + neuerEmployee.getExpertisen());
					System.out.println("Möchtest du eine weitere Expertise hinzufügen ? Antworte mit y für ja oder n für nein");
					if (Objects.equals(scanner.nextLine(), "y")) {
						System.out.println("Wie lautet die nächste Expertise?");
						expertise = scanner.nextLine();
						System.out.println("Auf welchem Level befindet sich die Expertise ? 1= Beginner, 2= Experte, 3= Top-Performer ");
						neuerEmployee.addExpertise(expertise, Integer.parseInt(scanner.nextLine()));
					}
					else {
						System.out.println("");
					}
				}
				else {
					System.out.println("");
				}

				/////////////////////////////////////////////////////////////////////////////////////////////////////////
				liste.add(neuerEmployee);
				System.out.println("Der Mitarbeiter " + neuerEmployee.getVorname() + " " + neuerEmployee.getName() + " wurde erfolgreich hinzugefügt!");

				/////////////////////////////////////////////////////////////////////////////////////////////////////////


				// Daten einlesen ...
				// this.addEmployee( new Employee( data ) ) um das Objekt in die Liste einzufügen.
			}

			if (  strings[0].equals("store")  ) {
				System.out.println("Die Employee Liste wurde abgespeichert");
				this.store();
			}

			if (  strings[0].equals("exit")  ) {
				System.out.println("Die Employee Liste wurde abgespeichert");
				this.store();
				scanner.close();
			}

			if (  strings[0].equals("load")  ) {
				System.out.println("'merge' (die geladenen Mitarbeiter-Objekte werden mit den Mitarbeiter-Objekten im Speicher vereinigt) oder 'force' (die geladenen Mitarbeiter-Objekte überschreiben die Mitarbeiter-Objekte im Speicher) ?");
				this.load();
			}

			if (  strings[0].equals("help")  ) {
				System.out.println("[Übersicht aller möglichen Befehle]");
				System.out.println("enter: Befehl um einen neuen Mitarbeiter hinzuzufügen");
				System.out.println("dump: Befehl um eine Übersicht über die Daten aller Mitarbeiter zu erhalten");
				System.out.println("store: Befehl um die Liste abzuspeichern");
				System.out.println("search: Befehl um nach Mitarbeitern zu suchen welche eine bestimmte Expertise besitzen");
				System.out.println("help: Befehl um eine Übersicht aller möglichen Befehle zu erhalten");
				System.out.println("exit: Befehl um die Anwendung zu verlassen");

			}

		} // Ende der Schleife
	}

	/**
	 * Diese Methode realisiert die Ausgabe.
	 */
	public void startAusgabe() {

		// Hier möchte Herr P. die Liste mit einem eigenen Sortieralgorithmus sortieren und dann
		// ausgeben. Allerdings weiss der Student hier nicht weiter

		// [Sortierung ausgelassen]
		// Todo (Sortierung der Liste)

		// Klassische Ausgabe ueber eine For-Each-Schleife
		if (liste.size() == 0) {
			System.out.println("Die Liste ist leer");
		}

		Client client = new Client();
		client.getCurrentList();

		// [Variante mit forEach-Methode / Streams (--> Kapitel 9, Lösung Übung Nr. 2)?
		//  Gerne auch mit Beachtung der neuen US1
		// (Filterung Abteilung = "ein Wert (z.B. Marketing)"
		// Todo (Herleitung ForEach-Methode)

	}

}
