package gootils.git

import java.time.ZonedDateTime

class PullRequest {
	int id
	String title
	String url
	ZonedDateTime merged = null

	PullRequest(int id, String title, String url, String merged) {
		this.id = id
		this.title = title
		this.url = url
		if (merged) {
			this.merged = ZonedDateTime.parse(merged)
		}
	}

	String toString() {
		return title
	}
}
