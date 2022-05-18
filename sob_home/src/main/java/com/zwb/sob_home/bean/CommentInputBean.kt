package com.zwb.sob_home.bean

data class CommentInputBean(
   var parentId: String?="",
   var articleId: String,
   var commentContent: String
)