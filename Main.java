import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String AUTO_SAVE_PATH = "E:\\data-student\\auto-save.txt";
    
    public static void main(String[] args) {
        // 创建班级列表
        List<Class> allClasses = new ArrayList<>();
        
        // 确保保存目录存在
        File saveDir = new File("E:\\data-student");
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        
        // 创建并显示管理界面
        ManagementGUI gui = new ManagementGUI(allClasses, AUTO_SAVE_PATH);
        
        // 尝试加载自动保存的数据
        if (new File(AUTO_SAVE_PATH).exists()) {
            gui.importDataFromFile(AUTO_SAVE_PATH);
        }
        
        gui.setVisible(true);
    }
    
    // 添加用于测试的辅助方法
    public static void printClassInfo(Class cls) {
        System.out.println("班级: " + cls.getClassName());
        System.out.println("小组数量: " + cls.getGroups().size());
        for (Group group : cls.getGroups()) {
            System.out.println("  小组: " + group.getGroupName());
            System.out.println("  学生数量: " + group.getStudents().size());
            for (Student student : group.getStudents()) {
                System.out.println("    学生: " + student.getName());
            }
        }
    }
} 