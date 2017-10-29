/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.football.db;

import com.football.beans.League;
import com.football.beans.League;
import com.football.beans.League;
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
public class LeagueList {
    private ArrayList<League> leagueList = new ArrayList<League>();

    private ArrayList<League> getLeagues(String sql) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                League league = new League();
                league.setId(rs.getLong("id"));
                league.setName(rs.getString("name"));
                league.setFoundation(rs.getInt("foundation"));
                league.setImage(rs.getBytes("logo"));
                leagueList.add(league);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LeagueList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt!=null) stmt.close();
                if (rs!=null)rs.close();
                if (conn!=null)conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LeagueList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return leagueList;
    }

    public ArrayList<League> getLeagueList() {
        if (!leagueList.isEmpty()) {
            return leagueList;
        } else {
            return getLeagues("select * from league");
        }
    }
    
    public ArrayList<League> getLeagueById(int id){
        return getLeagues("select * from league where id="+id);
    }
}
