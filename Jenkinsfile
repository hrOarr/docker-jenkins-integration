pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '95d711d7-6c13-4325-9a67-84f120243319', url: 'https://github.com/hrOarr/docker-jenkins-integration.git']]])
            }
        }

        stage('Mvn Package') {
            def mvnHome = tool name: 'Maven-6', type: 'maven'
            def mvnCMD = "${mvnHome}/bin/mvn"
            sh "${mvnCMD} clean package"
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t docker-jenkins-integration .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'cce7e3df-5469-404a-ad0f-2ea9ebebfcd1', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                  sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                }
                sh 'docker tag docker-jenkins-integration docker-home/docker-jenkins-integration'
                sh 'docker push docker-home/docker-jenkins-integration'
            }
        }

        stage('Deploy Docker Container') {
            steps {
                sh 'docker run -d -p 8080:8080 docker-home/docker-jenkins-integration'
              }
            }
        }
    }
}