package com.zwb.lib_common.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.zwb.lib_common.bean.ArticleTitleBean
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import thereisnospon.codeview.CodeView
import java.util.*
import kotlin.math.abs


class MyCodeView: CodeView {
    private var oldY = 0f
    private var oldX = 0f
    private var newY = 0f
    private var newX = 0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        parent.parent.requestDisallowInterceptTouchEvent(true)
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                newX = ev.x
                newY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                //手指滑动同时判断滑动方向，一旦滑动方向大于+-60便调用
                //getParent().getParent().requestDisallowInterceptTouchEvent(false);
                //将滑动事件交给RecyclerView来处理
                oldX = newX
                oldY = newY
                newX = ev.x
                newY = ev.y
                val moveX = abs(oldX - newX)
                val moveY = abs(oldY - newY)
                //moveX * 1.73 < moveY  ,判断左右滑动范围为+-60度
                if (moveX * 1.73 < moveY) {
                    parent.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        return super.onTouchEvent(ev)
    }


    override fun showCode(content: String){
        showCodeHtmlByClass(getNewContent(content), "code")
    }

    private var titleList:MutableList<ArticleTitleBean> = mutableListOf()
    fun getTitleList(): List<ArticleTitleBean>{
        return titleList
    }
    private fun parseTitle(all: Elements){
        val allHTag = all.filter { it.tag().name.toLowerCase() in arrayListOf(
            "h1",
            "h2",
            "h3",
            "h4"
        ) }
        allHTag.forEach {
            var id = it.tag().name+Random().nextInt()
            id = id.replace("-","")
            it.attr("id",id)
            when(it.tag().name.toLowerCase()){
                "h1" -> titleList.add(ArticleTitleBean(id, it.text(), 1))
                "h2" -> titleList.add(ArticleTitleBean(id, it.text(), 2))
                "h3" -> titleList.add(ArticleTitleBean(id, it.text(), 3))
                "h4" -> titleList.add(ArticleTitleBean(id, it.text(), 4))
            }
        }
    }

    /**
     * 修改html的样式
     */
    private fun getNewContent(htmlText: String): String {
        return try {
            var newHtml = htmlText.replace("<pre><code", "<my-block-code")
                .replace("</code></pre>", "</my-block-code>")
                .replace("<code", "<my-inline-code")
                .replace("</code>", "</my-inline-code>")
                .replace("<my-block-code", "<pre><code")
                .replace("</my-block-code>", "</code></pre>")
                .replace("language-language", "language-java")
                .replace("language-shell", "language-java")
                .replace("language-yaml", "language-java")
                .replace("language-yml", "language-java")
            newHtml = "<div style=\"line-height:2;font-size:14px;word-break:break-all;margin-bottom: 20px;\">${newHtml}</div>"
            val doc: Document = Jsoup.parse(newHtml)
            doc.head().append("\n<script src=\"file:///android_asset/scrollhelper.js\"></script>\n")
            parseTitle(doc.allElements)
            val imgs: Elements = doc.getElementsByTag("img")
            for (element in imgs) {
                // -webkit-backface-visibility: hidden; 这个很神奇，解决一些意想不到的问题
                element.attr("style", "max-width:100%;height:auto;-webkit-backface-visibility: hidden;")
            }
            val h1s: Elements = doc.getElementsByTag("h1")
            for (element in h1s) {
                element.attr("style", "font-size:18px")
            }
            val h2s: Elements = doc.getElementsByTag("h2")
            for (element in h2s) {
                element.attr("style", "font-size:17px")
            }
            val h3s: Elements = doc.getElementsByTag("h3")
            for (element in h3s) {
                element.attr("style", "font-size:16px")
            }
            val h4s: Elements = doc.getElementsByTag("h4")
            for (element in h4s) {
                element.attr("style", "font-size:15px")
            }
            val h5s: Elements = doc.getElementsByTag("h5")
            for (element in h5s) {
                element.attr("style", "font-size:14px")
            }
            val uls: Elements = doc.getElementsByTag("ul")
            for (element in uls) {
                element.attr("style", "margin: 10px;border: 0;padding: 0;")
            }
            val ps: Elements = doc.getElementsByTag("p")
            for (element in ps) {
                element.attr(
                    "style",
                    "padding:0;margin: 0;-webkit-margin-before: 0;-webkit-margin-after: 0;"
                )
            }
            val a: Elements = doc.getElementsByTag("a")
            for (element in a) {
                element.attr("style", "color:#0071E0")
            }
            val pres: Elements = doc.getElementsByTag("pre")
            for (element in pres) {
                if(element.children().isEmpty()){
                    val text = element.text()
                    element.empty()
                    element.appendElement("code").text(text)
                }
            }
            val codes: Elements = doc.getElementsByTag("code")
            for (element in codes) {
                element.attr("style", "line-height:2;font-size:12px;")
            }

            val inlineCodes: Elements = doc.getElementsByTag("my-inline-code")
            for (element in inlineCodes) {
                element.attr(
                    "style",
                    "background: #F7F7F7;font-size:12px;color:#FF502C;border-radius: 5px;padding:2px"
                )
            }
            // 引用
            val blockquotes: Elements = doc.getElementsByTag("blockquote")
            for (element in blockquotes) {
                element.attr(
                    "style",
                    "margin:0px 2px;border-left: 3px solid #0084ff;color: #7f828b;padding-left: 10px;background: #FCFAFA;"
                )
            }

            doc.toString()
        } catch (e: Exception) {
            htmlText
        }
    }


}