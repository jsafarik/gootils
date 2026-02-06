package gootils.util

import gootils.util.log.Log

class Shell {
	def static sh(def shell, boolean echo = true) {
		StringBuilder sout = new StringBuilder()
		StringBuilder serr = new StringBuilder()
		Log.info("sh ${shell}" + (echo ? "" : " (output suppressed)"))
		Process proc = shell.execute()
		proc.waitForProcessOutput(sout, serr)

		if (echo) {
			[sout, serr].findAll().each { print(it) }
		}

		return sout.toString()
	}
}
