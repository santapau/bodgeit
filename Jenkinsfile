stage('Start - Pull Request Merged') {
    node{
        echo 'Merge'
        git url: "https://github.com/santapau/bodgeit.git"
    }
}

stage('Dev') {
   parallel(Build: {
	node{
		sh 'git branch --set-upstream-to=origin/master master && git pull'
     	sh 'ant compile build'
     	sh 'wget http://localhost:9090/project/my_cool_web_app/risk -O - --content-on-error -q > tmp'
        env.RISK = readFile('tmp').trim()
        echo "${env.RISK}"
	} 
    }, UnitTest: {
        runTests(2)
    }, IntegrationTests: {
        runTests(2)
    })
}

stage('QA') {
    parallel(FunctionalTests: {
        runTests(2)
    }, PerformaceTests: {
        runTests(2)
    }, ZAP_BDDSecurity: {
        node {
            dir ('/var/jenkins_home/bdd-security/') {
                def returnStatus= sh (script:'./gradlew -Dcucumber.options="--tags @http_headers --tags ~@skip"', returnStatus:true)
                publishHTML (target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: '/var/jenkins_home/bdd-security/build/reports/cucumber/pretty/',
                    reportFiles: 'feature-overview.html',
                    reportName: "Staging_ZAP_BDD-Security Report"
                ])
               if (returnStatus != 0)
                {
                    error("ZAP_BDDSecurity failed")
 
                }
            }
        }
    })
    echo "Results"
}

milestone 1
stage('Staging') {
    milestone 2
}

stage('QA') {
    parallel(SmokeTests: {
        runTests(2)
    }, RegressionTests: {
        runTests(2)
    }, BDDSecurity: {
         node {
            dir ('/var/jenkins_home/bdd-security/') {
                def returnStatus= sh (script:'./gradlew -Dcucumber.options="--tags @http_headers --tags ~@skip"', returnStatus:true)
                publishHTML (target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: '/var/jenkins_home/bdd-security/build/reports/cucumber/pretty/',
                    reportFiles: 'feature-overview.html',
                    reportName: "Production_BDD-Security Report"
                ])
                if (returnStatus != 0)
                {
                    error("BDDSecurity failed")
 
                }
            }
        }
    })
    echo "Results"
}

milestone 3
stage ('Production') {
        node {
            echo 'Production server looks to be alive'
            if (Integer.parseInt(env.RISK) > 80){
                    currentBuild.result = 'FAILURE'
                    error("Risk is above the threshold --> 20")
            }
            else{
                currentBuild.result = 'SUCCESS'
                echo "SUCCESS"
        
            }
        }
}
def mvn(args) {
    echo "mvn"
}

def runTests(duration) {
    node {
        echo "tests"
    }
}
