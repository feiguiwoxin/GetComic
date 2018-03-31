在使用工厂模式重构代码后，软件已经基本具备可扩展性，如果需要增加一个新的下载漫画的网址，需要做如下的事情。\n
1）实现ComicCore包中Comic接口中的三个方法,分别返回书名，章节名和漫画图片地址。可以参考已有实现DlManhuagui类。\n
2）在UI包中SelectWebSite中依照注释样例增加一个网址。\n
3）在ComicCore包中comicfactory类中依照注释样例增加一个返回对象。\n
