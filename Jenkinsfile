pipeline {
	agent none
  stages {
    stage('Initialize'){
        def dockerHome = tool 'docker'
        env.PATH = "${dockerHome}/bin:${env.PATH}"
        steps{
           echo "PATH is: ${env.PATH}"
        }
    }
  	stage('Maven Install') {
    	agent {
      	docker {
        	image 'maven:4.0.0'
        }
      }
      steps {
      	sh 'mvn clean install'
      }
    }
  }
}