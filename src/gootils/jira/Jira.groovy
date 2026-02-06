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
			"--url", "${server}/rest/api/2${path}",
			"--header", "Authorization: Bearer ${token}",
			"--header", "Content-Type:application/json"
		], false)
	}

	def getAllJirasOfUser(String username) {
		int i = 0
		def issues = []
		while (true) {
			def response = Json.readJson(
				this.get("/search?jql=" +
					URLEncoder.encode("assignee = \"${username}\" ORDER BY updated DESC", StandardCharsets.UTF_8) +
					"&startAt=${i}&maxResults=100&fields=key,priority,summary,issuetype,status,creator,resolutiondate")
			)

			if (response.issues.size() == 0) {
				break
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

			i += 100;
		}
		return issues
	}
}
