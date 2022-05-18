package com.zwb.sob_ucenter.bean

/**
{
"userId":"1231137268748521472",
"nickname":"码划云",
"sign":"我视别人的钱财为粪土，我爱粪。",
"avatar":"https://images.sunofbeaches.com/content/2022_03_23/956117482517561344.png",
"relative":2,
"vip":true
}
relative对应的值:
0表示没有关注对方，可以显示为：关注
1表示对方关注自己，可以显示为：回粉
2表示已经关注对方，可以显示为：已关注
3表示相互关注，可以显示为：相互关注
 */
data class FollowBean(
    val avatar: String,
    val nickname: String,
    val relative: Int,
    val sign: String,
    val userId: String,
    val vip: Boolean
)