
# 添加用户 root、user1-100
# 默认密码 :123456
insert into user (username)
values
    ('root'),
    ('user1'),('user2'),('user3'),('user4'),('user5'),('user6'),('user7'),('user8'),('user9'),('user10'),('user11'),('user12'),('user13'),('user14'),('user15'),('user16'),('user17'),('user18'),('user19');

update user set img='/static/user/logo.svg' where username!='';


# 28 个标签
insert into tag (title) values
    ('C/C++'),('Java'),('Python'),('Web'),('Vue'),('SpringBoot'),('go'),('kotlin'),('android'),('SQL'),('AI'),('C#'),('JavaScript'),('html'),('css'),('nodeJS'),('PHP'),('Linux'),('docker'),('IOS'),('windows'),('Unity'),('UE5'),('TypeScript'),('pinia'),('git'),('Qt'),('redis');

# 随机生成文章标签
insert into link_article_tag values
    (17,32),(6,68),(17,41),(44,97),(30,9),(88,42),(34,98),(91,47),(69,18),(2,104),(61,11),(33,92),(98,66),(0,46),(13,69),(24,61),(0,5),(42,60),(59,94),(42,28),
    (38,72),(57,1),(67,52),(7,101),(16,82),(76,23),(8,57),(21,25),(29,69),(96,96),(63,20),(41,19),(38,48),(17,6),(49,103),(77,53),(9,45),(19,1),(42,74),(3,13),
    (60,54),(100,0),(60,29),(99,99),(1,28),(86,32),(87,103),(31,40),(24,26),(60,78),(94,96),(64,67),(23,76),(24,96),(63,24),(19,63),(52,38),(59,7),(40,77),(3,83),
    (17,58),(70,24),(67,47),(70,57),(69,55),(36,82),(77,87),(4,88),(90,5),(65,38),(18,34),(41,53),(10,103),(80,38),(11,8),(100,16),(26,2),(13,44),(94,62),(98,41),
    (6,12),(50,25),(50,99),(22,57),(6,102),(21,42),(100,58),(12,39),(82,43),(87,85),(96,34),(28,21),(55,56),(35,65),(36,44),(81,18),(94,22),(3,93),(62,74),(33,65);

# 随机生成关注
insert into link_user_follow values
    (2,3),(2,4)