def DOCKER_IMAGE_TAG = params.getOrDefault("dockerImageTag", "")

pipeline {
    agent any

    parameters {
        string(defaultValue: DOCKER_IMAGE_TAG, description: '', name: 'dockerImageTag', trim: true);
    }

    environment {
        dockerPrivateRegistryProtocol = 'https'
        dockerPrivateRegistryUrl = 'hub.docker.com'
        dockerRegistryCredential = 'cce7e3df-5469-404a-ad0f-2ea9ebebfcd1'
        dockerImageName = 'astrodust/docker-jenkins-integration'

        // don't need to change
        envDockerImageTag = ''
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '95d711d7-6c13-4325-9a67-84f120243319', url: 'https://github.com/hrOarr/docker-jenkins-integration.git']]])
            }
        }

        stage('Mvn Package') {
            tools {
                maven 'Maven-3.6'
            }
            steps {
                sh "mvn -Dhttps.protocols=TLSv1.2 package"
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // sh 'docker build -t astrodust/docker-jenkins-integration .'
                    envDockerImageTag = docker.build dockerImageName + ":${DOCKER_IMAGE_TAG}"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                // withCredentials([usernamePassword(credentialsId: 'cce7e3df-5469-404a-ad0f-2ea9ebebfcd1', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                // sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                // }
                // sh 'docker push astrodust/docker-jenkins-integration'
                script{
                    docker.withRegistry( "${env.dockerPrivateRegistryProtocol}://${env.dockerPrivateRegistryUrl}", dockerRegistryCredential ) {
                        // shortCommitDockerImageTag.push()
                        envDockerImageTag.push()
                    }
                }
            }
        }

        stage('Deploy Docker Container') {
            steps {
                sh 'docker run -d -p 8080:8080 astrodust/docker-jenkins-integration'
            }
        }
    }
}