package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
 
public class Test {
 
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
        	System.out.println("w");
            Class.forName("org.h2.Driver");
            System.out.println("w");
            Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "" );
            System.out.println("w");
            Statement stmt = con.createStatement();
            //stmt.executeUpdate( "DROP TABLE table1" );
            stmt.executeUpdate( "CREATE TABLE table1 ( user varchar(50) )" );
            stmt.executeUpdate( "INSERT INTO table1 ( user ) VALUES ( 'Claudio' )" );
            stmt.executeUpdate( "INSERT INTO table1 ( user ) VALUES ( 'Bernasconi' )" );
 
            ResultSet rs = stmt.executeQuery("SELECT * FROM table1");
            
            while( rs.next() )
            {
                String name = rs.getString("user");
                System.out.println( name );
            }
            stmt.close();
            con.close();
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
        }
    }
}