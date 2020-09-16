import myspring.YhxClassPathXmlApplicationContext;
import myspring.bean.Person;
import myspring.bean.PersonService;
import org.junit.Test;

public class SpringTest {

    @Test
    public void testInstance() {
        YhxClassPathXmlApplicationContext ctx = new YhxClassPathXmlApplicationContext("myspring/resources/bean1.xml");
        Person person=(Person) ctx.getBean("person");
        person.setId("123");
        person.setName("yhx");
        System.out.println(person.getId());
        System.out.println(person.getName());
    }

    @Test
    public void testInjection(){
        YhxClassPathXmlApplicationContext ctx = new YhxClassPathXmlApplicationContext("myspring/resources/bean1.xml");
        PersonService personService=(PersonService)ctx.getBean("personService");
        personService.savePerson();
    }

    @Test
    public void testAnnotation(){//测试注解
        YhxClassPathXmlApplicationContext ctx = new YhxClassPathXmlApplicationContext("myspring/resources/bean1.xml");
        PersonService personService=(PersonService)ctx.getBean("personService");
        personService.savePerson();
    }
}
