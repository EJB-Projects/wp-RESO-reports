package ru.reso.wp.srv.db.models;

import java.util.ArrayList;

public class StmtParamList {

    private ArrayList paramList;

    public StmtParamList() {
        this.paramList = new ArrayList<StmtParam>();
        // Тестовые значения кинем пока
        this.paramList.add(new StmtParam(10, 666));
    }

    public ArrayList getParamList() {
        return paramList;
    }

    public void setParamList(ArrayList paramList) {
        this.paramList = paramList;
    }


    public void add(StmtParam stmtParam) {

        this.paramList.add(stmtParam);

    }
}
