package MainPro;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JOptionPane;
import PublicModule.*;

public class Login extends Frame {
    private static final long serialVersionUID = -1758475247807861408L;
    TextField text_user;
    TextField text_pass;
    public Login(){
        this.setTitle("登录");
        this.setLayout(null);
        this.setSize(260,170);
        //增加标签和文本框
        Label lbUser = new Label("用户名: ");
        Label lbPass = new Label("密 码: ");
        Button btn_ok = new Button("确定");
        Button btn_cancel = new Button("取消");
        text_pass = new TextField();
        text_user = new TextField();
        lbUser.setBounds(40,53,60,20);
        lbPass.setBounds(40,83,60,20);
        text_user.setBounds(100,50,120,20);
        text_pass.setBounds(100,80,120,20);

        btn_ok.setBounds(45,120,80,25);     //确定按钮
        btn_ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                btn_okActionPerformed(e);
            }   
        });

        btn_cancel.setBounds(135, 120, 80, 25);     //取消按钮
        btn_cancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                DbOp.Close();   //关闭数据库
                System.exit(0);
            }
        });

        //关闭窗口
        this.addWindowListener(new WindowAdapter(){
            //重写windowClose()方法
            public void windowClosing(WindowEvent e){
                DbOp.Close();
                System.exit(0);
            }
        });

        add(lbUser);    add(lbPass);    add(text_user);
        add(text_pass); add(btn_ok);    add(btn_cancel);

        setLocationRelativeTo(null);    //使窗体在屏幕中央居中放置
        this.setVisible(true);  //使窗体可见
    } 

    public void btn_okActionPerformed(ActionEvent e){
        String user = text_user.getText();
        String pass = text_pass.getText();
        String is_admin;

        //如果用户名或密码任一为空,则终止后续操作
        if ( user.equals("")||pass.equals("") ){
            JOptionPane.showMessageDialog(null, "用户名或密码不能为空!");
            return;
            }
        try {
            //核对用户名密码
            String sql = "select *from user where username=" + "" + user + "and password=" + "" + pass +"";
            ResultSet rs =DbOp.executeQuery(sql);
            //如果此用户存在,则记录其状态 (否: 不是管理员, 是: 是管理员)
            if(rs.next()){
                is_admin = rs.getString("is_admin");
            }else{
                JOptionPane.showMessageDialog(null, "用户名或密码不正确!");
                return;
            }

            GlobalVar.login_user = user;    //记录登录用户名
            ShowMain show = new ShowMain();     //调用主程序

            //只有管理员才能使用"基础管理"和"借阅管理"菜单
            show.setRights(is_admin);

            //释放窗体及其全部组件的屏幕资源,即使释放登录窗体
            dispose();      //释放当前窗体
        }catch(SQLException el){
            JOptionPane.showMessageDialog(null, "用户数据库有误!");
        }
    }
    public static void main(String[] args){
        new Login();
    }
}
