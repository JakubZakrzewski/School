package School;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchoolGUI {
    private JPanel mainPanel;
    private JTabbedPane tabsTP;
    private JTextField nameTF;
    private JTextField surnameTF;
    private JButton addStudentButton;
    private JComboBox mathematicsCB;
    private JComboBox polishLangCB;
    private JComboBox physicsCB;
    private JComboBox geographyCB;
    private JComboBox studentCB2;
    private JTextField gradeTF;
    private JTextField averageTF;
    private JButton addGradeButton;
    private JButton calculateAverageButton;
    private JComboBox studentCB3;
    private JTextField gradesTF3;
    private JComboBox subjectCB3;
    private JComboBox yourDetailsTeacherCB;
    private JTextField subjectTF;
    private JRadioButton averageStudentRB;
    private JRadioButton averageGlobalRB;
    private JRadioButton invisibleRB;
    private JTextArea messageWindowTextArea;


    public void init() {

        // Field with messages to user
        // automatic scrolling down, welcome message
        DefaultCaret caret = (DefaultCaret) messageWindowTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        messageWindowTextArea.append("PROGRAM START\n   WELCOME\n**********************\n");

        // Reaction to changing tabs
        tabsTP.addChangeListener(new tabsTPChangeListener());

/*        Tab1 - "Add student"
        reaction to addStudentButton "Add" responsible for creating student
        filling comboboxes in tab1 with teachers (see comment to fillTeachersTab1 method) */
        addStudentButton.addActionListener(new addStudentButtonActionListener());
        fillTeachersTab1();

/*        Tab2 - "Teacher's panel"
        filling yourDetailsTeacherCB combobox in tab2 with teachers;
        reaction to changes in combobox, button "Add grade" (addGradeButton) responsible for giving grade
        and button "Calculate" (calculateAverageButton) responsible for calculating the average grade;
        setting button group for choosing mode for calculating the average grade + additional button
        allowing to deselect after changing tabs etc.*/
        fillTeachersTab2();
        yourDetailsTeacherCB.addActionListener(new yourDataTeacherCBActionListener());
        addGradeButton.addActionListener(new addGradeButtonActionListener());
        calculateAverageButton.addActionListener(new calculateAverageButtonActionListener());
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(averageStudentRB);
        buttonGroup.add(averageGlobalRB);
        buttonGroup.add(invisibleRB);

/*        Tab3 - "Register"
        filling subjectCB3 combobox with subjects
        reaction to changes in comboboxes (studentCB3, subjectCB3)*/
        showSubjects(subjectCB3);
        studentCB3.addActionListener(new studentCB3ActionListener());
        subjectCB3.addActionListener(new subjectCB3ActionListener());
    }

    // fills names and surnames of teachers in the yourDetailsTeacherCB combobox in tab2
    // (empty as initial + created teachers)
    private void fillTeachersTab2() {

        yourDetailsTeacherCB.addItem(null);
        for (Teacher teacher : Teacher.getTeachersList()) {
            yourDetailsTeacherCB.addItem(teacher.getName() + " " + teacher.getSurname());
        }
    }

    // fills names and surnames of teachers in the comboboxes in tab1
    // (to appropriate groups in comboboxes connected with subjects, based on taught one)
    private void fillTeachersTab1() {

        for (Teacher teacher : Teacher.getTeachersList()) {

            switch (teacher.getTaughtSubject()) {
                case MATHEMATICS:
                    mathematicsCB.addItem(teacher.getName() + " " + teacher.getSurname());
                    break;
                case POLISH_LANGUAGE:
                    polishLangCB.addItem(teacher.getName() + " " + teacher.getSurname());
                    break;
                case PHYSICS:
                    physicsCB.addItem(teacher.getName() + " " + teacher.getSurname());
                    break;
                case GEOGRAPHY:
                    geographyCB.addItem(teacher.getName() + " " + teacher.getSurname());
                    break;
            }
        }
    }

    // fills the studentCB2 combobox in tab2 with students from students list of given teacher  +
    // (+ empty as initial value)
    private void fillStudentsTab2(Teacher teacher) {
        studentCB2.addItem(null);
        for (Student student : teacher.getTeacher_sStudentsList()) {
            studentCB2.addItem(student.getName() + " " + student.getSurname());
        }
    }

    // fills the studentCB3 combobox in tab3 to show newly added students (+ empty as initial value)
    // before that it clears the combobox to prevent adding of previously existing students
    private void fillStudentsTab3() {
        studentCB3.removeAllItems();
        studentCB3.addItem(null);
        for (Student student : Student.getStudentsList()) {
            studentCB3.addItem(student.getName() + " " + student.getSurname());
        }
    }

/*     changing data from combobox with students for the object of Student class
     (it assumes that students with particular name and surname appears only once,
     so it searches using name and surname)*/
    private Student cBtoStudent(JComboBox cB) {

        String name = ((String) cB.getSelectedItem()).split(" ")[0];
        String surname = ((String) cB.getSelectedItem()).split(" ")[1];
        Student result = null;

        for (Student student : Student.getStudentsList()) {
            if (name.equals(student.getName()) && surname.equals(student.getSurname())) {
                result = student;
                break;
            } else {
                result = null;
            }
        }
        return result;
    }

/*     changing data from combobox with teachers for the object of Teacher class
     (it assumes that teachers with particular name and surname appears only once,
     so it searches using name and surname)*/
    private Teacher cBtoTeacher(JComboBox cB) {

        String name = ((String) cB.getSelectedItem()).split(" ")[0];
        String surname = ((String) cB.getSelectedItem()).split(" ")[1];
        Teacher result = null;

        for (Teacher teacher : Teacher.getTeachersList()) {
            if (name.equals(teacher.getName()) && surname.equals(teacher.getSurname())) {
                result = teacher;
                break;
            } else {
                result = null;
            }
        }
        return result;
    }

    // changing data from combobox with subjects for the object of enum Subject
    private Subject cBtoSubject(JComboBox cB) {
        Object input = cB.getSelectedItem();
        Subject output = null;
        for (Subject subject : Subject.values()) {
            if (input.equals(subject)) {
                output = subject;
                break;
            }
        }
        return output;
    }

    // adds subjects to given combobox (used once during initialization of tab3) (+ empty as initial value)
    private void showSubjects(JComboBox cB) {

        cB.addItem(null);
        for (Subject subject : Subject.values()) {
            cB.addItem(subject);
        }
    }

    /*    action for addStudentButton button responsible for adding student
    checks if name or surname textfield is not empty (if at least one is empty,
    it shows window with appropriate error message)
    creates student object using constructor from Student class
    adds message to message window*/
    private class addStudentButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!nameTF.getText().isEmpty() && !surnameTF.getText().isEmpty()) {
                String name = nameTF.getText();
                String surname = surnameTF.getText();
                Student student = new Student(name, surname, cBtoTeacher(mathematicsCB), cBtoTeacher(polishLangCB),
                        cBtoTeacher(physicsCB), cBtoTeacher(geographyCB));
                messageWindowTextArea.append("Student added. \n" + student.toString());
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Fill name and surname fields!");
            }
        }
    }


    /*    action for addGradeButton button adding grade
        it checks if teacher and student are chosen in comboboxes - if not, it shows window
            with appropriate error message
        parsing entered grade to integer - if it cannot be done, it shows window with appropriate error message
        checks if entered grade is 1, 2, 3, 4, 5 or 6 - if not, it shows window with appropriate error message
        adds message to message window (returned by giveGrade method)
        gives grade by method of chosen teacher (passes subject, student and grade to it)
        clears the field with grade
        grade is not 1, 2, 3, 4, 5 or 6 => window: "Only 1, 2, 3, 4, 5 or 6 acceptable"
        grade cannot be parsed to integer => window: "Entered grade is not a number!"
        teacher or student not chosen => window: "Complete the data!"
        clears the field with average and deselects chosen mode for calculating the average*/
    private class addGradeButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (yourDetailsTeacherCB.getSelectedItem() == null || studentCB2.getSelectedItem() == null) {
                    throw new NullPointerException();
                }
                int grade = Integer.parseInt(gradeTF.getText());
                if (Teacher.ValidGrade(grade)) {

                    messageWindowTextArea.append(
                            cBtoTeacher(yourDetailsTeacherCB).giveGrade(
                                    cBtoTeacher(yourDetailsTeacherCB).getTaughtSubject(),
                                    cBtoStudent(studentCB2),
                                    grade));
                    gradeTF.setText("");

                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Only 1, 2, 3, 4, 5 or 6 acceptable");
                }
            } catch (NumberFormatException ne) {
                JOptionPane.showMessageDialog(mainPanel, "Entered grade is not a number!");
            } catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(mainPanel, "Complete the data!");
            }
            averageTF.setText("");
            invisibleRB.setSelected(true);
        }
    }

    /*    action for calculateAverageButton button calculating the average
        takes teacher from the yourDetailsTeacherCB combobox
            (if none is chosen it shows window with appropriate error message)
        checks radiobuttons group for chosen mode for calculating average
            (if none is chosen it shows window with appropriate error message)
         1) "Student's average" chosen -> takes student from studentCB2 combobox
         (if none is chosen it shows window with appropriate error message)
              it uses teacher's method averageGrade to calculate the average (passes the student as argument)
              shows the result in averageTF text field
              adds message to message window
         2) "All my students' average" chosen
              it uses teacher's method averageGlobal to calculate the average
              shows the result in averageTF text field
              adds message to message window
        deselects chosen mode for calculating the average
        mode not chosen => window: "Choose mode for calculating the average!"
        teacher not chosen (and/or student for student's average) => window: "Complete the data!"*/
    public class calculateAverageButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                Teacher teacher = cBtoTeacher(yourDetailsTeacherCB);
                String result;
                if (averageStudentRB.isSelected()) {
                    Student student = cBtoStudent(studentCB2);
                    result = Double.toString(teacher.averageGrade(student));
                    averageTF.setText(result);
                    messageWindowTextArea.append("Calculated student's average: " + student.getName() + " " +
                            student.getSurname() + " from subject " + subjectTF.getText() +
                            ". Result: " + result + "\n");
                } else if (averageGlobalRB.isSelected()) {
                    result = Double.toString(teacher.averageGlobal());
                    averageTF.setText(result);
                    messageWindowTextArea.append("Calculated all students' average of teacher of subject " +
                            subjectTF.getText() + ": " + teacher.getName() + " " + teacher.getSurname() +
                            ". Result: " + result + "\n");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Choose mode for calculating the average!");
                }
                invisibleRB.setSelected(true);

            } catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(mainPanel, "Complete the data!");
            }
        }
    }

    /*    action for changing tabs
        sets initial values of particular comboboxes to empty
        clears particular textfields
        sets text of gradesTF3 textfield in tab3 to "Choose data"
        deselects chosen mode for calculating the average
        fills the studentCB3 combobox in tab3 to show students anew (see comment to fillStudentsTab3 method)*/
    private class tabsTPChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {

            yourDetailsTeacherCB.setSelectedItem(null);
            nameTF.setText("");
            surnameTF.setText("");
            subjectTF.setText("");
            gradeTF.setText("");
            averageTF.setText("");
            studentCB3.setSelectedItem(null);
            subjectCB3.setSelectedItem(null);
            gradesTF3.setText("Choose data");
            invisibleRB.setSelected(true);
            fillStudentsTab3();
        }
    }

    /*    action for changes in yourDetailsTeacherCB combobox with teacher's details in tab2
        takes teacher from combobox (if none is chosen it shows window with appropriate error message)
        refreshes studentCB2 combobox to correspond with chosen teacher
        shows subject taught by teacher in subjectTF textfield (auxiliary)
        yourDetailsTeacherCB combobox set to empty => clears students list from studentCB2 combobox
        clears textfields with grade and average (gradeTF, averageTF)*/
    private class yourDataTeacherCBActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                Teacher teacher = cBtoTeacher(yourDetailsTeacherCB);
                studentCB2.removeAllItems();
                fillStudentsTab2(teacher);

                subjectTF.setText(teacher.getTaughtSubject().toString());

            } catch (NullPointerException npe) {
                studentCB2.removeAllItems();
            }
            gradeTF.setText("");
            averageTF.setText("");
        }
    }

    /*    shows grades in gradesTF3 textfield in tab3
        removes square brackets from the list before showing it
        if the grades list is empty it shows "Lack of grades"*/
    private void showGrades() {
        String grades = (cBtoStudent(studentCB3).getGrades(cBtoSubject(subjectCB3))).toString();
        if (grades.length() > 2) {
            gradesTF3.setText(grades.substring(1, grades.length() - 1));
        } else {
            gradesTF3.setText("Lack of grades");
        }
    }

    /*    action for changing student in studentCB3 combobox in tab3
        if both student and subject are chosen in comboboxes of tab3 it uses showGrades method
        if at least one not chosen it shows "Choose data" in gradesTF3 textfield*/
    private class studentCB3ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!(studentCB3.getSelectedItem() == null) && !(subjectCB3.getSelectedItem() == null)) {
                showGrades();
            } else {
                gradesTF3.setText("Choose data");
            }
        }
    }

    /*    action for changing subject in subjectCB3 combobox in tab3
        if both student and subject are chosen in comboboxes of tab3 it uses showGrades method
        if at least one not chosen it shows "Choose data" in gradesTF3 textfield*/
    private class subjectCB3ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!(studentCB3.getSelectedItem() == null) && !(subjectCB3.getSelectedItem() == null)) {
                showGrades();
            } else {
                gradesTF3.setText("Choose data");
            }
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
