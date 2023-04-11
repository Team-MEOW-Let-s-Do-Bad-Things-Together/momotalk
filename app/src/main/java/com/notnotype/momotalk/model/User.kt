package com.notnotype.momotalk.model

data class User(val username: String, val nickname: String, val uid: String, val avatar: String) {}

fun getDefaultUser(): User {
    return User("Notnotype", "Notype", "114514", "https://www.sdfsdf.dev/100x100.png")
}

fun getDefaultMe(): User {
    return User("Me", "Me", "114514", "https://www.sdfsdf.dev/100x100.png")
}

fun isMe(user: User): Boolean {
    return user.username == "Me";
}