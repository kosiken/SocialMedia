package org.allisonkosy;

import org.allisonkosy.entity.*;
import org.allisonkosy.runner.Server;
import org.allisonkosy.service.PostService;
import org.allisonkosy.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test for simple App.
 */
@DisplayName("School Library Test class")
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    public static final  Server server = new Server();;

    @BeforeAll
    public static void beforeAll() {
        Server server = new Server();
        App.addBulkUsers(server, 15);
        App.createPlentyRandomPosts(server, 5, 2);
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
    public void shouldNotCreateComment() {
        assertTrue(true);
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
    public void shouldFindComment() {
        assertTrue(true);
    }

    @Test
    public void shouldNotFindPost() {
        assertTrue(true);
    }
    @Test
    public void shouldNotFindComment() {
        assertTrue(true);
    }


    @AfterAll
    public static void endAll() {
        App.close();
    }

}
