use celeste;

insert into article (uid, title, context)
values (1,'nginx --- 反向代理｜负载均衡 ｜ 动静分离',
'

反向代理如何配置

1、反向代理实例一

2、反向代理实例二

ocation 指令说明

 Nginx 负载均衡

负载均衡常用算法

应用场景

总结

Nginx实现动静分离

一、什么是动静分离

二、实现方案

三、配置Nginx动静分离

四、验证测试

反向代理如何配置
1、反向代理实例一
实现效果：使用 Nginx 反向代理，访问 http://localhost:8080/  直接跳转到 http://192.168.1.130:9090

配置代码

  server {
    listen 8080;
    server_name localhost;

    location / {
      root html;
      index index.html index.htm;
      try_files $uri $uri/ /index.html; # 解决刷新404
      proxy_pass http://192.168.1.130:9090;
    }
}
如上配置，我们监听 8080 端口，访问域名为  http://localhost:8080/，故访问该域名时会跳转到 http://192.168.1.130:9090 路径上（不加端口号时默认为 80 端口）。

此处的意思为：nginx 反向代理服务监听 http://localhost/的8080端口，如果有请求过来，则转到proxy_pass配置的对应服务器上，仅此而已。
实验结果：



2、反向代理实例二
实现效果：使用 Nginx 反向代理，根据访问的路径跳转到不同端口的服务中，Nginx 监听端口为 9001。

访问 http://192.168.1.130:8080/edu/ 直接跳转到 http://192.168.1.130:9091/;
访问 http://192.168.1.130:8080/vod/ 直接跳转到 http://192.168.1.130:9092;
第一步，需要准备两个服务器，一个 9091 端口，一个 9092 端口，并准备好测试的页面

第二步，修改 nginx 的配置文件，在 http 块中配置 server

  server {
    listen 8080;
    server_name localhost;

    #charset koi8-r;

    #access_log  logs/host.access.log  main;




    location / {
      root html;
      index index.html index.htm;
      try_files $uri $uri/ /index.html; # 解决刷新404
      proxy_pass http://192.168.1.130:9090;
    }

    location /edu/ {
      proxy_pass http://192.168.1.130:9091/;
    }

    location /vod/ {
      proxy_pass http://192.168.1.130:9092/;
    }
}

根据上面的配置，当请求到达 Nginx 反向代理服务器时，会根据请求进行分发到不同的服务上。

实验结果





在 Nginx 配置文件中，location 指令用于定义请求的处理规则。当指定一个 location 时，如果路径后面加上斜杠 /，表示匹配该路径及其所有子路径。如果不加斜杠，则只匹配精确的路径。

例如：

location /edu 会匹配任何以 /edu 开头的请求，但不会匹配 /edu/。
location /edu/ 会匹配以 /edu/ 开头的请求，包括 /edu/ 本身及其所有子路径。
在代理配置中，proxy_pass 指令用于指定请求应该被转发到哪个上游服务器。当你在 location 后面加上 / 时，Nginx 会将请求转发到指定的上游服务器，并且保持原始请求的路径不变。

ocation 指令说明
该指令用于匹配 URL， 语法如下：

location [ = | ~ | ~* | ^~] uri {

}
= ：用于不含正则表达式的 uri 前，要求请求字符串与 uri 严格匹配， 如果匹配成功，就停止继续向下搜索并立即处理该请求。
~：用于表示 uri 包含正则表达式，并且区分大小写。
~*：用于表示 uri 包含正则表达式，并且不区分大小写。
^~：用于不含正则表达式的 uri 前，要求 Nginx 服务器找到标识 uri 和请求
字符串匹配度最高的 location 后，立即使用此 location 处理请求，而不再使用 location块中的正则 uri 和请求字符串做匹配。

注意：如果 uri 包含正则表达式，则必须要有 ~ 或者 ~* 标识
 Nginx 负载均衡
负载均衡（Load Balance），它在网络现有结构之上可以提供一种廉价、有效、透明的方法来扩展网络设备和服务器的带宽，并可以在一定程度上增加吞吐量、加强网络数据处理能力、提高网络的灵活性和可用性等。用官网的话说，它充当着网络流中“交通指挥官”的角色，“站在”服务器前处理所有服务器端和客户端之间的请求，从而最大程度地提高响应速率和容量利用率，同时确保任何服务器都没有超负荷工作。如果单个服务器出现故障，负载均衡的方法会将流量重定向到其余的集群服务器，以保证服务的稳定性。当新的服务器添加到服务器组后，也可通过负载均衡的方法使其开始自动处理客户端发来的请求。（详情可参考：What Is Load Balancing?）

简言之，负载均衡实际上就是将大量请求进行分布式处理的策略。

负载均衡常用算法
1. 轮询 （round-robin）

轮询为负载均衡中较为基础也较为简单的算法，它不需要配置额外参数。假设配置文件中共有 M 台服务器，该算法遍历服务器节点列表，并按节点次序每轮选择一台服务器处理请求。当所有节点均被调用过一次后，该算法将从第一个节点开始重新一轮遍历。

特点：由于该算法中每个请求按时间顺序逐一分配到不同的服务器处理，因此适用于服务器性能相近的集群情况，其中每个服务器承载相同的负载。但对于服务器性能不同的集群而言，该算法容易引发资源分配不合理等问题。

2、加权轮询

为了避免普通轮询带来的弊端，加权轮询应运而生。在加权轮询中，每个服务器会有各自的 weight。一般情况下，weight 的值越大意味着该服务器的性能越好，可以承载更多的请求。该算法中，客户端的请求按权值比例分配，当一个请求到达时，优先为其分配权值最大的服务器。

特点：加权轮询可以应用于服务器性能不等的集群中，使资源分配更加合理化。

Nginx 加权轮询源码可见：ngx_http_upstream_round_robin.c，源码分析可参考：关于轮询策略原理的自我理解。其核心思想是，遍历各服务器节点，并计算节点权值，计算规则为 current_weight 与其对应的 effective_weight 之和，每轮遍历中选出权值最大的节点作为最优服务器节点。其中 effective_weight 会在算法的执行过程中随资源情况和响应情况而改变。较为核心的部分如下：

for (peer = rrp->peers->peer, i = 0;
	peer; 	/* peer 为当前遍历的服务器结点*/
  peer = peer->next, i++)
{
  ...

	/* 每轮遍历会更新 peer 当前的权值*/
	peer->current_weight += peer->effective_weight;

  ...

	/* best 为当前服务器中的最优节点，即本轮中选中的服务器节点*/
	if (best == NULL || peer->current_weight > best->current_weight) {
		best = peer;
  	p = i;
	}

  ...
}



3. IP 哈希（IP hash）

ip_hash 依据发出请求的客户端 IP 的 hash 值来分配服务器，该算法可以保证同 IP 发出的请求映射到同一服务器，或者具有相同 hash 值的不同 IP 映射到同一服务器。

特点：该算法在一定程度上解决了集群部署环境下 Session 不共享的问题。

Session 不共享问题是说，假设用户已经登录过，此时发出的请求被分配到了 A 服务器，但 A 服务器突然宕机，用户的请求则会被转发到 B 服务器。但由于 Session 不共享，B 无法直接读取用户的登录信息来继续执行其他操作。

实际应用中，我们可以利用 ip_hash，将一部分 IP 下的请求转发到运行新版本服务的服务器，另一部分转发到旧版本服务器上，实现灰度发布。再者，如遇到文件过大导致请求超时的情况，也可以利用 ip_hash 进行文件的分片上传，它可以保证同客户端发出的文件切片转发到同一服务器，利于其接收切片以及后续的文件合并操作。

4、其他算法

URL hash

url_hash 是根据请求的 URL 的 hash 值来分配服务器。该算法的特点是，相同 URL 的请求会分配给固定的服务器，当存在缓存的时候，效率一般较高。然而 Nginx 默认不支持这种负载均衡算法，需要依赖第三方库。

最小连接数（Least Connections）

假设共有 M  台服务器，当有新的请求出现时，遍历服务器节点列表并选取其中连接数最小的一台服务器来响应当前请求。连接数可以理解为当前处理的请求数。

应用场景
说了这么多理论，究竟基于 Nginx 的负载均衡要怎么用呢？接下来，将以加权轮询算法为例，带大家尝试通过自己的一台笔记本 + Nginx + Node 测试一下负载均衡。由于没有多台服务器，于是通过自己笔记本的多个不同端口来模拟不同的服务器。

Step 1：确保自己的电脑中，Nginx 已安装并能够成功启动（以 Mac 为例）

如果你也遇到了像我一样由于端口占用导致 Nginx 启动失败的问题，可以尝试下述步骤修改配置文件中的端口号

相关文件路径

/usr/local/etc/nginx/nginx.conf （配置文件路径）
/usr/local/var/www （服务器默认路径）
/usr/local/Cellar/nginx/1.8.0 （安装路径）
修改 nginx.conf 文件中的端口

server {
  # listen       8080;
  listen       8086;
  server_name  localhost;
}
Nginx 配置文件 nginx.conf 中主要包含以下几个部分：

server：主机服务相关设置，主要用于指定虚拟主机域名、IP 和端口
location：URL 匹配特定位置后的设置，反向代理设置
upstream：负载均衡相关配置
暂停 Nginx 并重启

// 暂停 Nginx 服务
sudo nginx -s stop
// 启动 Nginx 服务
nginx
打开 http://localhost:8086/ 测试是否成功，如果显示下图，则证明启动成功～





Step 2：基于 Node + Express 框架来搭建简单的服务器

Express 是一个简洁而灵活的轻量级 node.js Web 应用框架（详情可了解 Express），如果第一次使用，请先安装。

安装 Express

npm i express
新建 index.js 文件，并写入代码
const express = require(''express'');
const app = express();

// 定义要监听的端口号
const listenedPort = ''8087'';

app.get(''/'', (req, res) => res.send(`Hello World! I am port ${listenedPort}～`));

// 监听端口
app.listen(listenedPort, () => console.log(`success: ${listenedPort}`));
启动服务器

node index.js
此处可以多起几个服务，分别让 Node 监听 8087，8088，8089 端口，每个服务中通过 send 不同的文案用以区分不同的 Server。

Step 3：在 nginx.conf 文件中配置好需要轮询的服务器和代理

轮询的服务器，写在 http 中的 upstream 对象里：
upstream testServer {
  server localhost:8087 weight=10;
  server localhost:8088 weight=2;
  server localhost:8089;
}
代理地址，写在 http 中的 server 对象里：
location / {
  root   html;
  index  index.html index.htm;
  proxy_pass http://testServer; // testServer 为自己定义的服务器集群
}
Step 4：查看结果

重启 Nginx 服务

再次打开 http://localhost:8086/

通过多次刷新可以发现，由于设置了不同的 weight，端口号为 8087 的服务器出现的次数最多，同时证实了权值越高，服务器处理请求几率越大的规则。

总结
Nginx 作为一款优秀的反向代理服务器，可以通过不同的负载均衡算法来解决请求量过大情况下的服务器资源分配问题。较为常见的负载均衡算法有轮询、加权轮询、IP 哈希等等，可分别应对不同的请求场景

Nginx实现动静分离
简介： 前面介绍了Nginx的负载均衡，一般来说，都需要将动态资源和静态资源分开，这样可以很大程度的提升静态资源的访问速度，同时在开过程中也可以让前后端开发并行可以有效的提高开发时间，也可以有些的减少联调时间 。接下来介绍什么是动静分离以及如何使用Nginx实现动静分离。

前面介绍了Nginx的负载均衡，一般来说，都需要将动态资源和静态资源分开，这样可以很大程度的提升静态资源的访问速度，同时在开过程中也可以让前后端开发并行可以有效的提高开发时间，也可以有些的减少联调时间 。接下来介绍什么是动静分离以及如何使用Nginx实现动静分离。

一、什么是动静分离
在Web开发中，通常来说，动态资源其实就是指那些后台资源，而静态资源就是指HTML，JavaScript，CSS，img等文件。

动静分离，说白了，就是将网站静态资源（HTML，JavaScript，CSS，img等文件）与后台应用分开部署，提高用户访问静态代码的速度，降低对后台应用服务器的请求。后台应用服务器只负责动态数据请求。

优势：分担负载，减轻web服务器的压力，适用于大负载。静态资源放置cdn，同时还可以通过配置缓存到客户浏览器中，这样极大减轻web服务器的压力。

劣势：网络环境不佳时，ajax回应很慢，导致页面出现空白，出错处理会不好看。不利于网站SEO（搜索引擎优化），增加了开发复杂度。

二、实现方案
动静分离就是根据一定规则静态资源的请求全部请求Nginx服务器，后台数据请求转发到Web应用服务器上。从而达到动静分离的目的。目前比较流行的做法是将静态资源部署在Nginx上，而Web应用服务器只处理动态数据请求。这样减少Web应用服务器的并发压力。具体如下图所示：



三、配置Nginx动静分离
1. 修改nginx.conf配置，其中第一个location负责处理后台请求，第二个负责处理静态资源， nginx 的其他配置，请参考前之前的文章。 具体如下所示：

            #拦截静态资源
      location ~ .*\.(html|htm|gif|jpg|jpeg|bmp|png|ico|js|css)$ {
        root static;
        expires      30d;
       }



上面的示例，主要是配置image、js、css等资源文件的路径和地址。然后设置缓存失效的时间。

完成的Nginx配置如下所示：

worker_processes  1;

events {
    worker_connections  1024;
}

http {

   server {
       listen       80;
       server_name  localhost;

      #拦截后台请求
      location / {
        proxy_pass http://localhost:81;
        proxy_set_header X-Real-IP $remote_addr;
      }

      #拦截静态资源
      location ~ .*\.(html|htm|gif|jpg|jpeg|bmp|png|ico|js|css)$ {
        root static;
        expires      30d;
       }

    }

}

2. 在Nginx 下 创建 static 目录，将图片，js, css 等文件 拷贝到该目录下



3. 重启Nginx，使用命令： ./nginx -s reload 重新启动Nginx。

四、验证测试
Nginx 配置完成之后，在浏览器中访问：http://localhost/ 查看页面的请求效果。



通过浏览器的调试工具，通过图中红框内容都可以看出来引用静态资源成功了。动态请求转发到了81端口的web应用服务器，而静态的资源文件，访问的是80端口。说明Nginx的动静分离配置成功。
————————————————

                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。

原文链接：https://blog.csdn.net/qq_63358859/article/details/139345411
'),
(2,'Python基础教程（九）：装饰器',
'首先，欢迎各位来到我的博客，很高兴能够在这里和您见面！希望您在这里不仅可以有所收获，同时也能感受到一份轻松欢乐的氛围，祝你生活愉快！
💝💝💝如有需要请大家订阅我的专栏【Python系列】哟！我会定期更新相关系列的文章
💝💝💝关注！关注！！请关注！！！请大家关注下博主，您的支持是我不断创作的最大动力！！！

文章目录
Python 装饰器编程：增强与扩展的利器
一、装饰器基础
1.1 什么是装饰器？
1.2 装饰器的工作原理
1.3 简单的装饰器示例
二、装饰器的进阶使用
2.1 多层装饰器
2.2 带参数的装饰器
三、装饰器在实际编程中的应用
3.1 性能优化
3.2 日志记录
3.3 权限验证
四、装饰器的局限与最佳实践
五、总结
结束语
Python 装饰器编程：增强与扩展的利器
在 Python 编程中，装饰器（Decorators）是一种强大的高级特性，允许你在不修改原函数代码的情况下为其添加新的功能。装饰器本质上是一个接受函数作为参数的函数，返回一个新的函数，这个新函数通常会在执行原函数的基础上增加一些额外的操作。本文将深入探讨装饰器的概念、工作原理、以及如何在实际编程中运用装饰器来优化代码结构和功能。

一、装饰器基础
1.1 什么是装饰器？
装饰器是一种特殊类型的 Python 函数，用于修改其他函数的行为。它们通过在函数定义之前使用 @decorator_name 的语法来应用。装饰器可以接收函数作为参数，并返回一个新的函数，通常是在原有函数基础上增加了额外功能的新函数。

1.2 装饰器的工作原理
装饰器在 Python 中的工作原理基于函数即对象的思想。在 Python 中，函数是一种对象，这意味着你可以将函数赋值给变量、将其作为参数传递给其他函数，甚至从函数中返回函数。装饰器正是利用了这一点，它本身就是一个函数，它接收一个函数作为参数，并返回一个新的函数。

1.3 简单的装饰器示例
一个最简单的装饰器示例，用于打印函数的执行时间：

import time

def timer_decorator(func):
    def wrapper(*args, **kwargs):
        start_time = time.time()
        result = func(*args, **kwargs)
        end_time = time.time()
        print(f"{func.__name__} 执行时间: {end_time - start_time:.4f}秒")
        return result
    return wrapper

@timer_decorator
def example_function(n):
    time.sleep(n)

example_function(1)

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
二、装饰器的进阶使用
2.1 多层装饰器
一个函数可以被多个装饰器修饰，每个装饰器按照从上到下的顺序依次执行。这使得你可以组合不同的功能，例如日志记录、性能监控、权限验证等。

def log_decorator(func):
    def wrapper(*args, **kwargs):
        print(f"正在调用 {func.__name__}")
        return func(*args, **kwargs)
    return wrapper

@log_decorator
@timer_decorator
def complex_function():
    time.sleep(2)

complex_function()
1
2
3
4
5
6
7
8
9
10
11
12
2.2 带参数的装饰器
装饰器不仅可以接受函数作为参数，还可以接受额外的参数来定制其行为。这种装饰器被称为带参数的装饰器。

def repeat(n_times):
    def decorator_repeat(func):
        def wrapper(*args, **kwargs):
            for _ in range(n_times):
                result = func(*args, **kwargs)
            return result
        return wrapper
    return decorator_repeat

@repeat(n_times=3)
def say_hello(name):
    print(f"Hello {name}")

say_hello("World")
1
2
3
4
5
6
7
8
9
10
11
12
13
14
三、装饰器在实际编程中的应用
3.1 性能优化
装饰器常用于性能监控，例如记录函数的执行时间、缓存结果以避免重复计算（备忘录模式）、限制函数调用频率等。

3.2 日志记录
在大型应用程序中，装饰器可以用于统一管理日志记录，比如记录函数调用的开始和结束时间、参数、异常等信息。

3.3 权限验证
在 Web 开发中，装饰器常用于实现用户权限验证，确保只有特定角色的用户才能访问某些功能。

四、装饰器的局限与最佳实践
虽然装饰器功能强大，但在使用时也应注意其局限性：

可读性：过多的装饰器可能导致代码难以阅读和理解。
性能开销：装饰器本身会增加额外的函数调用开销，对于性能要求极高的场景应谨慎使用。
最佳实践是，合理使用装饰器来提高代码的模块化和可维护性，同时关注装饰器的性能影响，避免不必要的开销。

五、总结
装饰器是 Python 编程中一项强大的特性，它允许你在不修改原函数代码的情况下为其添加新的功能。通过理解装饰器的基本概念、工作原理，以及掌握其在实际编程中的应用技巧，你可以编写出更加灵活、高效和易于维护的代码。在接下来的编程实践中，不妨尝试将装饰器融入你的代码中，探索其在不同场景下的应用潜力。

结束语
喜欢博主的同学，请给博主一丢丢打赏吧↓↓↓您的支持是我不断创作的最大动力哟！感谢您的支持哦😘😘😘


💝💝💝如有需要请大家订阅我的专栏【Python系列】哟！我会定期更新相关系列的文章
💝💝💝关注！关注！！请关注！！！请大家关注下博主，您的支持是我不断创作的最大动力！！！

python相关文章索引	文章链接
Python基础语法（一）：标识符与保留字部分	Python基础语法（一）：标识符与保留字部分
Python基础语法（二）：数据类型	Python基础语法（二）：数据类型
Python基础语法（三）：运算符	Python基础语法（三）：运算符
Python基础语法（四）：条件控制	Python基础语法（四）：条件控制
Python基础语法（五）：循环语句	Python基础语法（五）：循环语句
Python基础语法（六）：推导式编程	Python基础语法（六）：推导式编程
Python基础教程（七）：函数编程-从基础到进阶	Python基础教程（七）：函数编程-从基础到进阶
Python基础教程（八）：迭代器与生成器编程	Python基础教程（八）：迭代器与生成器编程
Python基础教程（九）：Lambda 函数	Python基础教程（九）：Lambda 函数
❤️❤️❤️觉得有用的话点个赞 👍🏻 呗。
❤️❤️❤️本人水平有限，如有纰漏，欢迎各位大佬评论批评指正！😄😄😄
💘💘💘如果觉得这篇文对你有帮助的话，也请给个点赞、收藏下吧，非常感谢!👍 👍 👍
🔥🔥🔥Stay Hungry Stay Foolish 道阻且长,行则将至,让我们一起加油吧！🌙🌙🌙
————————————————

                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。

原文链接：https://blog.csdn.net/mwm0213/article/details/139577048
')
,(3,'常见排序算法——插入排序（直接插入排序 & 希尔排序）',
'直接插入排序
基本思想
把待排序的记录按其关键码值的大小逐个插入到一个已经排好序的有序序列中，直到所有的记录插入完为止，得到一个新的有序序列 。具体如下图所示：



当插入第i(i>=1)个元素时，前面的array[0],array[1],…,array[i-1]已经排好序，此时用array[i]的排序码与 array[i-1],array[i-2],…的排序码顺序进行比较，找到插入位置即将array[i]插入，原来位置上的元素顺序后移。

代码实现
// 插入排序
void InsertSort(int* arr, int n)
{
	for (int i = 0; i < n - 1; i++)
	{
		//end表示已经排好序的尾标
		int end = i;
		//保存要排序的数，一会就会被覆盖
		int tmp = arr[end + 1];
		//注意end可以等于0  （tmp比前面所有数字都要小）
		//while (end >= 0 && arr[end] < tmp)//降序
		while (end >= 0 && arr[end] > tmp)  //升序
		{
			//语句顺序不能乱	先交换再--
			arr[end + 1] = arr[end];
			end--;
		}
		arr[end + 1] = tmp;
	}
}

时间复杂度计算


特性总结
元素集合越接近有序，直接插入排序算法的时间效率越高
时间复杂度：O(N^2)
空间复杂度：O(1)
稳定性：稳定       （遍历数组不会改变相同元素的相对顺序）
 希尔排序（缩小增量排序）
基本思想
先选定一个整数，把待排序文件中所有记录分成多个组，所有距离为gap的记录分在同一组内，并对每一组内的记录进行排序。然后，减小gap，重复上述分组和排序的工作。当到达gap=1时，所有记录在统一组内排好序。



 可以理解为希尔排序是对插入排序的优化，这样做能让大的数更快到达后面，小的数更快到达前面，完成排序。



当gap > 1时都是预排序，目的是让数组更接近于有序。当gap == 1时，数组已经接近有序的了，这样就 会很快。这样整体而言，可以达到优化的效果。

代码实现
// 希尔排序
void ShellSort(int* arr, int n)
{
	int gap = n;
	while (gap > 1)
	{
		//只有gap最后为1，才能保证最后有序，所以这里要加1
		gap = gap / 3 + 1;
		//下面类似直接插入排序  区别在于将1换成gap
		for (int i = 0; i < n - gap; i++)
		{
			int end = i;
			int tmp = arr[end + gap];
			while (end >= 0)
			{
				//if(arr[end] < tmp)//降序
				if (arr[end] > tmp)//升序
				{
					arr[end + gap] = arr[end];
					end -= gap;
				}
				else
					break;
			}
			arr[end + gap] = tmp;
		}
	}
}

时间复杂度计算
希尔排序的时间复杂度不好计算，因为gap的取值方法很多，导致很难去计算，因此在好多书中给出的希尔排序的时间复杂度都不固定：



《数据结构(C语言版)》--- 严蔚敏



 《数据结构-用面相对象方法与C++描述》--- 殷人昆

因为我们的gap是按照Knuth提出的方式取值的，而且Knuth进行了大量的试验统计，我们暂时就按照：O(N ^ 1.25)到O(1.6 * N ^ 1.25)来算。

特性总结
希尔排序是对直接插入排序的优化。
当gap > 1时都是预排序，目的是让数组更接近于有序；当gap == 1时，数组已经接近有序的了。
时间复杂度：O(N ^ 1.25)~O(1.6 * N ^ 1.25)
空间复杂度：O(1)
稳定性：不稳定       （不能保证原来所具有的相对次序）
不能保证具有相同排序码的记录原来所具有的相对次序，即原来排在前面的，经过排序后有可能排某个具有相同码的记录的后面，例如排序码43，89，21，43，28，15，经过5遍排序后次序为15，21，28，43，43，89。排序前第一个位置上的排序码43现在位于第5个位置。
————————————————

                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。

原文链接：https://blog.csdn.net/fen_0108/article/details/139533773
'),
(4,'文心一言使用技巧',
  '前言

文心一言是一款基于人工智能技术的自然语言处理工具，它可以帮助用户生成、编辑和优化各种类型的文本。无论是写作、翻译、总结，还是进行信息提取和数据分析，文心一言都能提供强大的支持。本文将详细介绍文心一言的使用技巧，帮助用户充分发挥这款工具的潜力。

一、文心一言的基本功能
在深入使用技巧之前，我们首先了解文心一言的基本功能。文心一言主要包括以下几个核心功能：

1.1 文本生成
文心一言可以根据用户提供的提示词或主题生成高质量的文本内容。无论是写作文章、博客、演讲稿，还是创作故事和诗歌，文心一言都能帮助用户快速完成。

1.2 文本编辑
文心一言提供强大的文本编辑功能，可以对已有文本进行优化和润色。用户可以让文心一言对文本进行修改、扩展、缩写，或者纠正语法和拼写错误。

1.3 翻译与总结
文心一言支持多种语言的互译，并能对长篇文章进行摘要和总结。通过这些功能，用户可以方便地获取多语言信息，并快速理解长篇内容的核心要点。

1.4 信息提取与数据分析
文心一言能够从文本中提取关键信息，并进行简单的数据分析。用户可以提取出文本中的人名、地名、时间等信息，或对文本进行情感分析和主题识别。

二、文本生成的使用技巧
文本生成是文心一言最基础也是最强大的功能之一。通过合理的提示词和主题设置，用户可以生成高质量的文本内容。以下是一些使用技巧：

2.1 提供明确的提示词
为了生成符合预期的文本内容，用户需要提供明确且具体的提示词或主题。例如，如果用户想生成一篇关于“环保”的文章，可以使用以下提示词：

写一篇关于环保的重要性和如何进行有效环保的文章。
1
明确的提示词可以帮助文心一言更好地理解用户需求，生成的内容也会更加贴近主题。

2.2 设置文本结构
用户可以在提示词中设置文本的结构，帮助文心一言生成逻辑清晰的文章。例如，可以指定文章的段落或章节：

写一篇关于环保的重要性的文章，包括以下几个部分：1. 引言，2. 环保的重要性，3. 常见的环保措施，4. 结论。
1
这种方式可以确保生成的文本有明确的逻辑结构，内容更加丰富和全面。

2.3 控制文本长度
用户可以通过提示词控制生成文本的长度。例如，如果需要生成一篇短文，可以在提示词中明确说明：

写一篇关于环保的重要性的短文，大约300字。
1
通过这种方式，用户可以灵活控制生成文本的长度，满足不同场景的需求。

三、文本编辑的使用技巧
文本编辑功能可以帮助用户优化和润色已有文本，使其更加流畅和易读。以下是一些使用技巧：

3.1 语法和拼写检查
文心一言可以对文本进行语法和拼写检查，帮助用户发现并纠正错误。用户只需将文本输入文心一言，并请求进行语法和拼写检查：

请检查以下文本的语法和拼写错误：
……
1
2
这种方式可以确保文本的准确性和专业性，特别适用于学术论文、正式报告等需要高准确性的文档。

3.2 扩展和缩写文本
文心一言可以根据用户需求对文本进行扩展或缩写。例如，如果用户希望扩展某一段文字，可以使用以下提示：

请将以下段落扩展为两倍长度：
……
1
2
同样，如果需要将长段落缩写为简洁的版本，可以使用类似的提示：

请将以下段落缩写为一半长度：
……
1
2
通过这种方式，用户可以根据实际需求灵活调整文本长度，适应不同的使用场景。

3.3 风格和语气调整
文心一言可以根据用户需求调整文本的风格和语气。例如，如果用户希望将正式文档转换为更加口语化的风格，可以使用以下提示：

请将以下正式文档转换为口语化的风格：
……
1
2
反之，如果需要将口语化的文本调整为正式文档，可以使用类似的提示：

请将以下口语化文本转换为正式文档：
……
1
2
通过这种方式，用户可以轻松适应不同的写作风格和语境需求。

四、翻译与总结的使用技巧
文心一言的翻译与总结功能可以帮助用户处理多语言信息，并快速获取长篇内容的核心要点。以下是一些使用技巧：

4.1 多语言翻译
文心一言支持多种语言的互译，用户可以输入原文并指定目标语言。例如：

请将以下英文文本翻译为中文：
……
1
2
通过这种方式，用户可以方便地获取多语言内容，特别适用于国际交流和跨文化沟通。

4.2 长篇文章摘要
文心一言可以对长篇文章进行摘要，提取出关键内容和核心观点。用户只需输入长篇文本，并请求生成摘要：

请对以下长篇文章进行摘要：
……
1
2
这种方式可以帮助用户快速理解长篇内容的主要信息，节省阅读时间。

五、信息提取与数据分析的使用技巧
文心一言的信息提取与数据分析功能可以帮助用户从文本中提取关键信息，并进行简单的数据分析。以下是一些使用技巧：

5.1 信息提取
文心一言可以从文本中提取出人名、地名、时间等关键信息。用户可以输入文本，并请求提取特定信息：

请提取以下文本中的人名和地名：
……
1
2
这种方式可以帮助用户快速获取文本中的关键信息，特别适用于数据挖掘和信息检索。

5.2 情感分析
文心一言可以对文本进行情感分析，判断文本的情感倾向（如积极、消极、中性）。用户可以输入文本，并请求进行情感分析：

请对以下文本进行情感分析：
……
1
2
通过这种方式，用户可以了解文本的情感倾向，有助于市场分析、舆情监控等应用。

5.3 主题识别
文心一言可以对文本进行主题识别，提取出文本的主要主题和关键词。用户可以输入文本，并请求进行主题识别：

请对以下文本进行主题识别：
……
1
2
这种方式可以帮助用户快速了解文本的主要内容和主题，特别适用于文献综述和信息分类。

六、实用技巧与最佳实践
除了上述功能和使用技巧，以下是一些实用技巧和最佳实践，帮助用户更高效地使用文心一言：

6.1 多次迭代优化
在使用文心一言进行文本生成和编辑时，用户可以进行多次迭代优化。通过多次调整提示词和优化文本，用户可以获得更满意的结果。

6.2 结合人工审阅
尽管文心一言功能强大，但在生成和编辑文本时，仍然建议用户结合人工审阅。通过人工审阅，可以发现和纠正潜在的问题，确保文本质量。

6.3 保存和备份
在使用文心一言进行文本处理时，建议定期保存和备份文本。避免因意外情况导致文本丢失，确保工作进度和成果的安全。

6.4 学习和掌握更多功能
文心一言不断更新和完善，用户可以通过学习和掌握更多功能，提高使用效率。关注官方文档和更新日志，了解最新的功能和改进。

结论
文心一言是一款功能强大的自然语言处理工具，通过掌握其基本功能和使用技巧，用户可以在写作、翻译、总结、信息提取和数据分析等方面获得极大帮助。本文介绍了文心一言的文本生成、编辑、翻译、总结、信息提取和数据分析的使用技巧，希望能帮助用户充分发挥这款工具的潜力，提高工作效率和文本质量。在实际使用中，结合人工审阅和多次迭代优化，用户可以获得更满意的结果，实现高效和优质的文本处理。
————————————————

                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。

原文链接：https://blog.csdn.net/2301_79262050/article/details/139564362
  '),
(5,'AI大模型在健康睡眠监测中的深度融合与实践案例',
  '
随着穿戴设备的普及和AI技术的发展，利用AI大模型在睡眠监测中的应用成为可能。这种深度融合应用能够提供更准确、更个性化的睡眠分析与建议，帮助用户更好地管理睡眠健康。以下是AI大模型在穿戴设备睡眠监测中的应用方案、技术实现和优化策略。
1. 应用方案
多模态数据融合：

生理数据：心率、呼吸率、体温等。
环境数据：光照、噪音、温度等。
行为数据：运动数据、睡眠姿势等。
高级数据分析：

睡眠阶段分类：利用深度学习模型对数据进行分析，分类出浅睡、深睡、REM睡眠等阶段。
异常检测：检测睡眠呼吸暂停、失眠等异常情况。
个性化建议：

基于用户的历史数据和模型分析结果，提供个性化的睡眠改善建议。
实时监测与反馈：

实时监测用户睡眠状态，及时提供反馈和建议。
2. 技术实现
2.1 数据采集与预处理
首先，需要从穿戴设备中获取各类数据，并进行预处理。

import numpy as np
import pandas as pd

# 模拟数据采集
heart_rate_data = np.random.normal(60, 5, 1000)
respiration_rate_data = np.random.normal(16, 2, 1000)
temperature_data = np.random.normal(36.5, 0.5, 1000)
movement_data = np.random.normal(0, 1, 1000)  # 假设为运动强度数据

# 创建DataFrame
data = pd.DataFrame({
    ''heart_rate'': heart_rate_data,
    ''respiration_rate'': respiration_rate_data,
    ''temperature'': temperature_data,
    ''movement'': movement_data
})

# 数据预处理
def preprocess_data(data):
    # 归一化处理
    data_normalized = (data - data.mean()) / data.std()
    return data_normalized

data_preprocessed = preprocess_data(data)

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
2.2 构建与训练模型
利用深度学习模型（如LSTM）对预处理后的数据进行训练，识别睡眠阶段。

from keras.models import Sequential
from keras.layers import LSTM, Dense, Dropout

# 构建LSTM模型
model = Sequential()
model.add(LSTM(64, return_sequences=True, input_shape=(None, 4)))  # 输入为4维数据
model.add(Dropout(0.2))
model.add(LSTM(64, return_sequences=False))
model.add(Dropout(0.2))
model.add(Dense(3, activation=''softmax''))  # 输出为3类：浅睡、深睡、REM

model.compile(optimizer=''adam'', loss=''categorical_crossentropy'', metrics=[''accuracy''])

# 模拟训练数据
X_train = np.expand_dims(data_preprocessed.values, axis=0)
y_train = np.random.randint(0, 3, (1, 1000))  # 假设标签数据

# 训练模型
model.fit(X_train, y_train, epochs=10, batch_size=32)

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
2.3 个性化建议生成
根据模型输出的睡眠阶段和用户历史数据，生成个性化的睡眠建议。

def generate_sleep_advice(sleep_data):
    # 分析睡眠数据
    deep_sleep_ratio = np.sum(sleep_data == 1) / len(sleep_data)
    rem_sleep_ratio = np.sum(sleep_data == 2) / len(sleep_data)

    advice = "您的睡眠分析结果如下：\n"
    advice += f"深睡比例: {deep_sleep_ratio:.2f}\n"
    advice += f"REM睡眠比例: {rem_sleep_ratio:.2f}\n"

    if deep_sleep_ratio < 0.2:
        advice += "建议增加深睡时间，保持规律的作息，避免在睡前使用电子设备。\n"
    if rem_sleep_ratio < 0.2:
        advice += "建议改善睡眠质量，尝试放松训练，如冥想或听轻音乐。\n"

    return advice

# 模拟生成睡眠阶段数据
predicted_sleep_stages = model.predict(X_train)[0]
advice = generate_sleep_advice(predicted_sleep_stages)
print(advice)

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
3. 优化策略
模型优化与压缩：

使用模型量化和剪枝技术，减少模型的计算量和内存占用，以适应穿戴设备的资源限制。
个性化与自适应学习：

根据用户的历史数据和反馈，不断调整和优化模型，提高个性化分析的准确性。
实时性与延迟优化：

通过边缘计算和高效的数据处理技术，减少数据传输和处理的延迟，提升实时监测的效果。
数据隐私与安全：

采用数据加密和隐私保护技术，确保用户数据的安全性和隐私性。
4. 应用示例：多模态数据融合与实时监测
4.1 数据采集
# 模拟实时数据采集
def collect_real_time_data():
    heart_rate = np.random.normal(60, 5)
    respiration_rate = np.random.normal(16, 2)
    temperature = np.random.normal(36.5, 0.5)
    movement = np.random.normal(0, 1)
    return np.array([heart_rate, respiration_rate, temperature, movement])

# 模拟实时数据采集
real_time_data = collect_real_time_data()
print("实时数据采集:", real_time_data)
1
2
3
4
5
6
7
8
9
10
11
4.2 实时监测与反馈
# 实时监测和睡眠阶段预测
def real_time_sleep_monitor(model):
    data_window = []

    while True:
        new_data = collect_real_time_data()
        data_window.append(new_data)
        if len(data_window) > 100:
            data_window.pop(0)  # 保持固定窗口大小

        if len(data_window) == 100:
            data_window_array = np.expand_dims(np.array(data_window), axis=0)
            sleep_stage = model.predict(data_window_array)
            print(f"当前睡眠阶段: {np.argmax(sleep_stage)}")

            # 提供实时反馈
            if np.argmax(sleep_stage) == 2:  # 假设2代表深睡
                print("进入深睡状态，请保持安静环境。")
            elif np.argmax(sleep_stage) == 0:  # 假设0代表浅睡
                print("浅睡状态，建议放松。")

        time.sleep(1)  # 模拟每秒采集一次数据

# 启动实时监测
# real_time_sleep_monitor(model)

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
5. 深入分析模型选择和优化
5.1 LSTM模型的优势和优化策略
优势：

LSTM擅长处理时间序列数据，能够记住长期依赖关系，适合用于分析连续的生理数据，如心率和呼吸率。
在睡眠监测中，LSTM能够准确捕捉不同睡眠阶段的特征。
优化策略：

减小模型大小：通过剪枝和量化技术减少模型参数数量，减小模型大小，适应穿戴设备的计算资源限制。
改进架构：采用双向LSTM（BiLSTM）或多层LSTM结构，提升模型的表达能力和准确性。
from keras.models import Sequential
from keras.layers import LSTM, Dense, Dropout, Bidirectional

def build_optimized_lstm_model(input_shape):
    model = Sequential()
    model.add(Bidirectional(LSTM(64, return_sequences=True), input_shape=input_shape))
    model.add(Dropout(0.2))
    model.add(Bidirectional(LSTM(64, return_sequences=False)))
    model.add(Dropout(0.2))
    model.add(Dense(3, activation=''softmax''))

    model.compile(optimizer=''adam'', loss=''categorical_crossentropy'', metrics=[''accuracy''])
    return model

input_shape = (None, 4)  # 4个特征：心率、呼吸率、体温、运动
optimized_model = build_optimized_lstm_model(input_shape)

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
5.2 CNN模型的优势和优化策略
优势：

CNN能够高效地提取局部特征，适用于检测睡眠数据中的特定模式，如呼吸暂停和心率变化。
CNN的参数共享机制减少了模型参数量，提升计算效率。
优化策略：

卷积核优化：通过实验选择最优的卷积核大小和池化策略，提高特征提取能力。
深层网络：构建更深的卷积网络（如ResNet、DenseNet），提升模型的表达能力和准确性。
from keras.models import Sequential
from keras.layers import Conv1D, MaxPooling1D, Flatten, Dense

def build_optimized_cnn_model(input_shape):
    model = Sequential()
    model.add(Conv1D(64, kernel_size=3, activation=''relu'', input_shape=input_shape))
    model.add(MaxPooling1D(pool_size=2))
    model.add(Conv1D(128, kernel_size=3, activation=''relu''))
    model.add(MaxPooling1D(pool_size=2))
    model.add(Flatten())
    model.add(Dense(128, activation=''relu''))
    model.add(Dense(3, activation=''softmax''))

    model.compile(optimizer=''adam'', loss=''categorical_crossentropy'', metrics=[''accuracy''])
    return model

input_shape = (100, 4)  # 100个时间步，4个特征
optimized_cnn_model = build_optimized_cnn_model(input_shape)

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
5.3 Transformer模型的优势和优化策略
优势：

Transformer模型擅长捕捉长时间序列中的复杂依赖关系，适用于分析多模态生理数据。
多头注意力机制能够同时关注不同时间步的特征，提高模型的表达能力。
优化策略：

多头注意力机制优化：调整注意力头的数量和尺寸，找到最佳配置，提升模型性能。
层次优化：通过实验选择最优的Transformer层数和平行化策略，提高模型的效率和准确性。
from keras.models import Model
from keras.layers import Input, Dense, MultiHeadAttention, LayerNormalization, Dropout

def build_optimized_transformer_model(input_shape, num_heads=4, ff_dim=64):
    inputs = Input(shape=input_shape)
    attention_output = MultiHeadAttention(num_heads=num_heads, key_dim=ff_dim)(inputs, inputs)
    attention_output = LayerNormalization(epsilon=1e-6)(attention_output)
    ffn_output = Dense(ff_dim, activation=''relu'')(attention_output)
    ffn_output = Dense(input_shape[-1])(ffn_output)
    outputs = LayerNormalization(epsilon=1e-6)(ffn_output)
    model = Model(inputs, outputs)
    model.compile(optimizer=''adam'', loss=''mse'', metrics=[''accuracy''])
    return model

input_shape = (100, 4)
optimized_transformer_model = build_optimized_transformer_model(input_shape)

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
6. 数据隐私与安全策略
在使用穿戴设备监测用户睡眠数据时，确保数据的隐私与安全至关重要。以下是一些关键策略：

数据加密：在数据传输和存储过程中，使用加密技术保护数据安全。
from cryptography.fernet import Fernet

# 生成密钥
key = Fernet.generate_key()
cipher_suite = Fernet(key)

# 加密数据
data = b"Sensitive user data"
encrypted_data = cipher_suite.encrypt(data)

# 解密数据
decrypted_data = cipher_suite.decrypt(encrypted_data)
1
2
3
4
5
6
7
8
9
10
11
12
数据匿名化：在数据处理和分析过程中，去除或模糊化用户身份信息，保护用户隐私。
import pandas as pd

# 模拟用户数据
data = pd.DataFrame({
    ''user_id'': [''user1'', ''user2'', ''user3''],
    ''heart_rate'': [70, 65, 80],
    ''sleep_stage'': [''deep'', ''light'', ''REM'']
})

# 匿名化处理
data[''user_id''] = data[''user_id''].apply(lambda x: ''user_'' + str(hash(x)))
print(data)
1
2
3
4
5
6
7
8
9
10
11
12
访问控制：限制对数据的访问权限，确保只有授权人员和系统能够访问用户数据。
from flask import Flask, request, jsonify
from functools import wraps

app = Flask(__name__)

# 模拟用户数据存储
user_data = {
    ''user_1'': {''heart_rate'': 70, ''sleep_stage'': ''deep''},
    ''user_2'': {''heart_rate'': 65, ''sleep_stage'': ''light''}
}

# 模拟访问控制
def requires_auth(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        auth = request.headers.get(''Authorization'')
        if auth != ''Bearer secret-token'':
            return jsonify({"message": "Unauthorized"}), 403
        return f(*args, **kwargs)
    return decorated

@app.route(''/api/data'', methods=[''GET''])
@requires_auth
def get_data():
    user_id = request.args.get(''user_id'')
    return jsonify(user_data.get(user_id, {"message": "User not found"}))

if __name__ == ''__main__'':
    app.run()

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
7. 深入探讨未来发展方向
7.1. 多模态数据融合
现状与挑战：
当前的穿戴设备主要依赖心率、呼吸率、体温和运动数据进行睡眠监测。虽然这些数据已经能够提供较为全面的睡眠分析，但仍存在一些局限，如对睡眠环境的考虑不足、对其他生理信号（如脑电波）的利用较少。

未来发展：
未来的穿戴设备可以通过集成更多类型的传感器，实现多模态数据融合。这不仅包括更多的生理数据（如皮肤电反应、血氧饱和度），还可以包含环境数据（如噪音、光照、温度）和行为数据（如作息时间、日常活动）。通过这些数据的综合分析，能够更准确地判断用户的睡眠质量，并提供更加个性化的建议。

示例：

# 模拟多模态数据采集
def collect_multimodal_data():
    heart_rate = np.random.normal(60, 5)
    respiration_rate = np.random.normal(16, 2)
    temperature = np.random.normal(36.5, 0.5)
    movement = np.random.normal(0, 1)
    skin_conductance = np.random.normal(5, 1)  # 皮肤电反应
    blood_oxygen = np.random.normal(98, 1)  # 血氧饱和度
    noise_level = np.random.normal(30, 5)  # 噪音水平
    return np.array([heart_rate, respiration_rate, temperature, movement, skin_conductance, blood_oxygen, noise_level])

# 模拟数据采集
multimodal_data = collect_multimodal_data()
print("多模态数据采集:", multimodal_data)
1
2
3
4
5
6
7
8
9
10
11
12
13
14
7.2. 自适应学习
现状与挑战：
目前的模型通常基于固定的数据集进行训练，模型更新和优化需要重新训练并部署。用户的个体差异和动态变化难以实时反映到模型中。

未来发展：
通过自适应学习，可以实现模型的持续优化和个性化调整。自适应学习包括在线学习和增量学习，能够在接收到新的数据和用户反馈后，自动调整模型参数，提升模型的准确性和个性化程度。

示例：

from sklearn.linear_model import SGDClassifier
import numpy as np

# 模拟数据
X_train = np.random.rand(100, 7)  # 7个特征
y_train = np.random.randint(0, 3, 100)  # 3个睡眠阶段

# 初始训练
model = SGDClassifier()
model.fit(X_train, y_train)

# 模拟新的数据
X_new = np.random.rand(10, 7)
y_new = np.random.randint(0, 3, 10)

# 在线学习更新模型
model.partial_fit(X_new, y_new)

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
7.3. 跨平台集成
现状与挑战：
当前的穿戴设备和睡眠监测系统多为独立运行，缺乏与其他健康管理系统的集成。用户需要分别查看和管理不同平台的数据，不利于全面的健康管理。

未来发展：
通过跨平台集成，可以实现不同健康数据的互通和综合分析。例如，将睡眠数据与日常活动、饮食、心理状态等数据进行关联分析，提供更全面的健康管理服务。跨平台集成还可以实现数据的共享和协同，提高健康管理的整体效果。

示例：

from flask import Flask, request, jsonify

app = Flask(__name__)

# 模拟多平台数据
sleep_data = {
    ''user_1'': {''heart_rate'': 70, ''sleep_stage'': ''deep''},
    ''user_2'': {''heart_rate'': 65, ''sleep_stage'': ''light''}
}

activity_data = {
    ''user_1'': {''steps'': 10000, ''calories_burned'': 500},
    ''user_2'': {''steps'': 8000, ''calories_burned'': 400}
}

# 跨平台数据集成
@app.route(''/api/health_data'', methods=[''GET''])
def get_health_data():
    user_id = request.args.get(''user_id'')
    if user_id in sleep_data and user_id in activity_data:
        combined_data = {**sleep_data[user_id], **activity_data[user_id]}
        return jsonify(combined_data)
    else:
        return jsonify({"message": "User not found"}), 404

if __name__ == ''__main__'':
    app.run()

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
8. 深度学习模型优化
现状与挑战：
深度学习模型通常计算量大，资源消耗高，难以在资源受限的穿戴设备上高效运行。

未来发展：
通过模型压缩、知识蒸馏等技术，减少模型的计算复杂度和存储需求。此外，使用边缘计算，将部分计算任务下放到设备端，提高实时性和响应速度。

模型压缩和知识蒸馏示例：

from tensorflow_model_optimization.sparsity import keras as sparsity
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense

# 构建一个简单的神经网络模型
def build_model():
    model = Sequential([
        Dense(128, activation=''relu'', input_shape=(7,)),
        Dense(64, activation=''relu''),
        Dense(3, activation=''softmax'')
    ])
    model.compile(optimizer=''adam'', loss=''categorical_crossentropy'', metrics=[''accuracy''])
    return model

model = build_model()

# 使用模型剪枝技术
pruning_schedule = sparsity.PolynomialDecay(initial_sparsity=0.30, final_sparsity=0.70, begin_step=1000, end_step=2000)
model_for_pruning = sparsity.prune_low_magnitude(model, pruning_schedule=pruning_schedule)

model_for_pruning.compile(optimizer=''adam'', loss=''categorical_crossentropy'', metrics=[''accuracy''])
model_for_pruning.summary()

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
9. 总结
1、通过AI大模型与穿戴设备的深度融合，可以实现更加智能和个性化的睡眠监测与管理。多模态数据融合、实时监测与反馈、个性化建议生成等技术的应用，能够帮助用户更好地理解和改善自己的睡眠质量。未来，随着技术的不断进步，这种智能化的睡眠监测系统将会越来越普及，为用户提供更全面的健康管理服务。

2、通过详细分析AI大模型在穿戴设备睡眠监测中的技术架构、模型选择、数据处理、实时性要求和隐私保护，可以更好地理解其深度融合应用。选择适合的模型并进行优化，确保数据隐私和安全，是实现智能化睡眠监测系统的关键。未来，随着技术的不断进步，这种智能化的睡眠监测系统将会越来越普及，为用户提供更全面和个性化的健康管理服务。

3、AI大模型在穿戴设备睡眠监测中的深度融合应用，是通过多模态数据融合、自适应学习、跨平台集成以及模型优化等多种技术的综合应用，来实现更加智能和个性化的睡眠管理。未来，随着技术的不断进步和数据的积累，这种智能化的睡眠监测系统将会越来越普及，为用户提供更全面、更科学的健康管理服务。
————————————————

                            欢迎转载：https://rjdeng.blog.csdn.net/

原文链接：https://blog.csdn.net/rjdeng/article/details/139353961
  '),
(6,'Mysql 快速入门指南',
'1. MySQL简介
什么是MySQL
MySQL是一个开源的关系型数据库管理系统（RDBMS），它采用结构化查询语言（SQL）来管理和操作数据库。MySQL以其高性能、高可靠性和易用性而闻名，被广泛应用于各种Web应用和数据密集型应用中。其开源性和社区支持使得MySQL成为开发人员和企业的首选。

MySQL的历史和发展
MySQL的开发始于1995年，由瑞典的MySQL AB公司创立，创始人包括Michael Widenius、David Axmark和Allan Larsson。2008年，MySQL被Sun Microsystems收购，2010年，随着Sun Microsystems被Oracle公司收购，MySQL也成为Oracle旗下的一部分。MySQL在不断的发展过程中，经历了多个重要的版本更新，每个新版本都带来了显著的功能改进和性能提升。

MySQL的应用场景
MySQL被广泛应用于各种应用场景，包括：

Web开发：MySQL作为LAMP（Linux, Apache, MySQL, PHP/Python/Perl）和LEMP（Linux, Nginx, MySQL, PHP/Python/Perl）栈的一部分，被广泛用于动态网站和Web应用。
内容管理系统（CMS）：许多流行的CMS如WordPress、Drupal和Joomla都使用MySQL作为数据库后台。
电子商务平台：MySQL为许多电子商务网站提供数据存储和管理功能。
数据分析和商业智能：MySQL可用于存储和分析大量的业务数据，帮助企业做出数据驱动的决策。
嵌入式系统：由于MySQL的高性能和轻量级特点，它也被广泛用于嵌入式系统和物联网设备中。
2. 安装与配置
在不同操作系统上的安装
Windows
下载MySQL安装程序：
从MySQL官方网站（https://dev.mysql.com/downloads/installer/）下载适合的安装程序。

运行安装程序：
双击安装程序，按照安装向导的指示进行操作。选择适合的安装类型（如开发者默认安装、服务器安装等）。

配置MySQL服务器：
安装过程中，会要求配置MySQL服务器的基本设置，如端口号（默认3306）、root用户密码、字符集（建议选择utf8mb4），以及选择是否作为Windows服务启动。

完成安装并启动MySQL服务：
安装完成后，启动MySQL服务，并通过命令行或MySQL Workbench连接到MySQL服务器。

Linux
使用包管理器安装：

Ubuntu/Debian：
sudo apt update
sudo apt install mysql-server
1
2
CentOS/RHEL：
sudo yum install mysql-server
sudo systemctl start mysqld
sudo systemctl enable mysqld
1
2
3
安全配置：
安装完成后，运行mysql_secure_installation命令，设置root用户密码，并根据提示进行安全配置（如删除匿名用户、禁止远程root登录、删除测试数据库等）。

启动MySQL服务：
确保MySQL服务已启动，并配置为开机启动：

sudo systemctl start mysql
sudo systemctl enable mysql
1
2
macOS
使用Homebrew安装：

brew install mysql
1
启动MySQL服务：

brew services start mysql
1
配置MySQL：
设置root用户密码，并进行必要的安全配置：

mysql_secure_installation
1
MySQL服务器的配置
MySQL的配置文件通常为my.cnf（Linux和macOS）或my.ini（Windows），这些文件包含了服务器运行时的各种配置选项。下面是一些常见的配置选项及其解释：

port：MySQL服务器监听的端口号，默认是3306。
datadir：数据文件存放目录，通常为/var/lib/mysql（Linux）或C:\ProgramData\MySQL\MySQL Server X.Y\Data\（Windows）。
socket：UNIX socket文件路径（仅Linux和macOS），通常为/var/run/mysqld/mysqld.sock。
max_connections：最大连接数，默认值通常为151，可根据实际需求调整。
default-storage-engine：默认存储引擎，如InnoDB。InnoDB提供事务支持和外键约束。
character-set-server 和 collation-server：服务器的默认字符集和排序规则，建议设置为utf8mb4和utf8mb4_general_ci以支持多语言字符。
[mysqld]
port=3306
datadir=/var/lib/mysql
socket=/var/run/mysqld/mysqld.sock
max_connections=200
default-storage-engine=InnoDB
character-set-server=utf8mb4
collation-server=utf8mb4_general_ci
1
2
3
4
5
6
7
8
3. MySQL基础操作
连接到MySQL服务器
通过命令行客户端连接到MySQL服务器：

mysql -u root -p
1
输入密码后，即可进入MySQL命令行界面。

基本SQL语法
MySQL使用标准的SQL语法进行数据库管理。以下是一些基本的SQL操作：

创建数据库：

CREATE DATABASE mydatabase;
1
切换到某个数据库：

USE mydatabase;
1
创建表：

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100)
);
1
2
3
4
5
6
插入数据：

INSERT INTO users (username, password, email) VALUES (''john_doe'', ''securepassword'', ''john@example.com'');
1
查询数据：

SELECT * FROM users;
1
更新数据：

UPDATE users SET email = ''john_doe@example.com'' WHERE username = ''john_doe'';
1
删除数据：

DELETE FROM users WHERE username = ''john_doe'';
1
数据库和表的管理
查看所有数据库：

SHOW DATABASES;
1
查看当前数据库的所有表：

SHOW TABLES;
1
查看表结构：

DESCRIBE users;
1
删除数据库：

DROP DATABASE mydatabase;
1
删除表：

DROP TABLE users;
1
4. 数据库设计
数据库范式
数据库范式（Normalization）是数据库设计的一种理论，用于减少数据冗余，提高数据一致性。常见的范式包括：

第一范式（1NF）：确保每列的原子性，即每列都是不可再分的数据单元。

示例：在用户表中，每个用户的电话号码应该存储在单独的列中，而不是一个逗号分隔的字符串。
第二范式（2NF）：在满足1NF的基础上，确保每个非主键列完全依赖于主键。

示例：订单表中，订单详情应完全依赖于订单ID，而不是部分依赖。
第三范式（3NF）：在满足2NF的基础上，确保每个非主键列不依赖于其他非主键列。

示例：员工表中，员工地址不应依赖于部门ID，而应独立于其他信息。
表与关系的设计
设计数据库表时，需要考虑表之间的关系，如一对一、一对多和多对多关系。例如：

一对多关系：

一个用户可以有多个订单，一个订单只能属于一个用户。
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
1
2
3
4
5
6
多对多关系：

一个学生可以选修多门课程，一门课程可以有多个学生选修。
使用中间表实现多对多关系：
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100)
);

CREATE TABLE student_courses (
    student_id INT,
    course_id INT,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN


1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
KEY (course_id) REFERENCES courses(id)
);


#### 索引设计

索引是提高查询性能的重要工具。常见的索引类型包括：

- **主键索引**：唯一且非空的索引，如`PRIMARY KEY`。
- **唯一索引**：确保索引列的值唯一，如`UNIQUE`。
- **普通索引**：用于加速查询的普通索引，如`INDEX`。
- **全文索引**：用于全文搜索的索引，如`FULLTEXT`。

```sql
CREATE INDEX idx_username ON users(username);
1
2
3
4
5
6
7
8
9
10
11
12
外键与约束
外键用于维护表之间的关系和数据完整性。例如：

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
1
2
3
4
5
6
5. 高级操作与优化
事务管理
事务是一个或多个SQL操作的集合，这些操作要么全部成功，要么全部失败。MySQL通过START TRANSACTION、COMMIT和ROLLBACK语句来管理事务。

START TRANSACTION;
UPDATE accounts SET balance = balance - 100 WHERE user_id = 1;
UPDATE accounts SET balance = balance + 100 WHERE user_id = 2;
COMMIT;
1
2
3
4
事务特性（ACID）：
原子性（Atomicity）：事务的所有操作要么全部完成，要么全部不做。
一致性（Consistency）：事务前后数据库的状态要一致。
隔离性（Isolation）：一个事务的操作不会被其他事务干扰。
持久性（Durability）：事务一旦提交，数据将永久保存。
存储过程与触发器
存储过程是预编译的SQL代码，触发器是在特定事件发生时自动执行的SQL代码。例如：

创建存储过程：

DELIMITER //
CREATE PROCEDURE AddUser(IN username VARCHAR(50), IN password VARCHAR(50), IN email VARCHAR(100))
BEGIN
    INSERT INTO users (username, password, email) VALUES (username, password, email);
END //
DELIMITER ;
1
2
3
4
5
6
调用存储过程：

CALL AddUser(''jane_doe'', ''securepassword'', ''jane@example.com'');
1
创建触发器：

CREATE TRIGGER before_insert_user
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    SET NEW.created_at = NOW();
END;
1
2
3
4
5
6
视图与临时表
视图是虚拟表，用于简化复杂查询。临时表是会话级别的临时存储，用于复杂查询的中间结果。

创建视图：

CREATE VIEW user_orders AS
SELECT users.username, orders.order_date
FROM users
JOIN orders ON users.id = orders.user_id;
1
2
3
4
使用临时表：

CREATE TEMPORARY TABLE temp_orders AS
SELECT * FROM orders WHERE order_date >= ''2023-01-01'';
1
2
查询优化与索引优化
查询优化包括分析和优化SQL查询，以提高执行效率。常见的优化技术包括：

使用适当的索引：确保查询中的列有适当的索引，以加快数据检索。

**避免SELECT ***：仅选择必要的列，减少数据传输量。

使用连接（JOIN）而不是子查询：在大多数情况下，连接操作比子查询更高效。

EXPLAIN命令：用于分析查询执行计划，了解查询的性能瓶颈。

EXPLAIN SELECT * FROM users WHERE username = ''john_doe'';
1
MySQL性能调优
性能调优包括调整服务器配置、优化数据库设计和查询。常见的调优参数包括：

innodb_buffer_pool_size：调整InnoDB缓冲池大小，以便更有效地缓存数据和索引。通常设置为物理内存的70-80%。
query_cache_size：调整查询缓存大小，但注意在高并发环境中，查询缓存可能会带来性能问题。
tmp_table_size：调整临时表大小，以防止复杂查询时频繁使用磁盘临时表。
max_connections：调整最大连接数，以处理更多的并发连接。
6. 安全性
用户与权限管理
MySQL通过创建用户和分配权限来管理数据库访问。例如：

创建用户：

CREATE USER ''newuser''@''localhost'' IDENTIFIED BY ''password'';
1
分配权限：

GRANT SELECT, INSERT, UPDATE ON mydatabase.* TO ''newuser''@''localhost'';
1
撤销权限：

REVOKE INSERT ON mydatabase.* FROM ''newuser''@''localhost'';
1
删除用户：

DROP USER ''newuser''@''localhost'';
1
数据加密
MySQL支持数据传输加密和数据存储加密。通过SSL/TLS加密数据传输，通过InnoDB表空间加密保护数据存储。

启用SSL/TLS：
编辑MySQL配置文件my.cnf，添加以下配置：

[mysqld]
ssl-ca=/path/to/ca.pem
ssl-cert=/path/to/server-cert.pem
ssl-key=/path/to/server-key.pem
1
2
3
4
重启MySQL服务：

sudo systemctl restart mysql
1
验证SSL连接：

mysql --ssl-ca=/path/to/ca.pem --ssl-cert=/path/to/client-cert.pem --ssl-key=/path/to/client-key.pem -u root -p
1
安全审计
MySQL企业版提供了安全审计功能，用于记录和监控数据库活动。开源版可以使用第三方工具实现类似功能，如Percona Server的审计插件。

启用审计插件（以Percona Server为例）：
[mysqld]
plugin-load-add=audit_log.so
audit_log_format=JSON
audit_log_file=/var/log/mysql/audit.log
1
2
3
4
7. 备份与恢复
备份策略
备份是确保数据安全的重要手段。常见的备份策略包括：

完全备份：备份整个数据库。
增量备份：备份自上次完全备份或增量备份以来的更改部分。
差异备份：备份自上次完全备份以来的所有更改部分。
备份工具
mysqldump：适用于小型数据库的备份工具。

mysqldump -u root -p mydatabase > mydatabase_backup.sql
1
MySQL Enterprise Backup：适用于大型数据库和企业环境，支持热备份和增量备份。

Percona XtraBackup：开源的备份工具，支持InnoDB和XtraDB存储引擎的热备份。

数据恢复
数据恢复包括从备份文件中恢复数据。例如，使用mysqldump备份文件恢复数据库：

mysql -u root -p mydatabase < mydatabase_backup.sql
1
恢复特定数据库或表：
mysqldump -u root -p mydatabase mytable > mytable_backup.sql
mysql -u root -p mydatabase < mytable_backup.sql
1
2
8. 常见问题与解决方法
常见错误与故障排除
连接错误：

检查MySQL服务是否启动。
检查连接配置是否正确，包括主机名、端口号、用户名和密码。
权限错误：

检查用户权限设置，使用SHOW GRANTS FOR ''user''@''host'';查看权限。
确保用户拥有执行相应操作的权限。
性能问题：

使用EXPLAIN分析查询执行计划，了解查询的性能瓶颈。
检查索引使用情况，确保必要的列已创建索引。
使用性能监控工具，如MySQL Enterprise Monitor或Percona Monitoring and Management（PMM）。
9. 总结与未来展望
MySQL作为一种强大且广泛使用的关系型数据库管理系统，在各个方面都有着丰富的功能和广泛的应用。未来，随着技术的不断发展，MySQL将继续优化性能，增强安全性，并引入更多高级功能，以满足不断变化的需求。
————————————————

                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。

原文链接：https://blog.csdn.net/weixin_41883161/article/details/139520060
'),
(7,'vue2自定义指令',
'基本语法

使用: v-指令名="指令值"
定义: 通过 directives 局部定义或者全局定义
通过事件对象 el 可以拿到指令所在元素
通过形参 binding 可以拿到指令的传值
通过update钩子, 可以监听指令值的变化,进行更新操作
局部注册

<template>
  <div id="app">
    <input v-focus type="text" />
  </div>
</template>

<script>
export default {
  // 局部注册自定义指令
  directives: {
    focus: {
      // 指定的生命周期: 指令所在的元素, 挂载完毕后触发
      inserted(el) {
        // el就是指令绑定的元素
        el.focus();
      },
    },
  },
};
</script>

全局注册

... ...

Vue.directive(''focus'', {
  // 指定所在的元素被加载后执行
  inserted: function (el) {
    // el就是所绑定的元素
    el.focus()
  }
})

... ...
指令传值

<template>
  <div id="app">
    <h2 v-color="color1">我是一个标题</h2>
    <h2 v-color="color2">我是一个标题</h2>
  </div>
</template>

<script>
export default {
  data() {
    return {
      color1: "red",
      color2: "blue",
    }
  },
  // 局部注册自定义指令
  directives: {
    color: {
      inserted(el, binding) {
        el.style.color = binding.value;
      },
      // 指令的值(binding)修改时触发
      update(el,binding) {
        el.style.color = binding.value;
      }
    }
  },
};
</script>

v-loading
封装一个v-loading指令, 实现加载中的效果

分析

本质loading效果就是一个蒙层, 盖在盒子上
数据请求时, 开启loading状态, 添加蒙层
数据请求完毕, 关闭loading状态, 移除蒙层
实现

准备一个loading类, 通过伪元素定位, 设置宽高, 实现蒙层
开启关闭loading状态, 本质只需要添加和移除类名即可
结合自定义指令的语法进行封装复用
<template>
  <!-- 2,使用loading指令 -->
    <div class="box" v-loading="isLoading">
      <ul>
        <li v-for="item in list" :key="item">
            我是内容{{ item }}
        </li>
      </ul>
    </div>
</template>

<script>
export default {
  data () {
    return {
      list: [],
      isLoading: true,
    }
  },
  async created () {
    setTimeout(() => {
      this.list = [1,2,3,4,5]
      // 3,关闭loading效果
      this.isLoading = false
    }, 2000)
  },
  directives: {
    // 1, 注册loading指令
    loading: {
      inserted(el, binding) {
        binding.value ? el.classList.add(''loading'') : el.classList.remove(''loading'')
      },
      update(el, binding) {
        binding.value? el.classList.add(''loading'') : el.classList.remove(''loading'')
      }
    }
  }
}
</script>

<style>
/* 伪类 - 蒙层效果 */
.loading:before {
  content: '''';
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: #fff url(''./loading.gif'') no-repeat center;
}

.box {
  width: 800px;
  min-height: 500px;
  border: 3px solid orange;
  position: relative;
}
</style>
文章知识点
————————————————

                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。

原文链接：https://blog.csdn.net/CSDN20221005/article/details/139575864
'),
(8,'Python 标准库中常用的模块',
'Python 标准库中包含了很多常用的模块，以下是一些常用的模块：

math：提供了数学运算函数，如三角函数、对数函数、指数函数等。
random：提供了生成随机数的函数。
datetime：提供了处理日期和时间的函数，如获取当前日期和时间、格式化日期和时间等。
os：提供了与操作系统交互的函数，如获取当前工作目录、创建和删除文件夹、执行系统命令等。
sys：提供了与 Python 解释器交互的函数，如获取命令行参数、退出程序等。
json：提供了处理 JSON 数据的函数，如将 JSON 数据解析为 Python 对象、将 Python 对象转换为 JSON 数据等。
csv：提供了处理 CSV 文件的函数，如读取和写入 CSV 文件等。
re：提供了正则表达式的函数，用于对字符串进行模式匹配和替换。
collections：提供了一些有用的集合类，如字典、列表等。
urllib：提供了与网络通信的函数，如发送 HTTP 请求、下载文件等。
sqlite3：提供了与 SQLite 数据库交互的函数，如创建和查询数据库表等。
multiprocessing：提供了实现多进程编程的函数和类。
threading：提供了实现多线程编程的函数和类。
socket：提供了创建网络应用程序的函数和类，如创建 TCP 和 UDP 套接字等。
这只是一部分常用的模块，还有很多其他有用的模块可以在 Python 标准库中找到。
————————————————

                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。

原文链接：https://blog.csdn.net/xy520521/article/details/139567871
'),
(9,'Linux基础指令（一）',
'前言
Linux基础指令主要学习：对目录、文件、压缩包、匹配查找，权限等操作

第一次接触ubuntu需要知道的基本知识
sudo passwd root                先给root用户设置密码

su root                                  切换到root用户

su zhangsan                        切换到普通用户

apt-get install net-tools     安装网络工具

ifconfig                                 查看网卡ip地址

Linux中终端一旦连接成功，就可以开始敲命令，所有的命令都是从目录或者文件开始的。

打开终端后。默认会处于一个目录下

Linux是一个多用户操作系统，而对于用户的文件存放是以家（home目录）为单位的。



Windows中每一个盘符都对应了一块硬盘分区，有一个文件系统，可以管理存储的文件，而文件的管理是一种树形结构。



所以Windows是给空间分目录，Linux是给目录分空间

Linux下的常用指令
命令执行的一个格式

命令名称 操作选项 操作对象

ls
ls                        查看目录内容

ls -a                   查看所有文件，包括隐藏文件

-l                        查看文件的详细信息（包括属性）

ll                         等同于ls -a -l

在Linux中有一种文件，叫做隐藏文件，而隐藏文件就是文件名以 . 开始的文件

当需要表示一个文件所在位置的时候，我们需要一个完整的路径进行标识。

而路径表示的方式有两种：

绝对路径：唯一路径，从根目录开始表示的路径

相对路径：以当前所在位置开始表示的路径，会随着当前所在位置而变化

man
man                                     查看手册

cd
cd                                        进入目录

change direct                     变当前的工作路径

cd /                                      进入根目录

cd ~                                     进入当前用户的家目录

rm
rm                                 删除普通文件(无法删除目录)

rmdir                            只能对空的目录进行删除（为了避免误删而设计的）

rmdir -p.                      递归向外删除（从内向外）

rm -r                            递归删除指定目录下的所有文件后，删除指定目录

rm -ri                           在删除的时候给一个提示信息

rm -rf                           忽略所有提示信息，直接进行删除操作

rm ./*

(其中 . 表示当前目录，/ 表示是一个目录，* 通配符表示任意文件）

pwd
pwd                                   全称print work direct        打印出来的路径是一个绝对路径

mkdir
mkdir                                全称make direct                 创建一个目录

mkdir -p                           递归级多层目录创建，从父级目录开始，哪一层不存在就直接创建（即使没有父目录都可以全部创建）

比如在当前目录下没有workspace这一目录

那么

mkdir ./workspace/abc

无法进行创建

而使用

mkdir -p ./workspace/abc

就可以进行创建

cp
cp                                           拷贝一个文件到指定位置

cp /etc/passwd ./                 将/etc下的passwd给当前目录拷贝一份

cp /etc/passwd ./pass         拷贝的那份改名

cp -r                                      递归拷贝指定目录下的所有文件

mv
mv                                         移动一个文件到指定的位置（剪切一个文件到指定目录）

另类用法：改名

mv passwd pass                  即可改名


————————————————

                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。

原文链接：https://blog.csdn.net/m0_61630449/article/details/139552046
'),
(10,'【C++】初识C++',
'文章概括
谈谈C++在学习前的认知，C++是在C的基础上，容纳进去了面向对象的编程思想，并增加了许多有用的库，以及编程范式等等。所以学习C++之前一定对C有一定的认知，一个好的C++程序员一定会是一个优秀的C语言程序员。

本章主要介绍：补充C语言语法的不足，以及C++是如何对C语言程序设计不合理地方进行优化的，比如：作用域方面、IO方面、函数方面、指针方面、宏方面等等；同时也为后续学习类和对象做了铺垫。

关键字（C++98）
C++有63个关键字，C语言有32个关键字。

关键字	关键字	关键字	关键字	关键字
asm	auto	bool	break	case
catch	char	class	const	const_cast
delete	do	double	dynamic_cast	else
enum	explicit	export	extern	false
float	goto	if	inline	int
long	mutable	namespace	new	operator
private	protected	reinterpret_cast	return	short
signed	sizeof	static	static_case	struct
switch	template	this	try	typedef
typeid	typename	union	unsigned	using
virtual	void	volatile	continue	for
public	throw	wchar_t	default	friend
register	true	while
后续逐渐了解

命名空间
命名空间的定义
先以C语言举例：
假设需要定义一个全局变量随机数random为10

#include<stdio.h>

int rand = 10;

int main(void)
{
	printf("%d\n", rand);

	return 0;
}
1
2
3
4
5
6
7
8
9
10

这是可以编译成功的，但是我们之前有了解过rand是一个头文件stdlib.h的一个库函数，如果我们包含stdlib.h这个头文件会发生什么？

#include<stdio.h>
#include<stdlib.h>
int rand = 10;

int main(void)
{
	printf("%d\n", rand);

	return 0;
}
1
2
3
4
5
6
7
8
9
10

发生报错，这里可以明显突出一个C语言的库命名冲突问题。

有时在一个大的工程中有多个项目，每个项目会由不同的人负责，这时也会难免遇到项目之间的命名问题。

总之，C语言命名冲突的问题有：
1.库命名冲突问题
2.项目相互之间命名的冲突

在C++中，存在命名空间namespace可以解决这类型的问题。

在讲解命名空间前，需要先了解域的概念：域可以看作是一个作用区域，域包含类域、命名空间域、局部域、全局域等等

在一般情况下访问时，会先访问局部域，在局部域中未发现变量，会进而访问全局域。



假设在全局域中存在全局变量，同时在局部域中也存在一个局部变量，但是想要跳过局部域直接访问全局域，应该如何操作？

int a = 1;

int main(void)
{
	int a = 0;
	printf("%d\n", ::a);
	return 0;
}
1
2
3
4
5
6
7
8

这里需要介绍一个操作符"::"，域操作限定符，::a默认会跳过局部域，访问全局域。

那如果存在命名空间域namespace，其优先级是如何？

int a = 1;

namespace project
{
	int a = 2;
}
int main(void)
{
	int a = 0;
	printf("%d\n", a);
	printf("%d\n", ::a);
	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
13





由此可见，访问变量a，会先访问局部域——>然后访问全局域——>最后在默认情况下，编译器并不会主动去命名空间域搜索。

想要搜索命名空间域，有俩种方式：
1.展开命名空间域

namespace project
{
	int a = 2;
}

using namespace project;

int main(void)
{
	printf("%d\n", a);
	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12


2.指定访问命名空间域

namespace project
{
	int a = 2;
}

int main(void)
{
	printf("%d\n", project::a);
	return 0;
}
1
2
3
4
5
6
7
8
9
10


如果在局部域，全局域，命名空间域（展开命名空间域）中都存在变量a，会如何访问？



这里可以发现局部域的优先级最高，但如果只存在全局域与展开后命名空间域时，会发生报错，原因在于：展开的命名空间域相当于暴露在全局域。

所以不要轻易使用using namespace + 名，即不要请轻易展开命名空间域。

总结：在C/C++中，变量、函数和后面要学到的类都是大量存在的，这些变量、函数和类的名称将都存在于全局作用域中，可能会导致很多冲突。使用命名空间的目的是对标识符的名称进行本地化，以避免命名冲突或名字污染，namespace关键字的出现就是针对这种问题的。

命名空间的特性
定义命名空间，需要使用到namespace关键字，后面跟命名空间的名字，然后接一对{}即可，{}中即为命名空间的成员。
namespace project
{
	int a = 2;
}
1
2
3
4
1.命名空间域中可以定义变量、函数、类型

namespace project
{
	//定义变量
	int num = 10;
	//定义函数
	int add(int x, int y)
	{
		return x + y;
	}
	//定义结构体
	struct Node
	{
		struct Node* next;
		int data;
	};
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
2.命名空间可以嵌套

namespace project
{
	namespace N1
	{
		int a = 1;
	}
	namespace N2
	{
		int a = 2;
	//定义函数
	int add(int x, int y)
	{
		return x + y;
	}
	}
}
int main(void)
{
	printf("%d ", project::N1::a);
	printf("%d ", project::N2::a);
	printf("%d ", project::N2::add(1,2));
	return 0;
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23



std是C++标准库的命名空间名，C++将标准库的定义实现都放在这个命名空间里。


【注意】：一个命名空间就定义了一个新的作用域，命名空间中的所有内容都局限于该命名空间中

3.同一个工程中允许存在多个相同的命名空间，编译器最后会合成同一个命名空间，即可以在多个文件中定义相同名字的命名空间






输入与输出
C++中的输入输出
早年在VC6.0时没有命名空间，头文件C++中的头文件

#include<iostream.h>
1
后面改为了

#include<iostream>
#include<vector>
#include<list>
1
2
3
使用iostream这个头文件时，需要先学习C++的输入输出.
c语言中使用printf与scanf来将数据输出与输入，而在C++中使用cout与cin实现输入输出。

#include<iostream>

using namespace std;
int main(void)
{
	int a;
	cin >> a;
	cout << a << endl;
	return 0;
}
1
2
3
4
5
6
7
8
9
10

说明：
1.使用cout标准输出对象（控制台）和cin标准输入对象（键盘）时，必须包含头文件iostream，以及按照命名空间使用方法使用std。
2.cout和cin是全局的流对象，endl是特殊的C++符号，表示换行输出，它们都包含在iostream头文件中
3.<<是流插入运算符，>>是流提取运算符
4.cout和cin的使用比较方便，不需要同printf与scanf一样手动控制格式，C++的输入输出可以手动控制变量类型

输入输出的命名空间
使用输入输出有3种情况：
1.指定访问命名空间域

#include<iostream>
int main(void)
{
	std::cout << "Hello World！" << std::endl;
	return 0;
}
1
2
3
4
5
6

2.使用展开命名空间域

#include<iostream>
using namespace std;
int main(void)
{
	cout << "Hello World!" << endl;
	return 0;
}
1
2
3
4
5
6
7

编译器会去std这个命名空间搜索（std这个命名空间域中封有iostream）
【注意】直接展开std会有很大的风险，当存在自己定义的名字与库中名字重合会报错，建议项目中不要展开，日常使用可以进行展开，项目中建议指定访问，不要轻易展开命名空间。

3.展开部分命名

#include<iostream>
using std::cout;
using std::endl;
int main(void)
{
	cout << "Hello World!" << endl;
	return 0;
}
1
2
3
4
5
6
7
8


缺省参数
缺省参数也称默认参数，即函数在传参的时候可以存在缺省参数（默认参数）。

void Init(int* node, int sz = 4)
{
	int* newnode = (int*)malloc(sizeof(int) * sz);
	if (newnode == NULL)
	{
		perror("malloc fail");
		return;
	}
	node = newnode;
}
int main(void)
{
	int* node;
	//默认情况下，初始化4个字节
	Init(node);
	//可以指定实参，初始化100个字节
	Init(node, 100);
	return 0;
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
观察代码，在C++中传参存在俩种情况：
1.没有参数时，使用参数的默认值
2.有任何参数时，使用指定的实参
即实参的优先级最大，当不存在实参时，使用默认参数
【注意】当存在多个缺省参数时，不允许跳着传参，只能从左到右顺序传参

#include<iostream>

using namespace std;
//全缺省
int RetAdd(int a = 1, int b = 2, int c = 3)
{
	return a + b + c;
}
int main(void)
{
	int sum = RetAdd();
	cout << sum << endl;
	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
#include<iostream>

using namespace std;
//半缺省
int RetAdd(int a, int b, int c = 3)
{
	return a + b + c;
}
int main(void)
{
	int sum = RetAdd(1,2);
	cout << sum << endl;
	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
C++中全缺省与半缺省的概念
全缺省：所有的参数都给了缺省值
半缺省：缺省部分参数
【注意】半缺省参数必须从右至左依次缺省，切勿间隔缺省


【注意】在使用缺省参数使用需要注意，声明与定义不能同时给缺省值，一般在声明时存在缺省值，定义时不存在缺省值。
如果了解文件的编译与链接可以知道，编译期间只能看到声明，链接期间可以看到定义

函数重载
重载的意思是：一词多义
那么函数重载：是函数的一种特殊情况，C++允许在同以作用域中声明几个功能类似的同名函数，这些函数的形参列表（参数个数、参数类型、类型顺序）不同，常用来处理实现功能类似数据类型不同的问题。

参数类型不同
#include<iostream>

using namespace std;
//参数类型不同
int RetAdd(int a, int b)
{
	return a + b;
}
double RetAdd(double a, double b)
{
	return a + b;
}
int main(void)
{
	cout << RetAdd(1, 2) << endl;
	cout << RetAdd(1.5, 2.2) << endl;

	return 0;
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19


参数个数不同
#include<iostream>

using namespace std;
//参数个数不同
void Fun()
{
	cout << "无参数" << endl;
}
void Fun(int a)
{
	cout << "有参数" << endl;
}

int main(void)
{
	Fun();
	Fun(1);
	return 0;
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19


类型顺序不同
#include<iostream>
using namespace std;
//类型顺序不同
double RetAdd(int a, double b)
{
	return a + b;
}
double RetAdd(double a, int b)
{
	return a + b;
}
int main(void)
{
	cout << RetAdd(1, 2.3) << endl;
	cout << RetAdd(1.9, 2) << endl;

	return 0;
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18

【注意】有三个不构成函数重载的例子
1.仅返回值不同



2.仅变量名不同



3.不明确的函数调用



引用
引用的概念
引用不是新定义一个变量，而是给已存在变量取一个别名，编译器不会为引起变量开辟内存空间，它和它的变量共用同一块内存空间。

#include<iostream>
using namespace std;

int main(void)
{
	int a = 10;
	int& b = a;
	int& c = b;
	int& d = a;
	printf("%p\n", &a);
	printf("%p\n", &b);
	printf("%p\n", &c);
	printf("%p\n", &d);
	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15


类型& ： 引用变量名（对象名） = 引用实体

在学习C语言阶段，想要找到一个变量，可以使用指针，而在C++中引入了引用的概念，可以大幅度代替指针的作用。

int main(void)
{
	int a = 0;
	int* pa = &a;
	int** ppa = &pa;
	printf("%p\n", &a);
	printf("%p\n", pa);
	printf("%p\n", *ppa);
	return 0;
}
1
2
3
4
5
6
7
8
9
10






引用的特性
1.引用在定义时必须需要初识化在这里插入图片描述

2.一个变量可以有多个引体

#include<iostream>
using namespace std;

int main(void)
{
	int a = 10;
	int& b = a;
	int& c = a;
	int& d = a;

	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
3.引用一旦引用一个实体，便不可再引用其他引体

#include<iostream>
using namespace std;

int main(void)
{
	int a = 10;
	int x = 20;
	int& b = a;
	b = x;
	cout << b << endl;

	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
13


引用地使用场景
引用做参数
引用做参数时，可以作为输出型参数使用，何为输出型参数？

函数在传参地时候，会传输出型参数或者输入型参数，输入型参数的意思是形参的改变不可以改变实参，即形参是实参的一份临时拷贝；输出型参数的意思是形参的改变要改变实参。

在C语言中一般使用指针来做输出型参数：

//链表
typedef struct ListNode
{
	int data;
	struct ListNode* next;
}PNode;

void LTPushBack(PNode* p, int x);
1
2
3
4
5
6
7
8
而在C++中，可以使用引用来做输出型参数：

//链表
typedef struct ListNode
{
	int data;
	ListNode* next;
	//在C++中可以不写struct
}*PNode;

void LTPushBack(PNode& p, int x);
1
2
3
4
5
6
7
8
9
这段代码的意思是：定义一个结构体的指针，引用这个指针并使用phead作为别名。

引用做参数，同时也可以提高效率，但是只存在于数量较大对象或者深拷贝类对象。

下面这段代码可以比较传值与传引用的效率对比：

#include <time.h>
struct A { int a[100000]; };
void TestFunc1(A a) {}
void TestFunc2(A& a) {}
void TestRefAndValue()
{
	A a;
	// 以值作为函数参数
	size_t begin1 = clock();
	for (size_t i = 0; i < 10000; ++i)
		TestFunc1(a);
	size_t end1 = clock();
	// 以引用作为函数参数
	size_t begin2 = clock();
	for (size_t i = 0; i < 10000; ++i)
		TestFunc2(a);
	size_t end2 = clock();
	// 分别计算两个函数运行结束后的时间
	cout << "TestFunc1(A)-time:" << end1 - begin1 << endl;
	cout << "TestFunc2(A&)-time:" << end2 - begin2 << endl;
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21

由于函数在传参期间，以值作为参数，函数不会之间传递实参或者将变量本身直接返回，而是传递实参的一份临时拷贝，因此以值作为参数时的效率是很低的，尤其是参数非常大时，效率会更低。

对象越大，代价就会越大，提高的效率就越多

【注意】引用相比较于指针没有质的区别，但是在C++中实际情况下引用的使用情况较多。

引用做返回值
int Count()
{
	static int n = 0;
	n++;
	return n;
}

int main(void)
{
	int ret = Count();
	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
观察这段代码，使用传值返回时，会生成临时变量，可能会存放在寄存器中（寄存器的大小为4/8个字节，如果寄存器内存不够，会向上申请），作为int ret = Count();这段代码的返回值。

虽然n被static int修饰，成为静态变量，存放在静态区中，但是不管有没有static修饰或者存在全局变量，函数都会根据返回值int，创造出一个临时变量，这样会大大降低效率，那么使用引用做返回值可以解决这样的问题。

下面就是引用做返回值的第一个作用：

减少拷贝，提高效率
int& Count()
{
	static int n = 0;
	n++;
	return n;
}

int main(void)
{
	int ret = Count();
	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
下面这段代码可以比较传值返回与传引用返回：

#include <time.h>
struct A{ int a[10000]; };
A a;
// 值返回
A TestFunc1() { return a;}
// 引用返回
A& TestFunc2(){ return a;}
void TestReturnByRefOrValue()
{
 // 以值作为函数的返回值类型
 size_t begin1 = clock();
 for (size_t i = 0; i < 100000; ++i)
 TestFunc1();
 size_t end1 = clock();
 // 以引用作为函数的返回值类型
 size_t begin2 = clock();
 for (size_t i = 0; i < 100000; ++i)
 TestFunc2();
 size_t end2 = clock();
 // 计算两个函数运算完成之后的时间
 cout << "TestFunc1 time:" << end1 - begin1 << endl;
 cout << "TestFunc2 time:" << end2 - begin2 << endl;
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23


由于函数在传值返回期间，以值作为返回值，函数不会之间传递实参或者将变量本身直接返回，而是返回变量的一份临时拷贝，因此以值作为返回值时的效率是很低的，尤其是参数非常大时，效率会更低。

使用引用做返回值，还有另外一个作用：

修改返回值以及获取返回值
typedef struct SeqList
{
	int arr[100];
	int size;
}SL;

int& SLPostion(SL& s, int pos)
{
	assert(pos < 100 && pos >= 0);

	return s.arr[pos];
}

int main(void)
{
	SL s;
	SLPostion(s, 0) = 1;

	int ret = SLPostion(s, 0);
	cout << ret << endl;
	cout << SLPostion(s, 0) << endl;

	return 0;
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24


如果不使用static修饰静态变量，使用引用做返回值时，结果时不确定的。

int& Count()
{
	int n = 0;
	n++;
	return n;
}

int main(void)
{
	int& ret = Count();
	cout << ret << endl;
	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
13
这段代码虽然不会报错，但是明显可以发现，ret访问是存在越界访问。

int& Count(int n)
{
	n++;
	return n;
}

int main(void)
{
	int& ret = Count(10);
	cout << ret << endl;
	Count(20);
	cout << ret << endl;

	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15


观察这段代码，如果count函数结束，函数建立的栈帧会销毁，在vs编译器上没有清理栈帧，ret的值是第一次函数调用结束后，第二次函数建立在同样的位置。

int& Count(int n)
{
	n++;
	return n;
}

int main(void)
{
	int& ret = Count(10);
	cout << ret << endl;
	rand();
	cout << ret << endl;

	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15

若是随意插入一个函数，则ret的值变成了随机值。

可以发现，在这种情况下使用引用是很危险的。

总结：
1.基本任何场景都是可以使用引用传参
2.谨慎用引用做返回值，出了函数作用域，对象不在了就不能使用引用返回，如果对象还在就可以使用引用返回。
2.可以使用引用返回的场景：静态变量、全局变量、堆区malloc或者calloc

常引用
	const int a = 0;
	int& b = a;
1
2
观察这段代码，当变量a被const修饰变成不可修改的左值时，使用int引用是不可以的，原因是在引用的过程中国权限不能放大。

	int a = 0;
	int& b = a;
1
2
	const int a = 0;
	const int& b = a;
1
2
这俩种情况是被允许的，这俩种情况是权限的平移。

	int a = 0;
	const int& b = a;
1
2
这种情况也是被允许的，这种情况被称为权限的缩小。

	int a = 0;
	const int& b = a;
	a++;
1
2
3
这种情况也是被允许的，原因是编译器仅缩小了a和b所在地址的引用b的权限，而并没有缩小a的权限所有a++是被允许的，而b++是不被允许的。

	const int& x = 10;
1
同时，给变量取别名也是被允许的。

常引用的俩个例子
	double d = 1.11;
	int i = d;
1
2
我们都知道double在转变为int时，会进行类型转换，由于double是8个字节，int是4个字节，double变成int会进行截断，而截断的过程会建立一个新的临时变量，临时变量具有常性，即临时变量是不可修改的值。

	double d = 1.11;
	int& i = d;
1
2
所有这段代码是错误的，这里double变成临时变量权限缩小，而int&会将权限放大，引用的过程中权限是不可以放大的。

	double d = 1.11;
	const int& i = d;
1
2
这段代码是正确的，这里进行了权限的平移。

int Fun()
{
	static int x = 0;
	return x;
}
int main(void)
{
	int& ret1 = Fun();
	const int& ret2 = Fun();
}
1
2
3
4
5
6
7
8
9
10
第二个例子是关于函数在释放前会建立一个临时变量给返回值提供位置。此时这个临时变量也是具有常性，是不可以修改的，即ret1是错误的代码，而ret2是正确的代码。

引用与指针的区别
引用与指针在语法层面是不同的，引用不开空间，引用是对变量取别名，而指针不同，指针开空间，指针是存储变量的地址。

	int x = 10;
	int* y = &x;

	int a = 20;
	int& b = a;
1
2
3
4
5

观察这段代码，可以发现从底层汇编指令实现的角度来看，引用是类似指针的方式实现的。

指针与引用的不同点：
1.引用概念上定义一个变量的别名，而指针存储一个变量的地址
2.引用在定义时必须初始化，而指针没有要求
3.引用在初始化时引用一个实体后，就不可以引用其他实体，而指针可以在任何时候指向任何一个同类型的实体
4.没有NULL引用，但有NULL指针
5.引用和指针在sizeof中含义不同，引用结果为引用类型的大小，而指针始终是地址空间所占字节个数（32位平台下占4个字节）
6.引用自加即引用的实体增加1，指针自加即指针向后偏移一个指针类型的位置
7.有多级指针，但没有多级引用
8.访问实体的方式不同，指针需要解引用，引用编译器会自己处理
9.引用比指针使用起来相对安全
内联函数
在了解内联函数之前，应该对C语言中的宏的定义有一定了解。

//宏定义
#define ADD(x,y) ((x) + (y)) * 10
//注意规范，宏定义是完整的替换

int main(void)
{
	for (int i = 0; i < 100; i++)
	{
		cout << ADD(i, i + 1) << endl;
	}
	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
在C语言中，使用宏定义来解决函数建立过多且函数内容较少的问题。但是宏在使用过程中也存在着也许优点与缺点：
宏函数的优点：不需要建立栈帧，提高调用效率，增强代码的复用性
宏函数的缺点：复杂、容易出错；可读性差；不能调试。

在C++中，会使用内联函数来代替部分宏函数。
以inline修饰的函数被称为内联函数，编译时C++编译器会根据情况在调用内联函数的地方进行展开，没有函数调用建立栈帧的开销，内联函数提高程序运行的速度。

//内联函数
inline int Add(int x, int y)
{
	return (x + y) * 10;
}

int main(void)
{
	for (int i = 0; i < 100; i++)
	{
		cout << Add(i, i + 1) << endl;
	}
	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
由此可知，宏函数与内联函数是很相似的，但C++对宏函数进行了优化，宏函数与内联函数适用于短小，需要频繁调用的函数。

但是并不是所有的函数都可以使用内联函数，否则就会导致可执行程序变大。

这里假设Func不是内联函数（不被inline修饰），每次执行Func函数时都会跳转到Func去执行，如果存在n个位置调用Func函数，则合计会有m+n条指令；

这里假设Func是内联函数（被inline修饰），相当于每次执行Func函数都会对Func进行展开，如果存在n个位置调用Func函数，则合计会有m*n条指令。

若调用Func函数过多，则会导致可执行程序变大。

编译器在识别被inline修饰的内联函数时，内联函数对编译器只是一个建议，最终时候成为内联函数，编译器会自己决定，在这些情况下，编译器会自动否决内联：1.比较长的函数；2.递归函数。


默认在debug版本下，inline不会起作用，否则无法支持调试。在debug版本下，需要对编译器进行设置：





这里可以发现，在调用代码较少时，此时内联函数起效果；

inline int Add(int x, int y)
{
	for (int i = 0; i < 100; i++)
	{
		x *= 2;
	}
	for (int i = 0; i < 100; i++)
	{
		x /= 2;
	}
	for (int i = 0; i < 100; i++)
	{
		y *= 2;
	}
	for (int i = 0; i < 100; i++)
	{
		y /= 2;
	}

	return x + y;
}

int main(void)
{
	int ret = Add(1, 2);
	return 0;
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27

此时，函数内容过多，内联函数被编译器否决；

inline int Add(int x,int y)
{
	if (x > 5 && y > 5)
		return x + y;
	return Add(x + 1, y + 1);
}
int main(void)
{
	int ret = Add(1, 2);
	cout << ret << endl;
	return 0;
}
1
2
3
4
5
6
7
8
9
10
11
12

此时，函数递归，内联函数被编译器否决；

//Func.h文件
inline int Add(int x, int y);
//Func.cpp文件
#include"Func.h"
inline int Add(int x, int y)
{
	return (x + y) * 10;
}
//test.cpp文件
#include<iostream>
#include"Func.h"
using namespace std;
int main(void)
{
	int ret = Add(1, 2);
	cout << ret << endl;
	return 0;
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18


如果内联函数没有被编译器否决，那么内联函数就会在编译期间会被展开，建议内联函数声明与定义不分离，直接写在头文件中。
原因是，内联函数不会被call，所有内联函数就不会进入符号表进行链接，如果声明与定义分离，会导致链接错误，内联函数被展开后寻找不到函数地址，链接就会找不到。
————————————————

                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。

原文链接：https://blog.csdn.net/dab112/article/details/139417986
')