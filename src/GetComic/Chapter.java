package GetComic;

public class Chapter{
	private String Title = null;
	private String Html = null;
	
	public Chapter(String Title, String Html)
	{
		this.Title = Title;
		this.Html = Html;
	}

	public String getTitle() {
		return Title;
	}

	public String getHtml() {
		return Html;
	}
}
