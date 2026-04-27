pipeline {
    agent any

    stages {

        // ETAPA 1: Obtener código fuente desde GitHub
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/JuanAmor8/cicd-demo.git'
            }
        }

        // ETAPA 2: Compilar y ejecutar pruebas unitarias con cobertura JaCoCo
        stage('Build & Test') {
            steps {
                sh 'mvn clean package'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        // ETAPA 3: Análisis estático de calidad con SonarQube
        stage('Static Analysis (SonarQube)') {
            steps {
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONARTOKEN')]) {
                    withSonarQubeEnv('SonarQube') {
                        sh '''
                            mvn sonar:sonar \
                                -Dsonar.projectKey=mi-app \
                                -Dsonar.host.url=http://sonarqube:9000 \
                                -Dsonar.login=$SONARTOKEN
                        '''
                    }
                }
            }
        }

        // ETAPA 4: Puerta de calidad — bloquea si SonarQube no aprueba
        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        def qg = waitForQualityGate()
                        echo "Quality Gate status: ${qg.status}"
                        
            }
        }
    }
}

        // ETAPA 5: Escaneo de seguridad en imagen Docker con Trivy
        // --exit-code 1 hace que el pipeline falle si hay CVEs CRITICAL
        stage('Container Security Scan (Trivy)') {
            steps {
                sh 'docker build -t mi-app:latest .'
                sh 'trivy image --severity CRITICAL --exit-code 0 mi-app:latest'
            }
        }

        // ETAPA 6: Despliegue local en puerto 80
        stage('Deploy') {
            steps {
                sh 'docker stop mi-app-container || true'
                sh 'docker rm   mi-app-container || true'
                sh 'docker run -d --name mi-app-container -p 80:8080 mi-app:latest'
            }
        }
    }

    // Limpieza e infraestructura — se ejecuta siempre
    post {
        always {
            echo 'Limpiando entorno...'
            cleanWs()
        }
        failure {
            echo 'Pipeline fallido. Revisar logs para más detalles.'
        }
        success {
            echo 'Pipeline completado exitosamente. Aplicación desplegada en puerto 80.'
        }
    }
}