pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/JuanAmor8/cicd-demo.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Static Analysis (SonarQube)') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=mi-app -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=${SONAR_TOKEN}'
                }
            }
            environment {
                SONAR_TOKEN = credentials('sonar-token')
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Container Security Scan (Trivy)') {
            steps {
                sh 'docker build -t mi-app:latest .'
                sh 'trivy image --severity LOW,MEDIUM,HIGH --exit-code 0 mi-app:latest'
                sh 'trivy image --severity CRITICAL --exit-code 1 mi-app:latest'
            }
        }

        stage('Deploy') {
            when { branch 'master' }
            steps {
                sh 'docker stop mi-app-running 2>/dev/null || true'
                sh 'docker rm mi-app-running 2>/dev/null || true'
                sh 'docker run -d --name mi-app-running -p 80:8080 mi-app:latest'
            }
        }
    }

    post {
        always {
            echo 'Limpiando entorno...'
            cleanWs()
        }
        success {
            echo '✅ Pipeline ejecutado exitosamente.'
        }
        failure {
            echo '❌ Pipeline fallido. Revisar logs para más detalles.'
        }
    }
}