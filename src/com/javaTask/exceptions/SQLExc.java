package com.javaTask.exceptions;

import java.sql.SQLException;

public class SQLExc extends SQLException{

	public SQLExc(String reason) {
		super(reason);
	}

}
