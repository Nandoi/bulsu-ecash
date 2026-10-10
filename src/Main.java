import entities.user.Student;
import entities.Card;

class Main{
    public static void main(String[] args) {
        Student student = new Student(
        1,
        "Fernando",
        "V.",
        "Vidal",
        "09123456789",
        "fernando@email.com",
        "2025-001234",
        "BSIT",
        2
        );

        Card card = new Card(1, "ABCD", student);
        
        System.out.println(card.getStudent().getFirstName());
        System.out.println();
    }
}