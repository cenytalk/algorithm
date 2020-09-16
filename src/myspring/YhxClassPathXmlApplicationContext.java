package myspring;

import java.lang.reflect.Field;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.beanutils.ConvertUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 简易spring框架的ioc容器
 */
public class YhxClassPathXmlApplicationContext {
    //存储所有的bean定义对象BeanDefinition
    List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
    //存储实例化的bean
    private Map<String, Object> beans = new HashMap<String, Object>();

    public YhxClassPathXmlApplicationContext() {
    }

    /**
     * 构造方法，模仿spring中ioc容器的行为
     *
     * @param fileName bean的定义文件
     */
    public YhxClassPathXmlApplicationContext(String fileName) {
        //1、读取spring的配置文件
        this.readXml(fileName);
        //2、实例化bean
        this.instanceBeans();
        //4、实现依赖对象的注入功能
        this.injectObject();

        //3、根据注解注入依赖对象
        this.annotationInjection();
    }

    /**
     * 根据文件名读取xml配置文件
     *
     * @param fileName
     */
    private void readXml(String fileName) {
        //创建一个读取器
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            //获取要读取的配置文件的路径
            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            //读取文件内容
            document = saxReader.read(xmlPath);
            //获取xml文件的根元素
            Element rootElement = document.getRootElement();
            //循环读取子元素
            for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                //获取bean的id属性值
                String id = element.attributeValue("id");
                //获取bean的class属性值
                String claszz = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(id, claszz);

                //获取bean的property属性
                for (Iterator subElementIterator = element.elementIterator(); subElementIterator.hasNext(); ) {
                    Element subElement = (Element) subElementIterator.next();
                    //获取property的name属性
                    String name = subElement.attributeValue("name");
                    //获取property的ref属性
                    String ref = subElement.attributeValue("ref");
                    //获取property的value属性
                    String value = subElement.attributeValue("value");
                    PropertyDefinition propertyDefinition = new PropertyDefinition(name, ref, value);
                    beanDefinition.getPropertyDefinitions().add(propertyDefinition);
                }

                beanDefinitions.add(beanDefinition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 实例化BeanDefinitons列表中的定义的bean
     */
    private void instanceBeans() {
        if (beanDefinitions != null && beanDefinitions.size() > 0) {
            //对每个bean进行实例化
            for (BeanDefinition beanDefinition : beanDefinitions) {
                try {
                    //bean的class属性存在时才实例化，否则不进行实例化
                    if (beanDefinition.getClassName() != null && !"".equals(beanDefinition.getClassName())) {
                        beans.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
                        System.out.println("id为" + beanDefinition.getId() + "实例化成功");
                    }
                } catch (Exception e) {
                    System.out.println("bean实例化失败");
                }
            }
        }
    }

    /**
     * 根据注解为bean注入属性
     */
    private void annotationInjection() {
        //遍历所有的bean
        for (String beanName : beans.keySet()) {
            Object bean = beans.get(beanName);
            if (bean != null) {
                try {
                    //1、先对属性方法进行处理，即setter方法上标识有注解的
                    //获取对象的BeanInfo信息
                    BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                    //获得bean的所有属性描述
                    PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                    for (PropertyDescriptor pd : descriptors) {
                        //获取属性的setter方法
                        Method setterMethod = pd.getWriteMethod();
                        //属性存在setter方法，且setter方法标识上存在YhxResource注解
                        if (setterMethod != null && setterMethod.isAnnotationPresent(YhxResource.class)) {
                            //获取setter方法的注解
                            YhxResource resource = setterMethod.getAnnotation(YhxResource.class);
                            Object value = null;
                            //注解的name属性不为空
                            if (resource != null && resource.name() != null && !"".equals(resource.name())) {
                                //根据注解的name属性从容器中取出来
                                value = beans.get(resource.name());
                            } else {
                                //根据属性的名称从容器中取出来
                                value = beans.get(pd.getName());
                                if (value == null) {
                                    //如果根据名称从容器里没找到,则遍历所有的bean,找到类型匹配的bean
                                    for (String key : beans.keySet()) {
                                        //判断类型是否匹配
                                        if (pd.getPropertyType().isAssignableFrom(beans.get(key).getClass())) {
                                            value = beans.get(key);
                                            break;//找到相同类型的bean,退出循环
                                        }
                                    }
                                }
                            }
                            setterMethod.setAccessible(true);
                            try {
                                //将引用对象注入到属性中
                                setterMethod.invoke(bean,value);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    //再对字段进行处理，即字段上标识有注解
                    //取得声明的所有字段
                    Field[] fields = bean.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        //判断字段上是否存在注解
                        if (field.isAnnotationPresent(YhxResource.class)) {
                            //获取字段上的注解
                            YhxResource resource = field.getAnnotation(YhxResource.class);
                            Object value = null;
                            if (resource != null && resource.name() != null && !"".equals(resource.name())) {
                                value = beans.get(resource.name());
                            } else {
                                //根据字段的名称从容器中找bean
                                value = beans.get(field.getName());
                                if (value == null) {
                                    //如果根据名称从容器里没找到,则遍历所有的bean,找到类型匹配的bean
                                    for (String key : beans.keySet()) {
                                        if (field.getType().isAssignableFrom(beans.get(key).getClass())) {
                                            value = beans.get(key);
                                            break;
                                        }
                                    }
                                }
                            }
                            field.setAccessible(true);
                            //将值value注入到bean对象上
                            try {
                                field.set(bean, value);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 为bean对象的属性注入值
     */
    private void injectObject() {
        //遍历配置文件中定义的所有bean
        for (BeanDefinition beanDefinition : beanDefinitions) {
            //找到要注入的bean
            Object bean = beans.get(beanDefinition.getId());
            if (bean != null) {
                try {
                    //通过类Introspector的getBeanInfo方法获取对象的BeanInfo信息
                    BeanInfo info = Introspector.getBeanInfo(bean.getClass());
                    //通过BeanInfo来获取属性的描述器(PropertyDescriptor)
                    //通过这个属性描述器可以获取某个属性对应的getter/setter方法，然后通过反射机制调用这些方法
                    //获取bean所有的属性描述
                    PropertyDescriptor[] pds = info.getPropertyDescriptors();
                    //遍历要注入的bean的所有属性
                    for (PropertyDefinition pdf : beanDefinition.getPropertyDefinitions()) {
                        //遍历要注入的bean通过属性描述器得到的所有属性及行为
                        for (PropertyDescriptor pd : pds) {
                            //用户定义的bean属性与java内省后的bean属性名称相同时
                            if (pdf.getName().equals(pd.getName())) {
                                //获取属性的setter方法
                                Method setterMethod = pd.getWriteMethod();
                                if (setterMethod != null) {
                                    //用来存储引用的值
                                    Object value = null;
                                    if (pdf.getRef() != null && !"".equals(pdf.getRef())) {
                                        //获取引用的对象的值
                                        value = beans.get(pdf.getRef());
                                    } else {
                                        //ConvertUtil依赖两个jar包，一个是common-beanutils,而common-beanutils又依赖Common-logging
                                        //ConvertUtil将任意类型转换为需要的类型
                                        value = ConvertUtils.convert(pdf.getValue(), pd.getPropertyType());
                                    }
                                    //保证setter方法可以访问私有
                                    setterMethod.setAccessible(true);
                                    try {
                                        //将引用对象注入到属性
                                        //注意此处的value要转换成setterMethod的参数类型,在上面ConvertUtil中完成
                                        setterMethod.invoke(bean, value);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                //找到属性后跳出循环
                                break;
                            }

                        }
                    }

                } catch (IntrospectionException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 通过bean的id来获取bean对象
     *
     * @param name
     * @return
     */
    public Object getBean(String name) {
        return beans.get(name);
    }
}
