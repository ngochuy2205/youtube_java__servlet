package com.poly.controller.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.poly.dao.impl.UserDAO;

public class main {
	public static void main(String[] args) {
		List<UserDAO> UD = new ArrayList<>();
		 for (UserDAO userDAO : UD) {
			System.out.println(userDAO);
		}
	}
}
