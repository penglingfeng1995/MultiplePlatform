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
            sh 'mvn clean package -Dmaven.test.skip=true docker:build'
         }
      }
      stage('upload image'){
        steps{
            sh 'docker tag mp:latest 192.168.80.129:83/mp/mp:latest'
            sh 'docker login -u admin -p Harbor12345 192.168.80.129:83'
            sh 'docker push 192.168.80.129:83/mp/mp:latest'
        }
      }
      stage('start project'){
        steps{
            sh 'sshpass -p "123456" ssh root@192.168.80.129 "docker pull 192.168.80.129:83/mp/mp:latest"'
            sh 'sshpass -p "123456" ssh root@192.168.80.129 "docker run -p8083:8083 -d 192.168.80.129:83/mp/mp:latest"'
        }
      }

   }
}