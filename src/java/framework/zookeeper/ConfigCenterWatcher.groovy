package framework.zookeeper

import org.apache.zookeeper.WatchedEvent
import org.apache.zookeeper.Watcher
import org.apache.zookeeper.ZooKeeper

/**
 * @author wuzj
 * @since 2014/12/25.
 */
class ConfigCenterWatcher implements Watcher{
    private ConfigCenter configCenter
    public ConfigCenterWatcher(ConfigCenter configCenter){
        this.configCenter = configCenter
    }
    @Override
    void process(WatchedEvent watchedEvent) {
        if (watchedEvent.type== Watcher.Event.EventType.None){
//            println watchedEvent.path
//            println watchedEvent.state
//            println watchedEvent.type
            if (watchedEvent.state == Watcher.Event.KeeperState.Expired){
                configCenter.zooKeeper = new ZooKeeper(configCenter.connectionStr,this,null)
            }
            return;
        }
        String s = new String(configCenter.zooKeeper.getData(watchedEvent.path,this,null),"UTF-8")
//        println "*****"+s
    }
}
