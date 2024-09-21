package exception

open class DataNotFoundException(override val message: String?) : AppException(message) {
    override fun toString(): String {
        return "DataNotFoundException(message=$message)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as DataNotFoundException
        return message == other.message
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + this::class.hashCode()
        return result
    }
}