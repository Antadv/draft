package client;

/**
 * 描述
 * Created by liubingguang on 2017/5/15.
 */
public class ProxyTest {

    public static void main(String[] args) {
        ProxyFactory factory = new ProxyFactory();
        BusinessClass businessClass = (BusinessClass) factory.create(BusinessClass.class);
        System.out.println(businessClass.access());
    }
}
