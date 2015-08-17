package framework.zookeeper

import org.apache.zookeeper.CreateMode
import org.apache.zookeeper.ZooDefs
import org.apache.zookeeper.ZooKeeper
import org.apache.zookeeper.data.Stat

/**
 * @author wuzj
 * @since 2014/12/26.
 */
class ZKCommander {
    public ZKCommander(ZooKeeper zooKeeper){
        this.zooKeeper = zooKeeper
    }
    private ZooKeeper zooKeeper
    static final int VERSION = -1

    void delete(String path) {
        zooKeeper.delete(path, VERSION)
    }

    String createPath(String path, String data) {
        return zooKeeper.create(path, data.getBytes("UTF-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
    }

    Stat update(String path, String data) {
        return zooKeeper.setData(path, data.getBytes("UTF-8"), VERSION)
    }

    List<String> list(String path) {
        return zooKeeper.getChildren(path, true)
    }

    def detail(String path) {
        return new String(zooKeeper.getData(path, true, null), "UTF-8")
    }
    Boolean exists(String path){
        return zooKeeper.exists(path,true) != null
    }
}
