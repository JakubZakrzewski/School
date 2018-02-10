package School;

import java.util.ArrayList;

public class Student extends Human {

    private ArrayList<Integer> gradesMat;
    private ArrayList<Integer> gradesPol;
    private ArrayList<Integer> gradesPhy;
    private ArrayList<Integer> gradesGeo;
    private Teacher teacherMat;
    private Teacher teacherPol;
    private Teacher teacherPhy;
    private Teacher teacherGeo;
    private static ArrayList<Student> studentsList = new ArrayList<>();


/*    Student's constructor
    creates student object with name and surname based on parent Human, with empty grades lists,
    assigned teachers according to arguments; after assigning teachers to student assigns student to teachers;
    adds student to static students list*/
    public Student(String name, String surname, Teacher teacherMat, Teacher teacherPol,
                   Teacher teacherPhy, Teacher teacherGeo) {
        super(name, surname);
        this.gradesMat = new ArrayList<>();
        this.gradesPol = new ArrayList<>();
        this.gradesPhy = new ArrayList<>();
        this.gradesGeo = new ArrayList<>();
        this.teacherMat = teacherMat;
        teacherMat.assignStudent(this);
        this.teacherPol = teacherPol;
        teacherPol.assignStudent(this);
        this.teacherPhy = teacherPhy;
        teacherPhy.assignStudent(this);
        this.teacherGeo = teacherGeo;
        teacherGeo.assignStudent(this);
        studentsList.add(this);
    }

    //GETTERS AND SETTERS

    //returns grades list from subject given as argument
    public ArrayList<Integer> getGrades (Subject subject) {
        ArrayList<Integer> gradesList = null;
        switch (subject) {
            case MATHEMATICS:
                gradesList = this.gradesMat;
                break;
            case POLISH_LANGUAGE:
                gradesList = this.gradesPol;
                break;
            case PHYSICS:
                gradesList = this.gradesPhy;
                break;
            case GEOGRAPHY:
                gradesList = this.gradesGeo;
                break;
        }
        return gradesList;
    }


    public static ArrayList<Student> getStudentsList() {
        return studentsList;
    }

    @Override
    public String toString() {
        return "Student: " + getName() + " " + getSurname() + "\n" +
                "grades from Mathematics " + gradesMat +
                ", teacher: " + teacherMat.getName() + " " + teacherMat.getSurname() +
                "\ngrades from Polish language " + gradesPol +
                ", teacher: " + teacherPol.getName() + " " + teacherPol.getSurname() +
                "\ngrades from Physics " + gradesPhy +
                ", teacher: " + teacherPhy.getName() + " " + teacherPhy.getSurname() +
                "\ngrades from Geography " + gradesGeo +
                ", teacher: " + teacherGeo.getName() + " " + teacherGeo.getSurname() + "\n";
    }
}
