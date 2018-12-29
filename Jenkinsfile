try {
             timeout(time: 20, unit: 'MINUTES') {
                def appName="${APP_NAME}"
                def project=""
                node {
                  stage("Initialize") {
                    project = env.PROJECT_NAME
                  }
                }
                node("maven") {
                  stage("Checkout") {
                    git url: "${GIT_SOURCE_URL}", branch: "${GIT_SOURCE_REF}"
                  }
                  stage("Build WAR") {
                    sh "mvn clean package -Popenshift"
                    stash name:"war", includes:"target/ROOT.war"
                  }
                }
                node {
                  stage("Build Image") {
                    unstash name:"war"
                    sh "oc start-build ${appName}-docker --from-file=target/ROOT.war -n ${project}"
                    timeout(time: 20, unit: 'MINUTES') {
                      openshift.withCluster() {
                        openshift.withProject() {
                          def bc = openshift.selector('bc', "${appName}-docker")
                          echo "Found 1 ${bc.count()} buildconfig"
                          def blds = bc.related('builds')
                          blds.untilEach {
                            return it.object().status.phase == "Complete"
                          }
                        }
                      }  
                    }
                  }
                  stage("Deploy") {
                    openshift.withCluster() {
                      openshift.withProject() {
                        def dc = openshift.selector('dc', "${appName}")
                        dc.rollout().status()
                      }
                    }
                  }
                }
             }
          } catch (err) {
             echo "in catch block"
             echo "Caught: ${err}"
             currentBuild.result = 'FAILURE'
             throw err
          }
