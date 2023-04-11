package com.notnotype.momotalk.model


data class MessageGroup(val user: User, val messages: List<Message>) {}

data class Message(val text: String) {}

fun getDefaultMessages(): List<Message> {
    return listOf(
        Message("Hello, World"),
        Message("Make the world better."),
        Message("Can can need."),
        Message("qpweoirhjnkasdbnfzxcloiyhjosdfjkesja;ladksjfkljasdokfhjl;kasjd;lfj中文输入从屙屎"),
        Message("lfj中文输入从屙屎")
    )
}


fun getDefaultMessageGroup(user: User = getDefaultUser()): MessageGroup {
    return MessageGroup(
        user = user,
        messages = getDefaultMessages(),
    )
}