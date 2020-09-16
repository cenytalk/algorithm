package myspring.bean;

import myspring.YhxResource;

public class PersonServiceImpl implements PersonService {

  /*  @YhxResource*/
    private Person person;
    private Integer age;
  /*  private String age;*/

    public Person getPerson() {
        return person;
    }

   @YhxResource(name="person")
    public void setPerson(Person person) {
        this.person = person;
    }

   /* public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }*/

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public void savePerson() {
        System.out.println("age:" + age);
        System.out.println("service中的save方法调用成功");
        System.out.println("person的id为" + person.getId());
        System.out.println("person的name为" + person.getName());
        person.savePerson();
    }
}
