package myspring.bean;

public class Person {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  void savePerson(){
        System.out.println("保存person");
        System.out.println("id为"+this.getId());
        System.out.println("name为"+this.getName());
    }
}
