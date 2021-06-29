package MainPro;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import PublicModule.*;

public class UpdatePassword extends Frame {
    private static final long serialVersionUID = -6540483851542957663L;
    Label newPassword = new Label("请输入新密码!");
    Label confirmPass = new Label("再次确认密码!");
    TextField text_1 = new TextField();
    TextField text_2 = new TextField();
    Button confirmBtn = new Button("确定");
    Button cancelBtn = new Button("取消");

    public UpdatePassword(){
        setLayout(null);    setTitle("修改密码");   setSize(300,170);
        newPassword.setBounds(30, 50, 90, 30);  text_1.setBounds(125, 53, 120, 20);
        confirmPass.setBounds(30, 80, 90, 30);  text_2.setBounds(125, 83, 1120, 20);
       
        confirmBtn.setBounds(70, 120,70, 25);
        confirmBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                confirmButtonPerformed(e);
            }
        });


        cancelBtn.setBounds(160, 120, 70, 25);
        cancelBtn.addActionListener(new ActionListener() {
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

        add(newPassword);   add(confirmPass);   add(text_1);    add(text_2);
        add(cancelBtn);     add(confirmBtn);
        
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void confirmButtonPerformed(ActionEvent e){
        System.out.println(GlobalVar.login_user);
        String pass1 = text_1.getText();
        String pass2 = text_2.getText();

        if ( pass1.equals("") || pass2.equals("") ){
            JOptionPane.showMessageDialog(null, "密码不能为空,请重新输入!");
            return;
        }

        if ( !pass1.equals(pass2) ){
            JOptionPane.showMessageDialog(null, "两次密码不一致,请重新输入!");
            text_1.setText(""); text_2.setText("");
            return;
        }

        String sql = "update user set password='"+pass1+"'where username='"+GlobalVar.login_user+"'";
        int i = DbOp.executeUpdate(sql);
        if ( i ==1 ){
            JOptionPane.showMessageDialog(null, "修改密码成功!");
        }else
            JOptionPane.showMessageDialog(null, "用户数据库有误或不存在,修改密码失败!");

    }
}
