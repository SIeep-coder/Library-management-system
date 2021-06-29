package MainPro;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import PublicModule.*;

public class BookAdd extends Frame {
    private static final long serialVersionUID = 377287301994613384L;
    Label lbbookid = new Label("图书编号");     Label lbbookname = new Label("图书名称");
    Label lbbooktype = new Label("图书类型");   Label lbauthor = new Label("作者");
    Label lbtranslator = new Label("译者");     Label lbpublisher = new Label("出版社");
    Label lbpublish_time= new Label("出版时间");Label lbprice = new Label("定价");
    Label lbstock = new Label("库存");          Button saveBtn = new Button("保存");
    Button closeBtn = new Button("关闭");       TextField tf_bookid = new TextField();
    TextField tf_bookname = new TextField();    TextField tf_author = new TextField();
    TextField tf_translator = new TextField();  TextField tf_price = new TextField();
    TextField tf_publisher = new TextField();   TextField tf_publish_time = new TextField();
    TextField tf_stock = new TextField();       Choice tf_booktype = new Choice();

    BookAdd() {
        setLayout(null);
        setTitle("增加图书");
        setSize(500,250);

        lbbookid.setBounds(50,50,50,20);
        tf_bookid.setBounds(110,50,100,20);

        lbbookname.setBounds(240,50,20,20);
        tf_bookname.setBounds(300,50,100,20);

        lbbooktype.setBounds(50,80,50,20);
        tf_booktype.setBounds(110,80,100,20);
        tf_booktype.add("科技");
        tf_booktype.add("文学");
        tf_booktype.add("社科");
        tf_booktype.add("其他");

        lbauthor.setBounds(240,80,50,20);
        tf_author.setBounds(300,110,100,20);


        lbtranslator.setBounds(50,110,50,20);
        tf_translator.setBounds(110,110,100,20);

        lbpublisher.setBounds(240,110,50,20);
        tf_publisher.setBounds(300,110,100,20);

        lbpublish_time.setBounds(50,140,50,20);
        tf_publish_time.setBounds(110,140,100,20);

        lbprice.setBounds(240,140,50,20);
        tf_price.setBounds(300,140,100,20);

        lbstock.setBounds(50,170,50,20);
        tf_stock.setBounds(110,170,100,20);

        saveBtn.setBounds(150,210,80,25);
        saveBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                btn_saveActionPerformed(e);
            }
        });

        closeBtn.setBounds(280,210,80,25);
        closeBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

        //关闭窗口
        this.addWindowListener(new WindowAdapter(){
            //重写windowClosing()方法
            public void windowClosing(WindowEvent e){   //关闭当前窗口
                dispose();          //释放当前窗体
            }
        });

        add(lbbookid);      add(lbbookname);    add(lbbooktype);        add(lbauthor);
        add(lbtranslator);  add(lbpublisher);   add(lbpublish_time);    add(lbprice);
        add(lbstock);       add(saveBtn);       add(closeBtn);          add(tf_bookid);
        add(tf_bookname);   add(tf_booktype);   add(tf_author);         add(tf_translator);
        add(tf_price);      add(tf_publisher);  add(tf_publish_time);    add(tf_stock);

        setLocationRelativeTo(null);
        setVisible(true);//使窗体可见
    }

    //保存记录
    private void btn_saveActionPerformed(ActionEvent e){
        String id = tf_bookid.getText();
        String bookname = tf_bookname.getText();
        String booktype = tf_booktype.getSelectedItem().toString();
        String author = tf_author.getText();
        String translator = tf_translator.getText();
        String publisher = tf_publisher.getText();
        String publish_time = tf_publish_time.getText();
        
        //如果图书编号为空,则终止保存记录
        if ( id.equals("") ){
            JOptionPane.showMessageDialog(null, "图书编号不能为空! ");
            return;
        }

        //如果图书编号重复,则记录无效,需修改图书编号
        if ( IfbookIdExit(id) ){
            JOptionPane.showMessageDialog(null, "图书编号重复! ");
            return;
        }
        try{
            //----------------------------------------------
            //以下程序用于检查日期是否有效
            //如果日期无效,则会产生ParseException异常
            //创建一个简单的日期格式对象.  MM一定是大写
            //用户输入日期格式: 年-月,如2020-12,2021-1等
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            //将字符串转换为日期.如果日期无效,将抛出PaeseException异常
            //本语句用来判断日期格式是否有效
            sdf.parse(tf_publish_time.getText());

            float price = Float.parseFloat(tf_price.getText());
            int stock = Integer.parseInt(tf_stock.getText());

            //将记录保存到book表中
            String sql = "insert into book(id,bookname,booktype,"
                          +" auther,translator," 
                          +"publisher,publish_time,price,stock)+ values('"+id
                          +"','"+bookname+"','"+booktype+"','"+author
                          +"','"+translator+"','"+publisher+"','"+publish_time
                          +"','"+price+"','"+stock+"')";
            int i = DbOp.executeUpdate(sql);
            if ( i == 1 ) {
                JOptionPane.showMessageDialog(null, "图书添加成功! ");
                //清空全部文本框
                clearAllTextfield();
            }
        }catch(ParseException e2) {
            JOptionPane.showMessageDialog(null, "出版时间格式错误(年-月)! ");
        }catch(NumberFormatException e1){
            JOptionPane.showMessageDialog(null, "库存数量和价格错误,应为数字! ");
        }
    }

    //判断Book表中是否存在指定编号的图书,如果存在,返回true,否则,返回false.
    private boolean IfbookIdExit(String id) {
        String sql = "select * from book where id ='"+ id +"'";
        ResultSet rs = DbOp.executeQuery(sql);
        try{
            if ( rs.next() )
                return true;
            else
                return false;
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "无法正常读取数据库! ");
        }
        return false;
    }

    //清空全部文本框
    private void clearAllTextfield(){
        tf_bookid.setText("");       tf_bookname.setText("");    tf_author.setText("");  tf_translator.setText("");
        tf_publisher.setText("");    tf_publisher.setText("");   tf_price.setText("");   tf_stock.setText("");
    }

}