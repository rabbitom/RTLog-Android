# RTLog-Android

## 使用方法
**1.在[云巴](https://yunba.io)上注册账号并创建应用（注意填写的应用包名需与APP包名一致）**

**2.引入库**
> 在根项目下的build.gradle中添加下列代码

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
	
**3.初始化，只需在项目的application文件中添加一句代码即可。注意其中的appkey需要在[云巴](https://yunba.io)上的应用详情上查看**

	RtLog.init(this, "5a2123302a27c9d728f5353f");

**4.使用方式和系统的Log方法基本类似。只是在系统Log的相应方法上多了上传日志的功能。**

	RtLog.i(TAG, "your logs");
