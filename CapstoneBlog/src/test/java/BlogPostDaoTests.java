/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mycompany.capstoneblog.dao.BlogPostDao;
import com.mycompany.capstoneblog.dto.BlogPost;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author apprentice
 */
public class BlogPostDaoTests {

    BlogPostDao dao;
    BlogPost bp1 = new BlogPost();
    BlogPost bp2 = new BlogPost();

    public BlogPostDaoTests() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        dao = (BlogPostDao) ctx.getBean("BlogPostDao");

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        cleaner.execute("DELETE FROM blog_post");

    }

    @Before
    public void setUp() {
        bp1.setTitle("Interviews");
        bp1.setBody("Are Fun");
        bp1.setAuthorId(1);
        bp1.setApprovalStatus(1);
        
        bp2.setTitle("Junit");
        bp2.setBody("is useful");
        bp2.setAuthorId(2);
        bp2.setApprovalStatus(0);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreate1() {

        BlogPost fromDb = dao.create(bp1);

        Assert.assertEquals(fromDb.getTitle(), "Interviews");

    }

    @Test
    public void testCreate2() {

        BlogPost fromDb = dao.create(bp1);

        Assert.assertEquals(fromDb.getBody(), "Are Fun");

    }

    @Test
    public void testRead() {

        BlogPost fromDb = dao.create(bp1);

        BlogPost newFromDb = dao.read(fromDb.getId());

        Assert.assertEquals(newFromDb.getBody(), "Are Fun");

    }

    @Test
    public void testUpdate() {
        
        BlogPost fromDb = dao.create(bp1);
        
        fromDb.setTitle("Wooster");
        
        dao.update(fromDb);
        
        BlogPost newFromDb = dao.read(fromDb.getId());
        
        Assert.assertEquals(newFromDb.getTitle(), "Wooster");
        
    }
    
    @Test
    public void testUpdate2() {
        
        BlogPost fromDb = dao.create(bp1);
        
        fromDb.setBody("Blogstuff");
        
        dao.update(fromDb);
        
        BlogPost newFromDb = dao.read(fromDb.getId());
        
        Assert.assertEquals(newFromDb.getBody(), "Blogstuff");
        
    }
    
    @Test
    public void testListAll(){
        
        dao.create(bp1);
        dao.create(bp2);
        
        List<BlogPost> list = dao.listAll();
        
        Assert.assertEquals(list.size(), 2);
        
        
    }
    
   
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
