pipeline {
    agent any
    tools {
        maven 'Jenkin-Maven'
        jdk 'Jenkin-Java'
    }
    stages {
        stage ('Initialize') {
            steps {
                bat '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Build') {
            steps {
                bat 'mvn -Dmaven.test.failure.ignore=true install'
            }
            post {
                success {
                    junit 'target/footballleague-reports/**/*.xml'
                }
            }
        }
    }
}