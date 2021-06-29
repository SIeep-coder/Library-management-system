package MainPro;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import PublicModule.*;

public class Borrow extends Frame {
    private static final long serialVersionUID = -1036076990599464079L;
    String SepLine = "--------------------------------------------";
    Label lbbookid = new Label("图书编号");     Label lbreaderid = new Label("读者编号");
    TextField tf_bookid = new TextField();      TextField tf_readerid = new TextField();
    Button queryBtn = new Button("查询");

    Label lbbookinfo = new Label(SepLine+"图书信息"+SepLine);
    Label lbbookname = new Label("图书名称: ");     Label tf_bookname = new Label("xx");
    Label lbauthor = new Label("作者: ");           Label tf_author = new Label("xx");
    Label lbpublisher = new Label("出版社: ");      Label tf_publisher = new Label("xx");
    Label lbpublish_time = new Label("出版时间: "); Label tf_publish_time = new Label("xx");
    Label lbprice = new Label("定价:");             Label tf_price = new Label("xx");
    Label lbstock = new Label("库存: ");            Label tf_stock = new Label("xx");
    
    Label lbreaderinfo = new Label(SepLine+"读者信息"+SepLine);
    Label lbreadername = new Label("读者姓名: ");   Label tf_readername = new Label("xx");
    Label lbreadertype = new Label("读者类型");     Label tf_readertype = new Label("xx");
    Label lbmax_num = new Label("最大可借数: ");    Label tf_max_num = new Label("xx");
    Label lbdays_num = new Label("最大可借天数: "); Label tf_days_num = new Label("xx");

    Label lbborrowinfo = new Label(SepLine+"借阅信息"+SepLine);
    Label lbborrowednum = new Label("该读者已借图书数量: ");
    Label tf_borrowednum = new Label("xx");
    Label lbif_borrow = new Label("该读者是否可借所选图书: ");
    Label tf_if_borrow = new Label("xx");
    Label lbborrow_date = new Label("借阅日期: ");
    Label tf_borrow_date = new Label("xx");
    Button borrowBtn = new Button("借出");
    Button closeBtn = new Button("关闭");

    //构造方法
    public Borrow(){
        setLayout(null);
        setTitle("借阅图书");
        setSize(500,420);
        this.setForeground(Color.BLACK);
        
        lbbookid.setBounds(30, 40, 50, 25);
        tf_bookid.setBounds(90,40,90,20);

        lbreaderid.setBounds(200,40,50,25);
        tf_readerid.setBounds(260,40,90,20);

        queryBtn.setBounds(370,40,80,25);

        lbbookinfo.setBounds(30,70,440,25);		//图书信息提示条

        lbbookname.setBounds(30,100,60,25);
        tf_bookname.setBounds(90,100,200,25);

        lbauthor.setBounds(310,100,60,25);
        tf_author.setBounds(370,100,90,25);

        lbpublisher.setBounds(30,125,60,25);
        tf_publisher.setBounds(90,125,200,25);

        lbpublish_time.setBounds(310,125,60,25);
        tf_publish_time.setBounds(370,125,90,25);

        lbprice.setBounds(30,150,60,25);
        tf_price.setBounds(90,150,200,25);

        lbstock.setBounds(310,150,60,25);
        tf_stock.setBounds(370,150,90,25);

        lbreaderinfo.setBounds(30,180,440,25);		//读者信息提示条

        lbreadername.setBounds(30,205,60,25);
        tf_readername.setBounds(90,205,90,25);

        lbreadertype.setBounds(310,205,60,25);
        tf_readertype.setBounds(370,205,90,25);

        lbmax_num.setBounds(30,230,75,25);
        tf_max_num.setBounds(105,230,90,25);

        lbdays_num.setBounds(310,230,85,25);
        tf_days_num.setBounds(395,230,70,25);

        lbborrowinfo.setBounds(30,260,440,25);		//借阅信息提示条

        lbborrowednum.setBounds(30,285,120,25);
        tf_borrowednum.setBounds(150,285,50,25);

        lbif_borrow.setBounds(30,310,145,25);
        tf_if_borrow.setBounds(150,285,50,25);

        lbborrow_date.setBounds(30,335,60,25);
        tf_borrow_date.setBounds(90,335,100,25);

        borrowBtn.setBounds(160,365,80,25);
        borrowBtn.setEnabled(false);    //开始时禁用借出按钮

        closeBtn.setBounds(260,365,80,25);
        
        queryBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                btn_borrowActionPerformed(e);
            }
        });

        borrowBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                btn_borrowActionPerformed(e);
            }
        });

        closeBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setForeground(Color.BLACK);
                dispose();
            }
        });

        //关闭窗口
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                setForeground(Color.BLACK);
                dispose();
            }
        });

        add(lbbookid);      add(lbbookname);     add(lbreaderid);    add(lbreadername);    add(queryBtn);  
        add(lbbookinfo);    add(lbauthor);       add(lbpublisher);   add(lbpublish_time);  add(lbprice);
        add(lbstock);       add(lbreaderinfo);   add(lbreadertype);  add(lbmax_num);       add(lbdays_num);
        add(lbborrowinfo);  add(lbborrowednum);  add(lbif_borrow);   add(lbborrow_date);   add(borrowBtn);  add(closeBtn);  

        setLocationRelativeTo(null);

        setVisible(true);
        
        setForeground(Color.RED);

        add(tf_bookid);          add(tf_readerid);      add(tf_bookname);    add(tf_author);     
        add(tf_publisher);       add(tf_publish_time);  add(tf_price);       add(tf_stock);
        add(tf_readername);      add(tf_readertype);    add(tf_max_num);     add(tf_days_num);
        add(tf_borrowednum);     add(tf_if_borrow);     add(tf_borrow_date);
    }

    //图书和读者查询
    private void btn_querywActionPerformed(ActionEvent e){
        String bookid = tf_bookid.getText();
        String readerid = tf_readerid.getText();

        //如果图书编号或读者编号两者均为空,或者有一个为空,则返回
        if ( bookid.equals("") || readerid.equals("") ){
            JOptionPane.showMessageDialog(null, "图书编号和读者编号均不能为空!");
            init();     //重新初始化各参数并禁止借出按钮
            return;
        }

        //按编号查询图书,结果存在book对象里
        Book book = BookSelect.SelectBookById(bookid);

        if ( book != null ){
            tf_bookname.setText(book.getBookname());
            tf_author.setText(book.getAuthor());
            tf_publisher.setText(book.getPublisher());
            tf_publish_time.setText(String.valueOf(book.getPublish_time()));
            tf_price.setText(String.valueOf(book.getPrice()));
            tf_stock.setText(String.valueOf(book.getStock()));
        }else{
            JOptionPane.showMessageDialog(null, "图书编号有误,查无此书!");
            init();
            return;
        }
        if ( book.getStock() == 0 ){
            JOptionPane.showMessageDialog(null, "图书以无库存,无法借阅!");
            init();
            return;
        }


        //按编号查询读者,结果存在reader对象里
        Reader reader = ReaderSelect.SelectReaderById(readerid);

        //如果查询到结果,将其显示在各文本框
        if ( reader != null ){
            tf_readername.setText(reader.getReadername());
            tf_readertype.setText(reader.getReadertype());
            tf_max_num.setText(String.valueOf(reader.getMax_num()));
            tf_days_num.setText(String.valueOf(reader.getDay_num()));
        }else{
            JOptionPane.showMessageDialog(null, "读者编号有误,查无此人!");
            init();
            return;
        }

        //查询指定读者是否已借过指定图书且未归还
        if ( IfBorrowBack.findbook(bookid,readerid) ){
            JOptionPane.showMessageDialog(null, "该读者已借阅该图书,且未归还!");
            init();
            return;
        }

        //统计读者所借图书数量
        int borrowednum = statborrowednum(readerid);
        tf_borrowednum.setText(String.valueOf(borrowednum));
        //如果读者已借图书尚未超出其允许的最大借书量,则允许其继续借阅所选图书
        if ( borrowednum<reader.getMax_num() ){
            tf_if_borrow.setText("是");

            //创建一个简单日期格式对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            //创建一个日期变量
            Date currentdate = new Date();

            //将日期按指定格式输出
            String borrowdate = sdf.format(currentdate);
            tf_borrow_date.setText(borrowdate);

            borrowBtn.setEnabled(true);     //使按钮有效
        }else{
            JOptionPane.showMessageDialog(null, "该读者已借阅图书过多,无法继续借阅!");
            init();
            return;
        }
     } 

        //填写借出图书记录
        private void btn_borrowActionPerformed(ActionEvent e) {
            String sql;
            String bookid = tf_bookid.getText();
            String readerid = tf_readerid.getText();
            String borrowdate = tf_borrow_date.getText();
            
            //为borrow表添加借书记录
            sql = "insert into borrow(book_id,reader_id,"+
                  "borrow_date,if_back) values('"+bookid+"',"+
                  "'"+readerid+"','"+borrowdate+"','否')";
            DbOp.executeUpdate(sql);

            //将该读者所借书数量加1
            int iborrowednum = Integer.parseInt(tf_borrowednum.getText())+1;
            String cstock = String.valueOf(iborrowednum);
            //更新画面中的图书库存数量
            tf_borrowednum.setText(cstock);
            //更新数据库中的图书库存数量
            sql = "update book set stock='"+cstock+"',where id='"+bookid+"'";
            DbOp.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "借书成功!");
            init();
        }
        
        //统计某个读者当前已借阅图书且未归还的数量
        private int statborrowednum(String readerid){
            int borrowednum = 0;
            String reader_id,if_back;
            //读取数据库中记录
            String sql = "select * from borrow";
            ResultSet rs = DbOp.executeQuery(sql);
            //执行查询统计操作
            try{
                while(rs.next()){
                    reader_id = rs.getString("reader_id");
                    if_back = rs.getString("if_back");
                    if ( reader_id.equals(readerid)&&if_back.equals("否") ){
                        borrowednum++;
                    }
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "数据库统计失败!");
            }
            return borrowednum;
        }
        
        //初始化各参数项并禁止借出按钮
        private void init(){
            tf_bookname.setText("xx");      tf_author.setText("xx");        tf_publisher.setText("xx");
            tf_publish_time.setText("xx");  tf_price.setText("xx");         tf_stock.setText("xx");
            tf_readername.setText("xx");    tf_readertype.setText("xx");    tf_max_num.setText("xx");
            tf_days_num.setText("xx");      tf_borrowednum.setText("xx");   tf_borrow_date.setText("xx");
            tf_if_borrow.setText("xx");
            borrowBtn.setEnabled(false);        //禁止借出按钮
        }
}

