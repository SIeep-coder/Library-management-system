package PublicModule;

public class Reader {
    
    private String id;  private String readername;  private String readertype;
    private String sex; private int max_num;        private int day_num; 
    
    public String getId() { return id;}
    public void setId(String id) { this.id = id;}

    public String getReadername() { return readername;}
    public void setReaername(String readername) { this.readername = readername;}
    
    public String getReadertype() { return readertype;}
    public void setReadertype(String readertype) { this.readertype = readertype;} 

    public String getSex() { return sex;}
    public void setSex(String sex) { this.sex = sex;}


    public int getMax_num() { return max_num;}
    public void setMax_num(int max_num) { this.max_num = max_num;}

    public int getDay_num() { return day_num;}
    public void setDay_num(int day_num) { this.day_num = day_num;}

}
