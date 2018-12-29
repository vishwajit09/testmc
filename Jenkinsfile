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

    stage('deploy') {
      steps {
        script {
            openshift.withCluster() {
                openshift.withProject() {
                  def rm = openshift.selector("dc", templateName).rollout()
                  timeout(5) { 
                    openshift.selector("dc", templateName).related('pods').untilEach(1) {
                      return (it.object().status.phase == "Running")
                    }
                  }
                }
            }
        }
      }
    }
    stage('tag') {
      steps {
        script {
            openshift.withCluster() {
                openshift.withProject() {
                  openshift.tag("${templateName}:latest", "${templateName}-staging:latest") 
                }
            }
        }
      }
    }
  }
