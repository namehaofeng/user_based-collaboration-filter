# user_based-collaboration-filter
1.该代码是作者用Java编写的基于用户的协同过滤推荐算法
2.所涉及的用户相似度measure是通过计算用户的余弦相似得到
2.目的是通过学习已有数据集，向目标用户推送其感兴趣的电影
3.电影数据集网址https://grouplens.org/datasets/movielens/

算法输入：a:一个用户所看过的电影及其评分，b:电影数据集
算法输出：c:与该用户爱好相似的前k(k值可以自己设置)位用户（根据相似度由高到低排序），d:该用户可能感兴趣的电影（根据推荐指数由高到低排序）
