package ac.cn.saya.laboratory;

import ac.cn.saya.laboratory.tools.HtmlUtils;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.math.BigDecimal;

/**
 * @Title: MarkDownTest
 * @ProjectName laboratory
 * @Description: TODO
 * @Author saya
 * @Date: 2020/8/23 19:55
 * @Description:
 */

public class MarkDownTest {

    public static String convert(String md) {
        MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(md);
        String html = renderer.render(document);
        return html;
    }

    public static void main(String[] args) {
        BigDecimal income = new BigDecimal("52.454");
        BigDecimal account = new BigDecimal(100);
        BigDecimal zero = BigDecimal.ZERO;
        if (0 == zero.compareTo(account)){
            System.out.println("被除数为0");
            return;
        }
        BigDecimal percentage = income.divide(account,4, BigDecimal.ROUND_HALF_UP);
        String str = "632784564384839637t537428957654678987667898766235_电表图片1";
        System.out.println(str.replaceAll("632784564384839637t537428957654678987667898766235_电表图片",""));;

        return;
    }

    public static void main1(String[] args) {
        String value1 = "<h1 style=\"text-align:center;\"><span style=\"line-height: 1.5; font-size: 20px;\">刘能凯个人网站将于2016年5月正式上线，为确保上线能够做到有序合理，现将计划公布如下：</span><span style=\"line-height:1.5;\">&nbsp;</span></h1><h4 style=\"text-align:left;\"><span style=\"line-height:1.5;\">&nbsp; &nbsp; &nbsp;1、时间安排，根据计划进度，所有网站的代码务必于4月5日全部完毕（包括总体的框架，以及美工设计）。<br/></span><span style=\"line-height:1.5;\">&nbsp; &nbsp; &nbsp;2、网站的虚拟空间，在网站做好后，将租用一个虚拟服务器，并做好域名的备案工作。<br/></span><span style=\"line-height:1.5;\">&nbsp; &nbsp; &nbsp;3，在以上所有的工作完成后，将网站的所有文件全部上传到虚拟服务器上。</span></h4><p style=\"text-align: right;\"><span style=\"line-height:1.5;\">刘能凯</span></p><p style=\"text-align: right;\"><span style=\"line-height:1.5;\">2016年4月16日</span></p>";
        String value2 = "### Zookeper\n" +
                "\n" +
                "#### 1.什么是Zookeper？\n" +
                "\n" +
                "Zookeeper是一个开源的分布式的，为分布式应用提供协调服务的Apache项目\n" +
                "\n" +
                "#### 2.应用场景&功能？\n" +
                "\n" +
                "结合自己实际工作中，Zookeeper主要是用于dubbo框架的注册中心。Dubbo框架的提供者会向Zookeeper下的provider目录注册自己的URL。消费者订阅提供者的注册URL\n" +
                "\n" +
                "- 统一命名服务\n" +
                "- 统一配置管理\n" +
                "- 统一集群管理\n" +
                "- 软负载均衡\n" +
                "\n" +
                "#### 3.谈谈你对Zookeper的理解 ？\n" +
                "\n" +
                "Zookeeper 作为一个分布式的服务框架，主要用来解决分布式集群中应用系统的一致性问题。ZooKeeper 提供的服务包括：统一命名服务、统一配置管理、统一集群管理、软负载均衡。\n" +
                "ZooKeeper 提供基于类似于 Linux 文件系统的目录节点树方式的数据存储。Zookeeper 并不是用来专门存储数据的，它的作用主要是用来维护和监控你存储的数据的状态变化，通过监控这些数据状态的变化，从而可以达到基于数据的集群管理\n" +
                "ZooKeeper 节点的数据上限是 1MB。\n" +
                "我们可以认为 **Zookeeper=文件系统+通知机制**\n" +
                "\n" +
                "#### 4.请说明 ZooKeeper 的通知机制？\n" +
                "\n" +
                "客户端会对某个znode建立一个watcher事件，当该znode发生变化时，这些客户端会收到zookeeper的通知，然后客户端可以根据znode变化来做出业务上的改变。\n" +
                "\n" +
                "#### 5.ZooKeeper 的监听原理是什么？\n" +
                "\n" +
                "在应用程序中，mian()方法首先会创建 zkClient，创建 zkClient 的同时就会产生两个进程，即Listener 进程（监听进程）和connect 进程（网络连接/传输进程），当zkClient 调用getChildren()等方法注册监视器时，connect 进程向 ZooKeeper 注册监听器，注册后的监听器位于ZooKeeper 的监听器列表中，监听器列表中记录了 zkClient 的 IP，端口号以及要监控的路径，一旦目标文件发生变化，ZooKeeper 就会把这条消息发送给对应的zkClient 的Listener()进程，Listener 进程接收到后，就会执行 process()方法，在 process()方法中针对发生的事件进行处理。\n" +
                "\n" +
                "#### 6.ZooKeeper 的部署方式有哪几种？集群中的角色有哪些？集群最少需要几台机器？\n" +
                "\n" +
                "ZooKeeper 的部署方式有单机模式和集群模式，集群中的角色有 Leader 和 Follower，集群最少 3（2N+1）台，根据选举算法，应保证奇数。\n" +
                "\n" +
                "#### 7.ZooKeeper 节点类型？\n" +
                "\n" +
                "* 短暂（ephemeral）：客户端和服务器端断开连接后，创建的节点自己删除\n" +
                "* 持久（persistent）：客户端和服务器端断开连接后，创建的节点不删除\n" +
                "\n" +
                "#### 8.ZooKeeper 对节点的 watch 监听是永久的吗？为什么？\n" +
                "\n" +
                "不是。\n" +
                "官方声明：一个 Watch 事件是一个一次性的触发器，当被设置了 Watch 的数据发生了改变的时候，则服务器将这个改变发送给设置了 Watch 的客户端，以便通知它们。为什么不是永久的，举个例子，如果服务端变动频繁，而监听的客户端很多情况下，每次变动都要通知到所有的客户端，这太消耗性能了。一般是客户端执行 getData(“/节点 A”,true)，如果节点 A 发生了变更或删除，客户端会得到它的 watch 事件，但是在之后节点 A 又发生了变更，而客户端又没有设置 watch 事件，就不再给客户端发送。\n" +
                "\n" +
                "#### 9.ZooKeeper 中使用 watch 的注意事项有哪些？\n" +
                "\n" +
                "- Watches 通知是一次性的，必须重复注册\n" +
                "- 对某个节点注册了 watch，但是节点被删除了，那么注册在这个节点上的 watches 都会被移除\n" +
                "- 同一个 zk 客户端对某一个节点注册相同的 watch，只会收到一次通知\n" +
                "\n" +
                "#### 10.一个客户端修改了某个节点的数据，其他客户端能够马上获取到这个最新数据吗？\n" +
                "\n" +
                "ZooKeeper 不能确保任何客户端能够获取（即 Read Request）到一样的数据，除非客户端自己要求，方法是客户端在获取数据之前调用 sync\n" +
                "\n" +
                "#### 11.能否收到每次节点变化的通知？\n" +
                "\n" +
                "如果节点数据的更新频率很高的话，不能。原因在于：当一次数据修改，通知客户端，客户端再次注册 watch，在这个过程中，可能数据已经发生了许多次数据修改，因此，千万不要做这样的测试：”数据被修改了 n 次，一定会收到 n 次通知”来测试 server 是否正常工作。\n" +
                "\n" +
                "#### 12.能否为临时节点创建子节点？\n" +
                "\n" +
                "ZooKeeper 中不能为临时节点创建子节点，如果需要创建子节点，应该将要创建子节点的节点创建为永久性节点。\n" +
                "\n" +
                "#### 13.在 getChildren(String path, boolean watch)是注册了对节点子节点的变化，那么子节点的子节点变化能通知吗？\n" +
                "\n" +
                "不能通知。\n" +
                "\n" +
                "#### 14.Zookeeper工作原理\n" +
                "\n" +
                "Zookeeper 的核心是Zab协议，这个机制保证了各个Server之间的同步。Zab协议有两种模式，它们分别是恢复模式（选主）和广播模式（同步）。当服务启动或者在领导者崩溃后，Zab就进入了恢复模式，当领导者被选举出来，且大多数Server完成了和leader的状态同步以后，恢复模式就结束了。状态同步保证了leader和Server具有相同的系统状态。此时系统的不可用的，所以ZooKeeper采用的是CP原则.\n" +
                "\n" +
                "#### 15.Zookeeper 下 Server工作状态\n" +
                "\n" +
                "* looking：当前Server不知道leader是谁，正在搜寻\n" +
                "* leading：当前Server即为选举出来的leader\n" +
                "* following：leader已经选举出来，当前Server与之同步\n" +
                "* Observer：观察者状态。表明当前服务器角色是Observer。\n" +
                "\n" +
                "#### 16.zookeeper是如何保证事务的顺序一致性的？\n" +
                "\n" +
                "zookeeper采用了全局递增的事务Id来标识，所有的proposal（提议）都在被提出的时候加上了zxid，zxid实际上是一个64位的数字，高32位是epoch（时期; 纪元; 世; 新时代）用来标识leader周期，如果有新的leader产生出来，epoch会自增，低32位用来递增计数。当新产生proposal的时候，会依据数据库的两阶段过程，首先会向其他的server发出事务执行请求，如果超过半数的机器都能执行并且能够成功，那么就会开始执行。\n" +
                "\n" +
                "#### 17.Zookeeper同步流程\n" +
                "\n" +
                "1. Leader等待server连接；\n" +
                "2. Follower连接leader，将最大的zxid发送给leader;\n" +
                "3. Leader根据follower的zxid确定同步点;\n" +
                "4. 完成同步后通知follower 已经成为uptodate状态;\n" +
                "5. Follower收到uptodate消息后，又可以重新接受client的请求进行服务了\n" +
                "\n" +
                "#### 18.机器中为什么会有leader\n" +
                "\n" +
                "在分布式环境中，有些业务逻辑只需要集群中的某一台机器进行执行，其他的机器可以共享这个结果，这样可以大大减少重复计算，提高性能，于是就需要进行leader选举。\n" +
                "\n" +
                "#### 19.ZooKeeper文件系统\n" +
                "\n" +
                "Zookeeper提供一个多层级的节点命名空间（节点称为znode）。\n" +
                "与文件系统不同的是，这些节点都可以设置关联的数据，而文件系统中只有文件节点可以存放数据而目录节点不行。\n" +
                "Zookeeper为了保证高吞吐和低延迟，在内存中维护了这个树状的目录结构，这种特性使得Zookeeper不能用于存放大量的数据，每个节点的存放数据上限为1M。\n" +
                "\n" +
                "#### 20.简述Zookeper的选举机制";
        String newStr = convert(value2);
        System.out.println(HtmlUtils.Html2Text(newStr));
    }


}
