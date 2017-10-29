package javaapplication14;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;

public class MainClass{
    Connection connect;
    ResultSet rs;
    PreparedStatement updateTableStatment;
    String updateTableQuery, id;
    JFrame frame;
    JScrollPane tablePane;
    JTable recordsTable;
    JPanel newRecordPanel, controlPanel;
    JLabel name_lbl, mobileno_lbl, email_lbl, id_search_lbl;
    JTextField name_txt, mobileno_txt, email_txt, id_search_txt;
    JButton save_btn, clear_btn, edit_btn, delete_btn, update_btn;
    GroupLayout newRecordPanelLayout, controlPanelLayout, layout;
    
    MainClass(){
        
        
        //New Record Panel Starts Here.
        newRecordPanel = new JPanel();
        newRecordPanelLayout = new GroupLayout(newRecordPanel);
        name_lbl = new JLabel("Name");
        name_txt = new JTextField(50);
        mobileno_lbl = new JLabel("Mobile No");
        mobileno_txt = new JTextField(50);
        email_lbl = new JLabel("Email");
        email_txt = new JTextField(50);
        save_btn = new JButton("Save");
        clear_btn = new JButton("Clear");
        update_btn = new JButton("Update");
        name_txt.setText("");
        mobileno_txt.setText("");
        email_txt.setText("");
        update_btn.setEnabled(false);
        newRecordPanel.setBorder(BorderFactory.createTitledBorder("New Record"));
        //newFormPanel's Layout Creation Starts Here.
        newRecordPanel.setLayout(newRecordPanelLayout);
        newRecordPanelLayout.setHorizontalGroup(newRecordPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(newRecordPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newRecordPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(newRecordPanelLayout.createSequentialGroup()
                        .addComponent(name_lbl, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(name_txt, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                    )
                    .addGroup(newRecordPanelLayout.createSequentialGroup()
                        .addComponent(mobileno_lbl, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(mobileno_txt, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                    )
                    .addGroup(newRecordPanelLayout.createSequentialGroup()
                        .addComponent(email_lbl, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(email_txt, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                    )
                    .addGroup(newRecordPanelLayout.createSequentialGroup()
                        .addComponent(update_btn)
                        .addGap(20)
                        .addComponent(save_btn)
                        .addGap(20)
                        .addComponent(clear_btn)
                    )
                )
                .addContainerGap())
        );
        newRecordPanelLayout.setVerticalGroup(newRecordPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(newRecordPanelLayout.createSequentialGroup()
                .addGap(20)
                .addGroup(newRecordPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(name_lbl)
                    .addComponent(name_txt)
                )
                .addGap(20)
                .addGroup(newRecordPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(mobileno_lbl)
                    .addComponent(mobileno_txt)
                )
                .addGap(20)
                .addGroup(newRecordPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(email_lbl)
                    .addComponent(email_txt)
                )
                .addGap(20)
                .addGroup(newRecordPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(update_btn)
                    .addComponent(save_btn)
                    .addComponent(clear_btn)
                )
                    .addGap(20)
            )
        );
        //newFormPanel's Layout Creation Completes Here.
        //Table Coding Start Here.
        tablePane = new JScrollPane();
        recordsTable = new JTable();
        tablePane.setViewportView(recordsTable);
        //Table Coding Ends Here.
        
        //ControlPanel Coding Start Here.
        controlPanel = new JPanel();
        edit_btn = new JButton();
        delete_btn = new JButton();
        id_search_lbl = new JLabel("Enter id for Operation");
        id_search_txt = new JTextField(10);
        edit_btn.setText("Edit");
        delete_btn.setText("Delete");
        //controlPanel's Layout Creation Starts Here.
        controlPanelLayout = new GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(id_search_lbl)
                .addGap(10)
                .addComponent(id_search_txt)
                .addGap(30)
                .addComponent(edit_btn)
                .addGap(20)
                .addComponent(delete_btn)
                .addContainerGap()
            )
        );
        controlPanelLayout.setVerticalGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(id_search_lbl)
                    .addComponent(id_search_txt)
                    .addComponent(edit_btn)
                    .addComponent(delete_btn)
                )
            )
        );
        //controlPanel's Layout Creation Completes Here.
        //ControlPanel Coding Ends Here.

        //MainWindow Coding Starts Here.
        frame = new JFrame("PhoneBook");
        //Main Windows Layout Creation Starts Here.
        layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(newRecordPanel)
                        .addGap(20)
                        .addComponent(tablePane)
                    )
                    .addComponent(controlPanel)
                )
                .addContainerGap()
            )
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(newRecordPanel)
                    .addComponent(tablePane)
                )
                .addGap(20)
                .addComponent(controlPanel)
                .addContainerGap()
            )
        );
        //Main Windows Layout Creation Completes Here.
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //MainWindow Coding Ends Here.
    }
    void Connection(){
        try{
            connect = DriverManager.getConnection("jdbc:mysql://localhost/phonebook","root","");
            System.out.println("Connected");
        }catch(SQLException ex){
            System.err.println("Not Connected");
        }
    }
    void events(){
        saveevent se = new saveevent();
        clearevent ce = new clearevent();
        editevent ee = new editevent();
        deleteevent de = new deleteevent();
        updateevent er = new updateevent();
        update_btn.addActionListener(er);
        save_btn.addActionListener(se);
        clear_btn.addActionListener(ce);
        edit_btn.addActionListener(ee);
        delete_btn.addActionListener(de);
    }
    void updateTable(){
        try{
            updateTableQuery = "SELECT * FROM `persons`";
            updateTableStatment = connect.prepareStatement(updateTableQuery);
            rs = updateTableStatment.executeQuery();
            recordsTable.setModel(DbUtils.resultSetToTableModel(rs));
            System.out.println("Table Updated");
        }catch(Exception ex){
            System.err.println("Table Update Failed");
        }
    }
    public static void main(String[] args) {
        MainClass object= new MainClass();
        object.Connection();
        object.updateTable();
        object.events();
        
    }
    class saveevent implements ActionListener{
        PreparedStatement saveDataStatement;
        String dataInsertionString;
        @Override
        public void actionPerformed(ActionEvent se) {
            try{
                dataInsertionString = "INSERT INTO `persons` (`name`,`mobileno`,`email`) VALUES(?,?,?)";
                saveDataStatement = connect.prepareStatement(dataInsertionString);
                saveDataStatement.setString(1, name_txt.getText());
                saveDataStatement.setString(2, mobileno_txt.getText());
                saveDataStatement.setString(3, email_txt.getText());
                saveDataStatement.execute();
                System.out.println("Saved");
                updateTable();
                clearevent ce = new clearevent();
                ce.clearForm();
            }catch(Exception ex){
                System.err.println("Insertion Failed");
            }
        }        
    }
    class clearevent implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ce) {
            clearForm();
            update_btn.setEnabled(false);
            save_btn.setEnabled(true);
        }
        public void clearForm(){
            name_txt.setText("");
            mobileno_txt.setText("");
            email_txt.setText("");
            id_search_txt.setText("");
        }
    }
    class editevent implements ActionListener{
        PreparedStatement fetchRecordStatement;
        String dataFetchString, idString;
        ResultSet rs;
        @Override
        public void actionPerformed(ActionEvent ee) {
            update_btn.setEnabled(true);
            try{
                dataFetchString = "SELECT `name`,`mobileno`,`email` FROM `persons` WHERE `id` = ?";
                fetchRecordStatement = connect.prepareStatement(dataFetchString);
                fetchRecordStatement.setString(1, id_search_txt.getText());
                rs = fetchRecordStatement.executeQuery();
                while(rs.next()){
                    name_txt.setText(rs.getString("name"));
                    mobileno_txt.setText(rs.getString("mobileno"));
                    email_txt.setText(rs.getString("email"));                
                }
                save_btn.setEnabled(false);
                System.out.println("Fetched");
            }catch(Exception ex){
                System.err.println("Fetch Failed");
            }
        }
    }
    class deleteevent implements ActionListener{
        PreparedStatement deleteRecordStatement;
        String dataDeleteString;
        @Override
        public void actionPerformed(ActionEvent de) {
            try{
                dataDeleteString = "DELETE FROM `persons` WHERE `id` = ?";
                deleteRecordStatement = connect.prepareStatement(dataDeleteString);
                deleteRecordStatement.setString(1, id_search_txt.getText());
                deleteRecordStatement.execute();
                
                if("".equals(id_search_txt.getText())){
                    System.err.println("Enter ID");
                }
                updateTable();
                id_search_txt.setText("");
            }catch(Exception ex){
                System.err.println("Deletion Failed");
            }
        } 
    }
    class updateevent implements ActionListener{
        PreparedStatement updateDataStatement;
        String dataUpdateString;
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                dataUpdateString = "UPDATE `persons` SET `name`=?,`mobileno`=?,`email`=? WHERE `id`= ?";
                updateDataStatement = connect.prepareStatement(dataUpdateString);
                updateDataStatement.setString(1, name_txt.getText());
                updateDataStatement.setString(2, mobileno_txt.getText());
                updateDataStatement.setString(3, email_txt.getText());
                updateDataStatement.setString(4, id_search_txt.getText());
                updateDataStatement.execute();
                updateTable();
                save_btn.setEnabled(true);
                update_btn.setEnabled(false);
            }catch(Exception ex){
                System.err.println("Updation Failed");
            }
            clearevent ce = new clearevent();
            ce.clearForm();
        }
    }
}