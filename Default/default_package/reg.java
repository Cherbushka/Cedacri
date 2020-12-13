package default_package;

import java.util.regex.*;


public class reg {
	
	public static void main(String args[])
	{
		String text = "Behaviour we improving at something to. Evil true high lady roof men had open. "
				+ "To projection considered it precaution an melancholy or. Wound young you thing worse along being ham. "
				+ "Dissimilar of favourable (Behaviour) solicitude if sympathize middletons at. "
				+ "Forfeited up if disposing perfectly in an eagerness perceived necessary. "
				+ "Belonging sir curiosity discovery extremity yet forfeited prevailed own off. "
				+ "Travelling by introduced of mr terminated. Knew as miss my high hope quit. "
				+ "In curiosity shameless  dependent knowledge up. 19.99 .";
		
		//This pattern prints each character of the text to the console.
		String pattern = ".";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(text);
		output(m);
		
		System.out.println();
		
		//This pattern prints only numbers of the text to the console.
		pattern = "\\d+";
		r = Pattern.compile(pattern);
		m = r.matcher(text);
		output(m);
		
		System.out.println();
		
		//This pattern prints only "(1999)" from the end of the text to the console.
		pattern = "$(1999)";
		r = Pattern.compile(pattern);
		m = r.matcher(text);
		output(m);
		
		System.out.println();
		
		//This pattern prints only "Behaviour" from the begin of the text to the console.
		pattern = "^Behaviour";
		r = Pattern.compile(pattern);
		m = r.matcher(text);
		output(m);
		
		System.out.println();
		
		//This pattern displays only single letters "a"
		pattern = "[a]";
		r = Pattern.compile(pattern);
		m = r.matcher(text);
		output(m);
		
		System.out.println();
		
		//This pattern displays only the full line of the text "In curiosity shameless  dependent knowledge up"
		pattern = "\\bIn curiosity shameless  dependent knowledge up\\b";
		r = Pattern.compile(pattern);
		m = r.matcher(text);
		output(m);
		
		System.out.println();
		
		//This pattern displays all characters that do not form words and numbers.
		pattern = "\\W";
		r = Pattern.compile(pattern);
		m = r.matcher(text);
		output(m);
		
		System.out.println();
		
		//This pattern displays all characters that form words and numbers.
		pattern = "\\w";
		r = Pattern.compile(pattern);
		m = r.matcher(text);
		output(m);
		
		
		System.out.println();
		
		//This pattern displays all spaces in the text.
		pattern = "\\s";
		r = Pattern.compile(pattern);
		m = r.matcher(text);
		output(m);
		
		System.out.println();
		
		//This pattern displays all "number decimal point number"
		pattern = "\\A(\\d)\\.(\\d)\\z";
		r = Pattern.compile(pattern);
		m = r.matcher(text);
		output(m);
		

	}
	
	public static void output(Matcher m) 
	{
		while(m.find())
		{
			System.out.println(m.group());
		}
	}
}
