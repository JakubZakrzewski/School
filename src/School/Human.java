package School;

public abstract class Human {

    // Human abstract class allowing it's subclasses to have name and surname and pass them to their methods

    private String name;
    private String surname;

    Human(String name, String surname) {
        this. name = name;
        this. surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

}
