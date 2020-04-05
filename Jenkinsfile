pipeline {
   agent any

   stages {
      stage('pull code') {
         steps {
            checkout([$class: 'GitSCM', branches: [[name: '*/${branch}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '4a323edf-c525-4ee1-a853-7b46f9b61bb9', url: 'http://192.168.80.128:82/plf_group/mp.git']]])
         }
      }
      stage('build project') {
         steps {
            sh 'mvn clean package -Dmaven.test.skip=true'
         }
      }
      stage('sonar project') {
           steps {
              script{
              //这里设置jenkins全局工具里设置的scanner名称
                scannerHome = tool 'sonar1'
              }
              //这里设置系统设置的 sonar server 的名称
              withSonarQubeEnv('sonar'){
                sh "${scannerHome}/bin/sonar-scanner"
              }
           }
        }
      stage('start project') {
            steps {
              sh '''cp target/mp-1.0-SNAPSHOT.jar /app
              cd /app
              sh stop.sh
              BUILD_ID=dontKillMe
              sh start.sh
              '''
            }
       }
   }
}