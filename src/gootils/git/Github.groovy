package gootils.git

import gootils.util.Json
import gootils.util.Shell
import gootils.util.log.Log

import java.nio.charset.StandardCharsets

class Github {
	String server
	String token

	Github(String server = "https://api.github.com", String token) {
		this.server = server
		this.token = token
	}

	def get(String path) {
		return Shell.sh([
			"curl",
			"--request", "GET",
			"--url", server + path,
			"--header", "Authorization: Bearer ${token}",
			"--header", "X-GitHub-Api-Version:2022-11-28"
		], false)
	}

	def getAllPRs(String username) {
		int i = 1
		def prs = []
		do {
			String response = this.get("/search/issues?q=" + URLEncoder.encode("is:pr author:${username}", StandardCharsets.UTF_8) + "&sort=created&per_page=100&page=${i++}")
			def currentPrs = Json.readJson(response)
			if (currentPrs.incomplete_results) {
				i--
				Log.warn("Will retry search due to incomplete results")
				Thread.sleep(1000 * 5)
				continue
			}

			if (currentPrs.items.size() == 0) {
				break
			}

			currentPrs.items.each {
				def pr = Json.readJson(this.get(it.pull_request.url - this.server))
				prs.add(new PullRequest(pr.id, pr.title, pr.html_url, pr.merged_at))
				Thread.sleep(2000)
			}
		} while (true)

		return prs
	}
}
