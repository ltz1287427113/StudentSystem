public class Student {
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