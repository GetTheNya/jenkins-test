pipeline {
    agent any
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Run') {
            steps {
                sh './gradlew bootRun'
            }
        }
    }
}
