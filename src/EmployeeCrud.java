import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmployeeCrud {
    private JPanel Main;
    private JTextField textName;
    private JTable table1;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField textSearch;
    private JTextField textSalary;
    private JTextField textPhone;
    private  JScrollPane jScrollPane;

    public static void main(String[] args) {
        JFrame frame = new JFrame("EmployeeCrud");
        frame.setContentPane(new EmployeeCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public EmployeeCrud() {
        Connect();
        table_data();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name,salary,mobile;

                name=textName.getText();
                salary=textSalary.getText();
                mobile=textPhone.getText();

               try {
                   pst=con.prepareStatement("insert into company(pname,salary,mobile)values(?,?,?)");

                   pst.setString(1,name);
                   pst.setString(2,salary);
                   pst.setString(3,mobile);

                   pst.executeUpdate();
                   JOptionPane.showMessageDialog(null,"Save Data");

                   table_data();

                   textName.setText("");
                   textSalary.setText("");
                   textPhone.setText("");
                   textName.requestFocus();


               }



               catch (SQLException throwables) {
                   throwables.printStackTrace();
               }


            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               String name,salary,mobile,mid;

                name=textName.getText();
                salary=textSalary.getText();
                mobile=textPhone.getText();
                mid=textSearch.getText();

                try {
                    pst=con.prepareStatement("update company set pname=?,salary=?,mobile=? where id=?");
                    pst.setString(1,name);
                    pst.setString(2,salary);
                    pst.setString(3,mobile);
                    pst.setString(4,mid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Save Data");
                    table_data();

                    textName.setText("");
                    textSalary.setText("");
                    textPhone.setText("");
                    textName.requestFocus();


                }



                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String sid;

                sid=textSearch.getText();

                try {
                    pst=con.prepareStatement("select pname,salary,mobile from company where id=?");
                    pst.setString(1,sid);
                    ResultSet rs=pst.executeQuery();

                    if (rs.next()==true){

                        String name=rs.getString(1);
                        String salary=rs.getString(2);
                        String mobile=rs.getString(3);

                        textName.setText(name);
                        textSalary.setText(salary);
                        textPhone.setText(mobile);

                        table_data();




                    }else {

                        textName.setText("");
                        textSalary.setText("");
                        textPhone.setText("");


                        JOptionPane.showMessageDialog(null,"Invalid your Product Id");

                    }



                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String did;
                did=textSearch.getText();

                try {

                    pst=con.prepareStatement("delete from company where id=?");
                    pst.setString(1,did);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Record Is deleted");
                    table_data();

                    textName.setText("");
                    textSalary.setText("");
                    textPhone.setText("");
                    textSearch.requestFocus();



                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        });
    }

    void  table_data(){

        try {
            pst=con.prepareStatement("select * from company");
            ResultSet resultSet=pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void Connect(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/fdcompany","root","");
            System.out.println("Alhamdulillah Connection is Success");

        }catch (ClassNotFoundException ex){

            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }


}
