package gootils.jira

import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Issue {
	String summary
	String key
	String creator
	OffsetDateTime resolutionDate
	String priority
	String resolution
	String status
	String type

	Issue(String summary, String key, String creator, String resolutionDate, String priority, String resolution, String status, String type) {
		this.summary = summary
		this.key = key
		this.creator = creator
		if (resolutionDate) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
			this.resolutionDate = OffsetDateTime.parse(resolutionDate, formatter)
		}
		this.priority = priority
		this.resolution = resolution
		this.status = status
		this.type = type
	}
}
