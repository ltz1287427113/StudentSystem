import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 创建多个班级
        Class class1 = new Class("计算机科学1班");
        Class class2 = new Class("软件工程2班");
        Class class3 = new Class("人工智能3班");
        
        // 计算机科学1班的小组
        Group cs_group1 = new Group("计科1班第一组");
        Group cs_group2 = new Group("计科1班第二组");
        Group cs_group3 = new Group("计科1班第三组");
        
        // 软件工程2班的小组
        Group se_group1 = new Group("软工2班第一组");
        Group se_group2 = new Group("软工2班第二组");
        Group se_group3 = new Group("软工2班第三组");
        
        // 人工智能3班的小组
        Group ai_group1 = new Group("人工智能3班第一组");
        Group ai_group2 = new Group("人工智能3班第二组");
        Group ai_group3 = new Group("人工智能3班第三组");
        
        // 为计科1班添加学生
        cs_group1.addStudent(new Student("张三"));
        cs_group1.addStudent(new Student("李四"));
        cs_group1.addStudent(new Student("王五"));
        
        cs_group2.addStudent(new Student("赵六"));
        cs_group2.addStudent(new Student("钱七"));
        cs_group2.addStudent(new Student("孙八"));
        
        cs_group3.addStudent(new Student("周九"));
        cs_group3.addStudent(new Student("吴十"));
        cs_group3.addStudent(new Student("郑十一"));
        
        // 为软工2班添加学生
        se_group1.addStudent(new Student("刘一"));
        se_group1.addStudent(new Student("陈二"));
        se_group1.addStudent(new Student("杨三"));
        
        se_group2.addStudent(new Student("黄四"));
        se_group2.addStudent(new Student("周五"));
        se_group2.addStudent(new Student("吴六"));
        
        se_group3.addStudent(new Student("郑七"));
        se_group3.addStudent(new Student("王八"));
        se_group3.addStudent(new Student("冯九"));
        
        // 为人工智能3班添加学生
        ai_group1.addStudent(new Student("蒋一"));
        ai_group1.addStudent(new Student("沈二"));
        ai_group1.addStudent(new Student("韩三"));
        
        ai_group2.addStudent(new Student("杨四"));
        ai_group2.addStudent(new Student("朱五"));
        ai_group2.addStudent(new Student("秦六"));
        
        ai_group3.addStudent(new Student("许七"));
        ai_group3.addStudent(new Student("何八"));
        ai_group3.addStudent(new Student("张九"));
        
        // 将小组添加到对应的班级
        class1.addGroup(cs_group1);
        class1.addGroup(cs_group2);
        class1.addGroup(cs_group3);
        
        class2.addGroup(se_group1);
        class2.addGroup(se_group2);
        class2.addGroup(se_group3);
        
        class3.addGroup(ai_group1);
        class3.addGroup(ai_group2);
        class3.addGroup(ai_group3);
        
        // 创建班级列表
        List<Class> allClasses = new ArrayList<>();
        allClasses.add(class1);
        allClasses.add(class2);
        allClasses.add(class3);
        
        // 创建并显示GUI
        ManagementGUI gui = new ManagementGUI(allClasses);
        gui.setVisible(true);
    }
} 