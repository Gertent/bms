//@ sourceURL=LocalResizeDOC.js 
/**

     * 获得base64
     * @param {Object} obj
     * @param {Number} [obj.width] 图片需要压缩的宽度，高度会跟随调整
     * @param {Number} [obj.quality=0.8] 压缩质量，不压缩为1
     * @param {Function} [obj.before(this, blob, file)] 处理前函数,this指向的是input:file
     * @param {Function} obj.success(obj) 处理后函数
     * @example
     *
     */
    $.fn.localResizeDOC = function (obj) {
        this.on('change', function () {
            var file = this.files[0];
            var URL = URL || webkitURL;
            var blob = URL.createObjectURL(file);

            // 执行前函数
            if($.isFunction(obj.before)) { obj.before(this, blob, file) };

            _create(blob, file);
            this.value = '';   // 清空临时数据
        });

        /**
         * 生成base64
         * @param blob 通过file获得的二进制
         */
        function _create(blob,file) {

                // 生成结果
                var result = {
                    blob : file
                };
                // 执行后函数
                obj.success(result);
        }
    };
