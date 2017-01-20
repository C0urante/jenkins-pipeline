package com.github.jcustenborder.jenkins.pipeline

properties([
        buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '10'))
])

def execute() {
    node {
        checkout scm

        stage('build') {
            docker.image(images.jdk8_docker_image).inside {
                configFileProvider([configFile(fileId: 'mavenSettings', variable: 'MAVEN_SETTINGS')]) {
                    def mvn = new MavenUtilities(steps, "$MAVEN_SETTINGS")
                    mvn.execute('clean package')
                }
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }
    }
}