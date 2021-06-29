package PublicModule;
import java.sql.*;
import javax.swing.JOptionPane;
public class DbOp {

      //JDBC-ODBC驱动程序
      private static String driver = "sun.jdbc.odbc.jdbcOdbcDriver";

      //数据库url路径
      private static String url = "jdbc:odbc:bookdb";
      private static Connection con = null;

      //构造方法.如果数据库未打开,则通过创建连接打开数据库
      private DbOp(){
            try{
                  if(con == null){
                        Class.forName(driver);
                        con = DriverManager.getConnection(url);
                  }
            }catch(Exception e){
                  JOptionPane.showMessageDialog(null, "数据库未能打开!");
            }

      }

      //执行数据库查询工作.如果出现异常,返回null
      public static ResultSet executeQuery(String sql){
            try{
                  //如果未创建数据库连接,则创建连接
                  if (con == null){
                        new DbOp();
                  //返回查询结果
                        return con.createStatement().executeQuery(sql);
                  }
            }catch(SQLException e){
                  JOptionPane.showMessageDialog(null, "数据库不存在,或存在错误!");
                  return null;
            }
			return null;
      }

      //执行数据库更新操作.如果有问题,则返回-1
      public static int executeUpdate(String sql){
            try{
                  //如果未创建数据库连接,则创建连接
                  if (con == null){
                        new DbOp();
                  //返回操作结果
                  return con.createStatement().executeUpdate(sql);
                  }
            }catch(SQLException e){
                  JOptionPane.showMessageDialog(null, "数据有误,记录无法正常保存或更新!");
            }
            return 0;
      }


      //关闭数据库
      public static void Close(){
            try{
                  //如果数据库已打开,则关闭
                  if ( con != null )
                        con.close();
            }catch(SQLException e){
                  JOptionPane.showMessageDialog(null, "数据库未打开!");
            }
      }
}
