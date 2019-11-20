### 问题

1. coroutine 生命周期

    ~~可利用 kotlin-coroutines-android。~~
    
    现使用 viewModelScope

    1. 何时 await，嵌套子作用域时是否使用？suspend 与 await 什么关系？
    
        await 与 async 配合使用，async 创建协程时返回的是 Job 子类 Deferred，可利用 await 获取返回值。
        
        suspend 是 kotlin 关键字，生命函数可挂起当前协程，await 也是一个 suspend 函数。
    
    2. 如何嵌套子作用域？
        
        ~~需要管理 Job 即协程执行逻辑的生命周期时使用，简单而言需要 cancel 时使用。~~
        
        协程创建与启动需要上下文、启动模式和协程体。  
        每个协程构建器（如  launch启动和async异步）都是CoroutineScope 的扩展，  
        并且继承了  corutineContext 以自动传播上下文元素和取消。
        
        协程作用域包含着协程上下文，主要用来启动协程和追踪子协程。  
        协程作用域主要有 GlobalScope、coroutineScope { } 以及 supervisorScope { }。
        
        挂起函数只被允许在协程或另一个挂起函数中调用。
        
        CoroutineScope(Dispatchers.IO).async { } 调用 async 方法来创建协程。  
        注意，挂起函数中不该引入独立的作用域。
        
        coroutineScope { async { } } 调用 coroutineScope 方法来创建协程作用域，该方法要声明可挂起。  
        结构化并发，嵌套使用可形成嵌套子作用域。

2. livedata 作用是什么
    
    实时数据是数据持有类，是有生命周期的被观察者

3. room 判断是否缓存，repository 处理数据源

    async 一边加载网络，一边展示数据库，用 livedata 更新

4. 列表如何加载，传入那些参数？

    架构示例不涉及列表的真实加载，适配器也仅传入 ViewModel。

5. 本地数据与网络数据如何配合？

    架构示例中只是简单逻辑，其获取查询操作：先网络、然后若网络失败则本地。
    
    指南中：设置间隔时长，超过间隔才网络。

6. 为何使用缓存，简单缓存如何实现，如何配合使得数据源统一，并确保缓存生命周期？

    减少请求开销。内存级缓存实现需要利用数据结构，磁盘级即本地数据库或本地文件。
    
    使用方式：查询操作，先走缓存，未过期则本地获取数据，  
    已过期则清空本地与缓存、网络获取数据然后存入本地并缓存；  
    增改，先走缓存，再结构化并发；删除操作，先结构化并发，后走缓存。
    
    ViewModel 持有 Repository，缓存也跟随 ViewModel 生存。

### 方案

1. 统一数据层。Repository 实现统一接口，方便使用工厂；Source 也实现接口，方便实现单一数据源。

    不需要：无法定制传参；有依赖注入框架实现一切，无需再做统一。

2. 细化绑定层。添加 Domain 层的 UseCase，按功能或数据进行拆分。
    
    页面决定 ViewModel，ViewModel 负责绑定数据。

3. 取消协程库。无需使用协程库提供协程生命周期支持，现在 lifecycle 的 2.1.0 版本提供了 viewModelScope。

4. 加入时间戳。给需要的表统一加入时间戳字段。

### 参考

https://www.jianshu.com/p/f179d8352431

https://juejin.im/post/5a040585f265da43346f5d57

https://juejin.im/post/5c78c6cae51d453ecb6562a8