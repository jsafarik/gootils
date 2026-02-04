package gootils.util.log

enum Color {
	DEFAULT(0),
	BOLD(1),
	BLACK(30),
	RED(31),
	GREEN(32),
	YELLOW(33),
	BLUE(34),
	MAGENTA(35),
	CYAN(36),
	WHITE(37)

	int ansiColorCode

	Color(int code) {
		this.ansiColorCode = code
	}

	String ansi(String text) {
		return "\u001B[${this.ansiColorCode}m${text}\u001B[${DEFAULT.ansiColorCode}m"
	}
}
