package org.shtrudell.common.net;

public enum MessageMethod {
    AUTHENTICATE, REGISTER, GET_ALL_FUNDS, GET_ALL_DOCNAMES, GET_ALL_AUTHORS,
    GET_ALL_RECEIPTS_OF_FUNDS, ADD_AUTHOR, ADD_DOCNAME, ADD_RECEIPT,DISCONNECT,
    UPDATE_USER, GET_ALL_ROLES, GET_ALL_USERS, ADD_ROLE, UPDATE_ROLE, ADD_SIMPLE_FUND,
    UPDATE_SIMPLE_FUND, GET_ALL_SIMPLE_FUNDS;
}
