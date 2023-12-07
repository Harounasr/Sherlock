public class Main {
    public static void main(String[] args) {
        Person person = new Person("Victor", 12);
        System.out.println(person.getAge());
        System.out.println(args[0]);
        if (args[1].contains("throws")) {
            throw new IllegalArgumentException("The argument was illegal.");
        }
    }
}
