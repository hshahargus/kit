package com.argusoft.kite.util;

/**
 *
 * @author Alpesh
 */
//SELECT distinct type,type_desc
//FROM sys.objects
//
//FN	SQL_SCALAR_FUNCTION
//SO	SEQUENCE_OBJECT
//UQ	UNIQUE_CONSTRAINT
//SQ	SERVICE_QUEUE
//F 	FOREIGN_KEY_CONSTRAINT
//U 	USER_TABLE
//D 	DEFAULT_CONSTRAINT
//PK	PRIMARY_KEY_CONSTRAINT
//S 	SYSTEM_TABLE
//IT	INTERNAL_TABLE
//P 	SQL_STORED_PROCEDURE
public enum DatabaseObjects {

    SQL_STORED_PROCEDURE("P"),
    SQL_SCALAR_FUNCTION("FN"),
    USER_TABLE("U"),
    SYSTEM_TABLE("S"),
    INTERNAL_TABLE("IT"),
    SEQUENCE_OBJECT("SO"),
    SERVICE_QUEUE("SQ"),
    UNIQUE_CONSTRAINT("UQ"),
    FOREIGN_KEY_CONSTRAINT("F"),
    DEFAULT_CONSTRAINT("D"),
    PRIMARY_KEY_CONSTRAINT("PK");

    private DatabaseObjects(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DatabaseObjects{" + "value=" + value + '}';
    }

}
