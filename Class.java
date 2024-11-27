import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Class {
    private String className;           // 班级名称
    private List<Group> groups;         // 班级中的小组
    private List<Student> students;     // 班级所有学生
    
    // 构造方法
    public Class(String className) {
        this.className = className;
        this.groups = new ArrayList<>();
        this.students = new ArrayList<>();
    }
    
    // 添加小组
    public void addGroup(Group group) {
        groups.add(group);
        students.addAll(group.getStudents());
    }
    
    // 随机抽取小组
    public Group getRandomGroup() {
        if (groups.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return groups.get(random.nextInt(groups.size()));
    }
    
    // 随机抽取学生
    public Student getRandomStudent() {
        if (students.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return students.get(random.nextInt(students.size()));
    }
    
    // getter和setter方法
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    
    public List<Group> getGroups() { return groups; }
    public void setGroups(List<Group> groups) { this.groups = groups; }
    
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
} 