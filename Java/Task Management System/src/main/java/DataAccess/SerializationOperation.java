package DataAccess;

import java.io.*;
import java.text.ParseException;

public class SerializationOperation {

    public static void serialize(String fileName, Object object) throws IOException {

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
             objectOutputStream.writeObject(object);
             objectOutputStream.flush();
        }
        catch (IOException e){
            throw new IOException();
        }
    }


    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return objectInputStream.readObject();
        }catch (IOException e){
            throw new IOException();
        }
    }
}
