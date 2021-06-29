package PublicModule;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class IfBorrowBack {
    //查询指定读者是否借过指定图书未归还
    public static boolean findbook(String bookid,String readerid){
        String sql;
        sql = "select *from borrow where book_id=";
        sql = sql + bookid +"'and reader_id'" + readerid + "'and'";
        sql = sql + "if_back='否'";
        ResultSet rs = DbOp.executeQuery(sql);
        try{
            //如果指定读者借阅了指定图书,且未归还,返回true,否则返回false
            if ( rs.next() )
                return true;
            else
                return false;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "数据库查询失败!");
        }
        return true;
    }    
}
