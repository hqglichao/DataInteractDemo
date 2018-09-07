# DataInteractDemo
present 5 kinds of data exchange between activity and service   
列举了5种与service进行数据交互的方式   
进度条显示的数据是service传递过来的   
每一个viewpager的page是一个fragment，每一个fragment对应启动一个service，离开时stop/unbind对应的service

### 1、广播
利用广播中携带数据进行数据交互
### 2、Binder接口
通过扩展binder，进行同进程间的数据交互
### 3、Messenger
通过messenger进行不同进程间的同步数据交互，实质是用aidl
### 4、SharePreference
通过本地写/读SharePreference进行数据交互，缺点：耗时，复杂数据麻烦
### 5、AIDL
可以进行不同进程间的异步数据交互
如果没有多线程并发需要，建议使用Messenger
例子中使用了两种方式
a) 通过service返回服务器代理，在client端调用。
b) 通过client端向service注册方法，服务器端定时调用client端callback

## 效果
<img src="https://github.com/hqglichao/DataInteractDemo/blob/master/gif/data_interact.gif" alt="GitHub" title="GitHub,Social Coding" width="40%" height="40%" />
