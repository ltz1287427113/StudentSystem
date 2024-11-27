import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Group {
    private String groupName;           // 小组名称
    private List<Student> students;     // 小组成员
    
    // 构造方法
    public Group(String groupName) {
        this.groupName = groupName;
        this.students = new ArrayList<>();
    }
    
    // 添加学生到小组
    public void addStudent(Student student) {
        students.add(student);
    }
    
    // 随机抽取小组中的学生
    public Student getRandomStudent() {
        if (students.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return students.get(random.nextInt(students.size()));
    }
    
    // getter和setter方法
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
    
    @Override
    public String toString() {
        return "小组{" +
                "组名='" + groupName + '\'' +
                ", 成员数量=" + students.size() +
                '}';
    }
} 