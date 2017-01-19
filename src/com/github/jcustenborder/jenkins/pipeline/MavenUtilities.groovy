package com.github.jcustenborder.jenkins.pipeline

class MavenUtilities implements Serializable {
    def steps

    MavenUtilities(steps) { this.steps = steps; }

    def changeVersion(SString version) {
        if (env.BRANCH_NAME == 'master') {
            steps.sh "mvn --batch-mode versions:set -DgenerateBackupPoms=false -DnewVersion=${version}"
        }
    }

    def execute(String goals, String stage='build') {
        steps.stage(stage) {
            steps.configFileProvider([configFile(fileId: 'mavenSettings', variable: 'MAVEN_SETTINGS')]) {
                steps.sh "mvn --settings ${MAVEN_SETTINGS} --batch-mode ${goals}"
            }
        }
    }
}

