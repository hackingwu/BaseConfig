package framework.zookeeper

import framework.config.impl.DefaultConfigProvider
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.KeeperException
import org.apache.zookeeper.KeeperException.SessionExpiredException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper

/**
 * @author liuxy , wuzj
 * @since 2014/10/21.
 * @version 2014/12/25
 */
public class ConfigCenter {

    Log log = LogFactory.getLog(ConfigCenter.class);

    private static ConfigCenter configCenter = null;

    private ZooKeeper zooKeeper;

    private String connectionStr;

    private static Map<String, String> configMap = new HashMap<String, String>();

    private static final int ZK_TIMEOUT = 30000

    private static int ERROR_COUNT = 0

    private ConfigWatcher configWatcher = new ConfigWatcher();

    private ZKCommander zkCommander

    private static final long interval = 60 * 1000 * 30

    private ConfigCenter() {//host->connectionStr,支持写法ip，ip:port
        setConnectionStr()
        buildZK()
        zkCommander = new ZKCommander(zooKeeper)
        new Thread(){
            public void run(){
                while (true) {
                    try{
                        setupConfigMap()
                    }catch (SessionExpiredException e){
                        buildZK()
                        setupConfigMap()
                    }
                    Thread.currentThread().sleep(60 * 1000 * 30)
                }
            }
        }.start()
    }

    public static ConfigCenter getInstance() {
        if (configCenter == null) {
            configCenter = new ConfigCenter();
        }
        return configCenter;
    }

    ZKCommander getZKCommander() {
        return zkCommander
    }

    ZooKeeper getZooKeeper() {
        return zooKeeper;
    }


    public class ConfigWatcher implements Watcher {

        public void process(WatchedEvent event) {
            log.info(event.toString());
            try {
                if (event.getType().equals(Watcher.Event.EventType.None)) {
                    if (event.getState().equals(Watcher.Event.KeeperState.Expired)) {
                        try {
                            buildZK()
                            setupConfigMap()
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                    return;
                } else if (event.getType().equals(Watcher.Event.EventType.NodeChildrenChanged)) {
                    //增加操作可以实时，删除操作只能通过定时器去调度舒心真个configMap，删除操作比较少。
                    String path = event.getPath()
                    if (!event.getPath().endsWith("/"))
                        path = path + "/"
                    zkCommander.list(event.getPath()).each {
                        configMap.put(path+it, zkCommander.detail(path+it))
                    }
                } else if (event.getType().equals(Watcher.Event.EventType.NodeDataChanged)) {
                    configMap.put(event.getPath(), zkCommander.detail(event.getPath()))
                }
            } catch (KeeperException e) {
                if (ERROR_COUNT++ <1)
                    log.error(e.getMessage());
                //e.printStackTrace();

            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }

        }

    }

    private void buildZK(){
        try{
            zooKeeper = new ZooKeeper(connectionStr, ZK_TIMEOUT, configWatcher);
        }catch (Exception e){
            log.error('',e)
        }
    }
    private void setupConfigMap() {
        Map<String, String> map = new HashMap<String, String>()
        updateConfigMap("/", map)
        configMap.putAll(map)
        map = null
    }

    private void updateConfigMap(String path, Map map) {
        map.put(path, zkCommander.detail(path))
        zkCommander.list(path).each { updateConfigMap((path + "/" + it).replace("//", "/"), map) }
    }

    private void setConnectionStr() {
        connectionStr = new DefaultConfigProvider()
                            .buildConfiguration([:])
                            .get("cube.framework.zooKeeper.connectionStr")
    }

    public static String getConfig(String key) {
        return configMap.get(key);
    }
}
