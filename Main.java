import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 创建班级列表
        List<Class> allClasses = new ArrayList<>();
        
        // 创建并显示GUI
        ManagementGUI gui = new ManagementGUI(allClasses);
        gui.setVisible(true);
    }
} 