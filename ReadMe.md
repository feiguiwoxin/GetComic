    在使用工厂模式重构代码后，软件已经基本具备可扩展性，如果需要增加一个新的下载漫画的网址，需要做如下的事情：
    1）实现ComicCore/Comic接口中的三个方法。可以参考已有实现DlManhuagui;
    2）在UI/SelectWebSite中依照注释样例增加一个网址名;
    3）在ComicCore/comicfactory类中依照注释样例增加一个工厂对象。
    当然，你也可以使用LOG.log(String)来给自己的类添加打印信息以方便调试，这个打印信息会自动保存到当前程序下的Comiclog.txt文本中。
