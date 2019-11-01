# cache-spring-boot-starter

simple local cache

// 在查询操作上增加cache的name以及key参数格式 - #[参数名]，会根据 '名称_入参' 进行缓存
// value为true表示加缓存
@MapCacheAnnotation(name = "cachename", key = "#params", value = true)
public String select(String params) {
    
}

// 在修改操作上增加缓存
// name用来标志是哪个缓存
// value用来表示删除缓存，这样每次修改以后，缓存会及时更新
@MapCacheAnnotation(name = "cachename", value = false)
public void update(String params) {

}

maven依赖：
<dependency>
    <groupId>com.github.houbbbbb</groupId>
    <artifactId>cache-spring-boot-starter</artifactId>
    <version>0.0.1</version>
</dependency>
