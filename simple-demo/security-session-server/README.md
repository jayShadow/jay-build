###集群 Session(单机 Session 下的设置在集群下一样有效)
* 保存在数据库
* 保存在 Redis 中
* 利用 Servlet 容器来进行 Session 同步
* 或者使用 Nginx 使用用户 ip 进行哈希处理，在负载均衡时，让用户每次访问同一个主机避免多 Session 问题



### issue
-[ ] spring session  servlet session区别