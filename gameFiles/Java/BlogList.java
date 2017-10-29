/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.football.db;

import com.football.beans.Blog;
import com.football.connection.ConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Madiyar
 */
public class BlogList {
    private ArrayList <Blog> blogList = new ArrayList<Blog>();
    
    private ArrayList<Blog> getBlog(String sql){
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Blog blog = new Blog();
                blog.setId(rs.getLong("id"));
                blog.setTitle(rs.getString("title"));
                blog.setDescription(rs.getString("description"));
                String s = new String(rs.getBytes("content"));
                blog.setContent(s);
                blog.setLeagueName(rs.getString("league"));
                blog.setTeamName(rs.getString("team"));
                blog.setImage(rs.getBytes("image"));
                blogList.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return blogList;
    }
    
    public ArrayList<Blog>getAllBlog(){
        return getBlog("SELECT blog.id,title,description,content,image,team.name as team,league.name as league FROM blog "+
                "inner join team on team.id=blog.team_id "+
                "inner join league on league.id = blog.league_id;");
    } 
    public ArrayList<Blog>getBlogbyId(int id){
        return getBlog("SELECT blog.id,title,description,content,image,team.name as team,league.name as league FROM blog "+
                "inner join team on team.id=blog.team_id "+
                "inner join league on league.id = blog.league_id where blog.league_id="+id);
    } 
}
