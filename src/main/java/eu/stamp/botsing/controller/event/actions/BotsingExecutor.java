package eu.stamp.botsing.controller.event.actions;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.google.gson.JsonObject;

import eu.stamp.botsing.controller.event.ResponseBean;
import eu.stamp.botsing.controller.utils.Constants;
import eu.stamp.botsing.runner.MavenRunner;
import eu.stamp.botsing.service.CICDService;
import eu.stamp.botsing.utility.FileUtility;


public abstract class BotsingExecutor  {

	Logger log = LoggerFactory.getLogger(BotsingExecutor.class);
	
	private final String BOTSING_FILE = ".botsing";
	
	private final String GROUP_ID = "group_id";
	private final String ARTIFACT_ID = "artifact_id";
	private final String VERSION = "version";
	private final String SEARCH_BUDGET = "search_budget";
	private final String GLOBAL_TIMEOUT = "global_timeout";
	private final String POPULATION = "population";
	private final String MAX_TARGET_FRAME = "max_target_frame";
	private final String PACKAGE_FILTER = "package_filter";
	

	private CICDService cdciService;

	protected class BotsingParameters 
	{
		public String issueNumber, 
		repositoryName,
		repositoryURL,
		repositoryOwner,
		issueBody;
	
		public BotsingParameters() {
			// TODO Auto-generated constructor stub
		}
		
	}
	
	

	public ResponseBean executeBotsing (BotsingParameters botsingParameters) throws Exception
	{
		ResponseBean response = null;

		
		// read .botsing file
		String botsingFile = this.cdciService.getRawFile(botsingParameters.repositoryName, botsingParameters.repositoryOwner, BOTSING_FILE);

		if (botsingFile == null) {

			response = new ResponseBean(500,".botsing file not found");

		} else {

			// read botsing properties
			Properties botsingProperties = FileUtility.parsePropertiesString(botsingFile);

			if (botsingParameters.issueBody.length() > 0) {
				// TODO find a way to understand if this issue is a stacktrace that can be used by botsing

				log.debug("Received issue " + botsingParameters.issueNumber + " with a stacktrace");
				response = runBotsingAsExternalProcess(botsingParameters, botsingProperties);
	

			} else {
				response = new ResponseBean(400,"Received issue " + botsingParameters.issueNumber + " without stacktrace, issue will be ignored.");

			}
		}

		return response;

	}
	


	private ResponseBean runBotsingAsExternalProcess(BotsingParameters botsingParameters, Properties botsingProperties) throws IOException {
		ResponseBean result = null;

		log.info("Reading Botsing properties for issue " + botsingParameters.issueNumber);

		// prepare folder
		File workingDir = Files.createTempDir();

		// create dummy pom file
		File pomFile = new File(workingDir + (File.separator + "pom.xml"));
		FileUtils.writeStringToFile(pomFile, Constants.POM_FOR_BOTSING, Charset.defaultCharset());

		// create crashLog file
		File crashLogFile = new File(workingDir + (File.separator + "crash.log"));
		FileUtils.writeStringToFile(crashLogFile, botsingParameters.issueBody, Charset.defaultCharset());

		// read properties from .botsing file
		String groupId = botsingProperties.getProperty(GROUP_ID);
		String artifactId = botsingProperties.getProperty(ARTIFACT_ID);
		String version = botsingProperties.getProperty(VERSION);
		String searchBudget = botsingProperties.getProperty(SEARCH_BUDGET);
		String globalTimeout = botsingProperties.getProperty(GLOBAL_TIMEOUT);
		String population = botsingProperties.getProperty(POPULATION);
		String maxTargetFrame = botsingProperties.getProperty(MAX_TARGET_FRAME);
		String packageFilter = botsingProperties.getProperty(PACKAGE_FILTER);

		// add a comment before running botsing
		this.cdciService.createIssueComment(botsingParameters.repositoryName, botsingParameters.repositoryOwner, botsingParameters.issueNumber,
				"Start Botsing on artifact " + groupId + ":" + artifactId + ":" + version + " to reproduce the stacktrace in the issue.");

		// run Botsing
		log.info("Running Botsing on " + workingDir.getAbsolutePath());
		boolean noErrors = MavenRunner.runBotsingReproductionWithMaxTargetFrame(workingDir,
				crashLogFile.getAbsolutePath(), groupId, artifactId, version, maxTargetFrame, population, searchBudget,
				globalTimeout, packageFilter);

		if (noErrors == false) 
		{
			String comment = "Error executing Botsing";
			result = new ResponseBean(500,comment);
			this.cdciService.createIssueComment(botsingParameters.repositoryName, botsingParameters.repositoryOwner, botsingParameters.issueNumber, comment);

		} else {

			// get generated file as stringGitHubIssuesActionFactory.EVENT_NAME+"."+ACTION_OPENED;
			Collection<File> testFiles = FileUtility.search(workingDir.getAbsolutePath(),
					".*EvoSuite did not generate any tests.*", new String[] { "java" });

			if (testFiles != null) {
				result = new ResponseBean(200,"Botsing executed succesfully with reproduction test.");

				this.cdciService.createIssueComment(botsingParameters.repositoryName, botsingParameters.repositoryOwner, botsingParameters.issueNumber,
						"Botsing generate the following reproduction test.");

				for (File test : testFiles) {
					// Add comment to the issue with the test
					this.cdciService.sendFile(botsingParameters.repositoryName, botsingParameters.repositoryOwner, botsingParameters.issueNumber, test);
				}

			} else {

				String comment = "Botsing did not generate any reproduction test.";
				result = new ResponseBean(304, comment);
				this.cdciService.createIssueComment(botsingParameters.repositoryName, botsingParameters.repositoryOwner, botsingParameters.issueNumber, comment);
			}
		}

		return result;
	}

	


	protected void setService (CICDService service)
	{
		this.cdciService = service;
	}


	
}
