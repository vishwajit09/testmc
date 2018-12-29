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
            sh 'uname -a'
            sh 'mvn -B -DskipTests clean package'  
          }

        stage('Test') 
        {
            steps {
                //sh 'mvn test'
                sh 'echo "test"'
            }
            
        }

        stage('Deliver') 
          {
                sh 'bash ./jenkins/deliver.sh'
        }
}
