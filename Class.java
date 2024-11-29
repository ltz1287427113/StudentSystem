import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Class implements Serializable {
    private static final long serialVersionUID = 1L;
    private String className;           
    private List<Group> groups;         
    private List<Student> students;     

    // 构造方法

    public Class() {
    }

    public Class(String className) {
        this.className = className;
        this.groups = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    //添加小组到班级
    public void addGroup(Group group) {
        if (group != null) {//检查传入的group是否为null
            boolean exists = false;
            for (Group g : groups) {
                if (g.getGroupName().equals(group.getGroupName())) {//检查该小组名称是否已存在
                    exists = true;
                    break;
                }
            }
            //如果该小组名称不存在，则添加该小组
            if (!exists) {
                groups.add(group);
                if (group.getStudents() != null) { //检查小组中的学生是否为null
                    students.addAll(group.getStudents());
                }
            }
        }
    }
    
    //随机抽取一个小组
    public Group getRandomGroup() {
        if (groups.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return groups.get(random.nextInt(groups.size()));
    }
    
    public Student getRandomStudent() {
        updateStudentsList();
        if (students.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return students.get(random.nextInt(students.size()));
    }
    
    private void updateStudentsList() {
        students.clear();
        for (Group group : groups) {
            if (group != null && group.getStudents() != null) {
                students.addAll(group.getStudents());
            }
        }
    }
    
    public Group findGroup(String groupName) {
        for (Group group : groups) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }
    
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    
    public List<Group> getGroups() { return groups; }
    public void setGroups(List<Group> groups) { 
        if (groups != null) {
            this.groups = groups; 
            updateStudentsList();
        }
    }
    
    public List<Student> getStudents() { 
        updateStudentsList();
        return students; 
    }
    
    @Override
    public String toString() {
        return className;
    }
} 