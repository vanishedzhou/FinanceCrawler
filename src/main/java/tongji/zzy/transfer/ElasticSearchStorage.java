package tongji.zzy.transfer;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import tongji.zzy.resource.FCLog;

//import static java.net.InetAddress.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.common.xcontent.XContentFactory.*;


/**
 * Created by zhouzhiyong on 2016/12/1.
 */
public class ElasticSearchStorage {
    private static final Logger logger = FCLog.getLogger(ElasticSearchStorage.class);
//    public static TransportClient client = TransportClient.builder().build()
//            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("http://114.215.125.13/"),9300));
    public static TransportClient client = null;
    static {
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("114.215.125.13"), 9300));
        } catch (UnknownHostException e) {
            logger.error("unknow host.pls check",e);
        } catch (Exception e) {
            logger.error("client setup error", e);
        }
    }


    /**
     *
     * @param jsonFields 存储的数据内容fields （columns）
     * @param index 存储的index（db）
     * @param type 存储的type（table）
     * @param document 存储的document（rows）
     */
    public static void esStorage(String jsonFields, String index, String type, String document) {
        try {
            IndexResponse response = client.prepareIndex(index, type, document)
                    .setSource(jsonFields)
                    .get();
        } catch (Exception e) {
            logger.error("store data to elasticsearch error.",e);
        }
    }

    public static void main(String[] args) {
        String jsonStr = "{\"ZZY\":\"is a handsome man\"}";
        esStorage("test","zzy","1",jsonStr);
    }
}
