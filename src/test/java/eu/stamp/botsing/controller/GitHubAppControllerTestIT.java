package eu.stamp.botsing.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import eu.stamp.botsing.controller.event.EventFactoryImpl;
import eu.stamp.botsing.controller.event.filter.ConfigurationBasedLabelActionFilter;
import eu.stamp.botsing.controller.event.github.issues.GitHubIssuesActionFactoryImpl;
import eu.stamp.botsing.controller.event.github.issues.GitHubIssuesActionOpened;
import eu.stamp.botsing.controller.github.GitHubAppController;
import eu.stamp.botsing.service.github.GitHubService;
import eu.stamp.botsing.utility.ConfigurationBean;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GitHubAppController.class,EventFactoryImpl.class, GitHubIssuesActionFactoryImpl.class, 
		GitHubIssuesActionOpened.class,  
		ConfigurationBean.class, GitHubService.class,ConfigurationBasedLabelActionFilter.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class GitHubAppControllerTestIT {

	Logger log = LoggerFactory.getLogger(GitHubAppControllerTestIT.class);

	@Autowired
	private MockMvc mockMvc;
	String issueOpenedJson = "{\"action\":\"opened\",\"issue\":{\"url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/7\",\"repository_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app\",\"labels_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/7/labels{/name}\",\"comments_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/7/comments\",\"events_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/7/events\",\"html_url\":\"https://github.com/luandrea/testrepo-github-app/issues/7\",\"id\":425320024,\"node_id\":\"MDU6SXNzdWU0MjUzMjAwMjQ=\",\"number\":7,\"title\":\"Bug reproduction report\",\"user\":{\"login\":\"luandrea\",\"id\":1321508,\"node_id\":\"MDQ6VXNlcjEzMjE1MDg=\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/1321508?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/luandrea\",\"html_url\":\"https://github.com/luandrea\",\"followers_url\":\"https://api.github.com/users/luandrea/followers\",\"following_url\":\"https://api.github.com/users/luandrea/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/luandrea/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/luandrea/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/luandrea/subscriptions\",\"organizations_url\":\"https://api.github.com/users/luandrea/orgs\",\"repos_url\":\"https://api.github.com/users/luandrea/repos\",\"events_url\":\"https://api.github.com/users/luandrea/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/luandrea/received_events\",\"type\":\"User\",\"site_admin\":false},\"labels\":[{\"id\":1124779991,\"node_id\":\"MDU6TGFiZWwxMTI0Nzc5OTkx\",\"url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/labels/bug\",\"name\":\"bug\",\"color\":\"d73a4a\",\"default\":true}],\"state\":\"open\",\"locked\":false,\"assignee\":null,\"assignees\":[],\"milestone\":null,\"comments\":0,\"created_at\":\"2019-03-26T09:54:29Z\",\"updated_at\":\"2019-03-26T09:54:29Z\",\"closed_at\":null,\"author_association\":\"OWNER\",\"body\":\"java.lang.RuntimeException: Failed to load XML schemas: [classpath:pdp.xsd]\\r\\n        at org.ow2.authzforce.core.pdp.impl.SchemaHandler.createSchema(SchemaHandler.java:541)\\r\\n        at org.ow2.authzforce.core.pdp.impl.PdpModelHandler.<init>(PdpModelHandler.java:159)\\r\\n        at org.ow2.authzforce.core.pdp.impl.PdpEngineConfiguration.getInstance(PdpEngineConfiguration.java:682)\\r\\n        at org.ow2.authzforce.core.pdp.impl.PdpEngineConfiguration.getInstance(PdpEngineConfiguration.java:699)\\r\\n        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\\r\\n        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\\r\\n        at java.lang.reflect.Method.invoke(Method.java:498)\\r\\n        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:162)\\r\\n\"},\"repository\":{\"id\":157224078,\"node_id\":\"MDEwOlJlcG9zaXRvcnkxNTcyMjQwNzg=\",\"name\":\"testrepo-github-app\",\"full_name\":\"luandrea/testrepo-github-app\",\"private\":false,\"owner\":{\"login\":\"luandrea\",\"id\":1321508,\"node_id\":\"MDQ6VXNlcjEzMjE1MDg=\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/1321508?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/luandrea\",\"html_url\":\"https://github.com/luandrea\",\"followers_url\":\"https://api.github.com/users/luandrea/followers\",\"following_url\":\"https://api.github.com/users/luandrea/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/luandrea/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/luandrea/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/luandrea/subscriptions\",\"organizations_url\":\"https://api.github.com/users/luandrea/orgs\",\"repos_url\":\"https://api.github.com/users/luandrea/repos\",\"events_url\":\"https://api.github.com/users/luandrea/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/luandrea/received_events\",\"type\":\"User\",\"site_admin\":false},\"html_url\":\"https://github.com/luandrea/testrepo-github-app\",\"description\":\"repository di test per la github app\",\"fork\":false,\"url\":\"https://api.github.com/repos/luandrea/testrepo-github-app\",\"forks_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/forks\",\"keys_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/teams\",\"hooks_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/hooks\",\"issue_events_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/events\",\"assignees_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/tags\",\"blobs_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/languages\",\"stargazers_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/stargazers\",\"contributors_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/contributors\",\"subscribers_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/subscribers\",\"subscription_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/subscription\",\"commits_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/merges\",\"archive_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/downloads\",\"issues_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/deployments\",\"created_at\":\"2018-11-12T14:13:59Z\",\"updated_at\":\"2019-03-26T09:44:57Z\",\"pushed_at\":\"2019-03-26T09:44:56Z\",\"git_url\":\"git://github.com/luandrea/testrepo-github-app.git\",\"ssh_url\":\"git@github.com:luandrea/testrepo-github-app.git\",\"clone_url\":\"https://github.com/luandrea/testrepo-github-app.git\",\"svn_url\":\"https://github.com/luandrea/testrepo-github-app\",\"homepage\":null,\"size\":14,\"stargazers_count\":0,\"watchers_count\":0,\"language\":null,\"has_issues\":true,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":true,\"has_pages\":false,\"forks_count\":0,\"mirror_url\":null,\"archived\":false,\"open_issues_count\":7,\"license\":null,\"forks\":0,\"open_issues\":7,\"watchers\":0,\"default_branch\":\"master\"},\"sender\":{\"login\":\"luandrea\",\"id\":1321508,\"node_id\":\"MDQ6VXNlcjEzMjE1MDg=\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/1321508?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/luandrea\",\"html_url\":\"https://github.com/luandrea\",\"followers_url\":\"https://api.github.com/users/luandrea/followers\",\"following_url\":\"https://api.github.com/users/luandrea/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/luandrea/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/luandrea/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/luandrea/subscriptions\",\"organizations_url\":\"https://api.github.com/users/luandrea/orgs\",\"repos_url\":\"https://api.github.com/users/luandrea/repos\",\"events_url\":\"https://api.github.com/users/luandrea/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/luandrea/received_events\",\"type\":\"User\",\"site_admin\":false},\"installation\":{\"id\":779425,\"node_id\":\"MDIzOkludGVncmF0aW9uSW5zdGFsbGF0aW9uNzc5NDI1\"}}";
	String issueOpenedWrongTemplateJson = "{\"action\":\"opened\",\"issue\":{\"url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/6\",\"repository_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app\",\"labels_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/6/labels{/name}\",\"comments_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/6/comments\",\"events_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/6/events\",\"html_url\":\"https://github.com/luandrea/testrepo-github-app/issues/6\",\"id\":424997427,\"node_id\":\"MDU6SXNzdWU0MjQ5OTc0Mjc=\",\"number\":6,\"title\":\"Nuova issue per registrare l'evento\",\"user\":{\"login\":\"luandrea\",\"id\":1321508,\"node_id\":\"MDQ6VXNlcjEzMjE1MDg=\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/1321508?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/luandrea\",\"html_url\":\"https://github.com/luandrea\",\"followers_url\":\"https://api.github.com/users/luandrea/followers\",\"following_url\":\"https://api.github.com/users/luandrea/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/luandrea/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/luandrea/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/luandrea/subscriptions\",\"organizations_url\":\"https://api.github.com/users/luandrea/orgs\",\"repos_url\":\"https://api.github.com/users/luandrea/repos\",\"events_url\":\"https://api.github.com/users/luandrea/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/luandrea/received_events\",\"type\":\"User\",\"site_admin\":false},\"labels\":[],\"state\":\"open\",\"locked\":false,\"assignee\":null,\"assignees\":[],\"milestone\":null,\"comments\":0,\"created_at\":\"2019-03-25T16:27:16Z\",\"updated_at\":\"2019-03-25T16:27:16Z\",\"closed_at\":null,\"author_association\":\"OWNER\",\"body\":\"## Project source code\\r\\nhttps://github.com/STAMP-project/botsing\\r\\n\\r\\n## POM path\\r\\nbotsing/botsing-examples/pom.xml\\r\\n\\r\\n## Target frame\\r\\nmaster\\r\\n\\r\\n## Target frame\\r\\n1\\r\\n\\r\\n## Optional parameters\\r\\n-search_budget=1200\\r\\n\\r\\n## Crash log\\r\\njava.lang.ArithmeticException: / by zero\\r\\n    at eu.stamp.botsing.Fraction.getShiftedValue(Fraction.java:24)\"},\"repository\":{\"id\":157224078,\"node_id\":\"MDEwOlJlcG9zaXRvcnkxNTcyMjQwNzg=\",\"name\":\"testrepo-github-app\",\"full_name\":\"luandrea/testrepo-github-app\",\"private\":false,\"owner\":{\"login\":\"luandrea\",\"id\":1321508,\"node_id\":\"MDQ6VXNlcjEzMjE1MDg=\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/1321508?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/luandrea\",\"html_url\":\"https://github.com/luandrea\",\"followers_url\":\"https://api.github.com/users/luandrea/followers\",\"following_url\":\"https://api.github.com/users/luandrea/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/luandrea/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/luandrea/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/luandrea/subscriptions\",\"organizations_url\":\"https://api.github.com/users/luandrea/orgs\",\"repos_url\":\"https://api.github.com/users/luandrea/repos\",\"events_url\":\"https://api.github.com/users/luandrea/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/luandrea/received_events\",\"type\":\"User\",\"site_admin\":false},\"html_url\":\"https://github.com/luandrea/testrepo-github-app\",\"description\":\"repository di test per la github app\",\"fork\":false,\"url\":\"https://api.github.com/repos/luandrea/testrepo-github-app\",\"forks_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/forks\",\"keys_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/teams\",\"hooks_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/hooks\",\"issue_events_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/events\",\"assignees_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/tags\",\"blobs_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/languages\",\"stargazers_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/stargazers\",\"contributors_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/contributors\",\"subscribers_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/subscribers\",\"subscription_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/subscription\",\"commits_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/merges\",\"archive_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/downloads\",\"issues_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/deployments\",\"created_at\":\"2018-11-12T14:13:59Z\",\"updated_at\":\"2019-03-22T15:45:20Z\",\"pushed_at\":\"2019-03-22T15:45:18Z\",\"git_url\":\"git://github.com/luandrea/testrepo-github-app.git\",\"ssh_url\":\"git@github.com:luandrea/testrepo-github-app.git\",\"clone_url\":\"https://github.com/luandrea/testrepo-github-app.git\",\"svn_url\":\"https://github.com/luandrea/testrepo-github-app\",\"homepage\":null,\"size\":14,\"stargazers_count\":0,\"watchers_count\":0,\"language\":null,\"has_issues\":true,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":true,\"has_pages\":false,\"forks_count\":0,\"mirror_url\":null,\"archived\":false,\"open_issues_count\":6,\"license\":null,\"forks\":0,\"open_issues\":6,\"watchers\":0,\"default_branch\":\"master\"},\"sender\":{\"login\":\"luandrea\",\"id\":1321508,\"node_id\":\"MDQ6VXNlcjEzMjE1MDg=\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/1321508?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/luandrea\",\"html_url\":\"https://github.com/luandrea\",\"followers_url\":\"https://api.github.com/users/luandrea/followers\",\"following_url\":\"https://api.github.com/users/luandrea/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/luandrea/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/luandrea/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/luandrea/subscriptions\",\"organizations_url\":\"https://api.github.com/users/luandrea/orgs\",\"repos_url\":\"https://api.github.com/users/luandrea/repos\",\"events_url\":\"https://api.github.com/users/luandrea/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/luandrea/received_events\",\"type\":\"User\",\"site_admin\":false},\"installation\":{\"id\":779425,\"node_id\":\"MDIzOkludGVncmF0aW9uSW5zdGFsbGF0aW9uNzc5NDI1\"}}";
	String issueEditedWrongTemplateJson = "{\"action\":\"edited\",\"issue\":{\"url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/5\",\"repository_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app\",\"labels_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/5/labels{/name}\",\"comments_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/5/comments\",\"events_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/5/events\",\"html_url\":\"https://github.com/luandrea/testrepo-github-app/issues/5\",\"id\":424993778,\"node_id\":\"MDU6SXNzdWU0MjQ5OTM3Nzg=\",\"number\":5,\"title\":\"Nuova issue BUG\",\"user\":{\"login\":\"luandrea\",\"id\":1321508,\"node_id\":\"MDQ6VXNlcjEzMjE1MDg=\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/1321508?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/luandrea\",\"html_url\":\"https://github.com/luandrea\",\"followers_url\":\"https://api.github.com/users/luandrea/followers\",\"following_url\":\"https://api.github.com/users/luandrea/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/luandrea/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/luandrea/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/luandrea/subscriptions\",\"organizations_url\":\"https://api.github.com/users/luandrea/orgs\",\"repos_url\":\"https://api.github.com/users/luandrea/repos\",\"events_url\":\"https://api.github.com/users/luandrea/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/luandrea/received_events\",\"type\":\"User\",\"site_admin\":false},\"labels\":[],\"state\":\"open\",\"locked\":false,\"assignee\":null,\"assignees\":[],\"milestone\":null,\"comments\":0,\"created_at\":\"2019-03-25T16:20:17Z\",\"updated_at\":\"2019-03-25T16:25:18Z\",\"closed_at\":null,\"author_association\":\"OWNER\",\"body\":\"Esempio ISSUE\\r\\n\\r\\n## Project source code\\r\\nhttps://github.com/STAMP-project/botsing\\r\\n\\r\\n## POM path\\r\\nbotsing/botsing-examples/pom.xml\\r\\n\\r\\n## Target frame\\r\\nmaster\\r\\n\\r\\n## Target frame\\r\\n1\\r\\n\\r\\n## Optional parameters\\r\\n-search_budget=1200\\r\\n\\r\\n## Crash log\\r\\njava.lang.ArithmeticException: / by zero\\r\\n    at eu.stamp.botsing.Fraction.getShiftedValue(Fraction.java:24)\"},\"changes\":{\"body\":{\"from\":\"Esempio ISSUE\\r\\n=============\\r\\n\\r\\n## Project source code\\r\\nhttps://github.com/STAMP-project/botsing\\r\\n\\r\\n## POM path\\r\\nbotsing/botsing-examples/pom.xml\\r\\n\\r\\n## Target frame\\r\\nmaster\\r\\n\\r\\n## Target frame\\r\\n1\\r\\n\\r\\n## Optional parameters\\r\\n-search_budget=1200\\r\\n\\r\\n## Crash log\\r\\njava.lang.ArithmeticException: / by zero\\r\\n    at eu.stamp.botsing.Fraction.getShiftedValue(Fraction.java:24)\"}},\"repository\":{\"id\":157224078,\"node_id\":\"MDEwOlJlcG9zaXRvcnkxNTcyMjQwNzg=\",\"name\":\"testrepo-github-app\",\"full_name\":\"luandrea/testrepo-github-app\",\"private\":false,\"owner\":{\"login\":\"luandrea\",\"id\":1321508,\"node_id\":\"MDQ6VXNlcjEzMjE1MDg=\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/1321508?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/luandrea\",\"html_url\":\"https://github.com/luandrea\",\"followers_url\":\"https://api.github.com/users/luandrea/followers\",\"following_url\":\"https://api.github.com/users/luandrea/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/luandrea/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/luandrea/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/luandrea/subscriptions\",\"organizations_url\":\"https://api.github.com/users/luandrea/orgs\",\"repos_url\":\"https://api.github.com/users/luandrea/repos\",\"events_url\":\"https://api.github.com/users/luandrea/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/luandrea/received_events\",\"type\":\"User\",\"site_admin\":false},\"html_url\":\"https://github.com/luandrea/testrepo-github-app\",\"description\":\"repository di test per la github app\",\"fork\":false,\"url\":\"https://api.github.com/repos/luandrea/testrepo-github-app\",\"forks_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/forks\",\"keys_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/teams\",\"hooks_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/hooks\",\"issue_events_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/events\",\"assignees_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/tags\",\"blobs_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/languages\",\"stargazers_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/stargazers\",\"contributors_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/contributors\",\"subscribers_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/subscribers\",\"subscription_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/subscription\",\"commits_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/merges\",\"archive_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/downloads\",\"issues_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/luandrea/testrepo-github-app/deployments\",\"created_at\":\"2018-11-12T14:13:59Z\",\"updated_at\":\"2019-03-22T15:45:20Z\",\"pushed_at\":\"2019-03-22T15:45:18Z\",\"git_url\":\"git://github.com/luandrea/testrepo-github-app.git\",\"ssh_url\":\"git@github.com:luandrea/testrepo-github-app.git\",\"clone_url\":\"https://github.com/luandrea/testrepo-github-app.git\",\"svn_url\":\"https://github.com/luandrea/testrepo-github-app\",\"homepage\":null,\"size\":14,\"stargazers_count\":0,\"watchers_count\":0,\"language\":null,\"has_issues\":true,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":true,\"has_pages\":false,\"forks_count\":0,\"mirror_url\":null,\"archived\":false,\"open_issues_count\":5,\"license\":null,\"forks\":0,\"open_issues\":5,\"watchers\":0,\"default_branch\":\"master\"},\"sender\":{\"login\":\"luandrea\",\"id\":1321508,\"node_id\":\"MDQ6VXNlcjEzMjE1MDg=\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/1321508?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/luandrea\",\"html_url\":\"https://github.com/luandrea\",\"followers_url\":\"https://api.github.com/users/luandrea/followers\",\"following_url\":\"https://api.github.com/users/luandrea/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/luandrea/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/luandrea/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/luandrea/subscriptions\",\"organizations_url\":\"https://api.github.com/users/luandrea/orgs\",\"repos_url\":\"https://api.github.com/users/luandrea/repos\",\"events_url\":\"https://api.github.com/users/luandrea/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/luandrea/received_events\",\"type\":\"User\",\"site_admin\":false},\"installation\":{\"id\":779425,\"node_id\":\"MDIzOkludGVncmF0aW9uSW5zdGFsbGF0aW9uNzc5NDI1\"}}";

	@Test
	public void mockIssueOpened() throws Exception {


		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/botsing-github-app")
				.header("X-GitHub-Event", "issues")
				.accept(MediaType.APPLICATION_JSON)
				.content(issueOpenedJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		log.info(response.getContentAsString());
	}

	@Test
	public void mockIssueEdited() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/botsing-github-app")
				.header("X-GitHub-Event", "push")
				.accept(MediaType.APPLICATION_JSON)
				.content(issueEditedWrongTemplateJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		log.info(response.getContentAsString());
	}
}
