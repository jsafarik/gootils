package gootils.jira

import gootils.util.Json
import gootils.util.Shell

import java.nio.charset.StandardCharsets

class Jira {
	String server
	String token

	Jira(String server, String token) {
		this.server = server
		this.token = token
	}

	def get(String path) {
		return Shell.sh([
			"curl",
			"--silent",
			"--request", "GET",
			"--url", "${server}/rest/api/3${path}",
			"--header", "Authorization: Basic ${token}",
			"--header", "Content-Type:application/json"
		], false)
	}

	def getAllJirasOfUser(String username) {
		def issues = []
		String nextPageToken = ""
		while (true) {
			def response = Json.readJson(
				this.get("/search/jql?jql=" +
					URLEncoder.encode("assignee = \"${username}\" ORDER BY updated DESC", StandardCharsets.UTF_8) +
					"&maxResults=5000&fields=key,priority,summary,issuetype,status,creator,resolutiondate" + (nextPageToken ? "&nextPageToken=${nextPageToken}" : ""))
			)

			if (response.nextPageToken) {
				nextPageToken = response.nextPageToken
			}

			issues.addAll(response.issues.collect {
				return new Issue(
					it.fields.summary,
					it.key,
					it.fields.creator.emailAddress,
					it.fields.resolutiondate,
					it.fields.priority.name,
					it.fields.status.statusCategory.name,
					it.fields.status.name,
					it.fields.issuetype.name
				)
			})


			if (response.isLast == true) {
				break
			}
		}
		return issues
	}
}
