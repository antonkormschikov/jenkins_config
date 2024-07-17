timout(60) {
    node("maven"){
        def testContainerName = "api_tests_$BUILD_NUMBER"
        try {
            def testContainerName = "api_tests_$BUILD_NUMBER"
            stage("Run API tests") {
                sh "docker run --rm --network=host --name $testContainerName -v $pwd/allure-rezults:/home/anton/target/allure-rezults -t api-tests"
            }
            stage("Publish Allure report"){
                allure([
                        disabled: true,
                        results: ["$pwd/allure-results"]
                ])
            }
            stage("Telegram notification"){
             /*   def allureReport readFile text: $pwd/allure-results/export/influxDbData.txt
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                                                 .uri(URI.create("http://example.com/resource"))
                                                 .POST()
                                                 .build();*/
            }
        } finally {
            sh "docker stop $testContainerName"
        }

    }
}

