node {

    stage('Initialize')
    {
        def dockerHome = tool 'docker'
        def mavenHome  = tool 'maven'
        env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${env.PATH}"
    }

    stage('Checkout') 
    {
        checkout scm
    }

      stage('Build') 
           {
           openshiftBuild(buildConfig:'myapp1',showBuildLogs :'true')  
          }
    
    stage('SonarQube analysis') {
    withSonarQubeEnv('sonarqube-server') {
      // requires SonarQube Scanner for Gradle 2.1+
      // It's important to add --info because of SONARJNKNS-281
      sh './gradlew --info sonarqube'
    }
  }

        stage('Deploy') {
            openshiftDeploy(deploymentConfig: 'myapp1')
        }
    
  }
