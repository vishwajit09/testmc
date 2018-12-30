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
      sh 'mvn clean package sonar:sonar'
    } // SonarQube taskId is automatically attached to the pipeline context
  }

        stage('Deploy') {
            openshiftDeploy(deploymentConfig: 'myapp1')
        }
    
  }
