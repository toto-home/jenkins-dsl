import groovy.json.JsonSlurper

def reposJSON = new JsonSlurper().parseText(readFileFromWorkspace('repos.json'))

reposJSON.repos.each {
    createPipeline(it)
}

void createPipeline(it) {

    def jobname = it.jobname
    def gitrepo = it.gitrepo
    def desc = it.description
    pipelineJob(jobname) {
        description(desc)
        parameters {
            choiceParam('Happy', ['Yes (default)', 'Sure', 'Of course'], 'Are you happy?')
        }

        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(gitrepo)
                        }
                    }
                }
            }
        }
    }
}
