import framework.config.impl.DefaultConfigProvider
import framework.impl.ZooKeeperConfigProvider

cube.config = [
        defaultProvider: false,
        providers      : [
                ['provider': ZooKeeperConfigProvider.class, 'identityType': ZooKeeperConfigProvider.ZOOKEEPER_APPLICATION_IDENTIFIER_IP, 'identityArg': null],
                ['provider': ZooKeeperConfigProvider.class, 'identityType': ZooKeeperConfigProvider.ZOOKEEPER_APPLICATION_IDENTIFIER_FILE, 'identityArg': '../zookeeperIdentifier.txt'],
                ['provider': ZooKeeperConfigProvider.class],
                ['provider': DefaultConfigProvider.class]
        ]
]