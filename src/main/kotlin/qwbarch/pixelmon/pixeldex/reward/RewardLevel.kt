package qwbarch.pixelmon.pixeldex.reward

enum class RewardLevel(val value: Byte) {

    ZERO(0),
    TEN(10),
    TWENTY(20),
    THIRTY(30),
    FORTY(40),
    FIFTY(50),
    SIXTY(60),
    SEVENTY(70),
    EIGHTY(80),
    NINETY(90),
    HUNDRED(100);

    fun next(): RewardLevel = if (this == HUNDRED) HUNDRED else values()[(ordinal + 1) % values().size]

    companion object {
        fun parse(value: Float): RewardLevel {
            var value = ((value / 10).toInt() * 10).toByte()
            return values().single { it.value == value }
        }

        fun parse(value: Byte): RewardLevel = values().single { it.value == value }
    }
}