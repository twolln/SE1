package org.hbrs.se1.ws22.uebung4.persistence;
import org.hbrs.se1.ws22.uebung4.exception.PersistenceException;


import java.io.*;
import java.util.List;

public class PersistenceStrategyStream<E> implements PersistenceStrategy<E> {

    // URL der Datei, in der die Objekte gespeichert werden
    private String LOCATION = "allemployees1.ser";

    private ObjectOutputStream oos = null;
    private FileOutputStream fos = null;

    private FileInputStream fis = null;
    private ObjectInputStream ois = null;

    // Used only for testing purposes, if the location should be changed
    // Example: Location is a directory
    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    @Override
    /**
     * Used only for openining a connection for storing objects
     * Exception occurs, when both are created (TODO!)
     */
    public void openConnection() throws PersistenceException {
        //System.out.println("in Methode open");
        try {
            fos = new FileOutputStream( LOCATION );
            fis = new FileInputStream( LOCATION );
        } catch (FileNotFoundException e) {
            throw new PersistenceException( PersistenceException.ExceptionType.ConnectionNotAvailable
            , "Error in opening the connection, File could not be found");
        }
        try {
            oos = new ObjectOutputStream( fos );
            ois = new ObjectInputStream(  fis  );
        } catch (IOException e) {
            throw new PersistenceException( PersistenceException.ExceptionType.ConnectionNotAvailable
                    , "Error in opening the connection, problems with the stream");
        }
    }

    @Override
    public void closeConnection() throws PersistenceException {

        try {
            // Closing the outputstreams for storing
            if (oos != null) oos.close();
            if (fos != null) fos.close();

            // Closing the inputstreams for loading
            if (ois != null) ois.close();
            if (fis != null) fis.close();
        } catch( IOException e ) {
            // Lazy solution: catching the exception of any closing activity ;-)
            throw new PersistenceException(PersistenceException.ExceptionType.ClosingFailure , "error while closing connections");
        }
    }

    @Override
    /**
     * Method for saving a list of Member-objects to a disk (HDD)
     */
    public void save(List<E> list) throws PersistenceException {
        // ensure that the connection is open
        // this.openConnection();

        // Write the objects to stream
        try {
            //System.out.println(  list.size() + " Member-Objekte wurden erfolgreich gespeichert!");
            oos.writeObject( list );

        }
        catch (IOException e) {
            // Koennte man ausgeben für interne Debugs: e.printStackTrace();
            // Chain of Responsibility: Hochtragen der Exception in Richtung Ausgabe (UI)
            // Uebergabe in ein lesbares Format fuer den Benutzer
            e.printStackTrace();
            throw new PersistenceException( PersistenceException.ExceptionType.SaveFailure , "Fehler beim Speichern der Datei!");
        }
        finally {
            this.closeConnection();
        }
    }

    @Override
    /**
     * Method for loading a list of Member-objects from a disk (HDD)
     * Some coding examples comxe for free :-)
     */
    public List<E> load() throws PersistenceException {

        // Load the objects from stream
        List<E> list = null;

        try {
            // Create Streams here instead using "this.openConnection();"
            fis = new FileInputStream( LOCATION );
            ois = new ObjectInputStream( fis );

            // Auslesen der Liste
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                list = (List) obj;
            }
            System.out.println("LOG: Es wurden " + list.size() + " User Stories erfolgreich reingeladen!");
            return list;
        }
        catch (IOException e) {
            // Sup-Optimal, da Exeception in Form eines unlesbaren Stake-Traces ausgegeben wird
            e.printStackTrace();
            throw new PersistenceException( PersistenceException.ExceptionType.LoadFailure , "Fehler beim Laden der Datei!");
        }
        catch (ClassNotFoundException e) {
            // Chain of Responsbility erfuellt, durch Throw der Exceotion kann UI
            // benachrichtigt werden!
            throw new PersistenceException( PersistenceException.ExceptionType.LoadFailure , "Fehler beim Laden der Datei! Class not found!");
        }
        finally {
            this.closeConnection();
        }

    }
}
