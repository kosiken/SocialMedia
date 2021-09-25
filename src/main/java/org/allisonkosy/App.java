package org.allisonkosy;
import org.allisonkosy.entity.*;
import org.allisonkosy.runner.Server;
import org.allisonkosy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App

{
    public static final Logger logger = LogManager.getLogger(App.class);
    public static final String[] errors = ("No record found\n" +
    "An error occured\n" +
            "User with username already exists").split("\n");
    public static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("SocialMedia");
    public static final String userCsv =
            "1,Giffard,OjdlfD5vUFDF\n" +
            "2,Maynord,piaqDKS0kA\n" +
            "3,Thornie,PaHW6Imn5B\n" +
            "4,Rickie,ZQGYblRAhapl\n" +
            "5,Reed,ON7akEa\n" +
            "6,Benedetto,3QHKyccvEyf\n" +
            "7,Edmund,a2ngPU\n" +
            "8,Rhodie,yRreB69rJfr3\n" +
            "9,Vilma,hZkhrKHAp\n" +
            "10,Phaedra,LNfGON7EsG\n" +
            "11,Faydra,kBknirbgu0S\n" +
            "12,Barr,CDybGyv\n" +
            "13,Stearne,B2geUvL8eEHr\n" +
            "14,Stevena,FCuCqZiJRR\n" +
            "15,Karl,hIBZq5rgdasL\n" +
            "16,Fidelia,KlaLDnZoEgE\n" +
            "17,Truda,dfcRpt\n" +
            "18,Wallace,PiOKZUrOs2\n" +
            "19,Walt,PBPoTtN\n" +
            "20,Teresina,DRg6Ro\n" +
            "21,Merline,XoKoNc\n" +
            "22,Martha,bTdvKzsaj\n" +
            "23,Mariquilla,0sxkfz0M\n" +
            "24,Ivonne,Wt32LJCa\n" +
            "25,Grethel,1JjydZa7N\n" +
            "26,Brnaba,iOlAQu8h1qq3\n" +
            "27,Horatius,DoxZkAUUjI7k\n" +
            "28,Brittan,pvcbTnM9kZsw\n" +
            "29,Nero,x2SDQ7\n" +
            "30,Rebecka,SK0r73P\n" +
            "31,Rhett,GhYV3M9fDI\n" +
            "32,Marisa,SJkQ2kF20\n" +
            "33,Vick,kvQNWJF9\n" +
            "34,Roseann,2OpUnnJi\n" +
            "35,Laverne,Nu6wpupWmj1g\n" +
            "36,Tristam,FE0nGkIIBe\n" +
            "37,Puff,t1W930geGubk\n" +
            "38,Teodoro,CTYrVhHcJEV\n" +
            "39,Yank,PrRGWwBA\n" +
            "40,Vickie,qzbcsmztHeWO\n" +
            "41,Orlando,hrsFCQqF\n" +
            "42,Alaine,iFBMMs\n" +
            "43,Wolf,0FxRIQg\n" +
            "44,Brigit,OofMiMb9\n" +
            "45,Wandie,HwjagJm\n" +
            "46,Lutero,ohHE6N\n" +
            "47,Lexy,DiBJ2J\n" +
            "48,Erna,I3GS8a0G6\n" +
            "49,Miles,ljchT7GNaV\n" +
            "50,Niles,UDBuvNrWs";

    public static void main( String[] args )
    {
        Server server = new Server();
        ArrayList<User> users = addBulkUsers(server, 5);
        System.out.println(users.get(2));
        User u = server.getAUser("Thornie");
        System.out.println(u);


    }

    public static void close( ) {
        logger.info("Closing");
        entityManagerFactory.close();
    }

    public static ArrayList<User> addBulkUsers(Server server, int count) {
        String[] strings = userCsv.split("\n");
        ArrayList<User> users = new ArrayList<>();
        int c = 0;


        for (String s:
             strings) {
            String[] s1 = s.split(",");
            try {
                if(c>= count ) break;
                users.add(server.createUser(s1[1], s1[2]));
                c++;
            }catch (ArrayIndexOutOfBoundsException exception) {
                logger.info(exception.getMessage());
            }



        }
        return users;
    }

    public static void createRandomPost(Server server, int user) {
        server.createPost(user, "This is the body", "A title");
    }
    public static void createPlentyRandomPosts(Server server, int count, int user) {
        for (int i = 0; i < count; i++) {
            if(user == 0) {
                createRandomPost(server, i+1);
            }
            else {
                createRandomPost(server,user);
            }
        }
    }
    public static void logError(int index) {
        logger.error(errors[index]);
    }


}
