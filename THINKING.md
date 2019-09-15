1. coroutine 生命周期 --> 利用 kotlin-coroutines-android

    1. 何时 await？嵌套子作用域时是否使用？suspend 可以替换 await？ -->
    await 和 async 配套使用。await 也是 suspend 函数，可以挂起当前协程实现嵌套子作用域。
    
    2. 何时嵌套子作用域？ --> 需要管理 Job 即协程执行逻辑的生命周期时使用。

2. livedata --> 有生命周期的观察者

3. room 判断是否缓存，repository 处理数据源 --> async 一边加载网络，一边展示数据库，用 livedata 更新

4. koin 是如何契合 coroutine 的

https://www.jianshu.com/p/f179d8352431

https://juejin.im/post/5a040585f265da43346f5d57