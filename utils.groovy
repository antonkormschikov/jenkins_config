def prepareConfig(){
    def yamlConfig = readYaml text: $YAML_CONFIG
    
    yamlConfig.each(k, v -> System.setProperty(v))
}


def triggerJob(def jobName) {
    Job job = build job: $jobName
    job.getBuildNumber()
}

this