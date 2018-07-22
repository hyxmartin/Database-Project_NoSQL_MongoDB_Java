/**
 * This is a lab Java program to introduce students to working with MongoDb.
 *
 * Author: Ebenezer Anjorin (eanjorin@cmu.edu)
 *
 */

import com.mongodb.BasicDBObject;
import java.util.Scanner;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.net.UnknownHostException;
import java.util.Set;


public class MongodbLab {
    
    private static MongoClient mongoClient;
    private static DB db;
    private static Scanner in;
    private static DBCollection clients;
    
    private static void out(Object o) {
        System.out.println(o);
    }
    
    private static void outp(Object o) {
        System.out.print(o);
    }
    
    public static void main(String[] args) {
        
        out(" **** This is simple Java Application using MongoDB ****");
        init();
        menu();
    }

    /**
     * Connect to mongodb, and select clients collection
     */
    private static void init() {
        try {
            
            in = new Scanner(System.in);
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDB("dreamhome");
            
            out(" Successfully connected to dreamhome database");
            out(" Collections in this database");
            Set<String> colls = db.getCollectionNames();
            int i = 1;
            for (String s : colls) {
                out(" " + i++ + "\t" + s);
            }
            clients = db.getCollection("clients");
            
        } catch (Exception ex) {
            out("Unable to connect to Mongodb");
        }
        
    }

    /**
     *
     * Display menu
     */
    private static void menu() {
        
        out(" \nOption: ");
        out(" 1. Add  a new client ");
        out(" 2. View all clients ");
        out(" 3. Find a client ");
        out(" 4. Exit ");
        
        
        System.out.print(" Select an option(1-3): ");
        int opt = in.nextInt();
        out("\n");
        switch (opt) {
            case 1:
                addClient();
                break;
            case 2:
                viewAll();
                break;
            case 3:
                findClient();
                break;
            default:
                System.exit(0);
            
        }
        menu();
        
    }

    /**
     * Add a new client record
     */
    private static void addClient() {
        
        BasicDBObject client = new BasicDBObject();
        outp(" Client No: ");
        client.append("clientno", in.next());
        outp(" Firstname: ");
        client.append("fname", in.next());
        outp(" Lastname: ");
        client.append("lname", in.next());
        outp(" Email: ");
        client.append("email", in.next());
        outp(" Telephone: ");
        client.append("telno", in.next());
        outp(" Preference: ");
        client.append("pretype", in.next());
        outp(" Max Rent: ");
        client.append("maxrent", in.next());
        //out(client);

        clients.insert(client);
        
    }

    /**
     * Display all client records
     */
    private static void viewAll() {
        
        DBCursor cursor = clients.find();
        try {
            while (cursor.hasNext()) {
                DBObject c = cursor.next();
                printResult(c);
                out("\n");
            }
        } finally {
            cursor.close();
        }
    }

    /**
     * find client by id
     */
    private static void findClient() {
        
        outp(" Enter  client no to search: ");
        BasicDBObject query = new BasicDBObject("clientno", in.next());
        DBCursor cursor = clients.find(query);
        
        try {
            while (cursor.hasNext()) {
                printResult(cursor.next());
            }
        } finally {
            cursor.close();
        }
    }

    /**
     * print output
     */
    private static void printResult(DBObject c) {
        out(" Client No: " + c.get("clientno"));
        out(" Full Name: " + c.get("fname") + " " + c.get("lname"));
        out(" Email: " + c.get("email"));
        out(" Tel No: " + c.get("telno"));
        out(" Max Rent: " + c.get("maxrent"));
        out(" Preference Type: " + c.get("pretype"));
    }
}
