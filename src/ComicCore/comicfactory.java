package ComicCore;

import Start.LOG;

public class comicfactory {
	@SuppressWarnings("unchecked")
	public Comic create(String package_addr)
	{
		Comic getcomic = null;
		Class<? extends Comic> cls = null;
		try {
			cls = (Class<? extends Comic>) Class.forName(package_addr);
			getcomic = (Comic)cls.newInstance();
		} catch (ClassNotFoundException e) {
			LOG.log("comicfactory:找不到指定的类:" + package_addr);
		} catch (InstantiationException e) {
			LOG.log("comicfactory:没有默认构造器:" + package_addr);
		} catch (IllegalAccessException e) {
			LOG.log("comicfactory:没有默认构造器:" + package_addr);
		}
		return getcomic;
	}
}
