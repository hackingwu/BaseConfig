includeTargets << grailsScript("_GrailsInit")

target(setupConfig: "The description of the script goes here!") {
    // TODO: Implement script here
    def configFile = "${basedir}/grails-app/conf/ConfigurationConfig.groovy"
    if (!(configFile as File).exists() || confirmInput("configuration file already exists in your project.Overwrite it?")) {
        Ant.copy(
                file: "${baseConfigPluginDir}/grails-app/conf/DefaultConfigurationConfig.groovy",
                tofile: configFile,
                overwrite: true
        )
        event("CreatedFile",[configFile])
        event("StatusFinal",["Monitor configuration file was installed into /grails-app/conf/ConfigurationConfig.groovy"])
    }
    def propertyFile = "${basedir}/grails-app/conf/defaultConfig.properties"
    if (!(configFile as File).exists()) {
        Ant.copy(
                file: "${baseConfigPluginDir}/grails-app/conf/defaultConfig.properties",
                tofile: propertyFile,
                overwrite: true
        )
    } else {
        Ant.echo(file: propertyFile,
                message: "\n#zookeeper connection configuration\ncube.framework.zooKeeper.connectionStr=192.168.181.39",
                append: true
        )
    }
    confirmInput = { String message ->
        Ant.input(message: message, addproperty: "confirm.message", validargs: "y,n")
        Ant.antProject.properties."confirm.message" == "y"
    }
}

setDefaultTarget(setupConfig)
