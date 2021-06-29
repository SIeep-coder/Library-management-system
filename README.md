
# 一.系统详细设计
 ## 1.开发环境
    操作系统: Win10
    编程语言: Java 11.0.9
    数据库系统: Mysql
    开发工具: VS Code,Myeclipse8.5

## 2.数据库(Bookdb)设计
                            图书信息表(book)
    列名        数据类型    not NULL      索引      默认值      说明
     ID         文本(8)        .          .       AA000001    图书编号
   bookname    文本(100)                                      图书名称
   booktype     文本(50)                            科技      图书类别
    auther      文本(50)                                      图书作者
  translotor    文本(50)                                       译者
   publisher   文本(100)                                      出版社
  publish_time   date                                        出版时间
    price        float                              28        定价
    stock        int                                1        库存数量

                            读者信息表(reader)
    列名        数据类型    not NULL      索引      默认值      说明
     ID         文本(8)        .          .       AA000001    读者编号
  readername    文本(50)                                      读者姓名
   readtype     文本(20)                                      读者类型
     sex        文本(2)                                       读者性别
   max_num       int                                         最大可借数
   days_num      int                                          可借天数

                            借阅信息表(borrow)
     列名        数据类型    not NULL      索引      默认值      说明
     ID         lang int        .          .       自动编号    借阅流水号
    book_id     文本(8)                                        图书编号
   reader_id    文本(8)                                        读者编号
  borrow_date   date                                           借阅时间
   back_date    date                                           还书时间
   if_back      文本(2)                                        是否归还

                            用户信息表(user)
    列名        数据类型    not NULL      索引      默认值      说明
     ID         lang int        .          .       自动编号    用户流水号
   username     文本(50)                                       用户姓名
   password     文本(50)                                       用户密码
   is_admin     文本(2)                                      是否为管理员


## 3.系统模块设计

    设计MainPro和PublicMoudule两个包
     (1)MainPro包:主要包括登陆程序,系统主程序,图书和读者信息维护程序,
     图书借阅管理程序,以及图书和读者信息查询程序等等;

                        MainPro包中的类模块
    类名            功能描述                    设计要点
Login.java       用户登录模块               要将用户登录名和密码与用户信息表内容对比,如果
                                        正确,则进入系统主操作画面,否则提示错误信息.

ShowMain.java      系统主画面                主要是菜单设计,并通过各子菜单增加事件侦听器
                                        以调用其他功能

UpdatePassword.java   修改用户密码           修改密码,并将修改结果保存到用户信息表

BookAdd.java       录入图书信息             保存记录时要检查数据的有效性:
                                        1.图书编号必须唯一;2.出版日期格式必须正确,有效;
                                        3.定价,库存数量必须为有效数字

BookUpdate.java     修改图书信息             按图书编号查询记录,然后修改图书的其余信息.
                                        应确保出版时间,定价,库存数量等数据的有效性.

BookDelete.java     删除图书信息             按图书编号查询记录,确认无误后可删除所选记录.

BookQuery.java      查询图书信息             可按图书名称,作者,出版社,出版时间组合查询,
                                        结果将显示在一个表格中
                                        
ReaderAdd.java      录入,修改           
ReaderUpdate.java   删除和查询               与图书相关模块功能相同
ReaderDelete.java   读者信息
ReaderQuery.java

                                            输入参数为图书编号和读者编号,有几个判断:1.图书必须有库存;
                                        2.每个读者只能借阅自己未借过的图书.即使已经借过,但必须已经归还;
Borrow              借书模块             3.每种书最多只能借一本;4.每个读者都有允许最大可借图书数量,因此,
                                        读者已借未还的图书数量不能超过此限制

                                            输入参数同样为图书编号和读者编号,主要判断该读者
Back                还书模块             已借过此书,且未归还


     (2)PublicMoudule 包: 其中包含了一组供MainPro 包中各类使用的公共类;

                         MainPro包中的类模块
    类名            功能描述                    设计要点
 GlobalVar.java   定义用户名称变量            记录登录系统的用户名,主要用于密码修改模块

 Book.java       定义图书信息               和图书信息表中个表项一一对应,用来保存查询结果

 Reader.java     定义读者信息                和读者信息表中个表项一一对应,用来保存查询结果

 DbOp.java        数据库操作                 其构造方法用来创建数据库连接,即打开数据库;
                                          其他几个方法分别用来查询,修改记录,以及关闭数据库

 BookSelect.java   按图书编号查询              按图书编号查询图书信息表 

 IfBorrowBack   查询读者是否借过某本图书且未归还      查询指定读者是否借过指定图书,且未归还

# 二.公共模块设计
 ## 1.DbOp.java
        用于完成基本的数据库操作,包括加载数据库驱动,创建数据库连接,执行sql语句等;
    其构造方法用于加载数据库驱动程序和创建数据库连接(打开数据库);用于查询记录的方法为
    executeQuery(); 用于插入,删除,修改记录的方法为executeUpdate();用于关闭数据库的方法为Close().
        执行数据库查询和更新时,如果没有为数据库创建连接,系统会自动加载数据库驱动程序
    并创建数据库连接.

 ## 2.BookSelect.java 与 ReaderSelect.java
        分别用于按图书编号和读者编号查询记录,查询结果将分别保存在Book和Reader对象中.

 ## 3.IdBorrowBack.java
        该类中的findback()方法用于查询指定读者是否借阅过指定图书.如果已经借阅且未归还
    返回true,否则,返回false
  
 ## 4.公共模块中的其他类
        GlobalVar.java
        Book.java
        Reader.java 


# 三.主模块设计
  ## 1.Login.java
          登录模块用于实现用户登录功能,也是进入系统的入口.
          进行系统登录时,需要输入用户名和密码,系统会查询数据库表中的user表
      验证用户名和密码是否正确.

  ## 2.ShowMain.java
        成功登陆系统后即进入系统的主界面,系统会根据登录的用户类型(普通用户和管理员),决定
    "系统维护"和"借阅管理"菜单是否可用.
  
  ## 3.BookAdd.java
        该模块用于输入图书信息,要点有:
        <1>图书编号具有唯一性,不能重复.
        <2>正常输入并保存记录后,给出提示信息.
        <3>输入并保存一个记录之后,要清空文本框,让用户能继续输入下一个记录.

  ## 4.BookUpdate.java
        该模块
        
  ## 5.BookDelete.java
        该模块






# 遇到的问题
  ##1.构建DbOp类时出现驱动问题,使用VS Code无法导入(个人技术原因,不会导入)相关外部jar,
  使用Myeclipse导入成功.


