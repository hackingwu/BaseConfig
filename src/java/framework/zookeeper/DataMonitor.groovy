package framework.zookeeper

import org.apache.zookeeper.AsyncCallback
import org.apache.zookeeper.WatchedEvent
import org.apache.zookeeper.Watcher
import org.apache.zookeeper.ZooKeeper
import org.apache.zookeeper.data.Stat

/**
 * @author wuzj
 * @since 2014/12/25
 */
class DataMonitor implements Watcher,AsyncCallback.StatCallback{

    public DataMonitor(ZooKeeper zk,String znode,Watcher chainedWatcher,DataMonitorListener listener){

    }
    public interface DataMonitorListener{
        /**
         *
         * @param data the existence status of the node has changed
         */
        void exists(byte[] data)
        /**
         *
         * @param rc zookeeper reason code or response code?
         */
        void closing(int rc)
    }
    @Override
    void processResult(int i, String s, Object o, Stat stat) {

    }

    @Override
    void process(WatchedEvent watchedEvent) {
        String path = watchedEvent.getPath()
        if (watchedEvent.getType() == Watcher.Event.EventType.None){

        }
    }
}
