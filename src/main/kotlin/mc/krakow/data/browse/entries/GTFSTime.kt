package mc.krakow.data.browse.entries

data class GTFSTime(val time: String) {
    val hours: Long
    val minutes: Long
    val seconds: Long

    init {
        val timeSplit = time.split(":")
        if (timeSplit.size != 3) {
            throw IllegalArgumentException("GTFS time must be in the format HH:MM:SS, but is $time")
        }
        hours = timeSplit[0].toLong()
        minutes = timeSplit[1].toLong()
        seconds = timeSplit[2].toLong()
        validateFields()
    }

    private fun validateFields() {
        if (hours !in 0L..23L)
            throw IllegalArgumentException("Hours must be in range of 0 to 23, but are $hours")
        if (minutes !in 0L..59L)
            throw IllegalArgumentException("Minutes must be in range of 0 to 59, but are $minutes")
        if (seconds !in 0L..59L)
            throw IllegalArgumentException("Seconds must be in range of 0 to 59, but are $seconds")
    }

    fun toSeconds(): Long {
        return (hours * 60 + minutes) * 60 + seconds
    }

    operator fun compareTo(other: GTFSTime): Int {
        return this.toSeconds().compareTo(other.toSeconds())
    }

    operator fun minus(other: GTFSTime): GTFSTime {
        return fromSeconds(this.toSeconds() - other.toSeconds())
    }

    companion object {
        fun fromSeconds(seconds: Long): GTFSTime {
            val resultSeconds = seconds % 60
            val minutes = (seconds / 60) % 60
            val hours = (seconds / 3600)
            return GTFSTime("$hours:$minutes:$resultSeconds")
        }
    }
}
