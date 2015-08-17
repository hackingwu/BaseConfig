package framework.impl

import framework.zookeeper.ConfigCenter
import framework.config.ConfigProvider
import framework.config.Configuration

/**
 * @author wuzj
 * 2014/11/25
 */
class ZooKeeperConfigProvider implements ConfigProvider{

    public static final Integer ZOOKEEPER_APPLICATION_IDENTIFIER_IP = 1;
    public static final Integer ZOOKEEPER_APPLICATION_IDENTIFIER_FILE = 2;
    @Override
    Configuration buildConfiguration(Map args) {
        return new ZooKeeperConfiguration(ConfigCenter.getInstance(),getIdentifier(args))
    }

    String getIdentifier(Map args){
        String identifier = ""
        if (args.containsKey("identityType")){
            if (args.get("identityType").equals(ZOOKEEPER_APPLICATION_IDENTIFIER_IP)){
                identifier = InetAddress.getLocalHost().getHostAddress()
            }else if(args.get("identityType").equals(ZOOKEEPER_APPLICATION_IDENTIFIER_FILE)){
                File file = new File(args.get("identityArg"))
                if (file.exists()){
                    FileInputStream fileInputStream = new FileInputStream(file);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
                    identifier = inputStreamReader.readLine()
                }
            }
        }
        return identifier
    }
}
