pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '95d711d7-6c13-4325-9a67-84f120243319', url: 'https://github.com/hrOarr/docker-jenkins-integration.git']]])
            }
        }

        stage('Build and Test') {
            steps {
                sh './mvnw clean install'
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker build -t spring-test .'
                sh 'docker run -p 8080:8080 spring-test'
            }
        }
    }
}