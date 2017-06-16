package client;

/**
 * 描述
 * Created by liubingguang on 2017/5/15.
 */
@ProxyAnnotation(content = "class")
public class BusinessClass {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ProxyAnnotation(content = "method")
    public Object access() {
        return "haha";
    }
}
