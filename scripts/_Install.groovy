//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//
/**
 *
 * @author wuzj
 * @since 2015/1/4
 */
def configFile = "${basedir}/grails-app/conf/ConfigurationConfig.groovy"
if (!(configFile as File).exists() || confirmInput("configuration file already exists in your project.Overwrite it?")) {
    ant.copy(
            file: "${baseConfigPluginDir}/grails-app/conf/DefaultConfigurationConfig.groovy",
            tofile: configFile,
            overwrite: true
    )
}
def propertyFile = "${basedir}/grails-app/conf/defaultConfig.properties"
if (!(configFile as File).exists()) {
    ant.copy(
            file: "${baseConfigPluginDir}/grails-app/conf/defaultConfig.properties",
            tofile: propertyFile,
            overwrite: true
    )
} else {
    ant.echo(file: propertyFile,
            message: "cube.framework.zooKeeper.connectionStr=192.168.181.39",
            append: true
    )
}
confirmInput = { String message ->
    ant.input(message: message, addproperty: "confirm.message", validargs: "y,n")
    ant.antProject.properties."confirm.message" == "y"
}
