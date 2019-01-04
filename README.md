## 一、简介
爬虫的目标网站一般都会针对爬虫做一些防御措施，其中最常见的手段之一就是过滤掉操作频率过高的IP。比如一些内容社区，文章阅读量的计算方式与IP相关联，一个IP阅读一篇文章后阅读量增加了1，接下来半个小时内去点击阅读量都不会再增加的。假如你有一万个IP，不断切换IP去访问，这时候的阅读量是会持续增加，不受半小时的时间间隔限制的。但是这一万个IP去哪里找呢？本文介绍的IP池-Strawboat就能提供这些IP。

Github地址：https://github.com/autumn-projects/strawboat
## 二、使用方式
### 1、Jar包引入
- maven构建工具可按以下方式引入
例：maven
```xml
<dependency>
    <groupId>com.oscroll</groupId>
    <artifactId>strawboat</artifactId>
    <version>1.0.1</version>
</dependency>
```
- 其他构建工具或者直接下载Jar包-[maven中央仓库](https://mvnrepository.com/artifact/com.oscroll/strawboat/1.0.1)
### 2、快速使用
```java
// 创建默认的IP池
ScheduledPool pool = new ScheduledPool();
// 在线程中启动IP池
new Thread(pool::execute).start();
// 从IP池中取出IP
new Thread(() -> {
    for (; ; ) {
        IP ip = pool.take();
        // do something..
    }
}).start();
```
其中ScheduledPool是阻塞队列的一个装饰类，也就是说pool.take()方法在执行时，如果pool中没有IP，会一直阻塞。
## 三、进阶用法
### 1、实现原理
Strawboat主要声明了`Provider`与`Filter`两个接口。`Provider`接口用于提供IP，`Filter`接口用于过滤IP，对于这两个接口，项目中都有默认的实现方式。然后通过一个确定的规则将`Provider`与`Filter`结合起来。
#### Provider接口
`Provider`中提供了一个getIPList()方法，每次调用返回一组IP。

Strawboat默认提供了以下网站的免费代理IP:
- 西刺代理
- 快代理
- 云代理
- 89免费代理
#### Filter接口
`Provider`中提供了一个filter()方法，来验证当前IP是否可用。默认通过代理IP访问百度的返回结果来判断该IP是否有效。
#### IP池实现
IP池默认使用以上`Provider`与`Filter`的具体实现，循环获取每个网站的IP并对其进行过滤，如果IP有效则加入队列。
### 2、拓展与自定义
在`ScheduledPool`初始化时可以指定选用的`Provider`与`Filter`。
```java
public ScheduledPool(List<Provider> providerList, List<Filter> filterList) {
    this.providerList = providerList;
    this.filterList = filterList;
}
```
用户可以选用已有的`Provider`，或者实现`Provider`接口，将自定义的IP获取方式放入IP池中，同理`Filter`的实现方式也是如此。
## 四、结束语
当前版本为Strawboat的第一个版本(strawboat-1.0.1)，包含了IP池应有的功能，用户也能根据自己的需求进行拓展。只是功能上还比较单一，这一点在以后的版本中会进行拓展。
