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
        
        // 添加按钮事件
        addClassBtn.addActionListener(e -> addClass());
        addGroupBtn.addActionListener(e -> addGroup());
        addStudentBtn.addActionListener(e -> addStudent());
        randomSelectBtn.addActionListener(e -> openRandomSelectWindow());
        
        buttonPanel.add(addClassBtn);
        buttonPanel.add(addGroupBtn);
        buttonPanel.add(addStudentBtn);
        buttonPanel.add(randomSelectBtn);
        
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
} 