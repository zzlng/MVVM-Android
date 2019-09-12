1. coroutine 生命周期 --> 利用 kotlin-coroutines-android

    1. 何时 await --> 嵌套子作用域？但 suspend 可以替换 await？

2. Livedata --> 有生命周期的观察者

3. room 判断是否缓存，repository 处理数据源 --> 一边加载网络，一边展示数据库，用 Livedata 更新

4. koin 是如何契合 coroutine 的