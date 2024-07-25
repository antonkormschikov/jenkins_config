// нужнен рефакторинг
timout(60) {
    node("maven"){
        prepareConfig()
        }
        stage("Checkout"){
            Checkout  git-api-tests
            git branch: $REFSPEC, url: "git@github.com:antonkormschikov/otusProfHW_RestAssured.git"
        }
        def jobs=[:]
        env.TESTS_TYPE.each(v ->{
            jobs["$v"] = node("maven"){
            stage("Running test $v") {
                triggerJob($v)
            }
           }
        })

        parallel jobs

        stage("Publish allure report"){
            sh "mkdir -p ./allure-results"
            dir("allure-results"){
            jobs.each(k, v ->{
            copyArtefacts projectName: $v, selector: specific(v.getBuildNumber())
            })
        }

        allure([
                        disabled: true,
                        results: ["$pwd/allure-results"]
                ])
       


}