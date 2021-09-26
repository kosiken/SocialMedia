package org.allisonkosy;
import org.allisonkosy.entity.*;
import org.allisonkosy.runner.Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App

{
    public static final Logger logger = LogManager.getLogger(App.class);
    
    // app error messages array
    public static final String[] errors = ("No record found\n" +
    "An error occurred\n" +
            "User with username or password already exists\n"+"Invalid crieteria").split("\n");
    
    public static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("SocialMedia");

    // A group of preloaded users
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
            "15,Karl,hIBZq5rgdasL\n";

    // The user that is currently logged in
    public static User currentUser = null;

    // The post that is currently being viewed
    public static Post currentPost = null;

    public static void main( String[] args )
    {
        Server server = new Server();
        Scanner in = new Scanner(System.in);
        initialize(server);
        System.out.println("Welcome to the Socialite");

        int resp = 1;


        while (resp != 0) {
            /**
             * Run an infinite loop until one of the methods
             * @App.authenticated or @App.unAuthenticated
             * returns zero
             */
            if(currentUser != null) {
                // There is already a user logged in

                resp =  authenticated(server, in);

            }

            // No currently logged-in user
         else resp =  unAuthenticated(server, in);


        }



    }

    /**
     *
     * @param server
     * @param in
     * @return
     */
    public static int authenticated(Server server, Scanner in) {
        int choice; // Number user inputed
        if(currentPost != null) {
            // If there is a post being viewed then show the post
            System.out.println(currentPost.describe());

            // Prompt user to add comment, view comments or to
            // go back to select another post
            System.out.println("Input 1 to add comment\nInput 2 to view all comments\n Input 3 to go back \n Input >=4 to close");
           try {
               choice = in.nextInt();
           }
           catch (InputMismatchException err) {
               // If the user inputs something that is not a number
               // do not process further but user would be re-prompted because
               // we return 1
               System.out.println("Incorrect input");
               in.nextLine();
               return 1;


           }


            if(choice == 1) {
                addComment(server, in);
            }
            else if(choice == 2) {
                viewAllComments(server);
            }
            else if(choice == 3) {
                // User wants to go back
                currentPost  = null;
            }
            else if(choice >= 4) {
                // User wants to exit
                return 0;
            }

        }
        else {
            try {
                System.out.println("Input 1 to add post\nInput 2 to view all posts\n Input 3 to logout\nInput >= 4 to exit");

                choice = in.nextInt();
                if (choice == 1) {
                    addPost(server, in);
                }
                else if(choice == 2) {
                    viewAllPosts(server, in);
                }
                else if(choice == 3) {
                    // User wants to go back
                    currentUser = null;
                }
                else if(choice >= 4) {
                    // User wants to go exit
                    return 0;
                }
            }
            catch (InputMismatchException err) {
                // If the user inputs something that is not a number
                // do not process further but user would be re-prompted because
                // we return 1
                System.out.println("Incorrect input");
                in.nextLine();
            }
        }
        return  1;
    }

    public static void addPost(Server server, Scanner in) {
        in.nextLine();

        String[] questions = {
                "What is your post title: ",
                "What is post body: "
        };
        int index = 0;
        String title = "";
        String body = "";
        StringBuilder builder = new StringBuilder();

        while (true) {
            // infinite loop to capture multiline text
           if(index <= 1) System.out.print(questions[index]); // do not prompt user more than twice


            if(index<1)  title = in.nextLine(); // get post title
            else {
                /**
                 * What we try to achieve here is trying to enable a user enter multiline post bodies
                 * When the user wants to submit a post they send an empty line
                 * When this block executes the first time the "Keep Inputting..." prompt will be printed
                 * On the subsequent executions of the block we wait for an empty line before continuing
                 * while storing any non-empty lines in the StringBuilder
                 */
                if(index == 1) {
                    System.out.println("Keep Inputting lines or input an empty line to submit post");
                    index++;
                }
                body = in.nextLine(); // capture a line
                if(body.length() == 0) {
                    // catch empty lines and break out of loop
                    body = builder.toString();
                    break;
                }
                builder.append(body).append('\n');
                continue; // do not increment index while user is still inputting
                          // body text

            }
            index++;
        }
        server.createPost(currentUser.getId(), body, title);


    }

    public static void viewAllPosts(Server server, Scanner in) {
        in.nextLine();
        int choice;
        List<Post> posts = server.getAllPosts();
        // show all posts to user
        for (Post post : posts) {
            System.out.println( post.getId() +". "+  post.getTitle());
            System.out.println(post.getUser().getUsername());
            System.out.println("-".repeat(10));
        }
        /**
         * By slug, I mean for example if a post is titled "The Wonder Woman"
         * The slug for that would be the-wonder-woman
         */
        System.out.println("Input a post id or slug to select a post");
        while(true) {
            
            String nl = in.nextLine();

            try {
                choice = Integer.parseInt(nl);
                currentPost = server.findPost(choice);
            }
            catch (NumberFormatException exception) {
                currentPost = server.findPost(nl);
            }

            if(currentPost == null) {
                System.out.println("No such post");
            }
            else {
                break;
            }
        }
    }

    public static void addComment(Server server, Scanner in) {
        in.nextLine();

        System.out.print("\nWhat is your comment: ");
        String body = in.nextLine();
        Comment comment = server.addComment(currentUser.getId().intValue(), currentPost.getId().intValue(), body);
        if(comment != null) {
            System.out.println(comment.describe());
        }
    }

    public static void viewAllComments(Server server) {
        List<Comment > comments = server.getComments(currentPost.getId().intValue());
        if(comments.size() == 0) {
            System.out.println("No comments");
            return;
        }

        System.out.println("COMMENTS ON " + currentPost.getTitle());
        for (Comment comment: comments) {
            System.out.println(comment.describe());
        }
    }

    public static int unAuthenticated(Server server, Scanner in) {
        int choice;
        System.out.println("Input 1 to create new user\nInput 2 to log in as existing user\nInput 3 to exit");
        try {
            choice = in.nextInt();
            System.out.println(choice);
            if(choice == 1) {
                register(server, in);
            }
            else if(choice == 2) {
                login(server, in);
            }
            else if(choice == 3) {
                // user wants to exit
                return 0;
            }
        } catch (InputMismatchException err) {
            System.out.println("Incorrect input");
            in.nextLine();

        }
        return 1;
    }


    public static void login(Server server, Scanner in) {

        System.out.println(userCsv);
        System.out.println("Input one of the following username and password combination to log in or use yours");
        User user = null;
        String[] questions = {
                "What is your username: ",
                "What is your password: "
        };
        int index = 0;
        String name;
        String password = "";
        in.nextLine();

        while (index <  2) {
            System.out.print(questions[index]);

            if(index<1) {
                name = in.nextLine();
                user = server.getAUser(name);
                if(user == null) {
                    break;
                }
            }
            else {
                password = in.nextLine();

            }
            index++;

        }
        if( user != null && password.equals(user.getPassword())) {

            currentUser = user;
            currentUser.setLoggedIn(true);

        }
        else {
            System.out.println("Invalid username or password");
        }

    }
    public static void register(Server server, Scanner in) {
        in.nextLine();

        String[] questions = {
                "What is your username: ",
                "What is your password: "
        };
        int index = 0;
        String name = "";
        String password = "";

        while (index <  2) {
            System.out.print(questions[index]);

            if(index<1)  name = in.nextLine();
            else {
               password = in.nextLine();
            }
            index++;
        }
        currentUser = server.createUser(name, password);
        if (currentUser == null) {
            System.out.println("must use unique name or password" );
        }
    }
    public static void initialize(Server server) {
        App.addBulkUsers(server, 15);
        App.createPlentyRandomPosts(server, 5, 2);
        App.addPlentyRandomComments(server, 16, 1, 2, 2, 4, 5, 6 );
    }
    public static void close( ) {
        logger.info("Closing");
        entityManagerFactory.close();
    }

    public static void addBulkUsers(Server server, int count) {
        String[] strings = userCsv.split("\n");

        int c = 0;


        for (String s:
             strings) {
            String[] s1 = s.split(",");
            try {
                if(c>= count ) break;
              server.createUser(s1[1], s1[2]);
                c++;
            }catch (ArrayIndexOutOfBoundsException exception) {
                logger.info(exception.getMessage());
            }



        }
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

    public static void createRandomComment(Server server, int post, int user) {
        server.addComment(user, post, "This is a comment");
    }

    public static void addPlentyRandomComments(Server server, int post, Integer ...users) {
        for (Integer user : users) {
            createRandomComment(server, post, user);
        }
    }
    public static void logError(int index) {
        logger.error(errors[index]);
    }


}
