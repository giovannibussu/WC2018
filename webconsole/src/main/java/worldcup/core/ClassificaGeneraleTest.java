package worldcup.core;

public class ClassificaGeneraleTest {


	public static void main(String[] args) throws Exception {
		ClassificaGenerale c = new ClassificaGenerale();
		System.out.println("NULL: " +c.getClassificaGenerale(null).size());
		System.out.println("Link: " +c.getClassificaGenerale("Link").size());
	}
}
