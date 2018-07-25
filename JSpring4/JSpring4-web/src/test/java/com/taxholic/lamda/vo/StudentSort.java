package com.taxholic.lamda.vo;

public class StudentSort implements Comparable<StudentSort>{
	
	private String name;
    private int score;
 
    public StudentSort(String name, int score) {
        this.name = name;
        this.score = score;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public int getScore() {
        return score;
    }
 
    public void setScore(int score) {
        this.score = score;
    }

	@Override
	public int compareTo(StudentSort o) {
		return Integer.compare(score, o.score);
	}
 
}
