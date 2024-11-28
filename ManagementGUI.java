import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

public class ManagementGUI extends JFrame {
    private final List<Class> allClasses;
    private final DefaultListModel<String> classListModel;
    private final DefaultListModel<String> groupListModel;
    private final DefaultListModel<String> studentListModel;
    private JList<String> classList;
    private JList<String> groupList;

    public ManagementGUI(List<Class> classes) {
        this.allClasses = classes;
        
        setTitle("班级管理系统");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 创建主面板，使用BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // 创建列表面板，使用GridLayout
        JPanel listsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        
        // 初始化列表模型
        classListModel = new DefaultListModel<>();
        groupListModel = new DefaultListModel<>();
        studentListModel = new DefaultListModel<>();
        
        // 创建班级面板
        JPanel classPanel = createPanel("班级列表", classListModel, e -> showGroups());
        
        // 创建小组面板
        JPanel groupPanel = createPanel("小组列表", groupListModel, e -> showStudents());
        
        // 创建学生面板
        JPanel studentPanel = createPanel("学生列表", studentListModel, null);
        
        // 添加面板到列表面板
        listsPanel.add(classPanel);
        listsPanel.add(groupPanel);
        listsPanel.add(studentPanel);
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addClassBtn = new JButton("添加班级");
        JButton addGroupBtn = new JButton("添加小组");
        JButton addStudentBtn = new JButton("添加学生");
        JButton randomSelectBtn = new JButton("随机点名");
        JButton addExampleDataBtn = new JButton("添加示例数据");
        
        // 添加按钮事件
        addClassBtn.addActionListener(e -> addClass());
        addGroupBtn.addActionListener(e -> addGroup());
        addStudentBtn.addActionListener(e -> addStudent());
        randomSelectBtn.addActionListener(e -> openRandomSelectWindow());
        addExampleDataBtn.addActionListener(e -> addExampleData());
        
        buttonPanel.add(addClassBtn);
        buttonPanel.add(addGroupBtn);
        buttonPanel.add(addStudentBtn);
        buttonPanel.add(randomSelectBtn);
        buttonPanel.add(addExampleDataBtn);
        
        // 添加面板到主面板
        mainPanel.add(listsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // 添加主面板到窗口
        add(mainPanel);
        
        // 更新班级列表
        updateClassList();
    }
    
    private JPanel createPanel(String title, DefaultListModel<String> model, ListSelectionListener listener) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        
        JList<String> list = new JList<>(model);
        if (listener != null) {
            list.addListSelectionListener(listener);
        }

        JList<String> studentList;
        if (title.equals("班级列表")) classList = list;
        else if (title.equals("小组列表")) groupList = list;
        else if (title.equals("学生列表")) studentList = list;
        
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        return panel;
    }
    
    private void addClass() {
        String className = JOptionPane.showInputDialog(this, "请输入班级名称：");
        if (className != null && !className.trim().isEmpty()) {
            Class newClass = new Class(className);
            allClasses.add(newClass);
            updateClassList();
        }
    }
    
    private void addGroup() {
        int selectedIndex = classList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一个班级！");
            return;
        }
        
        String groupName = JOptionPane.showInputDialog(this, "请输入小组名称：");
        if (groupName != null && !groupName.trim().isEmpty()) {
            Class selectedClass = allClasses.get(selectedIndex);
            Group newGroup = new Group(groupName);
            selectedClass.addGroup(newGroup);
            showGroups();
        }
    }
    
    private void addStudent() {
        int classIndex = classList.getSelectedIndex();
        int groupIndex = groupList.getSelectedIndex();
        
        if (classIndex == -1 || groupIndex == -1) {
            JOptionPane.showMessageDialog(this, "请先选择班级和小组！");
            return;
        }
        
        String studentName = JOptionPane.showInputDialog(this, "请输入学生姓名：");
        if (studentName != null && !studentName.trim().isEmpty()) {
            Class selectedClass = allClasses.get(classIndex);
            Group selectedGroup = selectedClass.getGroups().get(groupIndex);
            Student newStudent = new Student(studentName);
            selectedGroup.addStudent(newStudent);
            showStudents();
        }
    }
    
    private void updateClassList() {
        classListModel.clear();
        for (Class c : allClasses) {
            classListModel.addElement(c.getClassName());
        }
    }
    
    private void showGroups() {
        groupListModel.clear();
        int selectedIndex = classList.getSelectedIndex();
        if (selectedIndex != -1) {
            Class selectedClass = allClasses.get(selectedIndex);
            for (Group g : selectedClass.getGroups()) {
                groupListModel.addElement(g.getGroupName());
            }
        }
    }
    
    private void showStudents() {
        studentListModel.clear();
        int classIndex = classList.getSelectedIndex();
        int groupIndex = groupList.getSelectedIndex();
        
        if (classIndex != -1 && groupIndex != -1) {
            Class selectedClass = allClasses.get(classIndex);
            Group selectedGroup = selectedClass.getGroups().get(groupIndex);
            for (Student s : selectedGroup.getStudents()) {
                studentListModel.addElement(s.getName());
            }
        }
    }
    
    private void openRandomSelectWindow() {
        RandomSelectGUI randomSelectGUI = new RandomSelectGUI(allClasses);
        randomSelectGUI.setVisible(true);
    }
    
    private void addExampleData() {
        // 确认对话框
        int choice = JOptionPane.showConfirmDialog(
            this,
            "这将添加示例数据（3个班级，每个班级3个小组，每个小组3个学生）。\n确定要继续吗？",
            "确认添加示例数据",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        // 创建班级
        Class class1 = new Class("计算机科学1班");
        Class class2 = new Class("软件工程2班");
        Class class3 = new Class("人工智能3班");
        
        // 创建小组
        Group cs_group1 = new Group("计科1班第一组");
        Group cs_group2 = new Group("计科1班第二组");
        Group cs_group3 = new Group("计科1班第三组");
        
        Group se_group1 = new Group("软工2班第一组");
        Group se_group2 = new Group("软工2班第二组");
        Group se_group3 = new Group("软工2班第三组");
        
        Group ai_group1 = new Group("人工智能3班第一组");
        Group ai_group2 = new Group("人工智能3班第二组");
        Group ai_group3 = new Group("人工智能3班第三组");
        
        // 添加学生
        cs_group1.addStudent(new Student("张三"));
        cs_group1.addStudent(new Student("李四"));
        cs_group1.addStudent(new Student("王五"));
        
        cs_group2.addStudent(new Student("赵六"));
        cs_group2.addStudent(new Student("钱七"));
        cs_group2.addStudent(new Student("孙八"));
        
        cs_group3.addStudent(new Student("周九"));
        cs_group3.addStudent(new Student("吴十"));
        cs_group3.addStudent(new Student("郑十一"));
        
        se_group1.addStudent(new Student("刘一"));
        se_group1.addStudent(new Student("陈二"));
        se_group1.addStudent(new Student("杨三"));
        
        se_group2.addStudent(new Student("黄四"));
        se_group2.addStudent(new Student("周五"));
        se_group2.addStudent(new Student("吴六"));
        
        se_group3.addStudent(new Student("郑七"));
        se_group3.addStudent(new Student("王八"));
        se_group3.addStudent(new Student("冯九"));
        
        ai_group1.addStudent(new Student("蒋一"));
        ai_group1.addStudent(new Student("沈二"));
        ai_group1.addStudent(new Student("韩三"));
        
        ai_group2.addStudent(new Student("杨四"));
        ai_group2.addStudent(new Student("朱五"));
        ai_group2.addStudent(new Student("秦六"));
        
        ai_group3.addStudent(new Student("许七"));
        ai_group3.addStudent(new Student("何八"));
        ai_group3.addStudent(new Student("张九"));
        
        // 将小组添加到班级
        class1.addGroup(cs_group1);
        class1.addGroup(cs_group2);
        class1.addGroup(cs_group3);
        
        class2.addGroup(se_group1);
        class2.addGroup(se_group2);
        class2.addGroup(se_group3);
        
        class3.addGroup(ai_group1);
        class3.addGroup(ai_group2);
        class3.addGroup(ai_group3);
        
        // 将班级添加到列表
        allClasses.add(class1);
        allClasses.add(class2);
        allClasses.add(class3);
        
        // 更新显示
        updateClassList();
        
        // 显示成功消息
        JOptionPane.showMessageDialog(
            this,
            "示例数据添加成功！\n已添加3个班级，每个班级3个小组，每个小组3个学生。",
            "添加成功",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
} 