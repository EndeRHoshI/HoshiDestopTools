package org.hoshi.desktoptools.extentions

/**
 * 匹配 true false 取不同值
 */
fun <T> Boolean.matchTrue(trueObject: T, falseObject: T): T {
    return if (this) {
        trueObject
    } else {
        falseObject
    }
}

/**
 * 匹配 true false 进行不同的操作得到不同值
 */
fun <T> Boolean.matchTrue(trueAction: () -> T, falseAction: () -> T): T {
    return if (this) {
        trueAction.invoke()
    } else {
        falseAction.invoke()
    }
}

/**
 * 匹配 true false 进行不同的操作得到不同值
 */
suspend fun <T> Boolean.matchTrueSuspend(trueAction: suspend () -> T, falseAction: suspend () -> T): T {
    return if (this) {
        trueAction.invoke()
    } else {
        falseAction.invoke()
    }
}