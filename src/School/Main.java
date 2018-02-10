package School;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // creates teachers
        Teacher teacherM = new Teacher("Andrzej", "Mathematician", Subject.MATHEMATICS);
        Teacher teacherP = new Teacher("Bartosz", "Pole", Subject.POLISH_LANGUAGE);
        Teacher teacherF = new Teacher("Cezary", "Physicist", Subject.PHYSICS);
        Teacher teacherG = new Teacher("Dariusz", "Geographer", Subject.GEOGRAPHY);

        // creates initial student for the purpose of experiments
        Student initialStudent = new Student("Jan", "Kowalski", teacherM, teacherP, teacherF, teacherG);


        JFrame mainFrame = new JFrame("SCHOOL");
        SchoolGUI schoolGUI = new SchoolGUI();
        mainFrame.setContentPane(schoolGUI.getMainPanel());
        mainFrame.pack();
        schoolGUI.init();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
