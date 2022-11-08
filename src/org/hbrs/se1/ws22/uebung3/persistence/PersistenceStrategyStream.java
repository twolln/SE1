package org.hbrs.se1.ws22.uebung3.persistence;


import org.hbrs.se1.ws22.uebung3.exception.PersistenceException;

import java.io.*;
import java.util.List;


public class PersistenceStrategyStream<E> implements PersistenceStrategy<E> {

    // URL of file, in which the objects are stored
    private String location = "objects.ser";

    //========================================================================================================
    //File Streams
    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    //Object Streams
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    //========================================================================================================

    // Backdoor method used only for testing purposes, if the location should be changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try this out ;-)!
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    /**
     * Method for opening the connection to a stream (here: Input- and Output-Stream)
     * In case of having problems while opening the streams, leave the code in methods load
     * and save
     */
    public void openConnection() throws PersistenceException {
        try {
            fileInputStream = new FileInputStream(location);
            fileOutputStream = new FileOutputStream(location);

            objectInputStream = new ObjectInputStream(fileInputStream);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

        } catch (FileNotFoundException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ImplementationNotAvailable, e.getMessage());
        } catch (IOException d) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, d.getMessage());
        }
    }

    @Override
    /**
     * Method for closing the connections to a stream
     */
    public void closeConnection() throws PersistenceException {
        try {
            fileInputStream.close();
            fileOutputStream.close();

            objectInputStream.close();
            objectOutputStream.close();

        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, e.getMessage());
        }
    }

    @Override
    /**
     * Method for saving a list of Member-objects to a disk (HDD)
     */
    public void save(List<E> member) throws PersistenceException  {
        try {
            fileOutputStream = new FileOutputStream(location);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
        } catch (FileNotFoundException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ImplementationNotAvailable, e.getMessage());
        } catch (IOException d) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, d.getMessage());
        }
        try {
            objectOutputStream.writeObject(member);

        } catch (IOException d) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, d.getMessage());
        }
        try {
            fileOutputStream.close();
            objectOutputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    /**
     * Method for loading a list of Member-objects from a disk (HDD)
     * Some coding examples come for free :-)
     * Take also a look at the import statements above ;-!
     */
    public List<E> load() throws PersistenceException  {
        // Some Coding hints ;-)

        // ObjectInputStream ois = null;
        // FileInputStream fis = null;
        // List<...> newListe =  null;
        //
        // Initiating the Stream (can also be moved to method openConnection()... ;-)
        // fis = new FileInputStream( " a location to a file" );
        // Tipp: Use a directory (ends with "/") to implement a negative test case ;-)
        // ois = new ObjectInputStream(fis);

        // Reading and extracting the list (try .. catch ommitted here)
        // Object obj = ois.readObject();

        // if (obj instanceof List<?>) {
        //       newListe = (List) obj;
        // return newListe

        // and finally close the streams (guess where this could be...?)
        List<E> liste;

        try {
            fileInputStream = new FileInputStream(location);
            objectInputStream = new ObjectInputStream(fileInputStream);

        } catch (FileNotFoundException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ImplementationNotAvailable, e.getMessage());
        } catch (IOException d) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, d.getMessage());
        }


        try {
            List<E> o = (List<E>) objectInputStream.readObject();
            liste = o;

        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, e.getMessage());
        } catch (ClassNotFoundException d) {
            throw new PersistenceException(PersistenceException.ExceptionType.ImplementationNotAvailable, d.getMessage());
        }


        try {
            fileInputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, e.getMessage());
        }

        return liste;
    }
}
