package gootils.util

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

class Json {
	def static readJson(String json) {
		return new JsonSlurper().parseText(json)
	}

	static String writeJson(def content) {
		return new JsonBuilder(content).toString()
	}
}
