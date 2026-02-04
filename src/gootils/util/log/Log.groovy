package gootils.util.log

class Log {
	def static info(String text) {
		println(Color.BLUE.ansi("[INFO] ${text}"))
	}

	def static warn(String text) {
		println(Color.YELLOW.ansi("[WARN] ${text}"))
	}

	def static error(String text) {
		println(Color.RED.ansi("[ERROR] ${text}"))
	}
}
