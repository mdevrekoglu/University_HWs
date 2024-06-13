package game;

public class StatisticParticipant 
{	
	//We are using this class to storage Statistics it is being used in Statistic class
	private String name;
	private String city;
	private int correct;
	private int age;
		
	
	public StatisticParticipant(String name, String city, int correct, int age) 
	{
		super();
		this.name = name;
		this.city = city;
		this.correct = correct;
		this.age = age;
	}
		
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getCity() 
	{
		return city;
	}
	public void setCity(String city) 
	{
		this.city = city;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) 
	{
		this.correct = correct;
	}
	public int getAge() 
	{
		return age;
	}
	public void setAge(int age) 
	{
		this.age = age;
	}
	
}
