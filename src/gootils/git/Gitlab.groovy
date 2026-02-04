package gootils.git

import gootils.util.Json
import gootils.util.Shell

class Gitlab {
	String url
	String token

	Gitlab(String url, String token) {
		this.url = url
		this.token = token
	}

	def get(String path) {
		return Shell.sh("curl --silent --header PRIVATE-TOKEN:${token} ${url}/api/v4${path}", false)
	}

	def getAllMRs(String username) {
		int i = 1
		def currentMrs = []
		def mrs = []
		do {
			String response = this.get("/merge_requests?author_username=${username}&per_page=100&page=${i++}")
			currentMrs = Json.readJson(response)

			currentMrs.each {
				mrs.add(new PullRequest(it.id, it.title, it.web_url, it.merged_at))
			}
		} while (currentMrs.size() >= 100)

		return mrs
	}
}
