timeout(60) {
    node("maven"){
        def testContainerName = "api_tests_$BUILD_NUMBER"
        try {
            stage("Run API tests") {
                sh "docker run --rm --network=host --name $testContainerName -v $pwdpwd/allure-rezults:/home/anton/target/allure-rezults -t api-tests"
            }
            stage("Publish Allure report"){

                allure([
                        disabled: true,
                        results: ["$pwd/allure-results"]
                ])
            }
            stage("Telegram notification"){
                    File[] files = new File("$pwd/allure-results").listFiles()
               for (File file in files){
                sh String.format("curl -s -X POST https://api.telegram.org/bot7460003126:AAGHETwA70UMIQokbKS9_hJaOR7eOeDCWKo/sendMessage -d chat_id=-4272423291 -d text=%s",file.getText('UTF-8')
               }

            }
        } finally {
            sh "docker stop $testContainerName"
        }

    }
}

