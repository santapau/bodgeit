stage('Start - Pull Request Merged') {
  echo 'Merge'
}


stage('Dev') {
   parallel(Build: {
	node{
		sh 'git branch --set-upstream-to=origin/master master && git pull'
     		sh 'ant compile build'
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
    }, SecurityTests_BDD_Security: {
        runTests(2)
    })
    echo "Results"
}

milestone 1
stage('Staging') {
        milestone 2
        node {
            echo 'staging'
        }
        //input message: "input message"
}

stage('QA') {
    parallel(SmokeTests: {
        runTests(2)
    }, RegressionTests: {
        runTests(2)
    }, SecurityTests_staging_BDD_Security: {
        runTests(2)
    })
    echo "Results"
}

milestone 3
stage ('Production') {
        node {
            echo 'Production server looks to be alive'
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

