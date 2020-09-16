package myspring;
import	java.util.ArrayList;

import java.util.List;

/**
 * 解析xml文件后得到的bean定义文件
 */
public class BeanDefinition {
    //bean 的id
    private String id;
    //bean所属的类名
    private String className;

    //bean对象的属性
    private List<PropertyDefinition> propertyDefinitions=new ArrayList<PropertyDefinition> ();

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<PropertyDefinition> getPropertyDefinitions() {
        return propertyDefinitions;
    }

    public void setPropertyDefinitions(List<PropertyDefinition> propertyDefinitions) {
        this.propertyDefinitions = propertyDefinitions;
    }
}
