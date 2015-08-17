package framework.impl

import framework.zookeeper.ConfigCenter
import framework.config.Configuration
import org.apache.zookeeper.ZooKeeper

/**
 * @author wuzj
 * 2014/11/25
 */
class ZooKeeperConfiguration implements Configuration{
    private ConfigCenter configCenter
    private String identity
    public ZooKeeperConfiguration(ConfigCenter configCenter,String identity){
        this.identity = identity
        this.configCenter = configCenter
    }
    @Override
    String get(String key) {
        try{
            if (!key.startsWith("/")){
                key = "/" + identity +"/"+ key
            }else{
                key = "/" + identity + key
            }
            return ConfigCenter.getConfig(key)
        }catch (Exception e){
            return null
        }
    }

}
