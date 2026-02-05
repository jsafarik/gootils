package gootils.git

import java.time.ZonedDateTime

class PullRequest {
	long id
	String title
	String url
	ZonedDateTime merged = null

	PullRequest(long id, String title, String url, String merged) {
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
