package MainPro;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import PublicModule.*;

public class ReaderUpdate extends Frame {
    private static final long serialVersionUID = -4657058729583467505L;
    Label lbreaderid_1 = new Label("读者编号");             Label lbreaderid = new Label("读者编号");
    Label lbreadername = new Label("读者姓名");             Label lbreadertype = new Label("读者类型");   
    Label lbsex = new Label("性别");                        Label lbmax_num = new Label("可借数量");
    Label lbdays_num = new Label("可借天数");               TextField tf_readerid1 = new TextField();
    TextField tf_readerid = new TextField();   TextField tf_readername = new TextField();
    TextField tf_max_num = new TextField();    TextField tf_days_num = new TextField();
    Choice tf_readertype = new Choice();       Choice tf_sex = new Choice();
    Button queryBtn = new Button("查询");       Button closeBtn = new Button("关闭");
    Button saveBtn = new Button("保存");

    public ReaderUpdate(){
        setLayout(null);
        setTitle("修改读者信息");
        setSize(500,240);

        lbreaderid_1.setBounds(100, 40, 50, 20);
        tf_readerid1.setBounds(160, 40, 100, 20);

        queryBtn.setBounds(290, 40, 80, 20);
        queryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                btn_queryActionPerformed(e);
            }
        });

        lbreaderid.setBounds(50, 80, 50, 20);
        tf_readerid.setBounds(110, 80, 100, 20);
        tf_readerid.setEnabled(false);
        
        lbreadername.setBounds(240, 80, 50, 20);;
        tf_readername.setBounds(300, 80, 100, 20);

        lbreadertype.setBounds(50, 110, 50, 20);
        tf_readertype.setBounds(110, 110, 100, 20);
        tf_readertype.add("教师");
        tf_readertype.add("学生");
        tf_readertype.add("职工");

        lbsex.setBounds(50, 140, 50, 20);
        tf_sex.setBounds(300, 110, 100, 20);
        tf_sex.add("男");
        tf_sex.add("女");

        lbmax_num.setBounds(50, 140, 50, 20);
        tf_max_num.setBounds(110, 140, 100, 20);

        lbdays_num.setBounds(240, 140, 50, 20);
        tf_days_num.setBounds(300, 140, 100, 20);

        saveBtn.setBounds(150, 180, 80, 25);
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                btn_saveActionPerformed(e);
            }
        });

        closeBtn.setBounds(280, 180, 80, 25);
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

                //关闭窗口
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                dispose();
            }
        });

        add(lbreaderid_1);  add(lbreaderid);    add(lbreadername);  add(lbreadertype);
        add(lbsex);         add(lbmax_num);     add(lbdays_num);    add(tf_readerid1);
        add(tf_readerid);   add(tf_readername); add(tf_readertype); add(tf_max_num);
        add(tf_days_num);   add(saveBtn);       add(closeBtn);      add(queryBtn);

        setLayout(null);
        setVisible(true);

    }


    private void btn_queryActionPerformed(ActionEvent e){
        String id = tf_readerid1.getText();
        if( id.equals("") ){
            JOptionPane.showMessageDialog(null, "读者编号不能为空!");
            return;
        }

        Reader reader = ReaderSelect.SelectReaderById(id);
        if ( reader != null ){
            tf_readerid.setText(reader.getId());
            tf_readername.setText(reader.getReadername());
            tf_readertype.select(reader.getReadertype());
            tf_sex.select(reader.getSex());
            tf_days_num.setText(String.valueOf(reader.getDay_num()));
            tf_max_num.setText(String.valueOf(reader.getMax_num()));
        }else
            JOptionPane.showMessageDialog(null, "读者编号有误,查无此人!");
    }

    private void btn_saveActionPerformed(ActionEvent e){
        String id = tf_readerid.getText();
        String readername = tf_readername.getText();
        String readertype = tf_readertype.getSelectedItem().toString();

        if( id.equals("") ){
            JOptionPane.showMessageDialog(null, "读者编号不能为空!");
            return;
        }
        try{
            int max_num = Integer.parseInt(tf_max_num.getText());
            int days_num = Integer.parseInt(tf_days_num.getText());
            String sex = tf_sex.getSelectedItem().toString();
            String sql = "update reader set readername='"+readername+"',readertype='"+readertype
                         +"',days_num='"+days_num+"',sex='"+sex+"',max_num='"+max_num+"' where id='"+id+"'";
            int i = DbOp.executeUpdate(sql);
            if (i==1){
                JOptionPane.showMessageDialog(null,"读者信息修改成功!");
                clearAllTextfield();
            }else
                JOptionPane.showMessageDialog(null, "读者信息修改失败!");
        }catch(NumberFormatException e1){
            JOptionPane.showMessageDialog(null,"最大可借数或最大可借天数错误,应为整数!");
        }
    }

    private void clearAllTextfield(){
        tf_readerid1.setText("");      tf_readerid.setText("");    tf_readername.setText("");
        tf_max_num.setText("");         tf_days_num.setText("");
    }
}
