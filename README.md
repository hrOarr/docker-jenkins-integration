## Docker Jenkins Integration
### Prerequisites
 - Install Docker and Git
 - Use Github and Docker-hub account

### Run Jenkins
You can use Jenkins docker image to run a container using command line. Also, you can make it done by docker-compose.yml file.
```
version: '3'
services:
  jenkins:
    image: jenkins/jenkins:lts
    ports:
      - 8181:8080
      - 50000:50000
    volumes:
      - ./jenkins_home:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
```
You can use the following command to start up the container - 

`docker-compose up -d`

Afterward, Jenkins should be visible on your web browser at localhost:8181

### Create Dockerfile to run application
You need to create a dockerfile to build an image for application. You can also look into each instruction how it works.
```
FROM openjdk:17
EXPOSE 8080

ADD target/<final-name>.jar <final-name>.jar
ENTRYPOINT ["java", "-jar", "<final-name>.jar"]
```

### Create a Pipeline Job
 - Create new item and enter your item name
 - Click on Pipeline and save it
 - Configure the pipeline to refer to GitHub for source control management by selecting Pipeline script from SCM. You need to give github url where the Jenkinsfile is located.

### Create Jenkinsfile
Create a Jenkinsfile to instruct our Pipeline job on what needs to be done.
At first, we add our agent as an executor environment. We also define variables, params and environment variables to make everything readable and precise.
```
def DOCKER_IMAGE_TAG = params.getOrDefault("dockerImageTag", "")

pipeline {
    agent any

    parameters {
        string(defaultValue: DOCKER_IMAGE_TAG, description: '', name: 'dockerImageTag', trim: true);
    }

    environment {
        // docker related variables
        dockerRegistryCredential = '<docker-credential-from-jenkins>'
        dockerImageName = '<username>/<image-name>' // example - astrodust/docker-jenkins-integration

        // don't need to change
        envDockerImageTag = ''
    }
}
```

Next, creating first stage where Git Checkout is executed.
```
stages {
    stage('Git Checkout') {
        steps {
            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '<git-credential-id-from-jenkins>', url: '<git-repository-url>']]])
        }
    }
}
```
Add the following stages one by one
```
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
                    envDockerImageTag = docker.build dockerImageName + ":${DOCKER_IMAGE_TAG}"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script{
                    docker.withRegistry( '', dockerRegistryCredential ) {
                        envDockerImageTag.push()
                    }
                }
            }
        }

        stage('Deploy Docker Container') {
            steps {
                sh "docker run -d -p 8080:8080 ${dockerImageName}"
            }
        }
```