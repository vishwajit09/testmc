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

        stage('Deploy') {
            openshiftDeploy(deployementConfig: 'myapp1')
        }
    
  }
