pipeline {
	agent none
  stages {
  	stage('Maven Install') {
    	tools {
                        maven 'maven-3.6.3'
                 }
      steps {
      	sh 'mvn clean install'
      }
    }
  }
}