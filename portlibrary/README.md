使用说明

一、串口
    1.  初始化port
         portManager = PortManager.getInstance();
    2.1 模块上电
        portManager.powerUp();
    2.2 模块断电
        portManager.powerDown();

    3.1 打开串口
        portManager.openSerialPort();
    3.2 关闭串口
            portManager.colseSerialPort();
    4.1 开始接收数据
       portManager.startRead(readCallback);
    4.1 停止接收数据
           portManager.stopRead();

二、解析标签数据
    1.new一个解析工厂，通过解析工厂创建一个解析器
        IGenerator generator;
        ITag tag;
        generator = TagGenerator.getInstance();
        tag = generator.createTagParse(i);//i的值：30、18，目前只支持解析这两种标签类型
    2.解析标签
        @Override
        public void exec(byte[] bytes, int i) {
             Log.d("NULLL",Parse.toHexString(bytes,i));
             TagBase30 tag1 = (TagBase30) tag.parseTag(Parse.toHexString(bytes, i));
             Log.d("exec",tag1.getTags().toString());
        }