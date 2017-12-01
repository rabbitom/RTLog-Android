# RTLog-Android
在网页上实时查看APP的日志。

## 使用方法
**1.在[云巴](https://yunba.io)上注册账号并创建应用（注意填写的应用包名需与APP包名一致）**

**2.引入库**
> 在项目根目录下的build.gradle中添加下列代码

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
> 添加依赖

    dependencies {
	        compile 'com.github.rabbitom:RTLog-Android:v0.1'
	}
	
**3.初始化**
> 在项目Application类的OnCreate中添加以下代码，appKey在[云巴](https://yunba.io)的应用详情中查看

	RtLog.init(this, appKey);

**4.输出日志**
> 调用方法和系统的Log类一致，打印到控制台的同时发布到云巴平台

	RtLog.i(TAG, "your logs");
