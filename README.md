# strawboat-草船
一个免费的IP代理池。用户可以自定义IP获取来源与IP的过滤方式。

## 默认IP获取来源
- [西刺代理](http://www.xicidaili.com/)
- [快代理](http://www.kuaidaili.com/)
- [3366ip](http://www.ip3366.net/)
- [89ip](http://www.89ip.cn)

## 默认过滤方式
访问[百度](https://www.baidu.com),校验http返回码

## 使用方式
见demo

### 自定义IP来源
实现`Provider`接口

### 自定义过滤方式
实现`Filter`接口
