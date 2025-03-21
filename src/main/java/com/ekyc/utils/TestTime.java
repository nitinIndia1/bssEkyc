package com.ekyc.utils;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
public class TestTime {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt =  new Date();
		System.out.println(sdf.format(dt));
	}

}
