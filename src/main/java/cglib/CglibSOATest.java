package cglib;

/**
 * 描述
 * Created by liubingguang on 2017/8/11.
 */
public class CglibSOATest {

    public static void main(String[] args) {
        ClientConfig clientConfig = new ClientConfig("SOAClient", "FI555");
        SOAClientFactory client = SOAClientFactory.initialize(clientConfig);
        SOAClient soaClient = client.create();

        soaClient.base();
    }
}
