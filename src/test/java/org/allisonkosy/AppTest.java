package org.allisonkosy;

import org.allisonkosy.entity.*;
import org.allisonkosy.runner.Server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test for simple App.
 */
@DisplayName("Social Media Test class")
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    public static final  Server server = new Server();;

    @BeforeAll
    public static void beforeAll() {
        Server server = new Server();
        App.initialize(server);
    }
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    @DisplayName("Test create user")
    public void shouldCreateUser() {

       User user = server.createUser("kosy", "lion");
        assertNotNull(user);
        assertEquals("kosy", user.getUsername());
    }

    @Test
    @DisplayName("Test create a new post with id")
    public void shouldCreatePost() {




        Post post = server.createPost(2, "lion", "lion is coming");
        assertNotNull(post);
        assertEquals("lion",post.getBody());
        assertNotNull(post.getUser());

        assertEquals("Maynord", post.getUser().getUsername());
     }

    @Test
    @DisplayName("Test create a new post with username")
    public void shouldCreatePostWithUsername() {


        Post post = server.createPost("Thornie", "lion", "lion is coming");
        assertNotNull(post);
        assertEquals("lion",post.getBody());
        assertNotNull(post.getUser());

        assertEquals("Thornie", post.getUser().getUsername());
    }

    @Test
    @DisplayName("Test create a new comment")
    public void shouldCreateComment() {
        Comment comment = server.addComment(1, 19, "lionel messi did it");
        assertNotNull(comment);


    }


    @Test
    @DisplayName("Test not create comment for invalid user")
    public void shouldNotCreateComment() {
        Comment comment = server.addComment(30, 19, "A null comment");
        assertNull(comment);
    }

    @Test
    @DisplayName("Test not create comment for invalid post")
    public void shouldNotCreateComment2() {
        Comment comment = server.addComment(1, 32, "A null comment");
        assertNull(comment);
    }



    @Test
    @DisplayName("Test get user with id")
    public void shouldReturnUser() {
        User user = server.getAUser((long)1);
        assertNotNull(user);
        assertEquals("Giffard", user.getUsername());
        assertTrue(true);
    }

    @Test
    @DisplayName("Test get user with username")
    public void shouldReturUser2() {
        User user = server.getAUser("Rickie");
        assertNotNull(user);
        assertEquals("Rickie", user.getUsername());
        assertTrue(true);
    }

    @Test
    @DisplayName("Test should not find user")
    public void shouldNotReturnUser() {
        User user = server.getAUser("Rickien");
        assertNull(user);

        assertTrue(true);
    }

    @Test
    @DisplayName("Test should find post with id")
    public void shouldFindPost() {
        Post post = server.findPost(19);
        assertNotNull(post);
        assertEquals("lion is coming", post.getTitle());
        assertTrue(true);

    }

    @Test
    @DisplayName("Test should find post with slug")
    public void shouldFindPost2() {
        Post post = server.findPost("a title".replace(' ', '-'));
        assertNotNull(post);
        assertEquals("A title", post.getTitle());
        assertTrue(true);
    }
    @Test
    @DisplayName("Test should find comments under post")
    public void shouldFindComments() {
        List<Comment > comments = server.getComments(16);
        assertNotNull(comments);
        assertEquals(6, comments.size());
        assertEquals("Benedetto", comments.get(5).getUser().getUsername());
        assertTrue(true);
    }

    @Test
    @DisplayName("Test should find comments under by a user")
    public void shouldFindCommentsByUser() {
        List<Comment > comments = server.getCommentsByUser(2);
        assertNotNull(comments);
        assertEquals(2, comments.size());
        assertEquals("Maynord", comments.get(0).getUser().getUsername());
        assertTrue(true);
    }


    @Test
    @DisplayName("Test should not find invalid post")
    public void shouldNotFindPost() {
        Post post = server.findPost(25);
        assertNull(post);
    }
    @Test
    @DisplayName("Test should not find comments under invalid post")
    public void shouldNotFindComments() {
        List<Comment> comments = server.getComments(30);

        assertNull(comments);

    }

    @Test
    @DisplayName("Test should get All valid posts")
    public void shouldGetAllPosts(){
        List<Post> posts = server.getAllPosts();
        assertNotNull(posts);

        assertEquals(5, posts.size());

        posts = server.getAllPosts(2);
        assertEquals(5, posts.size());

        posts = server.getAllPosts(3);
        assertEquals(0, posts.size());

        posts = server.getAllPosts(80);
        assertNull(posts);



    }

    @Test
    @DisplayName("Test should get All valid users")
    public void shouldGetAllUsers(){
        List<User> users = server.getAllUsers();
        assertNotNull(users);

        assertEquals(15, users.size());





    }
    @AfterAll
    public static void endAll() {
        App.close();
    }

}
