podTemplate(label: JOB_NAME, containers: [
    containerTemplate(name: 'docker', image: 'docker', ttyEnabled: true, command: 'cat'),
    containerTemplate(name: 'maven', image: 'maven:3.5.3-jdk-8', command: 'cat', ttyEnabled: true)
  ],
  envVars: [envVar(key: 'PROJECT', value: 'biosmart'),envVar(key: 'APP', value: 'license_service')],
  volumes: [
    hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock'),
    configMapVolume(configMapName: 'maven-settings', mountPath: '/root/.m2'),
    persistentVolumeClaim(claimName: 'maven-repo', mountPath: '/var/jenkins_home/.m2nrepo/')]) {
    node(JOB_NAME) {
        checkout scm
        gitlabBuilds(builds: ["build", "package", "deploy"]) {
            stage("build") {
                gitlabCommitStatus(name: "build") {
                    container('maven') {
                        script {
                            env.OLD_VERSION = sh(returnStdout: true, script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout')
                            env.VERSION = env.OLD_VERSION + '-' + env.BUILD_NUMBER
                        }
                        sh 'mvn versions:set -DnewVersion=$VERSION versions:commit'
                        sh 'mvn clean package -U -Ddockerfile.skip'
                    }
                }
            }
            stage("package") {
                gitlabCommitStatus(name: "package") {
                    container('docker') {
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', 
                                credentialsId: 'seamfix-registry',
                                usernameVariable: 'SEAMFIX_REGISTRY_USER', 
                                passwordVariable: 'SEAMFIX_REGISTRY_PASSWORD']]) {
                            sh 'docker login -u ${SEAMFIX_REGISTRY_USER} -p ${SEAMFIX_REGISTRY_PASSWORD} ${DOCKER_REGISTRY}'
                            sh 'docker build --pull -t ${DOCKER_REGISTRY}/${PROJECT}/${APP}:$VERSION .'
                            sh 'docker push ${DOCKER_REGISTRY}/${PROJECT}/${APP}:$VERSION'
                        }
                    }
                }
            }
            stage("deploy") {
                gitlabCommitStatus(name: "deploy") {
                    build job: 'k8-deploy', 
                        parameters: [
                            string(name: 'ENVIRONMENT', value: 'test'),
                            string(name: 'APP', value: APP.replace("_", "-")),
                            string(name: 'VERSION', value: VERSION),
                            string(name: 'NAMESPACE', value: PROJECT)
                        ]
                }
            }
        }
    }
}