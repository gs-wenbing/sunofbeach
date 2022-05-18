function getElementTop(id) {
    var element = document.getElementById(id)
    var actualTop = element.offsetTop;
    var current = element.offsetParent;   //这是获取父元素
    console.log(element.tagName+"==="+element.innerHtml);
    while (current !== null) {      //当它上面有元素时就继续执行
        actualTop += current.offsetTop;   //这是获取父元素距它的父元素顶部的距离累加起来
        current = current.offsetParent;　　//继续找父元素
    }
    return actualTop
}

function initGif(){
    if ('getContext' in document.createElement('canvas')) {
        HTMLImageElement.prototype.play = function() {
            if (this.storeCanvas) {
                // 移除存储的canvas
                this.storeCanvas.parentElement.removeChild(this.storeCanvas);
                this.storeCanvas = null;
                // 透明度还原
                image.style.opacity = '';
            }
            if (this.storeUrl) {
                this.src = this.storeUrl;
            }
        };
        HTMLImageElement.prototype.stop = function() {
            var canvas = document.createElement('canvas');
            // 尺寸
            var width = this.width, height = this.height;
            if (width && height) {
                // 存储之前的地址
                if (!this.storeUrl) {
                    this.storeUrl = this.src;
                }
                // canvas大小
                canvas.width = width;
                canvas.height = height;
                // 绘制图片帧（第一帧）
                canvas.getContext('2d').drawImage(this, 0, 0, width, height);
                // 重置当前图片
                try {
                    this.src = canvas.toDataURL("image/gif");
                } catch(e) {
                    // 跨域
                    this.removeAttribute('src');
                    // 载入canvas元素
//                    canvas.style.position = 'absolute';
                    // 前面插入图片
                    this.parentElement.insertBefore(canvas, this);
                    // 隐藏原图
                    this.style.opacity = '0';
                    // 存储canvas
                    this.storeCanvas = canvas;
                }
            }
        };
    }
//    var images = document.getElementsByTagName("img");
//    for(var i=0;i<images.length;i++){
//        images[i].stop();
//    }
}

function start(){
    var images = document.getElementsByTagName("img");
    for(var i=0;i<images.length;i++){
        images[i].play();
    }
}
function stop(){
    var images = document.getElementsByTagName("img");
    for(var i=0;i<images.length;i++){
        images[i].stop();
    }
}
