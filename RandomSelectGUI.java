import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class RandomSelectGUI extends JFrame {
    private List<Class> allClasses;
    private JLabel resultLabel;
    private JButton randomClassBtn;
    private JButton randomGroupBtn;
    private JButton randomStudentFromGroupBtn;
    private JButton randomStudentFromClassBtn;
    
    private Class currentClass;
    private Group currentGroup;
    
    public RandomSelectGUI(List<Class> classes) {
        this.allClasses = classes;
        
        // 设置窗口
        setTitle("随机点名系统");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 创建主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // 创建结果显示区域
        resultLabel = new JLabel("请点击按钮开始随机抽取", SwingConstants.CENTER);
        resultLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(resultLabel, BorderLayout.CENTER);
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 创建按钮
        randomClassBtn = new JButton("随机抽取班级");
        randomGroupBtn = new JButton("随机抽取小组");
        randomStudentFromGroupBtn = new JButton("从小组抽取学生");
        randomStudentFromClassBtn = new JButton("从班级抽取学生");
        
        // 设置按钮状态
        randomGroupBtn.setEnabled(false);
        randomStudentFromGroupBtn.setEnabled(false);
        randomStudentFromClassBtn.setEnabled(false);
        
        // 添加按钮事件
        randomClassBtn.addActionListener(e -> randomSelectClass());
        randomGroupBtn.addActionListener(e -> randomSelectGroup());
        randomStudentFromGroupBtn.addActionListener(e -> randomSelectStudentFromGroup());
        randomStudentFromClassBtn.addActionListener(e -> randomSelectStudentFromClass());
        
        // 添加按钮到面板
        buttonPanel.add(randomClassBtn);
        buttonPanel.add(randomGroupBtn);
        buttonPanel.add(randomStudentFromGroupBtn);
        buttonPanel.add(randomStudentFromClassBtn);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // 添加主面板到窗口
        add(mainPanel);
    }
    
    private void randomSelectClass() {
        if (allClasses.isEmpty()) {
            showMessage("没有可用的班级！");
            return;
        }
        
        currentClass = allClasses.get((int)(Math.random() * allClasses.size()));
        showResult("选中班级：" + currentClass.getClassName());
        
        randomGroupBtn.setEnabled(true);
        randomStudentFromClassBtn.setEnabled(true);
        randomStudentFromGroupBtn.setEnabled(false);
    }
    
    private void randomSelectGroup() {
        if (currentClass == null) {
            showMessage("请先选择班级！");
            return;
        }
        
        currentGroup = currentClass.getRandomGroup();
        if (currentGroup == null) {
            showMessage("该班级没有小组！");
            return;
        }
        
        showResult("选中小组：" + currentGroup.getGroupName());
        randomStudentFromGroupBtn.setEnabled(true);
    }
    
    private void randomSelectStudentFromGroup() {
        if (currentGroup == null) {
            showMessage("请先选择小组！");
            return;
        }
        
        Student student = currentGroup.getRandomStudent();
        if (student == null) {
            showMessage("该小组没有学生！");
            return;
        }
        
        showResult("从小组中选中学生：" + student.getName());
    }
    
    private void randomSelectStudentFromClass() {
        if (currentClass == null) {
            showMessage("请先选择班级！");
            return;
        }
        
        Student student = currentClass.getRandomStudent();
        if (student == null) {
            showMessage("该班级没有学生！");
            return;
        }
        
        showResult("从班级中选中学生：" + student.getName());
    }
    
    private void showResult(String message) {
        resultLabel.setText(message);
    }
    
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "提示", JOptionPane.INFORMATION_MESSAGE);
    }
} 