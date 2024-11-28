import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;        // 姓名
    
    // 构造方法
    public Student(String name) {
        this.name = name;
    }
    
    // getter和setter方法
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    @Override
    public String toString() {
        return "学生{姓名='" + name + "'}";
    }
} 