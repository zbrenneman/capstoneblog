/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.capstoneblog.dao;

import com.mycompany.capstoneblog.dto.BlogPost;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */
public class BlogPostDaoImpl implements BlogPostDao {

    private static final String SQL_INSERT_BLOGPOST = "INSERT INTO blog_post(`title`, `body`, `approval_status`, `author_id`, `publish_date`, `expiration_date`) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_BLOGPOST = "DELETE FROM blog_post WHERE id = ?";
    private static final String SQL_SELECT_BLOGPOST = "SELECT * FROM blog_post WHERE id= ?";
    private static final String SQL_UPDATE_BLOGPOST = "UPDATE `blog_post` SET title = ?, body = ?, approval_status = ?, author_id = ?, publish_date = ?, expiration_date = ? WHERE id = ?";
    private static final String SQL_SELECT_ALL_BLOGPOSTS = "SELECT * FROM blog_post";

    JdbcTemplate jdbcTemplate;

    public BlogPostDaoImpl(JdbcTemplate j) {
        this.jdbcTemplate = j;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BlogPost create(BlogPost blogPost) {

        jdbcTemplate.update(SQL_INSERT_BLOGPOST,
                blogPost.getTitle(),
                blogPost.getBody(),
                blogPost.getApprovalStatus(),
                blogPost.getAuthorId(),
                blogPost.getPublishDate(),
                blogPost.getExpirationDate()
        );

        Integer blogId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        blogPost.setId(blogId);

        return blogPost;
    }

    @Override
    public void delete(BlogPost blogPost) {

        jdbcTemplate.update(SQL_DELETE_BLOGPOST, blogPost.getId());

    }

    @Override
    public void update(BlogPost blogPost) {

        jdbcTemplate.update(SQL_UPDATE_BLOGPOST,
                blogPost.getTitle(),
                blogPost.getBody(),
                blogPost.getApprovalStatus(),
                blogPost.getAuthorId(),
                blogPost.getPublishDate(),
                blogPost.getExpirationDate(),
                blogPost.getId());

    }

    @Override
    public BlogPost read(Integer id) {

        BlogPost b = jdbcTemplate.queryForObject(SQL_SELECT_BLOGPOST, new BlogPostMapper(), id);

        return b;
    }

    @Override
    public List<BlogPost> listAll() {

        List<BlogPost> blogPosts = jdbcTemplate.query(SQL_SELECT_ALL_BLOGPOSTS, new BlogPostMapper());

        return blogPosts;

    }

    private static final class BlogPostMapper implements RowMapper<BlogPost> {

        @Override
        public BlogPost mapRow(ResultSet rs, int i) throws SQLException {

            BlogPost blogPost = new BlogPost();
            blogPost.setId(rs.getInt("id"));
            blogPost.setTitle(rs.getString("title"));
            blogPost.setBody(rs.getString("body"));
            blogPost.setApprovalStatus(rs.getInt("approval_status"));
            blogPost.setAuthorId(rs.getInt("author_id"));
            blogPost.setPublishDate(rs.getDate("publish_date"));
            blogPost.setExpirationDate(rs.getDate("expiration_date"));

            return blogPost;

        }

    }

}
