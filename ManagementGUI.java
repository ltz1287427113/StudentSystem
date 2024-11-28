import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.List;

public class ManagementGUI extends JFrame {
    private List<Class> allClasses;
    private DefaultListModel<String> classListModel;
    private DefaultListModel<String> groupListModel;
    private DefaultListModel<String> studentListModel;
    private JList<String> classList;
    private JList<String> groupList;
    private JList<String> studentList;
    private JButton deleteBtn;
    private JPanel mainPanel;

    public ManagementGUI(List<Class> classes) {
        this.allClasses = classes;
        
        setTitle("班级管理系统");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 创建主面板
        mainPanel = new JPanel(new BorderLayout());
        
        // 创建列表面板
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
        
        // 添加列表面板到主面板
        mainPanel.add(listsPanel, BorderLayout.CENTER);
        
        // 创建按钮面板
        createButtonPanel();
        
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
        
        // 根据标题存储对应的JList引用
        if (title.equals("班级列表")) {
            classList = list;
            // 为班级列表添加鼠标监听器
            list.addMouseListener(new MouseAdapter() {
                private int lastSelectedIndex = -1;
                
                @Override
                public void mouseClicked(MouseEvent e) {
                    int currentIndex = list.locationToIndex(e.getPoint());
                    if (currentIndex == lastSelectedIndex) {
                        list.clearSelection();
                        groupListModel.clear();
                        studentListModel.clear();
                        lastSelectedIndex = -1;
                    } else {
                        lastSelectedIndex = currentIndex;
                    }
                }
            });
        } else if (title.equals("小组列表")) {
            groupList = list;
            // 为小组列表添加鼠标监听器
            list.addMouseListener(new MouseAdapter() {
                private int lastSelectedIndex = -1;
                
                @Override
                public void mouseClicked(MouseEvent e) {
                    int currentIndex = list.locationToIndex(e.getPoint());
                    if (currentIndex == lastSelectedIndex) {
                        list.clearSelection();
                        studentListModel.clear();
                        lastSelectedIndex = -1;
                    } else {
                        lastSelectedIndex = currentIndex;
                    }
                }
            });
        } else if (title.equals("学生列表")) {
            studentList = list;
            // 为学生列表添加鼠标监听器
            list.addMouseListener(new MouseAdapter() {
                private int lastSelectedIndex = -1;
                
                @Override
                public void mouseClicked(MouseEvent e) {
                    int currentIndex = list.locationToIndex(e.getPoint());
                    if (currentIndex == lastSelectedIndex) {
                        list.clearSelection();
                        lastSelectedIndex = -1;
                    } else {
                        lastSelectedIndex = currentIndex;
                    }
                }
            });
        }
        
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        return panel;
    }
    
    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addClassBtn = new JButton("添加班级");
        JButton addGroupBtn = new JButton("添加小组");
        JButton addStudentBtn = new JButton("添加学生");
        JButton randomSelectBtn = new JButton("随机点名");
        JButton addExampleDataBtn = new JButton("添加示例数据");
        JButton exportBtn = new JButton("导出数据");
        JButton importBtn = new JButton("导入数据");
        deleteBtn = new JButton("删除选中项");
        
        addClassBtn.addActionListener(e -> addClass());
        addGroupBtn.addActionListener(e -> addGroup());
        addStudentBtn.addActionListener(e -> addStudent());
        randomSelectBtn.addActionListener(e -> openRandomSelectWindow());
        addExampleDataBtn.addActionListener(e -> addExampleData());
        exportBtn.addActionListener(e -> exportData());
        importBtn.addActionListener(e -> importData());
        deleteBtn.addActionListener(e -> deleteSelected());
        
        buttonPanel.add(addClassBtn);
        buttonPanel.add(addGroupBtn);
        buttonPanel.add(addStudentBtn);
        buttonPanel.add(randomSelectBtn);
        buttonPanel.add(addExampleDataBtn);
        buttonPanel.add(exportBtn);
        buttonPanel.add(importBtn);
        buttonPanel.add(deleteBtn);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
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
        
        // 将班���添加到列表
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
    
    private void exportData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("选择保存位置");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // 如果文件名没有.txt后缀，添加后缀
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // 写入班级数据
                for (Class cls : allClasses) {
                    writer.write("班级:" + cls.getClassName());
                    writer.newLine();
                    
                    // 写入该班级的小组数据
                    for (Group group : cls.getGroups()) {
                        writer.write("    小组:" + group.getGroupName());
                        writer.newLine();
                        
                        // 写入该小组的学生数据
                        for (Student student : group.getStudents()) {
                            writer.write("        学生:" + student.getName());
                            writer.newLine();
                        }
                    }
                }
                
                JOptionPane.showMessageDialog(this, 
                    "数据导出成功！\n保存位置：" + file.getAbsolutePath(),
                    "导出成功",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "导出失败：" + ex.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
    private void importData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("选择要导入的文件");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                // 确认是否覆盖现有数据
                if (!allClasses.isEmpty()) {
                    int choice = JOptionPane.showConfirmDialog(this,
                        "导入将覆盖现有数据，是否继续？",
                        "确认导入",
                        JOptionPane.YES_NO_OPTION);
                    if (choice != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                
                // 清空现有数据
                allClasses.clear();
                classListModel.clear();
                groupListModel.clear();
                studentListModel.clear();
                
                String line;
                Class currentClass = null;
                Group currentGroup = null;
                int classCount = 0;
                int groupCount = 0;
                int studentCount = 0;
                
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    
                    if (line.isEmpty()) {
                        continue;  // 跳过空行
                    }
                    
                    try {
                        if (line.startsWith("班级:")) {
                            String className = line.substring(3).trim();
                            if (!className.isEmpty()) {
                                currentClass = new Class(className);
                                allClasses.add(currentClass);
                                classCount++;
                                System.out.println("导入班级: " + className);
                            }
                        }
                        else if (line.startsWith("    小组:") || line.startsWith("小组:")) {
                            String groupName = line.contains("    小组:") ? 
                                             line.substring(7).trim() : 
                                             line.substring(3).trim();
                            if (!groupName.isEmpty() && currentClass != null) {
                                currentGroup = new Group(groupName);
                                currentClass.addGroup(currentGroup);
                                groupCount++;
                                System.out.println("导入小组: " + groupName + " 到班级: " + currentClass.getClassName());
                            }
                        }
                        else if (line.startsWith("        学生:") || line.startsWith("学生:")) {
                            String studentName = line.contains("        学生:") ? 
                                               line.substring(11).trim() : 
                                               line.substring(3).trim();
                            if (!studentName.isEmpty() && currentGroup != null) {
                                Student student = new Student(studentName);
                                currentGroup.addStudent(student);
                                studentCount++;
                                System.out.println("导入学生: " + studentName + " 到小组: " + currentGroup.getGroupName());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("处理行时出错: " + line);
                        e.printStackTrace();
                    }
                }
                
                // 更新显示
                updateClassList();
                
                // 如果有选中的班级，更新其小组列表
                if (classList.getSelectedIndex() != -1) {
                    showGroups();
                }
                
                // 如果有选中的小组，更新其学生列表
                if (groupList.getSelectedIndex() != -1) {
                    showStudents();
                }
                
                // 打印导入后的数据状态
                System.out.println("\n导入后的数据状态：");
                for (Class cls : allClasses) {
                    Main.printClassInfo(cls);
                }
                
                if (classCount > 0 || groupCount > 0 || studentCount > 0) {
                    JOptionPane.showMessageDialog(this,
                        String.format("数据导入成功！\n共导入：\n%d 个班级\n%d 个小组\n%d 名学生", 
                            classCount, groupCount, studentCount),
                        "导入成功",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "未能导入任何数据，请检查文件格式是否正确。",
                        "导入提示",
                        JOptionPane.WARNING_MESSAGE);
                }
                    
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "导入失败" + ex.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
    private void deleteSelected() {
        if (studentList != null && studentList.getSelectedIndex() != -1) {
            deleteStudent();
        }
        else if (groupList != null && groupList.getSelectedIndex() != -1) {
            deleteGroup();
        }
        else if (classList != null && classList.getSelectedIndex() != -1) {
            deleteClass();
        }
        else {
            JOptionPane.showMessageDialog(this,
                "请先选择要删除的项目",
                "提示",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void deleteClass() {
        if (classList == null) {
            return;
        }
        
        int selectedIndex = classList.getSelectedIndex();
        if (selectedIndex != -1) {
            String className = classListModel.getElementAt(selectedIndex);
            int confirm = JOptionPane.showConfirmDialog(this,
                "确定要删除班级 '" + className + "' 及其所有小组和学生吗？",
                "确认删除",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                allClasses.remove(selectedIndex);
                updateClassList();
                groupListModel.clear();
                studentListModel.clear();
                JOptionPane.showMessageDialog(this,
                    "班级删除成功！",
                    "成功",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void deleteGroup() {
        if (classList == null || groupList == null) {
            return;
        }
        
        int classIndex = classList.getSelectedIndex();
        int groupIndex = groupList.getSelectedIndex();
        
        if (classIndex != -1 && groupIndex != -1) {
            Class selectedClass = allClasses.get(classIndex);
            String groupName = groupListModel.getElementAt(groupIndex);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "确定要删除小组 '" + groupName + "' 及其所有学生吗？",
                "确认删除",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                selectedClass.getGroups().remove(groupIndex);
                showGroups();
                studentListModel.clear();
                JOptionPane.showMessageDialog(this,
                    "小组删除成功！",
                    "成功",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void deleteStudent() {
        if (classList == null || groupList == null || studentList == null) {
            return;
        }
        
        int classIndex = classList.getSelectedIndex();
        int groupIndex = groupList.getSelectedIndex();
        int studentIndex = studentList.getSelectedIndex();
        
        if (classIndex != -1 && groupIndex != -1 && studentIndex != -1) {
            Class selectedClass = allClasses.get(classIndex);
            Group selectedGroup = selectedClass.getGroups().get(groupIndex);
            String studentName = studentListModel.getElementAt(studentIndex);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "确定要删除学生 '" + studentName + "' 吗？",
                "确认删除",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                selectedGroup.getStudents().remove(studentIndex);
                showStudents();
                JOptionPane.showMessageDialog(this,
                    "学生删除成功！",
                    "成功",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
} 