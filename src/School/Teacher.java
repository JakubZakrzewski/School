package School;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Teacher extends Human {

    private Subject taughtSubject;
    private ArrayList<Student> teacher_sStudentsList;
    private ArrayList<Integer> studentsGradesList;
    private static ArrayList<Teacher> teachersList = new ArrayList<>();



/*    Teacher's constructor
    creates teacher object with name and surname based on parent Human, assigned chosen subject,
    empty students list, empty students' grades list; adds teacher to static teachers list*/
    Teacher(String name, String surname, Subject taughtSubject) {
        super(name, surname);
        this.taughtSubject = taughtSubject;
        this.teacher_sStudentsList = new ArrayList<>();
        this.studentsGradesList = new ArrayList<>();
        teachersList.add(this);
    }

    //assigns student to teacher by adding it to teacher's students list
    public void assignStudent(Student student) {
        this.teacher_sStudentsList.add(student);
    }

/*    gives a grade to student by adding it to his grades list of the subject taught by teacher giving it;
    adds grade to grades list of all teacher's students; returns message to be shown on message window*/
    public String giveGrade(Subject subject, Student student, int grade) {

        this.studentsGradesList.add(grade);
        student.getGrades(subject).add(grade);

        return (student.getName() + " " + student.getSurname() +
                " got grade: " + grade +
                ", from: " + subject +
                ". Teacher: " + this.getName() + " " + this.getSurname()) + "\n";
    }

/*    calculates the student's average grade from the subject taught by teacher;
    takes grades from student object's grades list from given subject
    calculates their sum, divides it by their number, rounds it to two decimal places and returns result;
    for the lack of grades it returns average of 0*/
    public double averageGrade(Student student) {

        Subject subject = this.getTaughtSubject();
        ArrayList<Integer> gradesList = student.getGrades(subject);

        double average;
        if (gradesList.size() > 0) {
            int sum = 0;
            for (int grade : gradesList) {
                sum += grade;
            }
            average = (double) sum / gradesList.size();
        } else {
            average = 0;
        }
        BigDecimal result = new BigDecimal(average);
        result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
        return result.doubleValue();
    }


/*    calculates the average grade of all teacher's students
    it uses grades list of all students of the teacher, on whom whe perform the method, for calculations
    it proceeds analogically to averageGrade()*/
    public double averageGlobal() {

        double average;
        if (this.studentsGradesList.size() > 0) {
            int sum = 0;
            for (int grade : this.studentsGradesList) {
                sum += grade;
            }
            average = (double) sum / this.studentsGradesList.size();
        } else {
            average = 0;
        }
        BigDecimal result = new BigDecimal(average);
        result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
        return result.doubleValue();

    }

    // checks if given integer is equal to 1, 2, 3, 4, 5 or 6 (returns true)
    // if not, it returns false
    public static boolean ValidGrade(int grade) {
        boolean validator;
        switch (grade) {
            case 1:
                validator = true;
                break;
            case 2:
                validator = true;
                break;
            case 3:
                validator = true;
                break;
            case 4:
                validator = true;
                break;
            case 5:
                validator = true;
                break;
            case 6:
                validator = true;
                break;
            default:
                validator = false;
        }
        return validator;
    }

    //GETTERS AND SETTERS
    public Subject getTaughtSubject() {
        return taughtSubject;
    }

    public ArrayList<Student> getTeacher_sStudentsList() {
        return teacher_sStudentsList;
    }

    public static ArrayList<Teacher> getTeachersList() {
        return teachersList;
    }

    public String toString() {
        return this.getName() + " " + this.getSurname() + ", taught subject: " +
                this.getTaughtSubject();
    }
}
