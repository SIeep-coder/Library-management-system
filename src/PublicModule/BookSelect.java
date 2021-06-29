package PublicModule;
import java.sql.*;
import javax.swing.JOptionPane;
public class BookSelect {
	//按图书编号查询,查询结果保留在Book类的对象中
	public static Book SelectBookById(String id){
		String sql = "select * from book where id =" + id +"";
		ResultSet rs = DbOp.executeQuery(sql);
		Book book = null;
		try{
			if ( rs.next() ){
				book = new Book();
				book.setId(rs.getString("id"));
				book.setBookname(rs.getString("bookname"));
				book.setBooktype(rs.getString("booktype"));
				book.setAuthor(rs.getString("auther"));
				book.setTranslator(rs.getString("translator"));
				book.setPublish_time(rs.getDate("publish_time"));
				book.setPrice(rs.getInt("price"));
				book.setStock(rs.getInt("stock"));
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "无法正常读取数据库!");
		}
		return book;
	}
}
