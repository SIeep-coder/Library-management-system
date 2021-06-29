package MainPro;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import PublicModule.*;

public class ReaderQuery extends Frame {
    private static final long serialVersionUID = -7717113202745852409L;
    JTable table;   
    JScrollPane scrollPane;
   
    Label lbreadername = new Label("读者姓名");		Label lbreadertype = new Label("读者类型");       
    TextField tf_readername = new TextField();      TextField tf_readertype = new TextField();
    Button queryBtn = new Button("查询");			Button closeBtn = new Button("关闭");
    
    String[] heads =  {"读者编号","读者姓名","读者类别","读者性别","最大可借书数","可借天数"}; 
    
    //构造方式
    public ReaderQuery(){
        setTitle("读者查询");
        setSize(600,500);
        setLayout(null);    //取消窗口布局

        lbreadername.setBounds(70, 50, 50, 20);
        tf_readername.setBounds(130, 50, 160, 20);

        lbreadertype.setBounds(310, 50, 50, 20);
        tf_readertype.setBounds(370, 50, 160, 20);

        queryBtn.setBounds(200, 80, 80, 25);
        queryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btn_queryActionPerformed(e);
            }
        });

        closeBtn.setBounds(320, 80, 80, 25);
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

        //关闭窗口
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                dispose();
            }
        });


        add(lbreadername);      add(tf_readername);      add(lbreadertype);        add(tf_readertype);
        add(queryBtn);          add(closeBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void btn_queryActionPerformed(ActionEvent e) {
        try{
            String readername,readertype;
            String sql,sql1,sql2,sql3;

            readername = tf_readername.getText();
            readertype = tf_readertype.getText();

            //创建一条基本的sql语句,表示选出表中全部记录
            sql = "select * from reader";

            //如果读者姓名不空,生成sql1字句
            if ( readername.equals("") )
                sql1 = "";
            else
                sql1 = "readername like '"+readername+"%'";

            //如果作者不空,生成sql2语句
            if ( readertype.equals("") ) 
                sql2 = "";
            else{
                sql2 = "readertype like '"+readertype+"%'";
                if( !readername.equals("") )    //如果书名不为空
                    sql2 = "and"+sql2;
            }
            
            sql3 = sql1 + sql2;

            //如果已设置任意一项条件,则修改sql语句
            if ( !sql3.equals("") ){
                sql = sql + "where" + sql3;
            }

            //执行查询
            ResultSet rs = DbOp.executeQuery(sql);

            //创建一个对象二位数组
            Object[][] readerq = new Object[30][heads.length];

            int i = 0;
            while(rs.next()) {          //将查询变量赋予Book数组
                readerq[i][0] = rs.getString("id");
                readerq[i][1] = rs.getString("readername");
                readerq[i][2] = rs.getString("readertype");
                readerq[i][3] = rs.getString("sex");
                readerq[i][4] = rs.getString("max_num");
                readerq[i][5] = rs.getString("days_num");
                i++;
            }

            table = new JTable(readerq,heads);  //创建一个组件
            //创建一个显示表格的JScrollPane
            scrollPane = new JScrollPane(table);
            //设置JScrollPane的位置和尺寸
            scrollPane.setBounds(20, 120, 560, 300);
            //将JScrollPane添加到窗体中
            add(scrollPane);
        }catch(SQLException e1) {
            JOptionPane.showMessageDialog(null, "数据库不存在,或存在错误!");
        }
    }
    
    
    
}