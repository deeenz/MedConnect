package com.bits9.medconnect

data class Answer(var question: String, var questioner:String, var upVotes: Int, var downVotes: Int, var answerId:String, var questionId:String)
data class Questions(var question: String, var questioner: String, var questionId:String)