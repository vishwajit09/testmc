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
      sh 'mvn sonar:sonar'
    } // SonarQube taskId is automatically attached to the pipeline context
  }
    
    stage("Quality Gate"){
  timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
    def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
    if (qg.status != 'OK') {
      error "Pipeline aborted due to quality gate failure: ${qg.status}"
    }
  }
    }
        stage('Deploy') {
            openshiftDeploy(deploymentConfig: 'myapp1')
        }
    
  }
