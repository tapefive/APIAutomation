pipeline {
    agent any
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'master', url: 'https://github.com/tapefive/APIAutomation'
            }
        }
        stage('Install Dependencies') {
            steps {
                // Install Newman locally
                bat 'npm install newman'
                
                // Confirm Newman installation
                bat 'node node_modules\\newman\\bin\\newman.js -v'
            }
        }
        stage('Run Postman Collection') {
            steps {
                // Run Newman with proper paths and escaping
                bat """
                node node_modules\\newman\\bin\\newman.js run C:\\Users\\{USERNAME}\\Desktop\\Postman\\APIAutomation\\GoREST.postman_collection.json ^
                    --environment C:\\Users\\{USERNAME}\\Desktop\\Postman\\APIAutomation\\MyEnvironment.postman_environment.json ^
                    --env-var ^"ACCESSTOKEN=${ACCESSTOKEN}^" ^
                    --reporters cli
                """
            }
        }
    }
}
