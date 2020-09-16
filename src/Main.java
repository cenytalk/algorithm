import myspring.YhxClassPathXmlApplicationContext;
import myspring.bean.Person;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        YhxClassPathXmlApplicationContext ctx = new YhxClassPathXmlApplicationContext("myspring/resources/bean1.xml");
        Person person=(Person) ctx.getBean("person");
    }
}
